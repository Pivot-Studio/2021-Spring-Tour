package models

import (
	"github.com/jinzhu/gorm"
)

//定义用户结构
type User struct {
	ID int `json:"id" gorm:"primary_key"`
	Username string `json:"username"`
	Password string `json:"password"`
}

