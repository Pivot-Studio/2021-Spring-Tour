package models

import (
	"errors"
	"github.com/beego/beego/v2/client/orm"
	"github.com/beego/beego/v2/core/logs"
	beego "github.com/beego/beego/v2/server/web"
	"math"
	"time"
)

var (
	comments map[string]*Comment
)

type Comment struct {
	Id int `orm:"pk;auto"`
	Content string `orm:"size(4000);description(评论内容)"`
	PostId int `orm:"description(帖子编号)"`  //属于哪个帖子
	AuthorId int
	AuthorName string
	CreateTime time.Time `orm:"auto_now_add;type(datetime);description(创建时间)"`
}


//根据具体的评论ID查找评论
func FindCommentWithId(commentid int) (Comment,error) {
	o := orm.NewOrm()
	qs := o.QueryTable(new(Comment)).Filter("id",commentid)
	if !qs.Exist() {
		logs.Info("不存在评论")
		return Comment{},errors.New("不存在评论")
	}
	logs.Info("查找到评论")
	comment := Comment{}
	qs.One(&comment)
	return comment,nil
}


//根据页码和帖子ID查找评论,实现分页返回,返回一页
func FindCommentWithPageAndPostid(page int, postid int) (Page,error) {
	pagesize, _ := beego.AppConfig.Int("commentListPageNum")
	o := orm.NewOrm()
	comments := []Comment{}


	qs := o.QueryTable(new(Comment)).Filter("post_id",postid) //过滤出全部userid的
	count, _ := qs.Count()

	totalPage := int(math.Ceil(float64(count) / float64(pagesize)))
	if totalPage < page || page <= 0 {
		return Page{},errors.New("Not Exist")
	}
	qs.Limit(pagesize,(page-1)*pagesize).All(&comments)
	var List Page
	List.CurrentPage = page
	List.AllNum = int(count)
	List.PageComments = comments

	if totalPage > page {
		List.HasNextPage = true
		List.NextPage = page + 1
	} else {
		List.HasNextPage = false
		List.NextPage = 0
	}
	if page > 1 {
		List.HasPrePage = true
		List.PrePage = page-1
	} else {
		List.HasPrePage = false
		List.PrePage = 0
	}


	return List,nil
}

