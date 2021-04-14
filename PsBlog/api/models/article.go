package models

import (
	"github.com/jinzhu/gorm"
	"time"
)

//基本数据
type Articles struct {
	ID    int `json:"id" gorm:"primary_key"`
	TagId int `json:"tag_id" gorm:"index"`
	Title string `json:"title"`
	Content string `json:"content"`
	DateTime time.Time `json:"date_time"`
}

//添加文章
func (article Articles)AddArticle(a map[string]interface{})  (err error)  {
	res := Db.Create(&Articles{
		TagId: a["tag_id"].(int),
		Title: a["title"].(string),
		Content: a["content"].(string),
		DateTime: a["date_time"].(time.Time),
	})
	if res.Error!=nil{
		err = res.Error
		return
	}
	return
}
//通过id查找
func (article *Articles)GetArticleByID(id int) bool{
	err := Db.Where("id = ?", id).First(&article).Error
	if err != nil{
		return false
	}
	return true
}

//更新文章
func (article Articles)UpdateArticle(id int, data interface {}) bool {
	Db.Model(&Articles{}).Where("id = ?", id).Updates(data)

	return true
}

//列表
func GetAllArticles(pageSize int, pageNum int) []Articles{
	var articles []Articles
	err := Db.Limit(pageSize).Offset((pageNum-1)*pageSize).Find(&articles).Error
	if err != nil && err != gorm.ErrRecordNotFound{
		return nil
	}
	return articles
}

//删除文章
func(article Articles)DeleteArticle(id int) bool {
	err := Db.Where("id=?", id).Delete(Articles{}).Error
	if err != nil{
		return false
	}
	return true
}


