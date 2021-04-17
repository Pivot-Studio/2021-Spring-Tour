package utils

import (
	"github.com/beego/beego/v2/core/logs"
	"gopkg.in/gomail.v2"
	"strconv"
)

func SendMail(mailTo string, subject string, body string) error {
	//定义邮箱服务器连接信息，如果是阿里邮箱 pass填密码，qq邮箱填授权码
	mailConn := map[string]string{
		"user": "1584616775@qq.com",
		"pass": "mgkachjatgvkiije",
		"host": "smtp.qq.com",
		"port": "465",
	}

	port, _ := strconv.Atoi(mailConn["port"]) //转换端口类型为int

	m := gomail.NewMessage()
	m.SetHeader("From", "Yang Yong"+"<"+mailConn["user"]+">") //这种方式可以添加别名，即“XD Game”， 也可以直接用<code>m.SetHeader("From",mailConn["user"])</code> 读者可以自行实验下效果
	m.SetHeader("To", mailTo)                                 //发送给用户
	m.SetHeader("Subject", subject)                           //设置邮件主题
	m.SetBody("text/plain", body)                             //设置邮件正文

	d := gomail.NewDialer(mailConn["host"], port, mailConn["user"], mailConn["pass"])

	err := d.DialAndSend(m)
	if err == nil {
		logs.Info("邮件发送成功")
	}
	return err
}

//func VerifyEmailFormat(email string) bool {
//	pattern := `^[0-9a-z][_.0-9a-z-]{0,31}@([0-9a-z][0-9a-z-]{0,30}[0-9a-z]\.){1,4}[a-z]{2,4}$`
//	reg := regexp.MustCompile(pattern)
//	return reg.MatchString(email)
//}
