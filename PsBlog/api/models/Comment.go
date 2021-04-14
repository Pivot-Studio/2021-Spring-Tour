package models

import (
	"github.com/jinzhu/gorm"
)

type Comments struct {
	gorm.Model
	UserId int `json:"user_id"`
	ArticleId int `json:"article_id"`
	Title string `json:"title"`
	Username string `json:"username"`
	Content string `gorm:"type:varchar(500);not null;" json:"content"`
}

//新增评论
func AddComment(data *Comments) bool {
	err := Db.Create(&data).Error
	if err != nil {
		return false
	}
	return true
}

//查询单个评论
func GetComment(id int) (Comments,bool) {
	var comment Comments
	err := Db.Where("id = ?", id).First(&comment).Error
	if err != nil {
		return comment, false
	}
	return comment, true

}

//分页获取评论列表
func GetCommentList(pageSize int, pageNum int) ([]Comments, int64, bool) {

	var commentList []Comments
	var total int64
	Db.Find(&commentList).Count(&total) //评论总数
	//limit()表示要取的数量
	//offset()表示要跳过的数量
	//order()按照创建时间顺序
	//Joins()联合查询
	//left join多表联查  on/ where后跟查询条件
	//以此保证评论是在相同的article_id，以及同一user_id
	//Scan()快速数据存储
	err := Db.Model(&commentList).Limit(pageSize).Offset((pageNum - 1) *
		pageSize).Order("Created_At DESC").Select("comments.id, articles.title,user_id,article_id, user.username, comments.content,comments.created_at,comments.deleted_at").Joins("LEFT JOIN articles ON comments.article_id = articles.id").Joins("LEFT JOIN user ON comments.user_id = user.id").Scan(&commentList).Error
	if err != nil {
		return commentList, 0, false
	}
	return commentList, total, true
}


//删除评论
func DeleteComment(id int)bool{
	var comment Comments
	err := Db.Where("id = ?", id).Delete(&comment).Error
	if err != nil {
		return false
	}
	return true
}