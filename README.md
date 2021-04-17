# README

## 1.数据库设计

- Blog（博文）

![未命名文件](https://github.com/nick887/PoohBlogPic/blob/main/未命名文件.jpg)

- comment（评论）

![未命名文件 (1)](https://github.com/nick887/PoohBlogPic/blob/main/未命名文件%20(1).jpg)

- replyComment(回复评论的评论)

![未命名文件 (2)](https://github.com/nick887/PoohBlogPic/blob/main/未命名文件%20(2).jpg)

- type(文章类型)

![未命名文件 (3)](https://github.com/nick887/PoohBlogPic/blob/main/未命名文件%20(3).jpg)

- user(用户)

![未命名文件 (4)](https://github.com/nick887/PoohBlogPic/blob/main/未命名文件%20(4).jpg)

## 2.架构配置

采用ssm框架，缓存使用redis

- 配置dao层

```xml
<!--    关联数据库配置文件-->
<context:property-placeholder location="classpath:database.properties"/>
<!--    采用c3p0连接池-->
<bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
  <property name="driverClass" value="${jdbc.driver}"/>
  <property name="jdbcUrl" value="${jdbc.url}"/>
  <property name="user" value="${jdbc.username}"/>
  <property name="password" value="${jdbc.password}"/>
</bean>
<!--    sqlSessionFactory-->
<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
  <property name="dataSource" ref="dataSource"/>
  <property name="configLocation" value="classpath:mybatis-config.xml"/>
</bean>
<!--    配置dao接口扫描包，动态实现Dao接口可以注入到Spring容器中-->
<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
  <!--        注入SqlSessionFactory-->
  <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
  <!--        要扫描的dao包-->
  <property name="basePackage" value="com.nick.dao"/>
</bean>
```

-  配置service层

```xml
<!--扫描service下的包-->
<context:component-scan base-package="com.nick.service"/>
<!--声明式事务配置-->
<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
  <!--        注入数据源-->
  <property name="dataSource" ref="dataSource"/>
</bean>
```

- 配置mvc层

```xml
<!--    注解驱动-->
<mvc:annotation-driven/>
<!--    静态资源过滤-->
<mvc:default-servlet-handler/>
<!--    扫描包：controller-->
<context:component-scan base-package="com.nick.controller"/>
```

- applicationContext配置以及工具类的bean注入

```xml
<!--key为用户邮箱，value为验证uid-->
<bean id="UIDMaiVerification" class="java.util.concurrent.ConcurrentHashMap"/>
<!--key为用户邮箱，value为验证增加用户的用户信息-->
<bean id="UserMap" class="java.util.concurrent.ConcurrentHashMap"/>
<!--key为用户邮箱，value为验证用户修改密码的信息-->
<bean id="UserModifyInfo" class="java.util.concurrent.ConcurrentHashMap"/>
<!--连接本地redis-->
<bean id="Jedis" class="redis.clients.jedis.Jedis">
  <constructor-arg name="host" value="127.0.0.1"/>
  <constructor-arg name="port" value="6379"/>
</bean>
<!--    拦截器配置-->
<mvc:interceptors>
  <mvc:interceptor>
    <mvc:mapping path="/blog/*"/>
    <mvc:mapping path="/comment/*"/>
    <mvc:mapping path="/replyComment/*"/>
    <mvc:mapping path="/user/modifyPassWord"/>
    <mvc:exclude-mapping path="/blog/queryAllBlogs"/>
    <mvc:exclude-mapping path="/blog/queryBlogById/*"/>
    <mvc:exclude-mapping path="/comment/queryCommentByPagingDesc/**"/>
    <mvc:exclude-mapping path="/replyComment/queryReplyCommentByPagingDesc/**"/>
    <bean class="com.nick.config.JwtInterceptor"/>
  </mvc:interceptor>
</mvc:interceptors>
```

- mybatis配置

```xml
<configuration>
  <!--    设置标准日志，在终端输出-->
  <settings>
    <setting name="logImpl" value="STDOUT_LOGGING"/>
  </settings>
  <!--    设置别名-->
  <typeAliases>
    <package name="com.nick.pojo"/>
  </typeAliases>
  <!--    注册mapper-->
  <mappers>
    <mapper class="com.nick.dao.BlogMapper"/>
    <mapper class="com.nick.dao.UserMapper"/>
    <mapper class="com.nick.dao.CommentMapper"/>
    <mapper class="com.nick.dao.ReplyCommentMapper"/>
  </mappers>
</configuration>
```

## 3.较复杂接口处理

### 1.BlogController

考虑到实际应用中不会出现查询数据库中所有文章的情况，故不予考虑

- 分页查询博文测试：

```java
public String queryCommentByPagingDesc(@ApiParam(required = true,value = "页码") @PathVariable int pageIndex,@ApiParam(required = true,value = "单页博文数目") @PathVariable int stepSize)
```



![未命名文件 (5)](https://github.com/nick887/PoohBlogPic/blob/main/未命名文件%20(5).png)

测试：

首先`flushall`清空redis数据库

输入参数：

![截屏2021-04-15 下午6.11.49](https://github.com/nick887/PoohBlogPic/blob/main/截屏2021-04-15%20下午6.11.49.png)

在idea终端通过日志看到向mysql进行了查询

![截屏2021-04-15 下午6.13.09](https://github.com/nick887/PoohBlogPic/blob/main/截屏2021-04-15%20下午6.13.09.png)

查看redis数据库

![截屏2021-04-15 下午6.14.36](https://github.com/nick887/PoohBlogPic/blob/main/截屏2021-04-15%20下午6.14.36.png)

可以看到redis已经缓存数据

再次输入相同的参数

idea终端日志未出现mysql查询日志，说明redis缓存运行成功

- 删除博文测试

删除前redis zset数据库

![截屏2021-04-15 下午6.06.37](https://github.com/nick887/PoohBlogPic/blob/main/截屏2021-04-15%20下午6.06.37.png)

删除后redis zset数据库

![截屏2021-04-15 下午6.08.02](https://github.com/nick887/PoohBlogPic/blob/main/截屏2021-04-15%20下午6.08.02.png)



可以看到redis确实工作了

### 2.User认证与操作

#### 1.用户的注册

流程图:

![mailVeritify](https://github.com/nick887/PoohBlogPic/blob/main/mailVeritify.jpg)

该流程有问题：

当出现大量只请求不验证的情况时可能会出现内存泄漏，后续改进应该采用redis，自动设置一个过期时间

测试：

![截屏2021-04-15 下午6.47.46](https://github.com/nick887/PoohBlogPic/blob/main/截屏2021-04-15%20下午6.47.46.png)

输入上述参数，随后邮箱收到邮件

![截屏2021-04-15 下午6.48.58](https://github.com/nick887/PoohBlogPic/blob/main/截屏2021-04-15%20下午6.48.58.png)

此时查看数据库

![截屏2021-04-15 下午6.49.45](https://github.com/nick887/PoohBlogPic/blob/main/截屏2021-04-15%20下午6.49.45.png)

数据库中没有注册用户

点击邮件链接

`http://localhost:8080/user/verification/2975684744@qq.com/fcc1d1fcc51d4dbf971afd5b1df2a638-26622085477361685042975684744@qq.com`跳入如下地址

再次查看数据库

![截屏2021-04-15 下午6.51.14](https://github.com/nick887/PoohBlogPic/blob/main/截屏2021-04-15%20下午6.51.14.png)

数据库添加到user信息

修改密码同样使用以上原理

登陆内容略

#### 2.JWT实现用户认证

- 拦截器的配置

```xml
<mvc:interceptors>
  <mvc:interceptor>
    <mvc:mapping path="/blog/*"/>
    <mvc:mapping path="/comment/*"/>
    <mvc:mapping path="/replyComment/*"/>
    <mvc:mapping path="/user/modifyPassWord"/>
    <mvc:exclude-mapping path="/blog/queryAllBlogs"/>
    <mvc:exclude-mapping path="/blog/queryBlogById/*"/>
    <mvc:exclude-mapping path="/comment/queryCommentByPagingDesc/**"/>
    <mvc:exclude-mapping path="/replyComment/queryReplyCommentByPagingDesc/**"/>
    <bean class="com.nick.config.JwtInterceptor"/>
  </mvc:interceptor>
</mvc:interceptors>
```

项目的文件目录：

![截屏2021-04-15 下午6.20.49](https://github.com/nick887/PoohBlogPic/blob/main/截屏2021-04-15%20下午6.20.49.png)

![截屏2021-04-15 下午6.21.52](https://github.com/nick887/PoohBlogPic/blob/main/截屏2021-04-15%20下午6.21.52.png)

拦截器编写：

```java
public class JwtInterceptor implements HandlerInterceptor {
  //return true执行下一个拦截器，放行
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    response.setCharacterEncoding("utf-8");
    String jwt=request.getHeader("Authorization");
    if(jwt==null)
    {
      return false;
    }
    Claims claims= JwtUtils.parseJWT(jwt);
    //若token不符合直接拦截
    if(claims==null){
      return false;
    }
    else
    {
      //isNotExpired 返回true表示未过期 不需要拦截
      //isNotExpired 返回false表示已经过期，拦截
      Boolean isNotExpired=JwtUtils.isNotExpired(claims);
      if(isNotExpired==false) {
        return isNotExpired;
      }
      return isNotExpired;
    }
  }
  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
  }
  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
  }
}
```

流程图：

![jwt](https://github.com/nick887/PoohBlogPic/blob/main/jwt.jpg)

存在问题：

可能有人可被验证过的jwt恶意修改他人id的blog与评论

后续需要在增删改的过滤器上再加一层过滤器过滤，判断上传的writerId是否符合jwt解析的id

测试：

登陆用户获得返回jwt

![截屏2021-04-15 下午7.02.15](https://github.com/nick887/PoohBlogPic/blob/main/截屏2021-04-15%20下午7.02.15.png)

在加入拦截器的情况下增加blog

![截屏2021-04-15 下午7.10.46](https://github.com/nick887/PoohBlogPic/blob/main/截屏2021-04-15%20下午7.10.46.png)

成功

![截屏2021-04-15 下午7.11.15](https://github.com/nick887/PoohBlogPic/blob/main/截屏2021-04-15%20下午7.11.15.png)

去掉jwt试一下

![截屏2021-04-15 下午7.12.06](https://github.com/nick887/PoohBlogPic/blob/main/截屏2021-04-15%20下午7.12.06.png)

失败，后续增加一下返回的消息

## 4.结语

还有不少接口未在文档中显示，项目内置了swagger工具，需要测试可以自行运行项目后访问`http://localhost:8080/swagger/index.html`进行测试

