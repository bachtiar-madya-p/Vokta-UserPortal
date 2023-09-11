package id.ic.vokta.controller;

import id.ic.vokta.model.MicrocontrollerEvent;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MicrocontrollerEventController extends BaseController {

    public MicrocontrollerEventController() {
        log = getLogger(this.getClass());
    }

    public MicrocontrollerEvent getCurrentStatus(String deviceId) {
        final String methodName = "getCurrentStatus";
        start(methodName);

        MicrocontrollerEvent event = new MicrocontrollerEvent();

        String sql = "SELECT uid, deviceId, latitude, longitude, `level`, ph, turbidity, tds, createDt FROM microcontroller_event WHERE deviceId = :deviceId ORDER BY createDt ASC;";

        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {
            q.bind("deviceId", deviceId);
            event = q.mapToBean(MicrocontrollerEvent.class).one();

        } catch (Exception ex) {
            log.error(methodName, ex.getMessage());
            if (ex.getMessage().contains("Expected one element, but found none")) {
                log.debug(methodName, "Email not found!");
            }
        }
        completed(methodName);
        return event;
    }

    public List<MicrocontrollerEvent> getDailyEvents(String deviceId, String startDate, String endDate) {
        final String methodName = "getDailyEvents";
        start(methodName);

        List<MicrocontrollerEvent> events = new ArrayList<>();

        String sql = "SELECT uid, deviceId, latitude, longitude, `level`, ph, turbidity, tds, createDt FROM microcontroller_event WHERE deviceId = :deviceId AND createDt >= :startDate AND createDt < :endDate ORDER BY createDt ASC;";

        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {
            q.bind("deviceId", deviceId);
            q.bind("startDate", startDate);
            q.bind("endDate", endDate);
            events = q.mapToBean(MicrocontrollerEvent.class).list();

        } catch (Exception ex) {
            log.error(methodName, ex.getMessage());
            if (ex.getMessage().contains("Expected one element, but found none")) {
                log.debug(methodName, "Email not found!");
            }
        }
        completed(methodName);
        return events;
    }
}
