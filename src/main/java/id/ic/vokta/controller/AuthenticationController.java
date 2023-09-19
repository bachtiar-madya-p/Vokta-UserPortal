package id.ic.vokta.controller;

import id.ic.vokta.model.User;
import id.ic.vokta.util.property.Constant;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.Update;

@Controller
public class AuthenticationController extends BaseController{

    public AuthenticationController() {
        log = getLogger(this.getClass());
    }

    public boolean createAuthentication(User user) {
        final String methodName = "createAuthentication";
        start(methodName);

        boolean result = false;

        String sql = "INSERT INTO `authentications` (`uid`, `salt`, `password`) " +
                "VALUES (:uid, :salt, :password);";

        try (Handle h = getHandle(); Update update = h.createUpdate(sql)) {
            update.bindBean(user);
            result = executeUpdate(update);
            log.debug(methodName, "Result: " + result);

        } catch (Exception ex) {
            log.error(methodName, ex.getMessage());
        }
        completed(methodName);
        return result;
    }

    public String getUserSalt(String userId) {
        final String methodName = "getUserSalt";

        String result = "";

        String sql = "SELECT salt  " +
                "FROM authentications WHERE uid = :userId;";

        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {
            q.bind("userId", userId);
            result = q.mapTo(String.class).one();

        } catch (Exception ex) {
            log.error(methodName, ex.getMessage());
            if (ex.getMessage().contains(Constant.ERROR_EXPECTED_ONE_ELEMENT)) {
                log.debug(methodName, "Email not found!");
            }
        }

        return result;
    }

    public boolean authenticate(String userId, String hashedPassword) {
        final String methodName = "authenticate";
        start(methodName);
        boolean result = false;

        String sql = "SELECT if(COUNT(uid)>0,'true','false') AS result " +
                "FROM authentications WHERE uid = :uid AND password = :password;";

        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {
            q.bind("uid", userId);
            q.bind("password", hashedPassword);
            result = q.mapTo(Boolean.class).one();
            log.debug(methodName, "Challenge authentication : " + (result? "Ok":"Unauthorized"));
        } catch (Exception ex) {
            log.error(methodName, ex.getMessage());
            if (ex.getMessage().contains(Constant.ERROR_EXPECTED_ONE_ELEMENT)) {
                log.debug(methodName, "Email not found!");
            }
        }
        completed(methodName);
        return result;
    }
}
