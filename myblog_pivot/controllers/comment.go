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

type CommentController struct {
	BaseController
}

func (o *CommentController) Post() {
	logs.Info("开始提交评论 begin to submit comment ")
	resp := make(map[string]interface{})
	o.Data["json"] = resp
	defer o.ServeJSON()

	//如果登录了
	if o.Islogin {
		postid, _ := o.GetInt("PostId")
		content := o.GetString("Content")
		authorid := o.UserId
		authorname := o.Username
		createtime := time.Now()
		comment := models.Comment{PostId: postid, Content: content, AuthorId: authorid, AuthorName: authorname, CreateTime: createtime}
		q := orm.NewOrm()
		if _, err := q.Insert(&comment); err == nil {
			logs.Info("插入评论成功 submit comment success ")
			resp["code"] = 1
			resp["msg"] = "创建评论成功"
		} else {
			resp["code"] = 0
			resp["msg"] = "创建评论失败"
		}
	} else {
		resp["code"] = 0
		resp["msg"] = "用户未登录"
	}

	return
}

func (o *CommentController) Get() {  //整体分页返回
	resp := make(map[string]interface{})
	o.Data["json"] = resp
	defer o.ServeJSON()
	page, err := o.GetInt("Page")
	postid, _ := o.GetInt("PostId")
	if err != nil || page <= 0 {
		page = 1
		logs.Debug("page is 1")
	}

	logs.Debug("pagenum is ", page)
	logs.Debug("postid is ", postid)

	//get Redis connection
	redisConn := models.RedisPool.Get()
	defer redisConn.Close()
	target := "comment:" + strconv.Itoa(page) + "postid:" + strconv.Itoa(postid)

	if ans, _ := redis.String(redisConn.Do("GET", target)); ans != "" {
		o.Data["json"] = ans
		logs.Info("从redis中获取 ")
		logs.Debug("获取的数据是 ", ans) //输出具体的值使用debug
		return
	}

	Page, err := models.FindCommentWithPageAndPostid(page, postid)
	if err != nil {
		logs.Info("查找评论失败")
		resp["code"] = 0
		resp["mag"] = "查找评论失败"
		return
	}
	resp["page"] = Page
	result, _ := json.Marshal(Page)

	//设置redis
	if _, err = redisConn.Do("SET", target, string(result)); err != nil {
		logs.Info("set key-value error ", err)
		return
	}

	if _, err = redisConn.Do("EXPIRE", target, 200); err != nil {
		logs.Info("set key-value error ", err)
		return
	}
	logs.Info("返回各页成功  return allcomments success ")
	logs.Info("从数据库中获取")
	logs.Debug(page)
	return
}

//update the comment
func (o *CommentController) Put() {
	resp := make(map[string]interface{})
	o.Data["json"] = resp
	defer o.ServeJSON()
	commentid, _ := o.GetInt("CommentId")
	comment, err := models.FindCommentWithId(commentid)
	if err != nil {
		logs.Info("更新评论查找失败")
		resp["code"] = 0
		resp["msg"] = "更新评论查找失败"
		return
	}

	if o.Islogin && o.UserId == comment.AuthorId {
		content := o.GetString("Content")
		comment.Content = content
		o := orm.NewOrm()
		_, err := o.Update(&comment, "content")
		if err == nil {
			resp["code"] = 1
			resp["msg"] = "修改评论成功"
		} else {
			resp["code"] = 0
			resp["msg"] = "修改评论失败"
		}
	} else {
		resp["code"] = 0
		resp["msg"] = "当前用户未登录，你没有权限修改"
	}
	return
}

func (o *CommentController) Delete() {
	resp := make(map[string]interface{})
	o.Data["json"] = resp
	defer o.ServeJSON()

	commentid, _ := o.GetInt("CommentId")
	comment, err := models.FindCommentWithId(commentid)
	if err != nil {
		resp["code"] = 0
		resp["msg"] = "查找评论失败"
		logs.Info("删除评论查找失败")
		return
	}

	if o.Islogin && o.UserId == comment.AuthorId {
		d := orm.NewOrm()
		_, err := d.Delete(&comment)
		if err == nil {
			resp["code"] = 1
			resp["msg"] = "删除评论成功"
		} else {
			resp["code"] = 0
			resp["msg"] = "删除评论失败"
		}
	} else {
		resp["code"] = 0
		resp["msg"] = "删除评论失败，当前用户未登录"
	}
	return
}
