# PoohBlog



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

