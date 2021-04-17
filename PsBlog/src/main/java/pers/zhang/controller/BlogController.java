package pers.zhang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pers.zhang.pojo.Blog;
import pers.zhang.service.BlogService;

@Controller
@RequestMapping("/blogs")
public class BlogController {
    @Autowired
    private BlogService blogService;

    //作用：发表一篇博客
    //参数：作者id号=authorId，博客内容=content
    @PostMapping
    public ResponseEntity createBlog(String authorId,String content){
        Blog blog = blogService.createBlog(Integer.parseInt(authorId),content);
        //内容为空，返回HTTP状态码422
        if(blog==null){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
        //创建成功，返回状态码201、刚创建的Blog实例转换成的JSon
        return ResponseEntity.status(HttpStatus.CREATED).body(blog);
    }

    //作用：删除一篇博客
    //参数：博客id号=id
    @DeleteMapping("/{id}")
    public ResponseEntity deleteBlog(@PathVariable String id){
        //所删除博客不存在，返回404
        if(blogService.deleteBlog(Integer.parseInt(id))==0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        //删除成功，返回204
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //作用：查询一篇博客
    //参数：博客id号=id
    @GetMapping("/{id}")
    public ResponseEntity<Blog> getBlog(@PathVariable String id){
        Blog blog = blogService.getBlog(Integer.parseInt(id));
        //资源不存在，返回404
        if(blog==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        //成功找到，返回相应对象转换成的Json
        return ResponseEntity.ok(blog);
    }
//    public Blog getBlog(@PathVariable String id){
//        return blogService.getBlog((Integer.parseInt(id)));
//    }

    //作用：修改一篇博客的内容
    //参数：博客id号=id，修改后博客内容=content
    @PatchMapping("/{id}")
    public ResponseEntity updateBlog(@PathVariable String id,String content){
        Blog blog = blogService.updateBlog(Integer.parseInt(id),content);
        //内容为空，返回HTTP状态码422
        if(blog==null){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
        //修改成功，返回状态码201、刚修改的博客
        return ResponseEntity.status(HttpStatus.CREATED).body(blog);
    }
}
