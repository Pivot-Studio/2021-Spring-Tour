package main

import (
	orm "2021-Spring-Tour/PsBlog/api/database"
	"2021-Spring-Tour/PsBlog/api/router"
)

func main()  {
	defer orm.Db.Close()
	r := router.InitRouter()
	_ = r.Run(":8000")
}
