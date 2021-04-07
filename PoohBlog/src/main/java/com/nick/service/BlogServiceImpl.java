package com.nick.service;

import com.nick.dao.BlogMapper;
import com.nick.pojo.Blog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Service
public class BlogServiceImpl implements BlogService{
    @Autowired
    private BlogMapper blogMapper;
    @Override
    public List<Blog> queryAllBlogs() {
        return blogMapper.queryAllBlogs();
    }

    @Override
    public int deleteBlog(int id) {
        return blogMapper.deleteBlog(id);
    }

    @Override
    public int updateBlog(Blog blog) {
        return blogMapper.updateBlog(blog);
    }

    @Override
    public int addBlog(Blog blog) {
        return blogMapper.addBlog(blog);
    }

    @Override
    public Blog queryBlog(int id) {
        return blogMapper.queryBlog(id);
    }
}
