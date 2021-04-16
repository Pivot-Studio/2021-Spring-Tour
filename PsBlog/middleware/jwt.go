package middleware

import (
	"github.com/dgrijalva/jwt-go"
	"github.com/gin-gonic/gin"
	"net/http"
	"strings"
	"time"
)
var code int
var message string

var JwtKey = []byte("zhang1bo2si3")

type MyClaims struct {
	Username string `json:"username"`
	Password string `json:"password"`
	//标准模型
	jwt.StandardClaims
}

//生成token
func SetToken(username string, password string)(string, bool)  {
	//过期时间
	expireTime := time.Now().Add(10*time.Hour)
	SetClaims := MyClaims{
		Username: username,
		//Password: password,
		StandardClaims: jwt.StandardClaims{
			ExpiresAt: expireTime.Unix(),
			Issuer: "zbs",
		},
	}
	//jwt生成token固定的两个函数，且前一个一般用HS256
	reqClaim := jwt.NewWithClaims(jwt.SigningMethodHS256, SetClaims)
	token, err := reqClaim.SignedString(JwtKey)
	if err != nil{
		return "", false
	}
	return token, true
}

//验证token
func CheckToken(token string) (*MyClaims, bool)  {
	setToken, _ :=jwt.ParseWithClaims(token, &MyClaims{}, func(token *jwt.Token) (interface{}, error) {
		return JwtKey, nil
	})
	if key, _ := setToken.Claims.(*MyClaims); setToken.Valid{
		//如果有效
		return key, true
	}else{
		return nil, false
	}
}

//jwt中间件, 控制token
func JwtToken() gin.HandlerFunc {
	return func(c *gin.Context){
		code = 1
		tokenHeader := c.Request.Header.Get("Authorization")
		//header不存在
		if tokenHeader == ""{
			code = -1
			message = "请求头不存在"
			c.JSON(http.StatusOK, gin.H{
				"code": code,
				"msg": message,
			})
			c.Abort()
			return
		}
		//固定写法，套模板
		checkToken := strings.SplitN(tokenHeader, " ", 2)
		if len(checkToken) != 2 && checkToken[0] != "Bearer"{
			code = -1
			c.JSON(http.StatusOK, gin.H{
				"code": code,
				"msg": message,
			})
			c.Abort()
			return
		}
		key, check := CheckToken(checkToken[1])
		//验证失败
		if check == false{
			code = -1
			message = "验证失败"
			c.JSON(http.StatusOK, gin.H{
				"code": code,
				"msg": message,
			})
			c.Abort()
			return
		}
		if time.Now().Unix() > key.ExpiresAt{
			code = -1
			message = "token已过期"
			c.JSON(http.StatusOK, gin.H{
				"code": code,
				"msg": message,
			})
			c.Abort()
			return
		}
		c.Set("username", key.Username)
		c.Next()
	}
}