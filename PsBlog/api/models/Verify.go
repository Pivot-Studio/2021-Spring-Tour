package models

//定义邮箱和验证码结构
type Verify struct {
	Username string `json:"username"`
	Password string `json:"password"`
	Email string `json:"email"`
	VerifyCode string `json:"verify_code"`
}
