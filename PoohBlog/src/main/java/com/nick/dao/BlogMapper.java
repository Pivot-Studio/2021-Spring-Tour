package com.nick.dao;

import com.nick.pojo.Blog;

import java.util.List;

public interface BlogMapper {
    //查询所有Blog
    public List<Blog> queryAllBlogs();
    //删除博客
    public int deleteBlog(int id);
    //更新博客
    public int updateBlog(Blog blog);
    //增加博客
    public int addBlog(Blog blog);
    //查询单篇博客
    public Blog queryBlog(int id);
    //TODO: 博客访问量+1
    //public void blogStarIncrement(Blog blog);
    //TODO: 评论数+1
    //public void blogCommentCountIncrement(Blog blog);
}
