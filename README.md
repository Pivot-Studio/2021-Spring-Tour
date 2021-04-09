# 实习手册 2021-Spring-Tour
Join Us And Explore
## git
```shell
# clone下来一个远程仓库
git clone https://github.com/Pivot-Studio/2021-Spring-Tour.git

# 将更改记录加入缓存区
git add .
# 将更改记录提交
git commit -m "Tag:Your commit info"
# 推送到远程分支
git push
# 查看分支情况
git branch
# 切换分支
git checkout your-branch-name
```

## 任务

我们为后端提供了三个实习任务（每个任务 的具体要求详见实习群中发的pdf)

1. 对树洞的运维提供支持

2. 实现一个Github管理系统

3. 实现一个博客后端

  
这三个任务难度依次递增，第一个任务面向没有网络和数据库操作编程经验的同学，第三个任务适合有一定后端基础并且对一些框架有所了解的同学。

## 实习要求

1. 所有实习题目语言框架不限
2. 我们希望你有良好的分支管理能力，提交commit时，请简单描述一下做出的改动，增添新功能以FEA:开头，修改bug以FIX:开头，将自己的分支基于主分支开发。
3. 保证代码风格的统一和工程化，不同模块的功能拆分到不同的包里面。
4. 良好的写注释习惯，推荐为自己负责的 class 及 function 添加相关文档。
5. 每天有适量的工作日志来记录完成的新功能，遇到的问题，学到的知识等等。
6. 在每天23:30之前将你的工作日志和更新的代码push到仓库的分支上面。
7. 在善用搜索引擎的同时主动提问，在遇到问题时可以咨询对应的出题人或者组长，欢迎私戳。

## 四月八日

1. 我先基于自己的水平和团队的推荐和情况确定选择任务二完成项目使用的编程语言为go语言，虽然并不熟练，以前也几乎没有用go语言编写代码的经历，但我认为实习就是一个挑战自我，不断学习的过程，也可以为将来的发展打好基础。
2. 通过网上查找资料学习了.gitgnore文件的作用和用法，并push了一个go的gitignore模板。
3. 由于以前我没有在vscode上配置完善go语言的环境，有多个远程北宋还未下载。我趁这次机会，查找资料配环境，其间我经历了许多波折。首先我弄明白了无法下载的原因是vscode无法翻墙下载，即使我开了vpn也无济于事。后来，我根据csdn上的文章手动clone了github上的go的库到本地，结果仍然无法成功配置。我又查找资料，可以复制错误信息上的网址来下载，可是我失望地发现有些网址已经找不到网页了（404）。就在我濒临绝望之际，天无绝人之路，我又发现了一篇博客，上面分享了go的包，我用网盘下载之后解压缩在放到规定目录，再下载就成功了。在这个过程中，我也算更加熟悉了git bash的用法。
4. 我正在阅读github 的 官方 api 文档，熟悉postman功能（尚未完成）

##  四月九日

1. 我基本完成GitHub官方文档rest api部分的目前所需知识的阅读，通过网络查找各种相关知识，对项目有了更深的理解。
2. 我了解了postman 和 curl命令的用法并加以测试 完成网络请求 push了一张响应截图
3. 我通过查找资料写了实现网络请求的go语言代码，成功实现。
4. 我查找相关知识，创建了GitHub的私人token，但不会如何完成身份认证，仍在学习。
