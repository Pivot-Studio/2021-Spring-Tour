# PoohBlog

# 1.

- datetime的java写入方法

  ```java
  Date date = new Date();  
  Timestamp timeStamp = new Timestamp(date.getTime());  //直接写入timeStamp即可
  ```

  

- Mysql 与 java 的时间类型 
  MySql的时间类型有 Java中与之对应的时间类型 
    date                 java.sql.Date 
    Datetime          java.sql.Timestamp 
    Timestamp       java.sql.Timestamp 
    Time                 java.sql.Time 
    Year                 java.sql.Date 
  转换相互转换：
  Date date = new Date();//获得系统时间. 
  String nowTime = new SimpleDateFormat(“yyyy-MM-dd HH:mm:ss”).format(date);
  Timestamp goodsC_date = Timestamp.valueOf(nowTime);//把时间转换 
  java.util.Date 是java.sql.Date的父类





- 遇到问题 `一个或多个筛选器启动失败。完整的详细信息将在相应的容器日志文件中找到`

  解决：

  需要在project structure 中的web目录下增加一个lib文件夹导入依赖

- 遇到问题`@ResponseBody直接返回json数据时中文乱码`

  解决：在`@RequestMapping中设置属性produces = "text/html;charset=UTF-8"`

- 约定：0为失败，1为成功

- 若传入为表单数据，则Controller参数为有表单参数的对象，postman中通过x-www-form-unlencoded提交
- 若传入参数为json数据，在controller参数前加注释`@RequestBody`
- 关于controller传入参数的博文：https://blog.csdn.net/weixin_39220472/article/details/80725574

- 知识点：

  ```sql
  select * from Blog orderby dt desc降序asc升序
  ```

# 2.邮件收发

- 通过java.mail实现

- 四个类
  - `Session`定义整个程序所需环境信息，主机名，端口号，采用的邮件发送
    和接受协议，此类创建`Transport`
  - `Transport`发送邮件,阴影Message
  - `Message`表示一封电子邮件
  - `Store`接受对象，用来接受邮件

# 3.jwt的使用

参考文章：https://www.huaweicloud.com/articles/e5ac59dfae65090c44d5835a6642cd48.html

![IMG_4140](/Users/txiao/Downloads/IMG_4140.JPG)

- quickStart

  - 生成token

  ```java
  import io.jsonwebtoken.Jwts;
  import io.jsonwebtoken.SignatureAlgorithm;
  import io.jsonwebtoken.security.Keys;
  import java.security.Key;
  
  // We need a signing key, so we'll create one just for this example. Usually
  // the key would be read from your application configuration instead.
  Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
  
  String jws = Jwts.builder().setSubject("Joe").signWith(key).compact();
  ```
  - 验证token

  ```java
  assert Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jws).getBody().getSubject().equals("Joe");
  ```

  - token非法

  ```java
  try {
  
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(compactJws);
  
      //OK, we can trust this JWT
  
  } catch (JwtException e) {
  
      //don't trust the JWT!
  }
  ```

- key的生成

  1. 生成

  ```java
  SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256); //or HS384 or HS512
  ```

  随机生成一个key

  2. 保存

  ```java
  String secretString = Encoders.BASE64.encode(key.getEncoded());
  ```

- 签名jwt

1. Header

```
{
  "alg": "HS256"
}
```

body

```
{
  "sub": "Joe"
}
```

2. Json格式

```java
String header = '{"alg":"HS256"}'
String claims = '{"sub":"Joe"}'
```

3. 获得utf-8 bytes 并切使用Base64URL-encode

```java
String encodedHeader = base64URLEncode( header.getBytes("UTF-8") )
String encodedClaims = base64URLEncode( claims.getBytes("UTF-8") )
```

4. 将编码头与编码身体用点连接

```java
String concatenated = encodedHeader + '.' + encodedClaims
```

5. 获得签名

```java
Key key = getMySecretKey()//自己实现
byte[] signature = hmacSha256( concatenated, key )
```

6. 生成token

```java
String jws = concatenated + '.' + base64URLEncode( signature )
  //这个被叫做jws，jwt的缩写
```

- 创建一个jws

  ```java
  String jws = Jwts.builder() // (1)
  
      .setSubject("Bob")      // (2) 
  
      .signWith(key)          // (3)
       
      .compact();             // (4)
  ```

- 设置header

  ```java
  String jws = Jwts.builder()
  
      .setHeaderParam("kid", "myKeyId")
      
      // 该方法可以调用多次
  ```

- 表头header的设置

```java
Map<String,Object> header = getMyHeaderMap(); //implement me

String jws = Jwts.builder()

  .setHeader(header)

  // ... etc ...
```

