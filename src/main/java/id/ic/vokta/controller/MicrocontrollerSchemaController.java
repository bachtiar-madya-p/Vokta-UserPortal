package id.ic.vokta.controller;

import id.ic.vokta.controller.model.MicrocontrollerSchema;
import id.ic.vokta.util.property.Constant;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.Update;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MicrocontrollerSchemaController extends BaseController {

    public MicrocontrollerSchemaController() {
        log = getLogger(this.getClass());
    }

    public List<MicrocontrollerSchema> getMicrocontrollerSchema(String userId) {
        final String methodName = "getMicrocontrollerSchema";

        List<MicrocontrollerSchema> result = new ArrayList<>();

        String sql = "SELECT uid, userUid, sensorUid, createDt FROM users_microcontroller_schema WHERE userUid = :userUid;";

        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {
            q.bind("userUid", userId);
            result = q.mapToBean(MicrocontrollerSchema.class).list();

        } catch (Exception ex) {
            log.error(methodName, ex.getMessage());
            if (ex.getMessage().contains(Constant.ERROR_EXPECTED_ONE_ELEMENT)) {
                log.debug(methodName, "Microcontroller schema not found!");
            }
        }
        return result;
    }

    public boolean createSensorSchema(MicrocontrollerSchema schema) {
        final String methodName = "createSensorSchema";
        start(methodName);

        boolean result = false;

        String sql = "INSERT INTO `users_microcontroller_schema` (`uid`, `userUid`, `sensorUid`) " +
                "VALUES (:uid, :userUid, :sensorUid);";

        try (Handle h = getHandle(); Update update = h.createUpdate(sql)) {
            update.bindBean(schema);
            result = executeUpdate(update);
            log.debug(methodName, "Result: " + result);

        } catch (Exception ex) {
            log.error(methodName, ex.getMessage());
        }
        completed(methodName);
        return result;
    }
}
