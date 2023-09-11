package id.ic.vokta.util.helper;

import id.ic.vokta.manager.PropertyManager;
import id.ic.vokta.util.json.JsonHelper;
import id.ic.vokta.util.log.BaseLogger;
import id.ic.vokta.util.property.Property;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class JWTHelper {

    private static final BaseLogger log = new BaseLogger(JsonHelper.class);
    private static String SECRET_KEY = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";

    private static Key HMAC_KEY = new SecretKeySpec(Base64.getDecoder().decode(SECRET_KEY),
            SignatureAlgorithm.HS256.getJcaName());

    private static Set<String> blacklistedTokens = ConcurrentHashMap.newKeySet();

    public static String createJWT(String uid) {

        Instant now = Instant.now();
        long expireMin = PropertyManager.getInstance().getLongProperty(Property.JWT_AUTH_EXPIRE_INTERVAL);
        //Builds the JWT and serializes it to a compact, URL-safe string
        return Jwts.builder()
                .claim("role", new String[]{"user"})
                .setSubject("user_auth")
                .setId(uid)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(expireMin, ChronoUnit.MINUTES)))
                .signWith(HMAC_KEY)
                .compact();
    }

    public static Jws<Claims> decodeJWT(String jwtString) {

        return Jwts.parserBuilder()
                .setSigningKey(HMAC_KEY)
                .build()
                .parseClaimsJws(jwtString);
    }

    public static boolean validateJWT(String jwtString) {

        boolean result = false;
        try {
            Jwts.parserBuilder()
                    .setSigningKey(HMAC_KEY)
                    .build()
                    .parseClaimsJws(jwtString);
            if (!isTokenBlacklisted(jwtString)) {
                result = true;
            }
        } catch (Exception e) {
            log.error("validateJWT", e.getMessage());
        }
        return result;
    }

    public static void blacklistToken(String token) {
        blacklistedTokens.add(token);
    }

    public static void purgeBlacklistToken() {
        blacklistedTokens.clear();
    }

    public static boolean isTokenBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }

    public static Set<String> getBlacklistedTokens() {
        return blacklistedTokens;
    }
}
