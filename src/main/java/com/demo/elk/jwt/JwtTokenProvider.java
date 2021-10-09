package com.demo.elk.jwt;

import com.demo.elk.entity.user.User;
import com.demo.elk.exception.JwtTokenException;
import com.demo.elk.exception.MessageResponse;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.netty.handler.codec.base64.Base64Encoder;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;

@Component
@Log4j2
public class JwtTokenProvider {

    @Value("${app.jwtExpirationInMs.token}")
    private long JWT_EXPIRATION_TOKEN;

    private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    
    public String generateAccessToken(User user) {
        return Jwts.builder()
                .setHeader(header())
                .setClaims(getClaims(user))
                .setSubject(Long.toString(user.getId()))
                .setIssuedAt(getTimeNow())
                .setExpiration(getExpiredTokenTime())
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(User user){
        String id = Long.toString(user.getId());
        return Jwts.builder()
                .setClaims(stringRandomGenerator())
                .setSubject(id)
                .signWith(key)
                .compact();
    }

    public int getIdFromSubjectJWT(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return Integer.parseInt(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            throw new JwtTokenException(MessageResponse.INVALID_TOKEN);
        } catch (ExpiredJwtException ex) {
            throw new JwtTokenException(MessageResponse.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException ex) {
            throw new JwtTokenException(MessageResponse.UNSUPPORTED_TOKEN);
        } catch (IllegalArgumentException ex) {
            throw new JwtTokenException(MessageResponse.JWT_CLAIM_EMPTY);
        }
    }

    private Date getTimeNow(){
        return new Date();
    }

    private Date getExpiredTokenTime(){
        return new Date(getTimeNow().getTime() + JWT_EXPIRATION_TOKEN);
    }

    private Map<String, Object> getClaims(User user){
        Object randomString = getRandomString();
        Map<String, Object> mClaims = new HashMap<>();

        mClaims.put("email", user.getEmail());
        mClaims.put("phone_number", user.getPhoneNumber());
        mClaims.put("roles", user.getRoles());
        mClaims.put("uid", user.getUid());
        mClaims.put("key", randomString);
        return mClaims;
    }

    private Map<String, Object> header(){
        Map<String, Object> map = new HashMap<>();
        map.put("typ", "JWT");
        map.put("alg", SignatureAlgorithm.HS512.getValue());
        return map;
    }

    private Map<String, Object> stringRandomGenerator(){
        Map<String, Object> mClaims = new HashMap<>();
        Object randomString = getRandomString();
        mClaims.put("info", randomString);
        return mClaims;
    }

    private List<?> getRandomString() {
        String randomString = RandomStringUtils.randomAlphabetic(120);
        byte[] encodedAsBytes = randomString.getBytes();
        String encode = Base64.getMimeEncoder().encodeToString(encodedAsBytes);
        return List.of(encode);
    }
}
