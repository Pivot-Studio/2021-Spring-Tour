package models

import (
	"errors"
	"github.com/beego/beego/v2/core/logs"
	beego "github.com/beego/beego/v2/server/web"
	"github.com/gomodule/redigo/redis"
	"github.com/beego/beego/v2/client/orm"
	"strconv"
	"time"
)

var RedisPool *redis.Pool

func init() {
	orm.RegisterModel(new(Post),new(Comment),new(User))

	var conn1, _ = beego.AppConfig.String("redisHost")
	var conn2, _ = beego.AppConfig.String("redisPort")
	var conn = conn1 + ":" + conn2
	var password, _ = beego.AppConfig.String("redisPass")
	var tmp, _ = beego.AppConfig.String("redisDB") 	//0号数据库
	var dbNum,_ = strconv.Atoi(tmp)
	dialFunc := func() (c redis.Conn, err error) {
		c, err = redis.Dial("tcp", conn)
		if err != nil {
			return nil, err
		}
		if password != "" {
			if _, err := c.Do("AUTH", password); err != nil {
				c.Close()
				return nil, err
			}
		}
		_, selecterr := c.Do("SELECT", dbNum)
		if selecterr != nil {
			c.Close()
			return nil, selecterr
		}
		return
	}
	// initialize a new pool
	RedisPool = &redis.Pool{
		MaxIdle:     3,
		IdleTimeout: 180 * time.Second,  //就是回收的时间吧180s
		Dial:        dialFunc,
	}

	InitPermissionRedisData()
}


//初始化缓存数据
func InitPermissionRedisData() {
	redisConn := RedisPool.Get()
	defer redisConn.Close()
	clearPermissionData(redisConn)
}


//清除相关redis数据
func clearPermissionData(redisConn redis.Conn) {
	redisKeys,err := redis.Strings(redisConn.Do("KEYS", "*"))
	if err != nil {
		return
	}
	redisConn.Do("MULTI")
	for _,v := range redisKeys {
		redisConn.Do("DEL", v)
	}
	redisConn.Do("EXEC")
}


//设置key value缓存
func SetRedisKeyValue(key, value string) {
	//var redisPrefix, _ = beego.AppConfig.String("redisPrefix")
	redisConn := RedisPool.Get()
	defer redisConn.Close()
	_,err := redisConn.Do("SET", key, value)
	if err != nil {
		logs.Info("set key-value error ",err)
		return
	}
}

//设置过期时间
func SetExpireTime(key string, time int64) {  //time
	redisConn := RedisPool.Get()
	defer redisConn.Close()
	_,err := redisConn.Do("EXPIRE", key, time)
	if err != nil {
		logs.Info("set expire time error ",err)
		return
	}
}

//删除单个key值
func DelRedisKey(key string) {
	redisConn := RedisPool.Get()
	defer redisConn.Close()
	_, err := redisConn.Do("DEL",key)
	if err != nil {
		logs.Info("delete key-value error ",err," key is ",key)
		return
	}
}

//获得value值
func GetRedisValue(key string) (interface{},error) {
	redisConn := RedisPool.Get()
	defer redisConn.Close()
	value,err := redisConn.Do("GET",key)
	if value == nil {
		logs.Info("Get value error",err)
		return "",errors.New("找不到")
	}
	logs.Info("redis查询的Redis值为",value)
	return value,nil
}