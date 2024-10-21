package zaza.techblog.global.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import zaza.techblog.global.common.code.RoleCode;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
public class JasonWebTokenUtils {

    private final SecretKey secretKey;
    private final long accessTokenExpireTime;

    public JasonWebTokenUtils(@Value("${jwt.secret}") String secretKey, @Value("${jwt.expiration_time}") long accessTokenExpireTime) {

        this.secretKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8),Jwts.SIG.HS256.key().build().getAlgorithm());
        this.accessTokenExpireTime = accessTokenExpireTime;
    }

    private Claims getPayload(String token) {

        JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();
        Jws<Claims> claimsJws = jwtParser.parseSignedClaims(token);

        return claimsJws.getPayload();
    }

    public String getUsername(String token) {
        return getPayload(token).get("username", String.class);
    }

    public RoleCode getRole(String token) {

        String roleCodename = getPayload(token).get("role", String.class);

        return RoleCode.of(roleCodename);
    }

    public Boolean isExpired(String token) {

        return getPayload(token).getExpiration().before(new Date());
    }

    public String createJwt(String username, RoleCode role) {

        return Jwts.builder()
                .claim("username", username)
                .claim("role", role.getCodename())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpireTime))
                .signWith(secretKey)
                .compact();
    }
}
