package router

import (
	. "2021-Spring-Tour/PsBlog/api/apis"
	"2021-Spring-Tour/PsBlog/middleware"
	"github.com/gin-gonic/gin"
)


//注册路由
func InitRouter()*gin.Engine{
	r := gin.Default()
	router := r.Group("")
	router.Use(middleware.JwtToken())
	{
		//用户模块的路由接口
		router.PUT("/user/:id", EditUser)
		router.DELETE("/user/:id", DeleteUser)

		//文章模块的路由接口
		//添加文章
		router.POST("/article", AddArticle)
		//更新文章
		router.PUT("/articles/:id", UpdateArticle)
		//删除文章
		router.DELETE("/articles/:id", DeleteArticle)

		//评论模块
		router.DELETE("delcomment/:id", DeleteComment)
		//只有登录了的才能添加评论
		router.POST("addcomment", AddComment)
	}
	router2 := r.Group("")
	{
		//用户模块
		router2.POST("/user/add", AddUser)
		router2.GET("/users", GetUsers)

		//获取文章列表
		router2.GET("/articles", GetArticles)
		//获取指定文章
		router2.GET("/articles/:id", GetArticle)
		//登录界面
		router2.POST("/login", Login)

		//评论模块
		router2.GET("comment/list", GetCommentList)
		router2.GET("comment/get/:id")
	}

	return r
}
