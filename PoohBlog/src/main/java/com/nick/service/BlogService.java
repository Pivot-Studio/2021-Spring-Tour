package com.nick.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nick.pojo.Blog;
import com.nick.utilObjects.AddBlogObject;
import com.nick.utilObjects.UpdateBlogObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogService {
    //查询所有Blog
    public List<Blog> queryAllBlogs();
    //删除博客
    public int deleteBlog(int id) throws Exception;
    //更新博客
    public int updateBlog(UpdateBlogObject updateBlogObject) throws Exception;
    //增加博客
    public int addBlog(AddBlogObject addBlogObject) throws JsonProcessingException;
    //查询单篇博客
    public Blog queryBlog(int id);
    //TODO: 博客访问量+1
    //public void blogStarIncrement(Blog blog);
    //TODO: 评论数+1
    //public void blogCommentCountIncrement(Blog blog);

    public List<Blog> queryBlogByPagingDesc(int beginIndex, int stepSize);
}
