package models

import (
	"errors"
	orm "github.com/beego/beego/v2/client/orm"
	"github.com/beego/beego/v2/core/logs"
	"time"
)

type Post struct {
	Id    int `orm:"pk;auto"`
	Title string `orm:"description(帖子标题)"`
	Content string `orm:"size(4000);description(帖子内容)"`
	AuthorId int `orm:"description(帖子作者)"`
	AuthorName string
	NumPosts int //用户有几篇文章
	CreateTime time.Time `orm:"auto_now_add;type(datetime);description(创建时间)"`
}



//根据某个具体的ID查询帖子
func FindPostWithId(postid int) (Post, error) {
	o := orm.NewOrm()
	qs := o.QueryTable(new(Post))
	post := Post{}
	if qs.Filter("id",postid).Exist() {
		qs.Filter("id",postid).One(&post)
	} else {
		logs.Info("未查找到",postid)
		return Post{},errors.New("Not Exist")
	}


	return post,nil
}



