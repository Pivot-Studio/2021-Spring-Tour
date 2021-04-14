package pers.zhang.service;

import pers.zhang.pojo.Blog;

public interface BlogService {
    int createBlog(int authorId, String content);
    int deleteBlog(int id);
    Blog getBlog(int id);
    int updateBlog(int id,String content);
}
