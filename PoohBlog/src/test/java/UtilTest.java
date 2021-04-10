import com.nick.util.IsEmail;
import com.nick.util.SendEmail;
import com.nick.util.jwtUtil.JwtUtils;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.junit.Test;

import javax.crypto.SecretKey;


public class UtilTest {
    @Test
    public void TestIsEmail() throws Exception {
        String mail="2975684744@qq.com";
        String content="sakjdfhjsafjhas";
    }
    @Test
    public void testJjwt()
    {
        for (int i = 0; i < 100; i++) {
            SecretKey key= Keys.secretKeyFor(SignatureAlgorithm.HS256);
            String secretString = Encoders.BASE64.encode(key.getEncoded());
            System.out.println(secretString);
        }
    }
    @Test
    public void testJJWT()
    {
        for (int i = 0; i < 10; i++) {
            System.out.println(Encoders.BASE64.encode(JwtUtils.getKey().getEncoded()));
        }
    }
}
