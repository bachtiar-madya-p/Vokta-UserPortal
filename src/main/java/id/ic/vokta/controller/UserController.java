package id.ic.vokta.controller;

import id.ic.vokta.model.User;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.Update;

@Controller
public class UserController extends BaseController {

    public UserController() {
        log = getLogger(this.getClass());
    }

    public boolean validateEmail(String email) {
        final String methodName = "validateEmail";
        start(methodName);

        boolean result = false;

        String sql = "SELECT if(COUNT(email)>0,'true','false') AS result " +
                "FROM users WHERE email = :email;";

        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {
            q.bind("email", email);
            result = q.mapTo(Boolean.class).one();

        } catch (Exception ex) {
            log.error(methodName, ex.getMessage());
            if (ex.getMessage().contains("Expected one element, but found none")) {
                log.debug(methodName, "Email not found!");
            }
        }
        completed(methodName);
        return result;
    }

    public boolean createUser(User user) {
        final String methodName = "createUser";
        start(methodName);

        boolean result = false;

        String sql = "INSERT INTO `users` (`uid`, `fullname`, `firstname`, `lastname`, `email`, `mobileNo`, `address`) " +
                "VALUES (:uid, :fullname, :firstname, :lastname, :email, :mobileNo, :address);";

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
