package models

import (
	orm "2021-Spring-Tour/PsBlog/api/database"
	"github.com/jinzhu/gorm"
	"time"
)

//基本数据
type Article struct {
	ID    int `json:"id" gorm:"primary_key"`
	TagId int `json:"tag_id" gorm:"index"`
	Title string `json:"title"`
	Content string `json:"content"`
	DateTime time.Time `json:"time"`
}

//添加文章
func (article Article)AddArticle(a map[string]interface{})  (err error)  {
	res := orm.Db.Create(&Article{
		TagId: a["tag_id"].(int),
		Title: a["title"].(string),
		Content: a["content"].(string),
		DateTime: a["time"].(time.Time),
	})
	if res.Error!=nil{
		err = res.Error
		return
	}
	return
}
//通过id查找
func (article Article)GetArticleByID(id int) bool{
	orm.Db.Select("id").Where("id = ?", id).First(&article)
	if article.ID < 0{
		return false
	}
	return true
}

//更新文章
func (article Article)UpdateArticle(id int, data interface {}) bool {
	orm.Db.Model(&Article{}).Where("id = ?", id).Updates(data)

	return true
}

//列表
func (article *Article)GetAllArticles()(articles []Article, err error){
	if err = orm.Db.Find(&articles).Error;err!=nil{
		return
	}
	return
}

//删除文章
func(article Article)DeleteArticle(id int) bool {
	err := orm.Db.Where("id=?", id).Delete(Article{})
	if err != nil{
		return false
	}
	return true
}

//生成时间戳
func (article *Article) BeforeCreate(scope *gorm.Scope) error {
	_ = scope.SetColumn("CreatedOn", time.Now().Unix())

	return nil
}

func (article *Article) BeforeUpdate(scope *gorm.Scope) error {
	_ = scope.SetColumn("ModifiedOn", time.Now().Unix())

	return nil
}

