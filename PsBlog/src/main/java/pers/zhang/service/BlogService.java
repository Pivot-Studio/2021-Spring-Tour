package pers.zhang.service;

import pers.zhang.pojo.Blog;

public interface BlogService {
    Blog createBlog(int authorId, String content);
    int deleteBlog(int id);
    Blog getBlog(int id);
    Blog updateBlog(int id,String content);
}
