package main

import (
	"encoding/json"
	"fmt"
)

type Star struct {
	Full_name  string
	Created_at string
}

/*type Starslice struct {
	Stars []Star
}*/

func Encode() []Star {
	var s []Star
	str := Getstar()
	//fmt.Println(str)
	err := json.Unmarshal([]byte(str), &s)
	if err != nil {
		fmt.Println(err)
	}
	return s
}
