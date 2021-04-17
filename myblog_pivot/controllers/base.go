package controllers

import (
	"github.com/beego/beego/v2/core/logs"
	beego "github.com/beego/beego/v2/server/web"
	"myblog/utils"
)

type BaseController struct {
	beego.Controller
	UserId   int
	Username string
	Islogin  bool
}

func (c *BaseController) Prepare() {
	usertoken := c.Ctx.GetCookie("loginuser")
	logs.Info("开始执行 prepare logging ")
	if usertoken == "" {
		logs.Info("当前用户未登录  login failure ")
		c.Islogin = false
		return
	}
	logs.Info("prepare token is ", usertoken)
	logs.Info("begin to validate ")
	if user, err := utils.ValidateToken(usertoken); err != nil {
		logs.Info("登陆失败 login failure ")
		c.Islogin = false //登陆失败
	} else {
		c.Islogin = true
		c.UserId = user.Id
		c.Username = user.Name
		logs.Info("登陆成功 login success")
		logs.Info("now the user is ", user)
	}
}

func init() {
	logs.SetLogger("console")
	logs.EnableFuncCallDepth(true)

}
