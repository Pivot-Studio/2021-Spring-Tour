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

    @Override
    public int createBlog(int authorId, String content) {
        if(content==null) return 0;
        Blog blog = new Blog();
        blog.setAuthorId(authorId);
        blog.setContent(content);
        return blogMapper.createBlog(blog);
    }

    @Override
    public int deleteBlog(int id) {
        Blog blog = new Blog();
        blog.setId(id);
        return blogMapper.deleteBlog(blog);
    }

    @Override
    public Blog getBlog(int id) {
        Blog blog = new Blog();
        blog.setId(id);
        return blogMapper.getBlog(blog);
    }

    @Override
    public int updateBlog(int id, String content) {
        if(content==null) return 0;
        Blog blog = new Blog();
        blog.setId(id);
        blog.setContent(content);
        return blogMapper.updateBlog(blog);
    }
}
