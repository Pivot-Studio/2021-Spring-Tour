import org.junit.Test;
import redis.clients.jedis.Jedis;

public class RedisTest {
    @Test
    public void testConnetion()
    {
        Jedis jedis=new Jedis("localhost",6379);
        System.out.println(jedis.ping());
    }
}
