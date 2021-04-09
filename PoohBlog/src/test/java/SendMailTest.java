import com.nick.service.UserServiceImpl;
import com.nick.utilObjects.AddUserObject;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;

public class SendMailTest {
    @Test
    public void testSendMail() throws Exception {
        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        UserServiceImpl userServiceImpl = (UserServiceImpl)context.getBean("userServiceImpl");
        AddUserObject addUserObject=new AddUserObject();
        addUserObject.setMail("2975684744@qq.com");
        addUserObject.setName("嘉然小姐的狗");
        addUserObject.setAccount("123456");
        addUserObject.setPassWord("123456");
        userServiceImpl.signIn(addUserObject);
        HashMap<String ,String> hashMap = (HashMap<String,String>)context.getBean("UserMap");
        for (String s :
                hashMap.keySet()) {
            System.out.println(s);
        }
    }
    @Test
    public void testUserMap()throws Exception
    {
        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        HashMap<String ,String> hashMap = (HashMap<String,String>)context.getBean("UserMap");
        for (String s :
                hashMap.keySet()) {
            System.out.println(s);
        }
    }
}
