package com.nick.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nick.pojo.Blog;
import com.nick.service.BlogService;
import com.nick.util.JudgmentToJson;
import com.nick.util.ObjectToJson;
import com.nick.utilObjects.AddBlogObject;
import com.nick.utilObjects.UpdateBlogObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    @Qualifier("blogServiceImpl")
    private BlogService blogService;

//    @RequestMapping("/queryAllBlogs")
//    public String queryAllBlogs(Model model)
//    {
//
//        List<Blog> blogs = blogService.queryAllBlogs();
//        model.addAttribute("blogs",blogs);
//        return "queryAllBlog";
//    }

    @ResponseBody
    @RequestMapping(value = "/queryAllBlogs",produces = "text/html;charset=UTF-8")
    public String queryAllBlogs() throws JsonProcessingException {
        List<Blog> blogs = blogService.queryAllBlogs();
        return ObjectToJson.objectToJson(blogs);
    }

    @ResponseBody
    @RequestMapping(value = "/deleteBlog/{id}",produces = "text/html;charset=UTF-8")
    public String deleteBlog(@PathVariable int id) throws JsonProcessingException {
        //res为0表示删除失败，1为删除成功
        int res = blogService.deleteBlog(id);
        return JudgmentToJson.judgmentToJson(res);
    }

    //TODO:测试更新功能
    //前端传来的应该是一个只有id与content属性与title属性的json
    @ResponseBody
    @PostMapping(value = "/updateBlog",produces = "text/html;charset=UTF-8")
    public String updateBlog(UpdateBlogObject updateBlogObject) throws JsonProcessingException {
        int res = blogService.updateBlog(updateBlogObject);
        return JudgmentToJson.judgmentToJson(res);
    }

    @ResponseBody
    @PostMapping(value = "/addBlog",produces = "text/html;charset=UTF-8")
    public String addBlog(AddBlogObject addBlogObject) throws JsonProcessingException {
        int res=blogService.addBlog(addBlogObject);
        return JudgmentToJson.judgmentToJson(res);
    }
}
