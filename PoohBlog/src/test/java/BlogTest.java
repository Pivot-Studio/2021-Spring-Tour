import com.nick.pojo.Blog;
import com.nick.service.BlogService;
import com.nick.service.BlogServiceImpl;
import com.nick.utilObjects.AddBlogObject;
import com.nick.utilObjects.UpdateBlogObject;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class BlogTest {
    @Test
    public void testQueryBlog()
    {
        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        BlogService blogServiceImpl = (BlogServiceImpl)context.getBean("blogServiceImpl");

        List<Blog> blogs = blogServiceImpl.queryAllBlogs();

        for (Blog b :
                blogs) {
            System.out.println(b.getContent());
        }
    }
    @Test
    public void testDeleteBlog()
    {
        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        BlogService blogServiceImpl = (BlogServiceImpl)context.getBean("blogServiceImpl");
        System.out.println("======================================");
        System.out.println(blogServiceImpl.deleteBlog(1));
        //删除成功返回1
        //删除失败返回0
    }

    @Test
    public void testUpdateBlog()
    {
        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        BlogService blogServiceImpl = (BlogServiceImpl)context.getBean("blogServiceImpl");
        UpdateBlogObject updateBlogObject =new UpdateBlogObject(4,"","罗素的话");
        System.out.println(blogServiceImpl.updateBlog(updateBlogObject));

    }

    @Test
    public void testAddBlog()
    {
        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        BlogService blogServiceImpl = (BlogServiceImpl)context.getBean("blogServiceImpl");
        AddBlogObject addBlogObject=new AddBlogObject();
        addBlogObject.setContent("参差多台方是幸福的本源");
        addBlogObject.setTitle("罗素");
        addBlogObject.setTypeId(1);
        addBlogObject.setWriterId(1);
        System.out.println(blogServiceImpl.addBlog(addBlogObject));
    }
}
