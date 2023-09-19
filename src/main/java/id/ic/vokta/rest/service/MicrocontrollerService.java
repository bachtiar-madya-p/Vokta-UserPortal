package id.ic.vokta.rest.service;

import id.ic.vokta.controller.MicrocontrollerController;
import id.ic.vokta.controller.MicrocontrollerEventController;
import id.ic.vokta.controller.MicrocontrollerSchemaController;
import id.ic.vokta.controller.model.MicrocontrollerSchema;
import id.ic.vokta.model.Microcontroller;
import id.ic.vokta.model.MicrocontrollerEvent;
import id.ic.vokta.rest.model.MicrocontrollerDetailResponse;
import id.ic.vokta.rest.model.MicrocontrollerEventsResponse;
import id.ic.vokta.rest.model.MicrocontrollerRequest;
import id.ic.vokta.rest.model.MicrocontrollerResponse;
import id.ic.vokta.rest.validator.MicrocontrollerValidator;
import id.ic.vokta.util.json.JsonHelper;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @Inject
    private MicrocontrollerValidator validator;

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
                Microcontroller microcontroller = microcontrollerController.getMinimalSensorInformation(schema.getSensorUid());
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
        Microcontroller microcontroller = microcontrollerController.getSensorInformation(uid);
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

        Microcontroller microcontroller = microcontrollerController.getMinimalSensorInformation(uid);
        log.debug(methodName, JsonHelper.toJson(microcontroller));
        MicrocontrollerEvent currentEvent = eventController.getCurrentStatus(microcontroller.getSensorId());
        if(currentEvent.getCreateDt() != null) {
            log.debug(methodName, JsonHelper.toJson(currentEvent));
            MicrocontrollerEvent currentEventResponse = new MicrocontrollerEvent();
            currentEventResponse.setLatitude(currentEvent.getLatitude());
            currentEventResponse.setLongitude(currentEvent.getLongitude());
            currentEventResponse.setPh(currentEvent.getPh());
            currentEventResponse.setTurbidity(currentEvent.getTurbidity());
            currentEventResponse.setTds(currentEvent.getTds());

            Double maxCapacity = Double.parseDouble(microcontroller.getCapacity());
            Double filledLevel = Double.parseDouble(currentEvent.getLevel()) / 100;
            double currentCapacity = maxCapacity * filledLevel;

            currentEvent.setLevelInLiters(String.valueOf(currentCapacity));
            currentEvent.setLevelInPercents(currentEvent.getLevel());
            //remove redundant values
            currentEvent.setLevel(null);
        }


        MicrocontrollerEventsResponse eventsResponse = new MicrocontrollerEventsResponse();
        eventsResponse.setUid(microcontroller.getUid());
        eventsResponse.setSensorId(microcontroller.getSensorId());
        eventsResponse.setSensorName(microcontroller.getSensorName());
        eventsResponse.setCapacity(microcontroller.getCapacity());
        eventsResponse.setEvent(currentEvent);
        Response response = buildSuccessResponse(eventsResponse);

        completed(methodName);
        return response;
    }

    @POST
    public Response addMicrocontroller(@HeaderParam("Authorization") String bearerToken, MicrocontrollerRequest request) {
        final String methodName = "addMicrocontroller";
        start(methodName);
        log.debug(methodName, "POST /microcontrollers");
        log.debug(methodName, JsonHelper.toJson(request));
        Response response = buildBadRequestResponse();
        String userId = extractUid(bearerToken);
        boolean validPayload = validator.validateCreate(request);
        log.debug(methodName, "validPayload: " + validPayload);
        if (validPayload) {
            Microcontroller microcontroller = new Microcontroller();

            String sensorUid = UUID.randomUUID().toString();
            microcontroller.setUid(sensorUid);
            microcontroller.setSensorId(request.getSensorId());
            microcontroller.setSensorName(request.getSensorName());
            microcontroller.setTankBrand(request.getTankBrand());
            microcontroller.setTankType(request.getTankType());
            microcontroller.setCapacity(request.getCapacity());
            microcontroller.setDiameter(request.getDiameter());
            microcontroller.setHeight(request.getHeight());

            boolean createSensor = microcontrollerController.addSensor(microcontroller);
            log.debug(methodName, "Creating new sensor record : " + createSensor);
            if (createSensor) {
                MicrocontrollerSchema schema = new MicrocontrollerSchema();
                schema.setUid(UUID.randomUUID().toString());
                schema.setSensorUid(sensorUid);
                schema.setUserUid(userId);

                boolean createSchema = schemaController.createSensorSchema(schema);
                log.debug(methodName, "Creating new sensor schema record : " + createSchema);
                if (createSchema) {
                    response = buildSuccessResponse();
                }
            }
        }
        completed(methodName);
        return response;
    }

    @PUT
    public Response updateMicrocontroller(MicrocontrollerRequest request) {
        final String methodName = "updateMicrocontroller";
        start(methodName);
        log.debug(methodName, "PUT /microcontrollers");
        log.debug(methodName, JsonHelper.toJson(request));
        Response response = buildBadRequestResponse();

        boolean validPayload = validator.validateUpdate(request);
        log.debug(methodName, "validPayload: " + validPayload);
        if (validPayload) {
            Microcontroller microcontroller = new Microcontroller();

            microcontroller.setUid(request.getUid());
            microcontroller.setSensorId(request.getSensorId());
            microcontroller.setSensorName(request.getSensorName());
            microcontroller.setTankBrand(request.getTankBrand());
            microcontroller.setTankType(request.getTankType());
            microcontroller.setCapacity(request.getCapacity());
            microcontroller.setDiameter(request.getDiameter());
            microcontroller.setHeight(request.getHeight());

            boolean updateSensor = microcontrollerController.updateSensor(microcontroller);
            log.debug(methodName, "Update sensor (" + request.getUid() + ") record : " + updateSensor);
            if (updateSensor) {
                response = buildSuccessResponse();
            }
        }
        completed(methodName);
        return response;
    }

    @DELETE
    public Response deleteMicrocontroller(@QueryParam("uid") String uid) {
        final String methodName = "deleteMicrocontroller";
        start(methodName);
        log.debug(methodName, "DELETE /microcontrollers?uid=" + uid);

        Response response = buildBadRequestResponse();
        boolean deleteSensor = microcontrollerController.deleteSensor(uid);
        log.debug(methodName, "Deleting sensor record (" + uid + ") : " + deleteSensor);
        if (deleteSensor) {
            response = buildSuccessResponse();
        }
        completed(methodName);
        return response;
    }
}
