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
  mysql> CREATE TABLE `article`(`id` int(10) unsigned NOT NULL auto_increment,
    -> `tag_id` int(8) unsigned DEFAULT '10' comment '标签ID',
    -> `title` varchar(100) DEFAULT '' COMMENT '文章标题',
    -> `content` text,
    -> `time` datetime,
    -> primary key(`id`)
    -> )ENGINE=MYISAM AUTO_INCREMENT=1 DEFAULT CHARSET=UTF8MB4;
  ```
  
  先跟着搭了类似的框架