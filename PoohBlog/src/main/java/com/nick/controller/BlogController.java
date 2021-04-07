package com.nick.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nick.pojo.Blog;
import com.nick.service.BlogService;
import com.nick.util.JudgmentToJson;
import com.nick.util.ObjectToJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
}
