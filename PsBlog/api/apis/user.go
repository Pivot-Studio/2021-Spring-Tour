package apis

import (
	model "2021-Spring-Tour/PsBlog/api/models"
	"fmt"
	"github.com/gin-gonic/gin"
	"net/http"
	"strconv"
)
//info
var message string
var code int

//查询用户是否存在
func UserExist(c *gin.Context){

}


//添加用户
func AddUser(c *gin.Context){
	var data model.User
	_ = c.ShouldBindJSON(&data)
	flag := model.CheckUser(data.Username)
	//fmt.Println("username: ", data.Username)
	//验证输入，前端验证
	//valid := validation.Validation{}
	//valid.Min(data.Username, 1, "username").Message("用户名长度必须大于0")
	//valid.Min(data.Password, 6, "password").Message("密码不得少于6位")
	if flag==true {
		code = 1
		model.RegisterUser(&data)
		fmt.Println("data: ", data.Username)
		message = "添加成功"
	}else{
		code = 2
		message = "用户名已存在"
	}
	c.JSON(http.StatusOK, gin.H{
		"status": code,
		"msg": message,
		"data": data,
	})
}

//查询单个用户


//查询用户列表
func GetUsers(c *gin.Context){
	//实现分页功能
	pageSize, _ := strconv.Atoi( c.Query("pagesize"))
	pageNum, _ := strconv.Atoi(c.Query("pagenum"))
	//gorm中将下列值设为-1则limit/offset不受限制
	if pageSize == 0{
		pageSize = -1
	}
	if pageNum == 0{
		pageNum = -1
	}
	data := model.GetUsers(pageSize, pageNum)
	code = 1
	message = "查询成功"
	c.JSON(http.StatusOK, gin.H{
		"status": code,
		"data": data,
		"msg": message,
	})
}

//编辑用户
func EditUser(c *gin.Context){
	var data model.User
	id ,_:= strconv.Atoi(c.Param("id"))
	_ = c.ShouldBindJSON(&data)
	fmt.Println("username:", data.Username)
	flag := model.CheckUser(data.Username)
	fmt.Println("flag: ", flag)
	fmt.Println(data.Username)
	if flag == true{
		code = 1
		message = "更新成功"
		model.EditUser(id, &data)
	}else {
		code = -1
		message = "用户名未更改或已存在"
		c.Abort()
	}
	c.JSON(http.StatusOK, gin.H{
		"status": code,
		"msg": message,
	})
}

//删除用户
func DeleteUser(c *gin.Context){
	id ,_:= strconv.Atoi(c.Param("id"))
	flag := model.Delete(id)
	if flag == true{
		code = 1
		message = "删除成功"
	}else{
		code = -1
		message = "删除失败"
	}
	c.JSON(http.StatusOK, gin.H{
		"status": code,
		"msg": message,
	})
}
