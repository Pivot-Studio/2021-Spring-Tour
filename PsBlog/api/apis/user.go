package apis

import (
	model "2021-Spring-Tour/PsBlog/api/models"
	"fmt"
	"github.com/gin-gonic/gin"
	"golang.org/x/time/rate"
	"net/http"
	"strconv"
	"time"
)

//创建限速器
//60s加入一个token
var R rate.Limit = rate.Every( 60 * time.Second)
//token容量设置为1
var Limiter = rate.NewLimiter(R, 1)
//暂存验证码
var Vcode string

//info
var message string
var code int
//查询用户是否存在
func UserExist(c *gin.Context){

}


//添加用户
func AddUser(c *gin.Context){
	var data model.User
	_ = c.ShouldBindJSON(&data)
	flag ,_:= model.CheckUser(data.Username)
	//fmt.Println("username: ", data.Username)
	//验证输入，前端验证
	//valid := validation.Validation{}
	//valid.Min(data.Username, 1, "username").Message("用户名长度必须大于0")
	//valid.Min(data.Password, 6, "password").Message("密码不得少于6位")
	if flag==true {
		code = 1
		model.RegisterUser(&data)
		fmt.Println("data: ", data.Username)
		message = "添加成功"
	}else{
		code = 2
		message = "用户名已存在"
	}
	c.JSON(http.StatusOK, gin.H{
		"status": code,
		"msg": message,
		"data": data,
	})
}

//查询单个用户


//查询用户列表
func GetUsers(c *gin.Context){
	//实现分页功能
	pageSize, _ := strconv.Atoi( c.Query("pagesize"))
	pageNum, _ := strconv.Atoi(c.Query("pagenum"))
	//gorm中将下列值设为-1则limit/offset不受限制
	if pageSize == 0{
		pageSize = -1
	}
	if pageNum == 0{
		pageNum = -1
	}
	data := model.GetUsers(pageSize, pageNum)
	code = 1
	message = "查询成功"
	c.JSON(http.StatusOK, gin.H{
		"status": code,
		"data": data,
		"msg": message,
	})
}

//编辑用户
func EditUser(c *gin.Context){
	var data model.User
	id ,_:= strconv.Atoi(c.Param("id"))
	_ = c.ShouldBindJSON(&data)
	//fmt.Println("username:", data.Username)
	flag ,_:= model.CheckUser(data.Username)
	//fmt.Println("flag: ", flag)
	//fmt.Println(data.Username)
	if flag == true{
		code = 1
		message = "更新成功"
		model.EditUser(id, &data)
	}else {
		code = -1
		message = "用户名未更改或已存在"
		c.Abort()
	}
	c.JSON(http.StatusOK, gin.H{
		"status": code,
		"msg": message,
	})
}

//删除用户
func DeleteUser(c *gin.Context){
	id ,_:= strconv.Atoi(c.Param("id"))
	flag := model.Delete(id)
	if flag == true{
		code = 1
		message = "删除成功"
	}else{
		code = -1
		message = "删除失败"
	}
	c.JSON(http.StatusOK, gin.H{
		"status": code,
		"msg": message,
	})
}

//修改密码
func ChangePassword(c *gin.Context){
	var data model.Verify
	id, _ := strconv.Atoi(c.Param("id"))
	newPassWd := c.Query("newPassword")
	//想加个时效判断
	//Email := c.Query("email")
	_ = c.ShouldBindJSON(&data)
	fmt.Println("data:", data.Username)
	var res *model.User
	_, res = model.CheckUser(data.Username)
	fmt.Println("name", res.Password==model.ScryptPw(data.Password))
	if data.Username != res.Username{
		// 无此用户
		c.JSON(http.StatusOK,gin.H{
			"success":false,
			"code":400,
			"msg":"无此用户",
		})
	}else{
		// 密码是否匹配
		if model.ScryptPw(data.Password) != res.Password {
			fmt.Println("password error")
			c.JSON(http.StatusOK, gin.H{
				"success": false,
				"code":    400,
				"msg":     "密码错误",
			})
		}else{
			//用户名和密码都对之后再发送邮件
			fmt.Println("ver: ", data.Email)
			//Allow每次调用消耗一个token, token数为0则阻塞
			if Limiter.Allow() {
				Vcode = model.SendEmail(data.Email)
				code = 1
				message = "得到验证码"
				fmt.Println("开始阻塞")
			}
			fmt.Println("data", data.VerifyCode,"  ", Vcode)
			if data.VerifyCode == Vcode {
				//Allow每次消耗一个token，如果token数为0则阻塞
				flag := model.ChangePasswd(id, model.ScryptPw(newPassWd))
				if flag == true {
					code = 1
					message = "修改成功"
				} else {
					code = -1
					message = "修改失败"
				}
			}
			c.JSON(
				http.StatusOK, gin.H{
					"status":  code,
					"message": message,
				},
			)
		}
	}

}
