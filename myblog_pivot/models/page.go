package models

import (
	"encoding/json"
	"errors"
	orm "github.com/beego/beego/v2/client/orm"
	"github.com/beego/beego/v2/core/logs"
	beego "github.com/beego/beego/v2/server/web"
	"math"
	"strconv"
)

type Page struct {
	PagePosts    []Post    `json:"Posts,omitempty"`
	PageComments []Comment `json:"Comments,omitempty"`
	AllNum      int
	CurrentPage  int
	PrePage      int
	NextPage     int
	HasPrePage   bool
	HasNextPage  bool
}

//根据页码查询帖子
func FindPostWithPage(page int) (Page, error) {
	//从配置文件中获取每页的文章数量
	pagesize, _ := beego.AppConfig.Int("postListPageNum")

	o := orm.NewOrm()

	var posts = []Post{}

	qs := o.QueryTable(new(Post))
	count, _ := qs.Count()

	totalPage := int(math.Ceil(float64(count) / float64(pagesize)))
	if totalPage < page || page <= 0 {
		return Page{}, errors.New("Not Exist")
	}

	qs.Limit(pagesize, (page-1)*pagesize).All(&posts)

	var List Page
	List.CurrentPage = page
	List.AllNum = int(count)
	List.PagePosts = posts

	if totalPage > page {
		List.HasNextPage = true
		List.NextPage = page + 1
	} else {
		List.HasNextPage = false
		List.NextPage = 0
	}
	if page > 1 {
		List.HasPrePage = true
		List.PrePage = page - 1
	} else {
		List.HasPrePage = false
		List.PrePage = 0
	}

	return List, nil
}

//根据页码和用户ID查询文章
func FindPostWithPageAndUser(page int, author_id int) (Page, error) {
	pagesize, _ := beego.AppConfig.Int("postListPageNum")
	o := orm.NewOrm()
	posts := []Post{}

	qs := o.QueryTable(new(Post)).Filter("author_id", author_id) //过滤出全部userid的
	count, _ := qs.Count()

	logs.Debug("the num of pageanduser is", count)
	totalPage := int(math.Ceil(float64(count) / float64(pagesize)))
	logs.Debug("total page is ", totalPage)
	logs.Debug("page is ", page)
	if totalPage < page || page <= 0 {
		return Page{}, errors.New("Not Exist")
	}
	qs.Limit(pagesize, (page-1)*pagesize).All(&posts)
	logs.Info(posts)
	var List Page
	List.CurrentPage = page
	List.AllNum = int(count)
	List.PagePosts = posts

	if totalPage > page {
		List.HasNextPage = true
		List.NextPage = page + 1
	} else {
		List.HasNextPage = false
		List.NextPage = 0
	}
	if page > 1 {
		List.HasPrePage = true
		List.PrePage = page - 1
	} else {
		List.HasPrePage = false
		List.PrePage = 0
	}
	target := "page:" + strconv.Itoa(page) + "user:" + strconv.Itoa(author_id)
	result, _ := json.Marshal(List)

	SetRedisKeyValue(target, string(result))
	SetExpireTime(target, 600) //10 min
	return List, nil
}
