package controllers

import (
	orm "github.com/beego/beego/v2/client/orm"
	"github.com/beego/beego/v2/core/logs"
	beego "github.com/beego/beego/v2/server/web"
	"github.com/gomodule/redigo/redis"
	"math/rand"
	"myblog/models"
	"myblog/utils"
	"strconv"
	"time"
)

// Operations about Users
type UserController struct {
	beego.Controller
}


func (u *UserController) Login() {
	resp := make(map[string]interface{})
	u.Data["json"] = resp
	defer u.ServeJSON()
	username := u.GetString("username")
	password := u.GetString("password")
	if len(password) < 6 {
		resp["code"] = 0
		resp["msg"] = "密码长度不能小于6"
		return
	}
	if username == "" {
		resp["code"] = 0
		resp["msg"] = "用户名不能为空"
		return
	}
	logs.Info("用户名是 ", username, "密码是 ", password)
	logs.Info("正在登录")
	o := orm.NewOrm()
	qs := o.QueryTable(new(models.User)).Filter("username", username)
	exist := qs.Exist()
	user := models.User{}
	if exist {
		logs.Info("用户存在")
		err := qs.One(&user)
		if err != nil {
			logs.Info("用户加载失败")
			return
		}
		logs.Info("正在登陆的用户是：", user)
		// 当前如果用户存在,就继续判断用户是否通过验证,即验证status字段是否为1
		if user.Status == 1 {
			// 当用户通过验证时,判断密码是否正确
			if user.Password == utils.GetMd5(password) {
				// 密码输入正确,就将用户信息写入到cookie当中保存起来
				logs.Info("登录成功")
				// 创建token  只含有user.Id 和 他的名字
				tokenuser := utils.User{user.Id, user.Username}
				tokenString := utils.GenerateToken(&tokenuser, 0) //使用默认的时间
				logs.Info("tokenString is ", tokenString)
				u.Ctx.SetCookie("loginuser", tokenString)
				logs.Info("Cookie 设置成功")
				resp["code"] = 1
				resp["msg"] = "登陆成功"
				resp["userid"] = user.Id
				resp["username"] = user.Username
			} else {
				resp["code"] = 0
				resp["msg"] = "密码错误"
			}
			// status不等于1即表示用户未激活
		} else {
			resp["code"] = 0
			resp["msg"] = "账户未激活"
		}
		// 账号不存在
	} else {
		resp["code"] = 0
		resp["msg"] = "账号不存在"
	}
	return
}

func (r *UserController) Register() {
	logs.Info("开始验证")

	resp := make(map[string]interface{})
	r.Data["json"] = resp
	defer r.ServeJSON()

	username := r.GetString("username")
	password := r.GetString("password")
	repassword := r.GetString("repassword")
	email := r.GetString("email")
	if username == "" || password == "" || email == "" {
		resp["code"] = 0
		resp["msg"] = "用户名、密码、邮箱不能为空"
		logs.Info("有内容为空了")
		return
	}

	if password != repassword {
		resp["code"] = 0
		resp["msg"] = "两次输入密码不一致"
		return
	}
	logs.Info("开始注册")
	logs.Info("用户名是 ", username, "密码是 ", password)
	//验证的可以交给前端
	o := orm.NewOrm()
	exist1 := o.QueryTable(new(models.User)).Filter("Username", username).Exist()

	exist2 := o.QueryTable(new(models.User)).Filter("Email", email).Exist()
	conut, _ := o.QueryTable(new(models.User)).Filter("Username", username).Count()
	logs.Info("名字人数： ", conut)
	if exist2 {  //邮箱是否已经存在
		user := models.User{}
		o.QueryTable(new(models.User)).Filter("Username", username).One(&user)
		if user.Status == 1 {
			resp["code"] = 0
			resp["msg"] = "邮箱已被使用"
			return
		} else {
			resp["code"] = 0
			resp["msg"] = "邮箱已经注册未被激活，已重发验证码"
			rand.Seed(time.Now().UnixNano())
			code := rand.Intn(8999) + 1000
			body := "验证码是：" + strconv.Itoa(code)
			logs.Info("验证码是 ：", code)

			// 发送给用户注册邮件
			subject := "博客注册验证"
			err := utils.SendMail(email, subject, body)
			if err != nil {
				logs.Info(err)
				r.Data["json"] = map[string]interface{}{"code": 0, "msg": "验证码发送失败"}
				return
			} else {
				logs.Info("发送邮件成功")
				models.SetRedisKeyValue(email, strconv.Itoa(code))
				models.SetExpireTime(email, 600) //过期时间 验证码
				return
			}
		}
	} else if exist1 { //用户名是否已经被注册
		resp["code"] = 0
		resp["msg"] = "用户名已被注册"
		return
	} else {
		newuser := models.User{
			Username: username,
			Email:    email,
			Password: utils.GetMd5(password),
			Status:   0,
		}
		o.Insert(&newuser) //传递引用能够更快
		logs.Info("插入新用户成功")
		subject := "博客注册验证"
		//生成验证码
		rand.Seed(time.Now().UnixNano())
		code := rand.Intn(8999) + 1000
		body := "验证码是：" + strconv.Itoa(code)
		logs.Info("验证码是 ：", code)

		// 发送给用户注册邮件
		err := utils.SendMail(email, subject, body)
		if err != nil {
			logs.Info(err)
			r.Data["json"] = map[string]interface{}{"code": 0, "msg": "验证码发送失败"}
			return
		} else {
			logs.Info("发送邮件成功")
			resp["code"] = 1
			resp["msg"] = "发送邮件成功"
			models.SetRedisKeyValue(email, strconv.Itoa(code))
			models.SetExpireTime(email, 600) //过期时间 验证码
			return
		}
	}
}

