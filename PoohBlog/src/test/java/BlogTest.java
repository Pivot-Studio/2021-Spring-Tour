import com.nick.pojo.Blog;
import com.nick.service.BlogService;
import com.nick.service.BlogServiceImpl;
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
}
