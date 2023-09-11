package id.ic.vokta.console;

import id.ic.vokta.model.User;
import id.ic.vokta.util.date.DateHelper;
import id.ic.vokta.util.helper.JWTHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import java.time.LocalDateTime;
import java.util.UUID;

public class JWTConsole {


    public static void main(String[] args) {

        String uid = UUID.randomUUID().toString();
        User user = new User();
        user.setUid(uid);
        user.setFullname("John Doe");
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setEmail("johndoe@gmail.com");
        user.setMobileNo("085747079410");
        user.setAddress("Jln. Sarwodadi Raya, No. 822, RT 02 RW 08, Purwokerto Kidul, Purwokerto Selatan, Banyumas, Jawa Tengah, 53147");
        user.setModifyDt(DateHelper.formatDateTime(LocalDateTime.now()));
        user.setCreateDt(DateHelper.formatDateTime(LocalDateTime.now()));

/*        String jwt = JWTHelper.createJWT(user.getUid(),user);
        Jws<Claims> claims = JWTHelper.decodeJWT(jwt);

        System.out.println(jwt);
        System.out.println(claims);*/
        String printJwt = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjp7InVpZCI6ImUyMWFjYTQyLWJmZGQtNGEyMi05NTFlLWYwYTQxOTFjNDFhZCIsImZ1bGxuYW1lIjoiSm9obiBEb2UiLCJmaXJzdG5hbWUiOiJKb2huIiwibGFzdG5hbWUiOiJEb2UiLCJlbWFpbCI6ImpvaG5kb2VAZ21haWwuY29tIiwibW9iaWxlTm8iOiIwODU3NDcwNzk0MTAiLCJhZGRyZXNzIjoiSmxuLiBTYXJ3b2RhZGkgUmF5YSwgTm8uIDgyMiwgUlQgMDIgUlcgMDgsIFB1cndva2VydG8gS2lkdWwsIFB1cndva2VydG8gU2VsYXRhbiwgQmFueXVtYXMsIEphd2EgVGVuZ2FoLCA1MzE0NyIsInN0YXR1cyI6ZmFsc2UsImNyZWF0ZUR0IjoiMjAyMy0wOS0xMSAxMToxNDoyMCIsIm1vZGlmeUR0IjoiMjAyMy0wOS0xMSAxMToxNDoyMCJ9LCJyb2xlIjpbInVzZXIiXSwic3ViIjoidXNlcl9hdXRoIiwianRpIjoiZTIxYWNhNDItYmZkZC00YTIyLTk1MWUtZjBhNDE5MWM0MWFkIiwiaWF0IjoxNjk0NDA1NjYxLCJleHAiOjE2OTQ0MDU5NjF9.IUpvS1vpjysiwdD6j1La-bTGSvb9LvAapawC1lFEygs";

        boolean valid = JWTHelper.validateJWT(printJwt);
        if (valid) {
            Jws<Claims> claims = JWTHelper.decodeJWT(printJwt);
            System.out.println(claims);
        } else {
            System.out.println("Invalid");
        }

    }
}
