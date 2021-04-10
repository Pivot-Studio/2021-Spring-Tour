package router

import (
	. "2021-Spring-Tour/PsBlog/api/apis"
	"github.com/gin-gonic/gin"
)


//注册路由
func InitRouter()*gin.Engine{
	router := gin.Default()

	//获取文章列表
	router.GET("/articles", GetArticles)
	//获取指定文章
	router.GET("/articles/:id", GetArticle)
	//添加文章
	router.POST("/article", AddArticle)
	//更新文章
	router.PUT("/articles/:id", UpdateArticle)
	//删除文章
	router.DELETE("/articles/:id", DeleteArticle)
	return router
}
