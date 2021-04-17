package controllers

import (
	"encoding/json"
	"github.com/beego/beego/v2/client/orm"
	"github.com/beego/beego/v2/core/logs"
	"github.com/gomodule/redigo/redis"
	"myblog/models"
	"strconv"
	"time"
)

// Operations about Post
type PostController struct {
	BaseController
}

func (o *PostController) Post() {
	logs.Info("开始提交帖子 begin to submit post ")
	resp := make(map[string]interface{})
	o.Data["json"] = resp
	defer o.ServeJSON()

	if o.Islogin {
		title := o.GetString("Title")
		content := o.GetString("Content")
		authorid := o.UserId
		authorname := o.Username
		createtime := time.Now()
		post := models.Post{Title: title, Content: content, AuthorId: authorid, AuthorName: authorname, CreateTime: createtime}
		q := orm.NewOrm()
		if _, err := q.Insert(&post); err == nil {
			logs.Info("插入帖子成功 insert post success")
			resp["code"] = 1
			resp["msg"] = "创建帖子成功"
		} else {
			resp["code"] = 0
			resp["msg"] = "创建帖子失败"
		}
	} else {
		resp["code"] = 0
		resp["msg"] = "用户未登录"
	}

	return
}


//整体分页返回 返回所有的帖子
func (o *PostController) GetAllPages() {
	logs.Info("开始获取所有帖子 begin to get allposts ")
	resp := make(map[string]interface{})
	o.Data["json"] = resp
	defer o.ServeJSON()
	page, err := o.GetInt("Page")
	if err != nil || page <= 0 {
		page = 1
		logs.Debug("page is 1")
	}

	//get Redis connection
	redisConn := models.RedisPool.Get()
	defer redisConn.Close()
	target := "page:" + strconv.Itoa(page)

	if ans, _ := redis.String(redisConn.Do("GET", target)); ans != "" {
		o.Data["json"] = ans
		logs.Info("从redis中获取 ")
		logs.Debug("获取的数据是 ", ans) //输出具体的值使用debug
		return
	}

	Page, error := models.FindPostWithPage(page)
	if error != nil {
		logs.Info("查找失败")
		resp["code"] = 0
		resp["mag"] = "查找失败"
		return
	}
	resp["page"] = Page

	result, _ := json.Marshal(Page)

	if _, err = redisConn.Do("SET", target, string(result)); err != nil {
		logs.Info("set key-value error ", err)
		return
	}

	if _, err = redisConn.Do("EXPIRE", target, 200); err != nil {
		logs.Info("set key-value error ", err)
		return
	}

	logs.Info("返回各页成功  return allposts success ")
	logs.Info("从数据库中获取")
	logs.Debug(page)
	return
}

//用户分页返回  返回某个特定用户的帖子
func (o *PostController) GetAllUserPages() {
	logs.Info("开始获取该用户的所有帖子 begin to get alluserposts ")
	resp := make(map[string]interface{})
	o.Data["json"] = resp
	defer o.ServeJSON()

	if o.Islogin {
		//get Redis connection
		redisConn := models.RedisPool.Get()
		defer redisConn.Close()
		page, err := o.GetInt("Page")
		if err != nil || page <= 0 {
			page = 1
			logs.Debug("page is 1")
		}
		authorid := o.UserId
		target := "page:" + strconv.Itoa(page) + "user:" + strconv.Itoa(authorid)

		if ans, _ := redis.String(redisConn.Do("GET", target)); ans != "" {
			resp["page"] = ans
			logs.Info("从redis中获取 ")
			logs.Debug("获取的数据是 ", ans) //输出具体的值使用debug
			return
		}

		Page, err := models.FindPostWithPageAndUser(page, authorid)
		if err != nil {
			resp["code"] = 0
			resp["msg"] = "该用户无帖子"
		} else {
			resp["page"] = Page
			result, _ := json.Marshal(Page)

			if _, err = redisConn.Do("SET", target, string(result)); err != nil {
				logs.Info("set key-value error ", err)
				return
			}

			if _, err = redisConn.Do("EXPIRE", target, 200); err != nil {
				logs.Info("set key-value error ", err)
				return
			}
			logs.Info("返回各用户页成功  return alluserposts success ")
			logs.Info("从数据库中获取")
			logs.Debug(page)
			return
		}
	} else {
		resp["code"] = 0
		resp["msg"] = "用户未登录"
	}

	return
}

func (o *PostController) Put() {
	resp := make(map[string]interface{})
	o.Data["json"] = resp
	defer o.ServeJSON()

	postid, _ := o.GetInt("PostId")
	prepost, err := models.FindPostWithId(postid)
	if err != nil {
		logs.Info("更新帖子查找失败")
		resp["code"] = 0
		resp["msg"] = "更新帖子查找失败"
		return
	}

	if o.Islogin && o.UserId == prepost.AuthorId {
		title := o.GetString("Title")
		content := o.GetString("Content")
		prepost.Title = title
		prepost.Content = content
		o := orm.NewOrm()
		_, err := o.Update(&prepost, "title", "content")
		if err == nil {
			resp["code"] = 1
			resp["msg"] = "修改帖子成功"
		} else {
			resp["code"] = 0
			resp["msg"] = "修改帖子失败"
		}
	} else {
		resp["code"] = 0
		resp["msg"] = "当前用户未登录，你没有权限修改"
	}
	return
}

func (o *PostController) Delete() {
	resp := make(map[string]interface{})
	o.Data["json"] = resp
	defer o.ServeJSON()

	postid, _ := o.GetInt("PostId")
	post, err := models.FindPostWithId(postid)
	if err != nil {
		resp["code"] = 0
		resp["msg"] = "查找帖子失败"
		logs.Info("删除帖子查找失败")
		return
	}

	if o.Islogin && o.UserId == post.AuthorId {
		d := orm.NewOrm()
		_, err := d.Delete(&post)
		if err == nil {
			resp["code"] = 1
			resp["msg"] = "删除帖子成功"
		} else {
			resp["code"] = 0
			resp["msg"] = "删除帖子失败"
		}
	} else {
		resp["code"] = 0
		resp["msg"] = "删除帖子失败，当前用户未登录"
	}
	return
}
