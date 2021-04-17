package main

import (
	"fmt"
	"github.com/beego/beego/v2/client/orm"
	"github.com/beego/beego/v2/core/logs"
	beego "github.com/beego/beego/v2/server/web"
	_ "github.com/go-sql-driver/mysql"
	"myblog/models"
	_ "myblog/routers"
	"strconv"
)

func main() {
	beego.Run()
}

func init() {

	drivername,_ := beego.AppConfig.String("drivername")
	orm.RegisterDriver(drivername,orm.DRMySQL)


	username, _ := beego.AppConfig.String("username")
	password, _ := beego.AppConfig.String("password")
	host, _ := beego.AppConfig.String("host")
	port, _ := beego.AppConfig.String("port")
	database, _ := beego.AppConfig.String("database")

	datasource := fmt.Sprintf("%s:%s@tcp(%s:%s)/%s?charset=utf8&loc=Local",username,password,host,port,database)

	err := orm.RegisterDataBase("default","mysql",datasource)

	fmt.Println("连接数据")

	if err != nil {
		logs.Info("连接失败")
	}

	//自动创建表 参数二为是否开启创建表   参数三是否更新表
	orm.RunSyncdb("default", true, true)
	//连接成功
	s := orm.NewOrm()
	for j := 1;j < 25;j++ {
		for i := 1; i < 25; i++ {
			comment := models.Comment{PostId: j, Content: strconv.Itoa(i * 11), AuthorId: 1}
			s.Insert(&comment)
		}
	}
	o := orm.NewOrm()
	for j := 1;j < 25;j++ {
		for i := 1; i < 25; i++ {
			post := models.Post{Content: "contentcontent", Title: strconv.Itoa(i), AuthorId: j}
			o.Insert(&post)
		}
	}
}