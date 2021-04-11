package dao.impl;

import dao.BlogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import pojo.Blog;

@Repository
public class BlogDaoImpl implements BlogDao {
    @Autowired
    @Qualifier("blog")
    private Blog blog;

    public Blog getBlog(String id) {
        Blog blog = new Blog();
        blog.setId("1");
        blog.setAuthor("zhang");
        blog.setContent("sofa");
        return blog;
    }
}