- claims的设置
  - `setIssuer`: sets the [`iss` (Issuer) Claim](https://tools.ietf.org/html/rfc7519#section-4.1.1)
  - `setSubject`: sets the [`sub` (Subject) Claim](https://tools.ietf.org/html/rfc7519#section-4.1.2)
  - `setAudience`: sets the [`aud` (Audience) Claim](https://tools.ietf.org/html/rfc7519#section-4.1.3)
  - `setExpiration`: sets the [`exp` (Expiration Time) Claim](https://tools.ietf.org/html/rfc7519#section-4.1.4)
  - `setNotBefore`: sets the [`nbf` (Not Before) Claim](https://tools.ietf.org/html/rfc7519#section-4.1.5)
  - `setIssuedAt`: sets the [`iat` (Issued At) Claim](https://tools.ietf.org/html/rfc7519#section-4.1.6)
  - `setId`: sets the [`jti` (JWT ID) Claim](https://tools.ietf.org/html/rfc7519#section-4.1.7)

- 自定义claims

  ```
  String jws = Jwts.builder()
  
      .claim("hello", "world")
      
      // ... etc ...
  ```

- 直接传入claims类

  ```java
  Claims claims = Jwts.claims();
  
  populate(claims); //implement me
  
  String jws = Jwts.builder()
  
      .setClaims(claims)
      
      // ... etc ...
  ```

- claims类使用map类实现

  ```java
  Map<String,Object> claims = getMyClaimsMap(); //implement me
  
  String jws = Jwts.builder()
  
      .setClaims(claims)
      
      // ... etc ...
  ```

  

4. Spring拦截器的使用

```xml
<!-- 不拦截登录的请求 -->
			<mvc:exclude-mapping path="/loginUser.do"/>
```

TODO：在增删改查操作之前添加验证机制，看token的账户是否与提交增删改查对象的用户一样

# 4.redis基本命令

- 基本命令：

```bash
set key value
get key
expire key second //设定该key存活时间
ttl key //查看key存活的剩余时间
type key //查看当前key的类型
keys * //查看当前数据库所有key
select number //切花到指定数据库
move key number //移动指定key到指定数据库
exists key //key是否存在
```

- string类型命令:

```bash
append key string //在指定key后面增加string
strlen key //获取字符串长度
--------------------------------------------------
incr key //key的值+1
decr key //key值-1
incrby key step//按照步长step增加
decrby key step //按照步长step减少
---------------------------------------------------
getrange key start end //获得key值字符串的一部分
getrange key 0 -1//查看全部字符串
set key offset value //设置key值从0开始偏移offset后设置value
---------------------------------------------------
setex key second value //设置的同时设置存活时间
setnx key value //如果不存在则设置，存在返回0，不设置
-------------------------------------------------------
mset k1 v1 k2 v2 k3 v3... //批量设置值
mget k1 k2 k3...//批量获取值
-----------------------------------------------------
msetnx k1 v1 k2 v2 k3 v3..//原子性操作，一起成功或一起失败
-----------------------------------------------------
getset key value //若存在，返回原来值设置新值，若不存在，返回nil，设置新值
```

- list类型命令:

把list想象成一个容器l开头代表从左边操作，r开头代表从右边操作

```
lpush list value//从左边插入
lrange list start end //显示list元素，从左到右
rpush list value //从右边插入
-----------------------------------------------------
lpop list //返回最左边元素
rpop list //返回最右边元素
-----------------------------------------------------
lindex number //返回index元素
llen list //返回列表长度
-----------------------------------------------------
lrem list count value//移除指定count的value
ltrim list start end //从start到end截断list
rpoplpush source destination//移除最后一个元素添加到新列表中
lset list index value //设置指定下标的值不存在会报错
-----------------------------------------------------
linsert list before|after desval val//在desval前面或者后面插入val
```

- set类型命令:

```
sadd set val//增加元素
smembers set //显示所有元素
sismember set val//返回val是否为set元素
scard set//返回set元素个数
srem set value //移除set中的val
srandmember set [count] //随机获得set中的指定count的元素
spop set //随机移除元素
smove src des val //将集合src中的val元素移动到des中
-----------------------------------------------------
sdiff key [key...] //返回key中有其他key没有的
sinter key [key...] //返回交集
sunion key [key...] //返回并集
```

- hash类型命令:

```
hset hashset field value//设置key-value到hashset中
hget hashset field //得到hashset中field映射的值
-----------------------------------------------------
hmset hashset f1 v1 f2 v2 ... //设置多个，原子性操作
hmget hashset f1 f2 f3 //得到多个
hdel hashset f1//删除hash表中key为f1的值
hgetall hashset //获得表中所有key-value
hlen hashset //获得hash表的key-value个数
hexists hashset filed //是否存在该key
hkeys hashset //获得所有的key
hvals hashset //获得所有的val
hincr hashset field step
hdecr hashset field step
hincr hashset field 
hdecr hashset field
hsetnx hashset field val//not exist
```

- 事务

  - 开启事务`multi`
  - 命令入队
  - 执行事务`exec`

  - 放弃事务`discard`

- 加锁（乐观锁）

  `watch`

  此时对watch对象的操作是加锁的

  `multi`开启事务

  `exec`执行命令

  `watch`失败后需要使用`unwatch`解锁



# 5.Jedis的使用

导入依赖

```xml
<!-- https://mvnrepository.com/artifact/redis.clients/jedis -->
<dependency>
  <groupId>redis.clients</groupId>
  <artifactId>jedis</artifactId>
  <version>2.9.0</version>
</dependency>

<!-- https://mvnrepository.com/artifact/com.alibaba/fastjson -->
<dependency>
  <groupId>com.alibaba</groupId>
  <artifactId>fastjson</artifactId>
  <version>1.2.75</version>
</dependency>
```

- 连接数据库
- 操作命令
- 断开连接

# 6.数据库与缓存的数据一致性

参考链接：https://blog.kido.site/2018/11/24/db-and-cache-preface/



考虑到并发问题以及项目的请求数不高的情况采用以下策略：

1. 写数据，先删除缓存，再更新数据库，然后数据库内容回刷缓存
2. 读数据，先访问缓存，缓存没有则访问数据库，数据库数据与缓存同步

使用分页查询的指令

`ZREVRANGE key start end` 

利用hash表与zset进行存储

hash表存储：id json

zset：加入时间 id



增加操作：

zset中增加，hash表中增加



改操作：

zset中不变，hash表中改



删除操作：

zset中删除id，hash表中删除



查操作：

zset中分页查询，通过hash表取具体值