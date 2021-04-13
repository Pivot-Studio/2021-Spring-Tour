package com.nick.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nick.dao.BlogMapper;
import com.nick.pojo.Blog;
import com.nick.pojo.User;
import com.nick.util.redisUtil.RedisOperation;
import com.nick.utilObjects.AddBlogObject;
import com.nick.utilObjects.UpdateBlogObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

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
    @Autowired
    @Qualifier("Jedis")
    private Jedis jedis;


    //该操作实际应用中一般不会使用，大部分使用分页查询，小部分使用id查询
    @Override
    public List<Blog> queryAllBlogs() {
        return blogMapper.queryAllBlogs();
    }

    //done
    @Override
    public int deleteBlog(int id) throws Exception {
        //已经删除，无法再次删除，删除失败返回0
        if(blogMapper.getBlogIsDeleted(id)==1) {
            return 0;
        }
        else {
            //返回操作删除后的操作
            RedisOperation.deleteItem(jedis,id);
            return blogMapper.deleteBlog(id);
        }
    }
    //done
    @Override
    public int updateBlog(UpdateBlogObject updateBlogObject) throws Exception {
        //博文id<=0返回0
        if (updateBlogObject.getId()<=0) {
            return 0;
        }
        Blog blog=queryBlog(updateBlogObject.getId());
        //若找不到博文返回0
        if(blog==null)
        {
            return 0;
        }
        else
        {
            //为空或者相等时不改变
            if(updateBlogObject.getTitle()!=null&&!updateBlogObject.getTitle().equals(blog.getTitle()))
            {
                //重新设置title
                blog.setTitle(updateBlogObject.getTitle());
            }
            if(updateBlogObject.getContent()!=null&&!updateBlogObject.getContent().equals(blog.getContent()))
            {
                //重新设置content
            blog.setContent(updateBlogObject.getContent());
            }
        }
        RedisOperation.updateItem(jedis,blog);
        return blogMapper.updateBlog(blog);
    }

    //done
    @Override
    public int addBlog(AddBlogObject addBlogObject) throws JsonProcessingException {
        //若有一项为空则返回0
        if(addBlogObject.getContent()==null||addBlogObject.getTitle()==null||addBlogObject.getTypeId()<=0||addBlogObject.getWriterId()<=0)
        {
            return 0;
        }
        Blog blog=new Blog();
        User user=userService.queryUser(addBlogObject.getWriterId());
        //若查不到此user返回0
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
            RedisOperation.addItem(jedis,blog);
            return blogMapper.addBlog(blog);
        }
    }
    //done
    @Override
    public Blog queryBlog(int id) {
        return blogMapper.queryBlog(id);
    }

    //分页查询
    @Override
    public List<Blog> queryBlogByPagingDesc(int pageIndex, int stepSize) {
        if(pageIndex<=0||stepSize<=0)
        {
            return null;
        }
        return blogMapper.queryBlogByPagingDesc((pageIndex-1)*stepSize,stepSize);
    }
}
