package apis

import (
	model "2021-Spring-Tour/PsBlog/api/models"
	"github.com/astaxie/beego/validation"
	"github.com/gin-gonic/gin"
	"log"
	"net/http"
	"strconv"
	"time"
)

//获得多个文章
func GetArticles(c *gin.Context){
	//实现分页功能
	pageSize, _ := strconv.Atoi( c.Query("pagesize"))
	pageNum, _ := strconv.Atoi(c.Query("pagenum"))
	//gorm中将下列值设为-1则limit/offset不受限制
	if pageSize == 0{
		pageSize = -1
	}
	if pageNum == 0{
		pageNum = -1
	}
	data := model.GetAllArticles(pageSize, pageNum)
	code = 1
	message = "查询成功"
	c.JSON(http.StatusOK, gin.H{
		"status": code,
		"data": data,
		"msg": message,
	})
}

//获取指定文章
func GetArticle(c *gin.Context)  {
	//通过id查询
	//com包进行类型转换
	id, _ := strconv.Atoi(c.Param("id"))
	var article model.Articles
	flag := article.GetArticleByID(int(id))
	if flag == true{
		c.JSON(http.StatusOK, gin.H{
			"code" : 1,
			"msg" : "查找成功",
			"data" : article,
		})
	}else{
		c.JSON(http.StatusOK, gin.H{
			"code" : -1,
			"msg" : "查找失败",
		})
	}
}

//添加文章
func AddArticle(c *gin.Context) {
	var d model.Articles
	_ = c.ShouldBindJSON(&d)
	//tagId, _ := strconv.Atoi(c.Query("tag_id"))
	//title := c.Query("title")
	//content := c.Query("content")
	//datetime := c.Query("time")
	//beego/validation对数据进行合法判断
	valid := validation.Validation{}
	valid.Min(d.TagId, 1, "tag_id").Message("标签ID必须大于0")
	valid.Required(d.Title, "title").Message("标题不能为空")

	if !valid.HasErrors(){
		//data["tag_id"] = tagId
		//data["title"] = title
		//data["content"] = content
		d.DateTime= time.Now() //当前时间
		var article model.Articles
		//添加文章
		_ = article.AddArticle(&d)
		c.JSON(http.StatusOK, gin.H{
			"code" : 1,
			"message": "添加成功",
			"data" : d,
		})
	}else{
		//数据不合法
		for _, err := range valid.Errors {
			log.Printf("err.key: %s, err.message: %s", err.Key, err.Message)
		}
		c.JSON(http.StatusOK, gin.H{
			"code" : -1,
			"message": "添加失败",
		})
	}
}
//更新文章
func UpdateArticle(c *gin.Context) {

	var d model.Articles
	_ = c.ShouldBindJSON(&d)

	valid := validation.Validation{}

	id, _ := strconv.Atoi(c.Param("id"))

	valid.Min(d.TagId, 1, "tag_id").Message("标签id必须大于0")
	valid.Required(d.Title, "title").Message("标题不能为空")
	//内容可以为空
	if !valid.HasErrors() {
		d.DateTime = time.Now() //当前时间
		var article model.Articles
		//添加文章
		article.UpdateArticle(id, &d)
		c.JSON(http.StatusOK, gin.H{
			"code":    1,
			"message": "更新成功",
			"data":    d,
		})
	} else {
		//数据不合法
		for _, err := range valid.Errors {
			log.Printf("err.key: %s, err.message: %s", err.Key, err.Message)
		}
		c.JSON(http.StatusOK, gin.H{
			"code":    -1,
			"message": "更新失败",
		})
	}
}

//删除文章
func DeleteArticle(c *gin.Context)  {
	id, _ := strconv.Atoi(c.Param("id"))
	var article model.Articles
	flag := article.DeleteArticle(int(id))
	if flag == true{
		c.JSON(http.StatusOK, gin.H{
			"code" : 1,
			"msg" : "删除成功",
			"data" : id,
		})
	}else{
		c.JSON(http.StatusOK, gin.H{
			"code" : -1,
			"msg" : "删除失败",
		})
	}
}