func (r *UserController) EditPassword() {
	username := r.GetString("username")
	password := r.GetString("password")
	repassword := r.GetString("repassword")
	email := r.GetString("email")
	logs.Info("用户名是 ", username, "密码是 ", password, "邮箱时", email)
	resp := make(map[string]interface{})
	r.Data["json"] = resp
	defer r.ServeJSON()

	if username == "" || password == "" || email == "" {
		resp["code"] = 0
		resp["msg"] = "用户名、密码、邮箱不能为空"
		return
	}
	if password != repassword {
		resp["code"] = 0
		resp["msg"] = "两次输入密码不一致"
		return
	}

	o := orm.NewOrm()
	user := models.User{}
	qs := o.QueryTable(new(models.User)).Filter("Username", username)
	if qs.Exist() {
		qs.One(&user)
		if user.Status == 0 {
			r.Data["json"] = map[string]interface{}{"code": 0, "msg": "账号未验证"}
			r.ServeJSON()
			return
		} else if user.Status == 1 && user.Email != email {
			r.Data["json"] = map[string]interface{}{"code": 0, "msg": "邮箱不正确"}
			r.ServeJSON()
			return
		} else {
			rnd := rand.Intn(8999) + 1000
			subject := "博客密码修改的验证码"
			body := "验证码是" + strconv.Itoa(rnd)
			err := utils.SendMail(email, subject, body)
			if err != nil {
				logs.Info(err)
				logs.Info("邮件发送失败")
				return
			}
			r.Data["json"] = map[string]interface{}{"code": 1, "msg": "已经发送验证码"}
			models.SetRedisKeyValue(email, strconv.Itoa(rnd))
			models.SetExpireTime(email, 600) //过期时间 验证码
		}
	} else {
		resp["code"] = 0
		resp["msg"] = "用户不存在"
	}
	return
}


//验证码验证机制
//综合修改密码和状态激活
func (r *UserController) VerifyCode() {
	email := r.GetString("email")
	code, _ := r.GetInt("code") //用户给的
	username := r.GetString("username")
	password := r.GetString("password")
	//repassword := r.GetString("repassword")  其实都已经注册过了
	trueCode, _ := redis.Int(models.GetRedisValue(email))
	logs.Info("验证码是", trueCode)
	logs.Info("我输入的验证码是", code)
	defer r.ServeJSON()
	if code != trueCode {
		logs.Info("验证码失败")
		r.Data["json"] = map[string]interface{}{"code": "0", "msg": "验证码错误"}
		return
	} else {
		models.DelRedisKey(email)
	}
	o := orm.NewOrm()
	//有需要查询的结构体对象
	user := models.User{Email: email, Username: username}
	err := o.Read(&user, "Username")
	logs.Info(user)
	if user.Status == 0 {
		user.Status = 1
		if err == nil {
			count, err := o.Update(&user, "status") //省略的是一个int类型的返回值，代表的是更新了多少条数据
			logs.Info(user)
			if err != nil {
				logs.Info(err)
			} else {
				logs.Info(count)
			}
			logs.Info("激活成功")
			r.Data["json"] = map[string]interface{}{"code": "1", "msg": "激活成功"}
		} else {
			logs.Info(err)
		}
	} else {
		user.Password = utils.GetMd5(password)
		o.Update(&user, "Password")
		logs.Info("修改密码")
		r.Data["json"] = map[string]interface{}{"code": "0", "msg": "修改成功"}
	}
 return
}
