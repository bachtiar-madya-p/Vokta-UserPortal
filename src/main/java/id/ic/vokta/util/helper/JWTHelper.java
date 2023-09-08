package id.ic.vokta.util.helper;

import id.ic.vokta.util.json.JsonHelper;
import id.ic.vokta.util.log.BaseLogger;
import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

public class JWTHelper {

    private static final BaseLogger log = new BaseLogger(JsonHelper.class);
    private static String SECRET_KEY = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";

    private static Key HMAC_KEY = new SecretKeySpec(Base64.getDecoder().decode(SECRET_KEY),
            SignatureAlgorithm.HS256.getJcaName());

    public static String createJWT(String uid, String name, String email, String subject) {

        Instant now = Instant.now();
        //Builds the JWT and serializes it to a compact, URL-safe string
        return Jwts.builder()
                .claim("name", name)
                .claim("email", email)
                .claim("role", new String[]{"user"})
                .setSubject(subject)
                .setId(uid)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(3l, ChronoUnit.MINUTES)))
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

            result = true;
        } catch (Exception e) {
            //log.error("validateJWT", e.getMessage());
            System.out.println(e.getMessage());
        }
        return result;
    }
}
