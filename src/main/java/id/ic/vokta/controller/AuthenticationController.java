package id.ic.vokta.controller;

import id.ic.vokta.model.User;
import org.jdbi.v3.core.Handle;
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
}
