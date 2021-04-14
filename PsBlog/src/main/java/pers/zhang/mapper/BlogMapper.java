package pers.zhang.mapper;

import org.springframework.stereotype.Repository;
import pers.zhang.pojo.Blog;


public interface BlogMapper {
    int createBlog(Blog blog);
    int deleteBlog(Blog blog );
    Blog getBlog(Blog blog);
    int updateBlog(Blog blog);
}
