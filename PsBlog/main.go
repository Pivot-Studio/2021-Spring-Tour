package main

import (
	"2021-Spring-Tour/PsBlog/api/models"
	"2021-Spring-Tour/PsBlog/api/router"
)

func main()  {
	defer models.Db.Close()
	r := router.InitRouter()
	_ = r.Run(":8000")
}
