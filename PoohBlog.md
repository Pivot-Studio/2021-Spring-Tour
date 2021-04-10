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

  

