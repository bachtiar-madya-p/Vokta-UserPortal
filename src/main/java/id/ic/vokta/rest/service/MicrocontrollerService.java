package id.ic.vokta.rest.service;

import id.ic.vokta.controller.MicrocontrollerController;
import id.ic.vokta.controller.MicrocontrollerEventController;
import id.ic.vokta.controller.MicrocontrollerSchemaController;
import id.ic.vokta.controller.model.MicrocontrollerSchema;
import id.ic.vokta.model.Microcontroller;
import id.ic.vokta.model.MicrocontrollerEvent;
import id.ic.vokta.rest.model.MicrocontrollerDetailResponse;
import id.ic.vokta.rest.model.MicrocontrollerEventsResponse;
import id.ic.vokta.rest.model.MicrocontrollerResponse;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("microcontrollers")
@Singleton
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MicrocontrollerService extends BaseService {

    @Inject
    private MicrocontrollerEventController eventController;

    @Inject
    private MicrocontrollerController microcontrollerController;

    @Inject
    private MicrocontrollerSchemaController schemaController;

    public MicrocontrollerService() {
        log = getLogger(this.getClass());
    }

    @GET
    public Response getMicrocontrollerList(@HeaderParam("Authorization") String authorizationHeader) {
        final String methodName = "getMicrocontrollerList";
        start(methodName);
        log.debug(methodName, "GET /microcontrollers");

        String userId = extractUid(authorizationHeader);

        List<MicrocontrollerSchema> schemaList = schemaController.getMicrocontrollerSchema(userId);

        MicrocontrollerResponse microcontrollerResponse = new MicrocontrollerResponse();
        if (!schemaList.isEmpty()) {
            List<Microcontroller> microcontrollers = new ArrayList<>();
            for (MicrocontrollerSchema schema : schemaList) {
                Microcontroller microcontroller = microcontrollerController.getMinimalMicrocontrollerInformation(schema.getMicrocontrollerId());
                microcontrollers.add(microcontroller);
            }
            microcontrollerResponse.setMicrocontrollers(microcontrollers);
        }

        Response response = buildSuccessResponse(microcontrollerResponse);

        completed(methodName);
        return response;
    }

    @GET
    @Path("{uid}")
    public Response getMicrocontrollerDetails(@HeaderParam("Authorization") String authorizationHeader, @PathParam("uid") String uid) {
        final String methodName = "getMicrocontrollerDetails";
        start(methodName);
        log.debug(methodName, "GET /microcontrollers/" + uid);

        MicrocontrollerDetailResponse detailResponse = new MicrocontrollerDetailResponse();
        Microcontroller microcontroller = microcontrollerController.getMicrocontrollerInformation(uid);
        detailResponse.setMicrocontroller(microcontroller);

        Response response = buildSuccessResponse(detailResponse);

        completed(methodName);
        return response;
    }

    @GET
    @Path("events/{uid}")
    public Response getMicrocontrollerEventDetails(@HeaderParam("Authorization") String authorizationHeader, @PathParam("uid") String uid) {
        final String methodName = "getMicrocontrollerDetails";
        start(methodName);
        log.debug(methodName, "GET /microcontrollers/events/" + uid);

        Microcontroller microcontroller = microcontrollerController.getMinimalMicrocontrollerInformation(uid);
        MicrocontrollerEvent currentEvent = eventController.getCurrentStatus(microcontroller.getDeviceId());

        MicrocontrollerEvent currentEventResponse = new MicrocontrollerEvent();
        currentEventResponse.setLatitude(currentEvent.getLatitude());
        currentEventResponse.setLongitude(currentEvent.getLongitude());
        currentEventResponse.setPh(currentEvent.getPh());
        currentEventResponse.setTurbidity(currentEvent.getTurbidity());
        currentEventResponse.setTds(currentEvent.getTds());

        Double maxCapacity = Double.parseDouble(microcontroller.getCapacity());
        Double filledLevel = Double.parseDouble(currentEvent.getLevel()) / 100;
        double currentCapacity = maxCapacity * filledLevel;

        currentEvent.setLevel(String.valueOf(currentCapacity));

        MicrocontrollerEventsResponse eventsResponse = new MicrocontrollerEventsResponse();
        eventsResponse.setUid(microcontroller.getUid());
        eventsResponse.setDeviceId(microcontroller.getDeviceId());
        eventsResponse.setDeviceName(microcontroller.getDeviceName());
        eventsResponse.setEvent(currentEvent);
        Response response = buildSuccessResponse(eventsResponse);

        completed(methodName);
        return response;
    }
}
