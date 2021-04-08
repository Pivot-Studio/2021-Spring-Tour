import com.nick.service.BlogService;
import com.nick.service.BlogServiceImpl;
import com.nick.service.CommentService;
import com.nick.service.CommentServiceImpl;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CommentTest {
    //TODO:测试
    @Test
    public void testCommentServiceImpl()
    {
        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        CommentService commentService = (CommentServiceImpl)context.getBean("commentServiceImpl");
        commentService.queryCommentByPagingDesc(0,2,1);
    }

    //TODO:先创建addcomment
}
