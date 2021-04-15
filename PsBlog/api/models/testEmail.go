package models

import (
	"fmt"
	"log"
	"math/rand" //随机数
	"net/smtp"
	"strconv"
	"time"
	//"encoding/base64"
)


//发送方：我的邮箱
const HostEmail = "1397157763@qq.com"
//smtp服务的授权码
const EmailPasswd = "ldtlepxkmidnjhgb"

func SendEmail(email string) string{
	auth := smtp.PlainAuth("", HostEmail, EmailPasswd, "smtp.qq.com")
	rand.Seed(time.Now().Unix())
	//生成随机数
	num := rand.Intn(10000)
	//邮件格式
	str := fmt.Sprintf("From:1397157763@qq.com\r\nTo:%s\r\nSubject:verifycode\r\n\r\n验证码为： %d\r\n", email,num)
	msg := []byte(str)
	err := smtp.SendMail("smtp.qq.com:25", auth, "1397157763@qq.com", []string{email}, msg)
	if err != nil {
		log.Fatal(err)
	}
	return strconv.Itoa(num)
}
