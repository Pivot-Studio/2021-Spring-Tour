package com.nick.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nick.pojo.Blog;
import com.nick.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @RequestMapping("/queryAllBlogs")
    public String queryAllBlogs(Model model)
    {

        List<Blog> blogs = blogService.queryAllBlogs();
        model.addAttribute("blogs",blogs);
        return "queryAllBlog";
    }

    @ResponseBody
    @RequestMapping(value = "/testJson",produces = "text/html;charset=UTF-8")
    public String testJson() throws JsonProcessingException {
        ObjectMapper objectMapper=new ObjectMapper();
        List<Blog> blogs = blogService.queryAllBlogs();
        String s = objectMapper.writeValueAsString(blogs);

        return s;
    }
}
