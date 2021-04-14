package main

import (
	"fmt"
	"io/ioutil"
	"net/http"
	"plz"
	"time"
)

func Long_poll1() string {

	url := "https://api.github.com/users/Destined777/events"
	method := "GET"

	client := &http.Client{}
	req, err := http.NewRequest(method, url, nil)

	if err != nil {
		fmt.Println(err)
		return ""
	}
	req.Header.Add("Authorization", plz.Token())

	res, err := client.Do(req)
	if err != nil {
		fmt.Println(err)
		return ""
	}
	defer res.Body.Close()

	body1, err := ioutil.ReadAll(res.Body)
	if err != nil {
		fmt.Println(err)
		return ""
	}
	return string(body1)
}
func Long_poll2() string {
	url := "https://api.github.com/users/Destined777/events"
	method := "GET"

	client := &http.Client{}
	req, err := http.NewRequest(method, url, nil)

	if err != nil {
		fmt.Println(err)
		return ""
	}
	req.Header.Add("Authorization", plz.Token())

	res, err := client.Do(req)
	if err != nil {
		fmt.Println(err)
		return ""
	}
	defer res.Body.Close()

	body1, err := ioutil.ReadAll(res.Body)
	if err != nil {
		fmt.Println(err)
		return ""
	}
	return string(body1)
}
func digui() {
	str1 := Long_poll1()
	time.Sleep(5 * time.Second)
	str2 := Long_poll2()
	if str1 == str2 {
		time.Sleep(5 * time.Second)
		digui()
	}
	if str1 != str2 {
		Email1()
	}
}
func main() {
	digui()
}
