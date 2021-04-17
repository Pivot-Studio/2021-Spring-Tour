package routers

import (
	"myblog/controllers"
	beego "github.com/beego/beego/v2/server/web"
)

func init() {
    beego.Router("/", &controllers.MainController{})
	beego.Router("/post/", &controllers.PostController{})
	beego.Router("/post/page", &controllers.PostController{},"get:GetAllPages")
	beego.Router("/post/pageanduser", &controllers.PostController{},"get:GetAllUserPages")
	beego.Router("/user/register", &controllers.UserController{},"post:Register")
	beego.Router("/user/login", &controllers.UserController{},"post:Login")
	beego.Router("/user/editpass", &controllers.UserController{},"post:EditPassword")
	beego.Router("/user/verify", &controllers.UserController{},"post:VerifyCode")
	beego.Router("/comment/", &controllers.CommentController{})
}
