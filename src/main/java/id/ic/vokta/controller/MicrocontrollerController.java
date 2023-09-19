package id.ic.vokta.controller;

import id.ic.vokta.model.Microcontroller;
import id.ic.vokta.model.User;
import id.ic.vokta.util.property.Constant;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.Update;

@Controller
public class MicrocontrollerController extends BaseController {

    public MicrocontrollerController() {
        log = getLogger(this.getClass());
    }

    public Microcontroller getSensorInformation(String uid) {
        final String methodName = "getSensorInformation";
        start(methodName);

        Microcontroller microcontroller = new Microcontroller();

        String sql = "SELECT uid, sensorId, sensorName ,tankBrand , tankType, capacity, diameter, height, createDt, modifyDt FROM microcontroller WHERE uid = :uid;";

        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {
            q.bind("uid", uid);
            microcontroller = q.mapToBean(Microcontroller.class).one();

        } catch (Exception ex) {
            log.error(methodName, ex.getMessage());
            if (ex.getMessage().contains(Constant.ERROR_EXPECTED_ONE_ELEMENT)) {
                log.debug(methodName, "Microcontroller not found!");
            }
        }
        completed(methodName);
        return microcontroller;
    }

    public Microcontroller getMinimalSensorInformation(String uid) {
        final String methodName = "getMinimalSensorInformation";
        start(methodName);

        Microcontroller microcontroller = new Microcontroller();

        String sql = "SELECT uid, sensorId, sensorName, capacity FROM microcontroller WHERE uid = :uid;";

        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {
            q.bind("uid", uid);
            microcontroller = q.mapToBean(Microcontroller.class).one();

        } catch (Exception ex) {
            log.error(methodName, ex.getMessage());
            if (ex.getMessage().contains(Constant.ERROR_EXPECTED_ONE_ELEMENT)) {
                log.debug(methodName, "Microcontroller not found!");
            }
        }
        completed(methodName);
        return microcontroller;
    }

    public boolean addSensor(Microcontroller microcontroller) {
        final String methodName = "createSensor";
        start(methodName);

        boolean result = false;

        String sql = "INSERT INTO microcontroller (uid, sensorId, sensorName, capacity, diameter, height, tankBrand, tankType) " +
                "VALUES(:uid, :sensorId, :sensorName, :capacity, :diameter, :height, :tankBrand, :tankType);";

        try (Handle h = getHandle(); Update update = h.createUpdate(sql)) {
            update.bindBean(microcontroller);
            result = executeUpdate(update);
            log.debug(methodName, "Result: " + result);

        } catch (Exception ex) {
            log.error(methodName, ex.getMessage());
        }
        completed(methodName);
        return result;
    }

    public boolean updateSensor(Microcontroller microcontroller) {
        final String methodName = "updateSensor";
        start(methodName);

        boolean result = false;

        String sql = "UPDATE microcontroller " +
                    "SET sensorId= :sensorId, sensorName= :sensorName, tankBrand= :tankBrand, tankType= :tankType, capacity= :capacity, diameter= :diameter, height= :height " +
                "WHERE uid = :uid;";

        try (Handle h = getHandle(); Update update = h.createUpdate(sql)) {
            update.bindBean(microcontroller);
            result = executeUpdate(update);
            log.debug(methodName, "Result: " + result);

        } catch (Exception ex) {
            log.error(methodName, ex.getMessage());
        }
        completed(methodName);
        return result;
    }
    public boolean deleteSensor(String uid) {
        final String methodName = "deleteSensor";
        start(methodName);

        boolean result = false;

        String sql = "DELETE FROM microcontroller WHERE uid= :uid;";

        try (Handle h = getHandle(); Update update = h.createUpdate(sql)) {
            update.bind("uid", uid);
            result = executeUpdate(update);
            log.debug(methodName, "Result: " + result);

        } catch (Exception ex) {
            log.error(methodName, ex.getMessage());
        }
        completed(methodName);
        return result;
    }
}
