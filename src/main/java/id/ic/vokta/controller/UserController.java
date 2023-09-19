package id.ic.vokta.controller;

import id.ic.vokta.model.User;
import id.ic.vokta.util.property.Constant;
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
            if (ex.getMessage().contains(Constant.ERROR_EXPECTED_ONE_ELEMENT)) {
                log.debug(methodName, "User not found!");
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

    public String getUserUid(String email) {
        final String methodName = "getUserUid";

        String result = "";

        String sql = "SELECT uid  " +
                "FROM users WHERE email = :email;";

        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {
            q.bind("email", email);
            result = q.mapTo(String.class).one();

        } catch (Exception ex) {
            log.error(methodName, ex.getMessage());
            if (ex.getMessage().contains(Constant.ERROR_EXPECTED_ONE_ELEMENT)) {
                log.debug(methodName, "User not found!");
            }
        }

        return result;
    }

    public User getUserInformation(String userid) {
        final String methodName = "getUserInformation";
        start(methodName);

        User user = new User();

        String sql = "SELECT uid, fullname, firstname, lastname, email, mobileNo, address, createDt, modifyDt  " +
                "FROM users WHERE uid = :uid;";

        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {
            q.bind("uid", userid);
            user = q.mapToBean(User.class).one();

        } catch (Exception ex) {
            log.error(methodName, ex.getMessage());
            if (ex.getMessage().contains(Constant.ERROR_EXPECTED_ONE_ELEMENT)) {
                log.debug(methodName, "Email not found!");
            }
        }
        completed(methodName);
        return user;
    }

    public boolean updateUser(User user) {
        final String methodName = "updateUser";
        start(methodName);

        boolean result = false;

        String sql = "UPDATE users SET fullname=:fullname, firstname=:firstname, lastname=:lastname, mobileNo=:mobileNo, address=:address WHERE uid=:uid;";

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
