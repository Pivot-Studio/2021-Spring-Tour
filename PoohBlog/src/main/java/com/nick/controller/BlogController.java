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
public class BlogController {
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
    public String queryAllBlogs() throws JsonProcessingException {
        List<Blog> blogs = blogService.queryAllBlogs();
        return ObjectToJson.objectToJson(blogs);
    }


    //done
    @ResponseBody
    @RequestMapping(value = "/deleteBlog/{id}",produces = "text/html;charset=UTF-8")
    public String deleteBlog(@PathVariable int id) throws Exception {
        //res为0表示删除失败，1为删除成功
        int res = blogService.deleteBlog(id);
        return JudgmentToJson.judgmentToJson(res);
    }

    //done
    @ResponseBody
    @RequestMapping(value = "/queryBlogById/{id}",produces = "text/html;charset=UTF-8")
    public String queryBlogById(@PathVariable int id) throws JsonProcessingException
    {
        Blog blog=blogService.queryBlog(id);
        return ObjectToJson.objectToJson(blog);
    }
    //done
    //前端传来的应该是一个只有id与content属性与title属性的json
    @ResponseBody
    @PostMapping(value = "/updateBlog",produces = "text/html;charset=UTF-8")
    public String updateBlog(@RequestBody UpdateBlogObject updateBlogObject) throws Exception {
        int res = blogService.updateBlog(updateBlogObject);
        return JudgmentToJson.judgmentToJson(res);
    }
    //done
    @ResponseBody
    @PostMapping(value = "/addBlog",produces = "text/html;charset=UTF-8")
    public String addBlog(@RequestBody AddBlogObject addBlogObject, HttpServletRequest request) throws JsonProcessingException {
        System.out.println(addBlogObject);
        int res=blogService.addBlog(addBlogObject);
        return JudgmentToJson.judgmentToJson(res);
    }
    //done
    @ResponseBody
    @GetMapping(value = "/queryBlogByPagingDesc/{pageIndex}/{stepSize}",produces = "text/html;charset=UTF-8")
    public String queryCommentByPagingDesc(@PathVariable int pageIndex,@PathVariable int stepSize) throws Exception {
        
        List<String> res1= RedisOperation.queryBlogByPagingDesc((pageIndex-1)*stepSize,stepSize,jedis);
        System.out.println(res1.size());
        if(res1.size()>=stepSize)
        {
            return ListJsonToJson.listJsonToJson(res1);
        }
        else {
            List<Blog> res2;
            res2 = blogService.queryBlogByPagingDesc(pageIndex, stepSize);
            if (res2 == null || res2.isEmpty()) {
                return JudgmentToJson.judgmentToJson(0);
            } else {//若查询结果不为空
                for (Blog b :
                        res2) {
                    RedisOperation.updateItem(jedis,b);
                }
                return ObjectToJson.objectToJson(res2);
            }
        }
    }
}
