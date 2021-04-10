package com.nick.util.jwtUtil;

import com.nick.pojo.User;
import com.nick.util.ObjectToJson;
import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtils {
    //done,每次获得的都是同一个key
    public static SecretKey getKey()
    {
        return Constant.JWT_SECRET;
    }

    public static String createJWT(String subject,long ttlMillis)
    {
        //生成jwt的时间
        long nowMillis=System.currentTimeMillis();
        Date now=new Date(nowMillis);
        //生成签名密钥
        SecretKey key=getKey();
        JwtBuilder builder=Jwts.builder()
                .setIssuedAt(now)
                .setSubject(subject)//JWT主体可以作为用户的唯一标志
                .signWith(key);//签名
        //设置过期时间
        if(ttlMillis>=0)
        {
            long expMillis=nowMillis+ttlMillis;
            Date exp=new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    //解密jwt
    public static Claims parseJWT(String jws)
    {
        try{
            Claims claims=Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(jws).getBody();
            return claims;
        }
        catch (JwtException e)
        {
            System.out.println("token错误");
            return null;
        }
    }

    public static boolean isNotExpired(Claims claims)
    {
        Date dateNow=new Date(System.currentTimeMillis());
        Date dateExpire=claims.getExpiration();
        if(dateNow.after(dateExpire))
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    public static void main(String[] args) throws Exception {
        User user=new User();
        user.setName("nick");
        user.setMail("2975684744@qq.com");
        user.setAccount("123456");
        user.setPassWord("123456");
        String subject=ObjectToJson.objectToJson(user);
        try{
            String jwt = JwtUtils.createJWT( subject, Constant.JWT_TTL);
            System.out.println(jwt);

            Claims c = JwtUtils.parseJWT(jwt);
            if(c==null)
            {
                System.out.println("token错误");
                System.exit(0);
            }
            System.out.println(c);
            System.out.println(c.getExpiration());
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
