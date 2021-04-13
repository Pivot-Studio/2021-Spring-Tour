package models

import (
	"fmt"
	_ "github.com/go-sql-driver/mysql"
	"github.com/jinzhu/gorm"
	"gopkg.in/yaml.v2"
	_ "gopkg.in/yaml.v2"
	"io/ioutil"
)

type Mysql struct{
	Dbname string `yaml:"dbname"`
	Username string `yaml:"username"`
	Password string `yaml:"password"`
	Host string `yaml:"host"`
	Port string `yaml:"port"`
}

var Db *gorm.DB

func (m *Mysql)load(path string) bool{
	yamlFile, err := ioutil.ReadFile(path)
	if err != nil{
		fmt.Println(err.Error())
	}
	err = yaml.Unmarshal(yamlFile, &m)
	if err != nil {
		fmt.Println(err.Error())
	}
	return true
}

func init(){
	var err error
	var m Mysql
	m.load("PsBlog\\api\\config.yaml")
	//链接数据库"root:zbs123@tcp(127.0.0.1:3306)/test?charset=utf8&parseTime=True&loc=Local&timeout=10ms"
	Db, err = gorm.Open("mysql", fmt.Sprintf("%s:%s@tcp(%s:%s)/%s?charset=utf8&parseTime=True&loc=Local&timeout=10ms",
		m.Username, m.Password, m.Host, m.Port, m.Dbname))
	if err != nil {
		fmt.Printf("mysql connect error %v", err)
	}
	if Db.Error != nil{
		fmt.Printf("database error: %v\n", Db.Error)
	}
	//迁移数据表
	_ = Db.AutoMigrate(Comments{})
	//禁用默认表名复数形式
	Db.SingularTable(true)
}
