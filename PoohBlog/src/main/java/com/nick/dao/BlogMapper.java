package com.nick.dao;

import com.nick.pojo.Blog;
import com.nick.pojo.Comment;
import org.apache.ibatis.annotations.Param;

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
    //TODO: 博客访问量+1 建议使用redis
    //public void blogStarIncrement(Blog blog);
    //TODO: 评论数+1 建议使用redis
    //public void blogCommentCountIncrement(Blog blog);
    //查询博文是否已被删除,0为未删除，1为已删除
    public int getBlogIsDeleted(int id);
    //分页查询博客
    public List<Blog> queryBlogByPagingDesc(@Param("beginIndex") int beginIndex,@Param("stepSize") int stepSize);
}
