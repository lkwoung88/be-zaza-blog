package zaza.techblog.global.auth.jwt;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class JasonWebTokenUtils {

    private final SecretKey secretKey;
    private final long accessTokenExpireTime;

    public JasonWebTokenUtils(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.expiration_time}")long accessTokenExpireTime
            ) {
        this.secretKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8),Jwts.SIG.HS256.key().build().getAlgorithm());
        this.accessTokenExpireTime = accessTokenExpireTime;
    }
}
