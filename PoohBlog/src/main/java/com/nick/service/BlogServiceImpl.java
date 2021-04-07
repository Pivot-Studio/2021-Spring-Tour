package com.nick.service;

import com.nick.dao.BlogMapper;
import com.nick.pojo.Blog;
import com.nick.pojo.User;
import com.nick.utilObjects.AddBlogObject;
import com.nick.utilObjects.UpdateBlogObject;
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
    @Autowired
    private UserService userService;
    @Override
    public List<Blog> queryAllBlogs() {
        return blogMapper.queryAllBlogs();
    }

    @Override
    public int deleteBlog(int id) {
        //已经删除，无法再次删除，删除失败返回0
        if(blogMapper.getBlogIsDeleted(id)==1) {
            return 0;
        }
        else {
            //返回操作删除后的操作
            return blogMapper.deleteBlog(id);
        }
    }

    @Override
    public int updateBlog(UpdateBlogObject updateBlogObject) {
        Blog blog=queryBlog(updateBlogObject.getId());
        if(blog==null)
        {
            return 0;
        }
        else
        {
            //为空或者相等时不改变
            if(updateBlogObject.getTitle()!=""&&!updateBlogObject.getTitle().equals(blog.getTitle()))
            {
                blog.setTitle(updateBlogObject.getTitle());
            }
            if(updateBlogObject.getContent()!=""&&!updateBlogObject.getContent().equals(blog.getContent()))
            {
            blog.setContent(updateBlogObject.getContent());
            }
        }
        return blogMapper.updateBlog(blog);
    }

    @Override
    public int addBlog(AddBlogObject addBlogObject) {
        Blog blog=new Blog();
        User user=userService.queryUser(addBlogObject.getWriterId());
        if(user==null)
        {
            return 0;
        }
        else {
            blog.setWriter(user.getName());
            blog.setContent(addBlogObject.getContent());
            blog.setTitle(addBlogObject.getTitle());
            blog.setWriterId(addBlogObject.getWriterId());
            blog.setTypeId(addBlogObject.getTypeId());
            return blogMapper.addBlog(blog);
        }
    }

    @Override
    public Blog queryBlog(int id) {
        return blogMapper.queryBlog(id);
    }

}
