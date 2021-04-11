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