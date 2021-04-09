import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;

public class HashMapTest {
    @Test
    public void testIod()
    {
        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        HashMap<String,String> uidMaiVerification = (HashMap<String,String>)context.getBean("UIDMaiVerification");

        uidMaiVerification.put("hello","hello");

    }
}
