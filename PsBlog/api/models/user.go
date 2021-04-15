package models

import (
	"encoding/base64"
	"github.com/jinzhu/gorm"
	"golang.org/x/crypto/scrypt"
	"log"
)

//定义用户结构
type User struct {
	ID int `json:"id" gorm:"primary_key"`
	Username string `json:"username"`
	Password string `json:"password"`
}

//查询用户是否存在
func CheckUser(username string) (bool, *User){
	var data User
	//查询第一个参数
	Db.Select("id, username, password").Where("username = ?", username).First(&data)
	if data.ID > 0{
		return false, &data
	}else{
		return true, &data
	}
}

//注册用户
func RegisterUser(data *User) bool{
	//盐值加密
	//data.Password = ScryptPw(data.Password)
	err := Db.Create(&data).Error
	if err != nil{
		return false
	}
	return true
}

//查询用户列表
func GetUsers(pageSize int, pageNum int) []User{
	var users []User
	err := Db.Limit(pageSize).Offset((pageNum-1)*pageSize).Find(&users).Error
	if err != nil && err != gorm.ErrRecordNotFound{
		return nil
	}
	return users
}

//编辑用户
func EditUser(id int, data *User)bool{
	var user User
	var maps = make(map[string] interface{})
	maps["username"] = data.Username
	//密码暂不在这更新
	err := Db.Model(&user).Where("id = ?", id).Update(maps).Error
	if err != nil{
		return false
	}
	return true
}


//删除用户
func Delete(id int)bool{
	var user User
	err := Db.Where("id = ?", id).Delete(&user).Error
	if err != nil{
		return false
	}
	return true
}

//钩子函数处理事务
//在创建和更新密码前进行加密
func (u *User) BeforeCreate(_ *gorm.DB) (err error) {
	u.Password = ScryptPw(u.Password)
	return nil
}

func (u *User) BeforeUpdate(_ *gorm.DB) (err error) {
	u.Password = ScryptPw(u.Password)
	return nil
}


//密码加密
func ScryptPw(password string) string{
	const keyLen = 10
	//盐值
	salt := make([]byte, 8)
	//任意8个数
	salt = []byte{13, 2, 45, 22, 11, 67, 87, 99}
	//调用专家库scrypt
	//func Key(pw, salt []byte, N, r, p, keyLen int)
	//N必须是2的幂
	HashPasswd, err := scrypt.Key([]byte(password), salt, 16384, 8, 1, keyLen)
	if err != nil{
		log.Fatal(err)
	}
	EncPw := base64.StdEncoding.EncodeToString(HashPasswd)
	return EncPw
}

//登陆验证
func CheckLogin(username string, password string) string  {
	var user User
	Db.Where("username = ?", username).First(&user)
	if user.ID == 0{
		return "用户名不存在"
	}
	if ScryptPw(password) != user.Password{
		return "密码错误"
	}
	return "成功登录"
}

//修改密码
func ChangePasswd(id int, newPasswd string) bool{
	var user User
	var maps = make(map[string]interface{})
	maps["password"] = newPasswd
	err := Db.Model(&user).Where("id = ?", id).Updates(maps).Error
	if err != nil{
		return false
	}
	return true
}