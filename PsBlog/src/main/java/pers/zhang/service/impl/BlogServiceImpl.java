package pers.zhang.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.zhang.mapper.BlogMapper;
import pers.zhang.pojo.Blog;
import pers.zhang.service.BlogService;

@Service("blogService")
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogMapper blogMapper;

    //作用：发表一篇博客
    //参数：作者id号=authorId，博客内容=content
    //返回值：成功=1，因为内容为空=0
    @Override
    public int createBlog(int authorId, String content) {
        if(content==null) return 0;
        Blog blog = new Blog();
        blog.setAuthorId(authorId);
        blog.setContent(content);
        return blogMapper.createBlog(blog);
    }
    //作用：删除一篇博客
    //参数：博客id号=id
    //返回值：删除记录数（成功=1，失败=0）
    @Override
    public int deleteBlog(int id) {
        Blog blog = new Blog();
        blog.setId(id);
        return blogMapper.deleteBlog(blog);
    }

    //作用：查询一篇博客
    //参数：作博客id号=id
    //返回值：Blog类
    @Override
    public Blog getBlog(int id) {
        Blog blog = new Blog();
        blog.setId(id);
        return blogMapper.getBlog(blog);
    }

    //作用：修改一篇博客的内容
    //参数：博客id号=id，修改后内容
    //返回值：修改记录数（成功=1，因为内容为空失败=0）
    @Override
    public int updateBlog(int id, String content) {
        if(content==null) return 0;
        Blog blog = new Blog();
        blog.setId(id);
        blog.setContent(content);
        return blogMapper.updateBlog(blog);
    }
}
