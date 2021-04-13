package apis

import (
	"2021-Spring-Tour/PsBlog/api/models"
	"github.com/gin-gonic/gin"
	"net/http"
	"strconv"
)


//新增评论
func AddComment(c* gin.Context)  {
	var data models.Comments
	_ = c.ShouldBindJSON(&data)
	flag := models.AddComment(&data)
	if flag == true{
		code = 1
		message = "添加评论成功"
	}else {
		code = -1
		message = "评论失败"
	}
	c.JSON(http.StatusOK, gin.H{
		"status": code,
		"data": data,
		"msg": message,
	})
}

//获取单个评论
func GetComment(c *gin.Context)  {
	id, _ := strconv.Atoi(c.Param("id"))
	data , flag := models.GetComment(id)
	if flag == true{
		code = 1
		message = "获取成功"
	}else {
		code = -1
		message = "获取失败"
	}
	c.JSON(http.StatusOK, gin.H{
		"status": code,
		"data": data,
		"msg": message,
	})

}

//删除评论
func DeleteComment(c *gin.Context)  {
  	id, _ := strconv.Atoi(c.Param("id"))
	flag := models.DeleteComment(id)
	if flag == true{
		code = 1
		message = "删除成功"
	}else {
		code = -1
		message = "删除失败"
	}
	c.JSON(http.StatusOK, gin.H{
		"status": code,
		"msg": message,
	})
}

//分页返回评论列表
func GetCommentList(c *gin.Context)  {
	pageSize, _ := strconv.Atoi( c.Query("pagesize"))
	pageNum, _ := strconv.Atoi(c.Query("pagenum"))

	data, total, flag := models.GetCommentList(pageSize, pageNum)
	if flag == true{
		code = 1
		message = "获取成功"
	}else {
		code = -1
		message = "获取失败"
	}
	c.JSON(http.StatusOK, gin.H{
		"status": code,
		"data": data,
		"total": total,
		"msg": message,
	})
}