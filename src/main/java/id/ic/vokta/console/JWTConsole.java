package id.ic.vokta.console;

import id.ic.vokta.util.helper.JWTHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import java.util.UUID;

public class JWTConsole {


    public static void main(String[] args) {

        String uid = UUID.randomUUID().toString();
        String name = "bmp";
        String email = "bmp@gmail.com";
        String subject = "Authentication";
/*        String jwt = JWTHelper.createJWT(uid,name,email, subject);
        Jws<Claims> claims = JWTHelper.decodeJWT(jwt);

        System.out.println(jwt);
        System.out.println(claims);*/

        String printJwt = "eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiYm1wIiwiZW1haWwiOiJibXBAZ21haWwuY29tIiwicm9sZSI6WyJ1c2VyIl0sInN1YiI6IkF1dGhlbnRpY2F0aW9uIiwianRpIjoiZmU1NDlhN2YtYWZlYS00NGM2LWE0ZmMtZTg4MzA3N2JmY2JmIiwiaWF0IjoxNjk0MTc5MzA1LCJleHAiOjE2OTQxNzk0ODV9.sR_a3fStsaXaG6cjEKBDdaHlh3nGjBnQqtNO-ExwPqE";

        boolean valid = JWTHelper.validateJWT(printJwt);
        if (valid) {
            Jws<Claims> claims = JWTHelper.decodeJWT(printJwt);
            System.out.println(claims);
        } else {
            System.out.println("Invalid");
        }

    }
}
