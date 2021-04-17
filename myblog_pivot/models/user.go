package models


type User struct {
	Id         int `orm:"pk;auto"`
	Username   string
	Password   string
	Status int //0表示已注册但未验证	1表示已经注册且已经验证
	Email string
	Createtime int64 `orm:"auto_now_add;type(datetime);description(创建时间)"`
}
