package pers.zhang.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;

public class JwtUtil {
    private static String SIGN = "PivotStudio";

    //生成token
    public static String getToken(String uid,String name){
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND,180);  //180秒过期
        JWTCreator.Builder builder = JWT.create();  //创建JWT builder
        builder.withClaim("uid",uid);
        builder.withClaim("name",name); //payload载荷
        String token = builder.withExpiresAt(instance.getTime())   //设置过期时间
                .sign(Algorithm.HMAC256(SIGN)); //设置签名
        return token;
    }

    //验证token并获取信息
    public static DecodedJWT verify(String token){
        return JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
    }


}
