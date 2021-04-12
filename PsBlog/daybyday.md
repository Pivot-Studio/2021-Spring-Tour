#每日学习进度
- **4.8**

    阅读任务书，确定任务。学习gin相关知识。配置gin相关包。https://learnku.com/docs/gin-gonic/2019
  http://www.topgoer.com/gin%E6%A1%86%E6%9E%B6/%E7%AE%80%E4%BB%8B.html
  
- **4.9**
  
  学习gin(感觉知识点好碎)，配置数据库mysql，熟悉gorm库，之前用的是xampp集成环境

  了解restful风格的api编写
  1. 获取文章 /blog/getXxx Get blog/Xxx

  2. 添加 /blog/addXxx POST blog/Xxx

  3. 修改 /blog/updateXxx PUT blog/Xxx

  4. 删除 /blog/delXxxx DELETE blog/Xxx
  ```
  r := gin.Default()
  r.GET/.PUT/.POST/.RUN //方法
  ```
  
  创建"文章类型"表单，分模块创建文件夹，写了初步代码。
  ```
  mysql> CREATE TABLE `articles`(`id` int(10) unsigned NOT NULL auto_increment,
    -> `tag_id` int(8) unsigned DEFAULT '10' comment '标签ID',
    -> `title` varchar(100) DEFAULT '' COMMENT '文章标题',
    -> `content` text,
    -> `date_time` datetime,
    -> primary key(`id`)
    -> )ENGINE=MYISAM AUTO_INCREMENT=1 DEFAULT CHARSET=UTF8MB4;
  ```
  
  先跟着搭了类似的框架

- **4.10**
  
  开始搭建，似乎还没有问题
  ![img.png](img.png)
  初步构建增删改查操作，bug冒出来了，对于mysql事件类型有bug...
  学会使用google插件postman，确实很强大，来进行post等操作
  ![img_1.png](img_1.png)
  
- **4.11**

  添加文章没有问题了，但是对这个报错很疑惑，我没有定义date_time这一列啊，不知道为什么
  create的时候，它会这么匹配，猜想可能是dateTime类型的转化问题？？？
  ![img_2.png](img_2.png)
  增删改查基本完成，返回值形式应该是json，然后查询如何将信息写入配置文件中并由其它模块调用。
  增加配置文件config.yaml，链接数据库通过加载load()函数处理配置文件信息。
  part1应该差不多了吧。。。
  查阅关于数据库表设计的相关资料
  
  属性的 **关联**/**约束**
  好像有点难以下手了，是需要搭个简单的前端吗？但不太会鸭
  准备学习下中间件鉴权
  
- **4.12**

  对写评论的api不太了解，猜测可能url类似为点击某个文章，发送Get请求
  ```
  /articles/id/...comment操作
  ```
  为验证猜想，决定找几个博客试试。查询了一下资料显示有的参数中需要有评论页。
  ![img_3.png](img_3.png)
  发现了一个bug的原因，原来gorm在做数据迁移时会自动变成复数，怪不得一开始创建article时报错不存在articles数据库。
  准备先搞注册功能唉