package id.ic.vokta.controller;

import id.ic.vokta.model.Microcontroller;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;

@Controller
public class MicrocontrollerController extends BaseController {

    public MicrocontrollerController() {
        log = getLogger(this.getClass());
    }

    public Microcontroller getMicrocontrollerInformation(String uid) {
        final String methodName = "getMicrocontrollerInformation";
        start(methodName);

        Microcontroller microcontroller = new Microcontroller();

        String sql = "SELECT uid, deviceId, deviceName, capacity, diameter, height, createDt, modifyDt, brand, capDiameter FROM microcontroller WHERE uid = :uid;";

        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {
            q.bind("uid", uid);
            microcontroller = q.mapToBean(Microcontroller.class).one();

        } catch (Exception ex) {
            log.error(methodName, ex.getMessage());
            if (ex.getMessage().contains("Expected one element, but found none")) {
                log.debug(methodName, "Email not found!");
            }
        }
        completed(methodName);
        return microcontroller;
    }

    public Microcontroller getMinimalMicrocontrollerInformation(String uid) {
        final String methodName = "getMinimalMicrocontrollerInformation";
        start(methodName);

        Microcontroller microcontroller = new Microcontroller();

        String sql = "SELECT uid, deviceId, deviceName, capacity FROM microcontroller WHERE uid = :uid;";

        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {
            q.bind("uid", uid);
            microcontroller = q.mapToBean(Microcontroller.class).one();

        } catch (Exception ex) {
            log.error(methodName, ex.getMessage());
            if (ex.getMessage().contains("Expected one element, but found none")) {
                log.debug(methodName, "Email not found!");
            }
        }
        completed(methodName);
        return microcontroller;
    }
}
