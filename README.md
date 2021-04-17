# Psblog项目介绍

## 简介
  
该项目用gin+gorm作为主要框架，jwt作为鉴权中间件，搭建了一个简易的博客后端。
api基于restful风格编写。总项目由三个模块组成，apis(路由处理逻辑)，models(定义数据类型，链接数据库)，router, 另middleware为中间件处理。数据库配置写在config.yaml中（本来还想做个错误处理配置文件的。

## 功能
1. 用户注册登录，密码采用Scrypt盐值加密
2. 修改密码，旧密码+邮箱4位验证码验证(基于smtp，且有效期为一分钟
3. 文章，评论分页返回(基本的增删改查)，对文章得输入用validator进行了限制。jwt实现token认证，只有登录的用户才能评论。

- 路由接口
需要token认证的：
```
    //用户模块的路由接口
	router.PUT("/user/:id", EditUser)
	router.DELETE("/user/:id", DeleteUser)
	router.PUT("/changePw/:id", ChangePassword)

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
```
无须认证的：
```
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
```

## 测试

软件: 
- postman && apipost (token认证好像比postman更方便

以上功能均已在测试软件上测试通过。daybyday.md文件有部分截图样例。

## todo

学习redis和计网，配置错误文件