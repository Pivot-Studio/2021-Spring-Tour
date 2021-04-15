package com.nick.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nick.pojo.Blog;
import com.nick.pojo.Comment;
import com.nick.service.BlogService;
import com.nick.util.JudgmentToJson;
import com.nick.util.ListJsonToJson;
import com.nick.util.ObjectToJson;
import com.nick.util.jwtUtil.JwtUtils;
import com.nick.util.redisUtil.RedisOperation;
import com.nick.utilObjects.AddBlogObject;
import com.nick.utilObjects.UpdateBlogObject;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/blog")
@Api(value = "Blog的操作类")
public class BlogController {
    public static final String lock="lock";
    @Autowired
    @Qualifier("blogServiceImpl")
    private BlogService blogService;

    @Autowired
    @Qualifier("Jedis")
    private Jedis jedis;

//    @RequestMapping("/queryAllBlogs")
//    public String queryAllBlogs(Model model)
//    {
//
//        List<Blog> blogs = blogService.queryAllBlogs();
//        model.addAttribute("blogs",blogs);
//        return "queryAllBlog";
//    }

    //done
    @ResponseBody
    @RequestMapping(value = "/queryAllBlogs",produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "查找所有Blog", notes = "查询blog",httpMethod = "GET")
    public String queryAllBlogs() throws JsonProcessingException {
        List<Blog> blogs = blogService.queryAllBlogs();
        return ObjectToJson.objectToJson(blogs);
    }


    //done
    @ResponseBody
    @RequestMapping(value = "/deleteBlog/{id}",produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "按照id删除博文" ,notes = "实际操作数据库将字段isDeleted置1" ,httpMethod = "GET")
    public String deleteBlog(@ApiParam(required = true,value = "Blog id") @PathVariable int id) throws Exception {
        //res为0表示删除失败，1为删除成功
        int res = blogService.deleteBlog(id);
        return JudgmentToJson.judgmentToJson(res);
    }

    //done
    @ResponseBody
    @RequestMapping(value = "/queryBlogById/{id}",produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "通过id查找博文",httpMethod = "GET")
    public String queryBlogById(@ApiParam(required = true,value = "Blog id") @PathVariable int id) throws JsonProcessingException
    {
        Blog blog=blogService.queryBlog(id);
        return ObjectToJson.objectToJson(blog);
    }
    //done
    //前端传来的应该是一个只有id与content属性与title属性的json
    @ResponseBody
    @PostMapping(value = "/updateBlog",produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "更新博文",httpMethod = "POST")
    public String updateBlog(@ApiParam(required = true,value = "上传博文信息")@RequestBody UpdateBlogObject updateBlogObject) throws Exception {
        int res = blogService.updateBlog(updateBlogObject);
        return JudgmentToJson.judgmentToJson(res);
    }
    //done
    @ResponseBody
    @PostMapping(value = "/addBlog",produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "增加博文")
    public String addBlog(@ApiParam(required = true,value = "上传博文信息")@RequestBody AddBlogObject addBlogObject) throws JsonProcessingException {
        System.out.println(addBlogObject);
        int res=blogService.addBlog(addBlogObject);
        return JudgmentToJson.judgmentToJson(res);
    }
    //done
    @ResponseBody
    @GetMapping(value = "/queryBlogByPagingDesc/{pageIndex}/{stepSize}",produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "分页按时间倒序查找博文",httpMethod = "GET")
    public String queryCommentByPagingDesc(@ApiParam(required = true,value = "页码") @PathVariable int pageIndex,@ApiParam(required = true,value = "单页博文数目") @PathVariable int stepSize) throws Exception {
        List<String> res1=null;
        synchronized (lock) {
             res1= RedisOperation.queryBlogByPagingDesc((pageIndex - 1) * stepSize, stepSize, jedis);
        }
        if(res1.size()>=stepSize||res1==null)
        {
            return ListJsonToJson.listJsonToJson(res1);
        }
        else {
            List<Blog> res2;
            res2 = blogService.queryBlogByPagingDesc(pageIndex, stepSize);
            if (res2 == null || res2.isEmpty()) {
                return JudgmentToJson.judgmentToJson(0);
            } else {//若查询结果不为空
                synchronized (lock){
                for (Blog b :
                        res2) {
                    RedisOperation.updateItem(jedis,b);
                }
                }
                return ObjectToJson.objectToJson(res2);
            }
        }
    }
}
