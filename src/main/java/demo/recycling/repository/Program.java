package demo.recycling.repository;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class Program {
    private static String SECRET_KEY = "secret";
    private static long tokenValidMilisecond = 1000L* 60 * 60 * 6;

    //토큰 생성
    public String createToken(String key){
        var claims = Jwts.claims().setId(key);
        Date now = new Date();

        return Jwts.builder().setClaims(claims)
                .setIssuedAt(now).setExpiration(new Date(now.getTime() + tokenValidMilisecond))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    //String된 토큰을 복호화
    public Jws<Claims> getClaims(String jwt){
        //System.out.println("!!!"+jwt);
        try{
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(jwt);
        }catch (SignatureException e){
            return null;
        }
    }
    //토큰 시간 검증 함수
    public boolean validateToken(Jws<Claims> claims){
        return !claims.getBody().getExpiration().before(new Date());
    }
    // 토큰을 통해 PayLoad의 ID를 취득
    public String getKey(Jws<Claims> claims){
        return claims.getBody().getId();
    }
    public Object getClaims(Jws<Claims> claims,  String key){
        return claims.getBody().get(key);
    }
}