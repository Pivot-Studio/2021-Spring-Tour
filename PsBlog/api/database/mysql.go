package database

import (
	"fmt"
	_ "github.com/go-sql-driver/mysql"
	"github.com/jinzhu/gorm"
)

var Db *gorm.DB

func init(){
	var err error
	//链接数据库
	Db, err = gorm.Open("mysql", "root:zbs123@tcp(127.0.0.1:3306)/test?charset=utf8&parseTime=True&loc=Local&timeout=10ms")
	if err != nil {
		fmt.Printf("mysql connect error %v", err)
	}
	if Db.Error != nil{
		fmt.Printf("database error: %v\n", Db.Error)
	}
}
