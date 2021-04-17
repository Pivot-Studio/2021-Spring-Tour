package main

import (
	"encoding/json"
	"fmt"
	"io/ioutil"
	"sort"
)

type Star struct {
	Full_name  string
	Created_at string
}

/*type Starslice struct {
	Stars []Star
}*/

func Encode() []Star { //解析所需的json信息
	var s []Star
	str := Getstar()
	//fmt.Println(str)
	err := json.Unmarshal([]byte(str), &s)
	if err != nil {
		fmt.Println(err)
	}
	return s
}
func Add(S []Star, i int) []Star { //将解析后的目标仓库添加到收藏夹里
	s := Encode()
	fmt.Println(s[i])
	S = append(S, s[i])
	fmt.Println(S)
	return S
}
func Rmfirst(S []Star) []Star { //删除目标文件夹的第一个仓库
	S = S[1:]
	return S
}
func Rmmid(S []Star, i int) []Star { //删除目标文件夹的第i个仓库
	S = append(S[:i], S[i+1:]...)
	return S
}
func Rmlast(S []Star) []Star { //删除目标收藏夹的最后一个仓库
	if len(S) > 0 {
		S = S[:len(S)-1]
	}
	return S
}
func main() {
	var s1 []Star
	s1 = Add(s1, 0)
	s1 = Add(s1, 1)
	fmt.Println(s1)
	s1 = Rmlast(s1)
	fmt.Println(s1)
	s1 = Add(s1, 1)
	sort.Slice(s1, func(i, j int) bool { //将收藏夹内仓库按创建时间升序排列
		return s1[i].Created_at < s1[j].Created_at
	})
	fmt.Println(s1)
	fileName := "starsfile"
	fileContent, err := json.Marshal(s1)
	if err = ioutil.WriteFile(fileName, fileContent, 0666); err != nil {
		fmt.Println("Writefile Error =", err)
	}
	InitDB()
	/*for i := 0; i < len(s1); i++ {
		InsertUser(s1[i])
	}*/
	//defer DB.Close()
}
