# 工作日志

<!-- 
## 2021.04.09

### Developing Log
1. 选择任务：实现一个博客后端
2. 建立一些文件夹

### Studying Log
1. Markdown的学习
2. Github的熟悉
	```
	git clone "url"
	git add .
	git commit -m "FEA/FIX:Commit"
	git push
	git pull
	git branch
	git checkout my-branch-name
	```
3. Spring的学习
	* 优点：轻量级、一站式
	* 解耦的实现：IOC（控制反转）与DI（依赖注入）
	* 配置步骤
-->

## 2021.04.09 (rewritten version)

*由于当天写日志的日志较晚，而完成的任务又较多，所以日志质量不太好，决定重写。作出自我批评，以后要避免同类情况*

### 开发日志
1. 选择任务：实现一个博客后端
2. 在github里建立自己的分支
3. 对任务进行了总体规划，分为三个阶段：第一阶段：实现发帖功能；第二阶段：实现评论和身份认证功能；第三阶段：实现数据缓存功能

### 学习日志
1. 对任务中出现的尚未掌握的知识点进行资料的搜集：
	* 第一阶段：Spring、restful api、json
	* 第二阶段 良好的数据库表的设计、redis、JWT
2. 学习Markdown语法
3. 熟悉github的操作
	```
	git clone "url"
	git add .
	git commit -m "FEA/FIX:Commit"
	git push
	git pull
	git branch
	git checkout my-branch-name
	```
4. 学习Spring
	* 优点：轻量级、一站式
	* 解耦的实现：IOC（控制反转）与DI（依赖注入）
	* 知识点：applicationContext.xml主配置文件与bean、property标签；控制层与声明父接口引用、set方法注入对象；service层；测试类。
	
	
## 2021.04.10

### 开发日志
1. 重构了github里自己的分支
2. 使用Maven正式创建了项目

### 学习日志
1. 学习Spring
	* 两种配置方式：xml配置与annotation(注解）配置
	* 注解配置：多一个aop.jar包；主配置文件多一个context命名空间约束
	* 常用注解：四层注解（@Controller,@Service,@Repository,@Component）；扫描注解（主配置文件/配置类@Configuration、@ComponentScan）；用于依赖注入的注解（@Autowired、@Qualifier）
	* controller、service层：面向引用、set注入对象、调用服务方方法
	* 测试类：方法前注解@Test；方法中三步走（获取applicationContext对象并调用getBean方法获取对象、调用相关方法）
2. 找到了更多有用的资料：关于ssm框架、Maven、MyBatis、SpringBoot
3. 学习Maven
	* 认识Maven：一个项目构建工具
	* 使用Maven创建Web项目：创建Maven项目、添加Web模块（File -> Program Structure）、修改Web模块的信息；该步骤有所创建Web项目纯净的优点
4. 学习MyBatis
	* 认识MyBatis：一个dao层框架
	* 优点：dao层零实现（毋须写实现类）、无需编写结果集ResultSet和javaBean属性之间的映射关系的java代码，通过xml配置文件配置即可
	* 配置步骤：导包、创建主配置文件mybatis-config.xml、创建MyBatisUtil工具类、创建实体类、创建操作接口及映射文件
	* CRUD操作：映射文件法/注解法
	* db.properties文件与properties标签
	
### 问题及解决
1. 问题：由于之前的实践没有做到前后端分离，所以不知道怎么进行接口的编写，虽然理解了restful api的思想，但还没有达到能着手编码的层次；	解决：经过补习框架的知识，明天应该可以进入接口的编写
2. 问题：还未能深刻地体会到IOC和DI的优点；
3. 问题：在学习spring的纯注解开发方式时不知道classpath的含义，查找资料发现别人的项目结构与自己不同；	解决：学习了Maven的相关知识知道了实际开发时的项目结构，明天可以继续研究classpath的含义


## 2021.04.11

### 开发日志
1. 初步编写了Blog类和User类的各层
2. 用RESTful风格编写了BlogController对get请求的响应

### 学习日志
1. 学习SpringMVC：Controller接收请求和数据以及响应，关键注解@RestController,@RequestMapping,@PathVarible
2. 学习RESTful api：注解@GetMapping等，ResponseEntity<>	返回Json

### 问题及解决
1. 不会进行接口测试，postman和rest client暂时没学会

## 2021.04.14

### 解决了一系列问题
1. 明白了接口测试并不是多么难，前几天使用postman失败的原因并不是自己没学会，而是因为Intellij自动为项目设置了上下文路径，导致url定位失败
2. 在为spring整合Mybatis框架时一直失败，经过请教他人发现了许多小细节上的错误，比如pom.xml未添加spring-jdbc的依赖、mysql-connector-java的版本不匹配造成启动项目后控制台进入死循环、mapper映射文件没有放在resource目录里而是放在mapper类同包环境下、同时使用了注解配置和xml配置等
3. 对spring的IOC和DI有了进一步的理解，上一层的类是面向接口的，不必实现写出下一层的实现，实现了开发过程中的解耦，不然开发过程中一片红字（这一点在我之前没有用框架而是用原始的servlet和jdbc开发时深有体会）
4. 理解了classpath的含义，classpath指的是一类目录，在Intellij中标记的sources boot、resources boot都属于classpath
5. 在使用一个工具类转换返回结果为json的过程中遇到了服务器500的错误，错误说明为未找到工具类的转换器，上网一搜发现是因为没写get和set方法

### 开发日志
1. 创建了blog、user和commit的数据库表
2. 实现了从浏览器发请求访问到数据库中记录并以json返回，说明ssm终于整合成功了
3. 实现了从浏览器通过post请求增加博客的功能，并编写了一个工具类用于转换返回结果为Json

### 近日问题反思
1. 目前看来任务进展偏慢了，自己在选题目时低估了难度，从零开始学spring框架不是那么简单的，其间我认为最难的是ssm的整合，需要写很多配置语句，网上的文字教程各有各的风格，视频教程又偏过时，最后还是靠咨询了技术大佬才成功解决了问题
2. 在开发过程中发现自身存在以下问题：不能很好的贯彻自顶向下设计的思想，开发时东一榔头西一棒子，这和自己之前没有编写过如此规模的项目有联系；没有运用好git的版本管理功能，而且初学阶段更新知识非常快，学完xml配置又知道了注解配置，ssm分别都能跑，一整合各种报错，项目多次推倒重来

## 2021.04.15

### 开发日志
1. 实现了简单的帖子的增删查改、评论的增删查，当访问资源不存在时给出了正确返回，不过尚未实现分页返回，GET请求访问不存在的资源时返回值还是有问题

### 遇到的问题
1. GET请求访问不存在的资源（对应没有满足数据库select条件的结果）时，数据库语句返回的是int类型的1，目前还没有发现原因
2. 目前在网上找到的关于分页返回用到的PageHelper以及鉴权用到的JWT的教程几乎都是基于springboot的，而我并没有用springboot，不知道影响大不大

## 2021.04.17

### 开发日志
1. 完善了控制层的响应，能根据不同情况返回相应的状态码
2. 编写了jwt的封装工具类，能够根据用户登录时的信息生成token

### 学习日志
1. 学习ReponseEntity的用法（不仅可以将返回值转换成Json，还可以设置响应状态码）
2. 学习JWT，理解了其原理，并掌握了一些简单的方法（生成、验证token）

###遇到的问题
1. 昨日问题未解决，由于网上的教程大多是基于springboot和纯注解式配置的，所以在今天没有学会PageHelper和JWT的使用，当返回null时转换成的Json仍然是1