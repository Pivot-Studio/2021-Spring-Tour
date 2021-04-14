package apis

import (
	model "2021-Spring-Tour/PsBlog/api/models"
	"2021-Spring-Tour/PsBlog/middleware"
	"github.com/gin-gonic/gin"
	"net/http"
)

func Login(c*gin.Context)  {
	var data model.User
	var token string
	_ = c.ShouldBindJSON(&data)
	message = model.CheckLogin(data.Username, data.Password)
	if message == "成功登录"{
		token ,_ = middleware.SetToken(data.Username, data.Password)
	}
	c.JSON(http.StatusOK, gin.H{
		"status": 1,
		"msg": message,
		"token": token,
	})
}