package service;

import dao.BlogDao;
import pojo.Blog;

public interface BlogService{
    Blog getBlog(String id);
}
