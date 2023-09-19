package id.ic.vokta.controller;

import id.ic.vokta.model.MicrocontrollerEvent;
import id.ic.vokta.util.property.Constant;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MicrocontrollerEventController extends BaseController {

    public MicrocontrollerEventController() {
        log = getLogger(this.getClass());
    }

    public MicrocontrollerEvent getCurrentStatus(String sensorId) {
        final String methodName = "getCurrentStatus";
        start(methodName);

        MicrocontrollerEvent event = null;

        String sql = "SELECT latitude, longitude, `level`, ph, turbidity, tds, createDt FROM microcontroller_event WHERE sensorId = :sensorId ORDER BY createDt DESC LIMIT 1;";

        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {
            q.bind("sensorId", sensorId);
            event = new MicrocontrollerEvent();
            event = q.mapToBean(MicrocontrollerEvent.class).one();

        } catch (Exception ex) {
            log.error(methodName, ex.getMessage());
            if (ex.getMessage().contains(Constant.ERROR_EXPECTED_ONE_ELEMENT)) {
                log.debug(methodName, "Microcontroller Event not found!");
            }
        }
        completed(methodName);
        return event;
    }

    public List<MicrocontrollerEvent> getDailyEvents(String sensorId, String startDate, String endDate) {
        final String methodName = "getDailyEvents";
        start(methodName);

        List<MicrocontrollerEvent> events = new ArrayList<>();

        String sql = "SELECT uid, sensorId, latitude, longitude, `level`, ph, turbidity, tds, createDt FROM microcontroller_event WHERE sensorId = :sensorId AND createDt >= :startDate AND createDt < :endDate ORDER BY createDt ASC;";

        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {
            q.bind("sensorId", sensorId);
            q.bind("startDate", startDate);
            q.bind("endDate", endDate);
            events = q.mapToBean(MicrocontrollerEvent.class).list();

        } catch (Exception ex) {
            log.error(methodName, ex.getMessage());
            if (ex.getMessage().contains(Constant.ERROR_EXPECTED_ONE_ELEMENT)) {
                log.debug(methodName, "Microcontroller Event not found!");
            }
        }
        completed(methodName);
        return events;
    }
}
