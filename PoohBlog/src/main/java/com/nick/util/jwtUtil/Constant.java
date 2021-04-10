package com.nick.util.jwtUtil;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.UUID;



public class Constant {
    static
    {
        JWT_SECRET=Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }
    public static final SecretKey JWT_SECRET;
    public static final int JWT_TTL=3*60*60*1000;//生效时间
}
