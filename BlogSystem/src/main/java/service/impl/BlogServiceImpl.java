package service.impl;

import dao.BlogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pojo.Blog;
import service.BlogService;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    @Qualifier("blogDao")
    private BlogDao blogDao;
    public Blog getBlog(String id) {
        return this.blogDao.getBlog(id);
    }
}
