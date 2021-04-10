package com.nick.util.jwtUtil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nick.pojo.User;
import com.nick.util.ObjectToJson;
import com.sun.mail.util.BASE64DecoderStream;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.crypto.Data;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class jwtUtils {
    public static SecretKey generalKey()
    {
        String stringKey=Constant.JWT_SECRET;
        //本地密码解码
        byte[] encodeKey= stringKey.getBytes(StandardCharsets.UTF_8);
        //根据给定的字节数组使用AES加密算法构造密钥
        System.out.println("================");
        SecretKey key=new SecretKeySpec(encodeKey,0,encodeKey.length,"AES");
        return key;
    }

    public static String createJWT(String id,String issuer,String subject,long ttlMillis)throws Exception
    {
        SignatureAlgorithm signatureAlgorithm=SignatureAlgorithm.HS256;
        //生成jwt的时间
        long nowMillis=System.currentTimeMillis();
        Date now=new Date(nowMillis);
        //创建payload的私有声明
        Map<String,Object> claims=new HashMap<>();
        claims.put("uid","123456");
        claims.put("user_name","nick");

        System.out.println("----------");
        //生成签名密钥
        SecretKey key=generalKey();

        JwtBuilder builder=Jwts.builder().setClaims(claims)
                .setId(id)
                .setIssuedAt(now)
                .setIssuer(issuer)//签发人
                .setSubject(subject)//JWT主体可以作为用户的唯一标志
                .signWith(signatureAlgorithm,key);//签名

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
    public static Claims parseJWT(String jwt)throws Exception
    {
        SecretKey key=generalKey();
        Claims claims=Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody();
        return claims;
    }
    public static void main(String[] args) throws Exception {
        User user=new User();
        user.setName("nick");
        user.setMail("2975684744@qq.com");
        user.setAccount("123456");
        user.setPassWord("123456");
        String subject=ObjectToJson.objectToJson(user);

        try{
            String jwt = jwtUtils.createJWT(Constant.JWT_ID, "nick", subject, Constant.JWT_TTL);
            System.out.println(jwt);

            Claims c = jwtUtils.parseJWT(jwt);

            System.out.println(c.getExpiration());
            System.out.println(c.getId());
            System.out.println(c.getIssuedAt());
            System.out.println(c.getSubject());
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
