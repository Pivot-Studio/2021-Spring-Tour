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

## 项目简介:

### 使用的是java语言，版本是java15.0.2
   
   这个项目是一个Github管理系统，实现的功能有两个。
   一个是在任何时候只要用户在本地仓库上成功使用git push就会向用户的QQ邮箱发送一封邮件，邮件的内容是“你的代码已经提交到远程仓库”。
   另外一个功能是爬取用户github上的star仓库列表，并且可以用收藏夹对这些仓库进行分类，收藏夹的名称在创建的时候由用户指定。用户可以创建一个收藏夹，可以将一个仓库添加至某个收藏夹，但每个仓库只能进入一个收藏夹。用户也可以从收藏夹中删除掉里面的仓库。用户可以在控制台查看所有收藏夹的列表，也可以查看star的仓库列表。项目还利用了快速排序算法按照用户指定的关键字对star中仓库进行排序。所有的结果都会以文件的形式保存。
