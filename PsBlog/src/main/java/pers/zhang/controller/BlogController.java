package pers.zhang.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pers.zhang.pojo.Blog;
import pers.zhang.service.BlogService;
import pers.zhang.utils.JudgementUtile;

@RestController
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    private BlogService blogService;

    //作用：发表一篇博客
    //参数：作者id号=authorId，博客内容=content
    //返回值：status=OK/FALSE的Json
    @PostMapping
    public JudgementUtile createBlog(String authorId,String content){
        int judgement = blogService.createBlog(Integer.parseInt(authorId),content);
        JudgementUtile judgementUtile = new JudgementUtile();
        judgementUtile.setStatus(judgement);
        return judgementUtile;
    }


    //作用：删除一篇博客
    //参数：博客id号=id
    //返回值：status=OK/FALSE的Json
    @DeleteMapping("/{id}")
    public JudgementUtile deleteBlog(@PathVariable String id){
        int judgement = blogService.deleteBlog(Integer.parseInt(id));
        JudgementUtile judgementUtile = new JudgementUtile();
        judgementUtile.setStatus(judgement);
        return judgementUtile;
    }

    //作用：查询一篇博客
    //参数：博客id号=id
    //返回值：Blog类对象转换的Json
    @GetMapping("/{id}")
    public Blog getBlog(@PathVariable String id){
        return blogService.getBlog((Integer.parseInt(id)));
    }

    //作用：修改一篇博客的内容
    //参数：博客id号=id，修改后博客内容=content
    //返回值：status=OK/FALSE的Json
    @PatchMapping("/{id}")
    public JudgementUtile updateBlog(@PathVariable String id,String content){
        int judgement = blogService.updateBlog(Integer.parseInt(id),content);
        JudgementUtile judgementUtile = new JudgementUtile();
        judgementUtile.setStatus(judgement);
        return judgementUtile;
    }
}
