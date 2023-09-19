package id.ic.vokta.rest.service;


import id.ic.vokta.util.date.DateHelper;

import javax.annotation.security.PermitAll;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.TimeZone;

@Path("onboard")
@Singleton
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OnBoardService extends BaseService {

    public OnBoardService() {
        log = getLogger(this.getClass());
    }

    @GET
    @Path("register-start")
    public Response startRegisterOnboard() {
        final String methodName = "startRegisterOnboard";
        start(methodName);
        log.debug(methodName, "GET /onboard/register-start");
        // Clear old sessions
        if (getSession() != null) {
            getSession().invalidate();
        }
        setTrackingID();
        completed(methodName);
        return buildSuccessResponse();

    }

    @GET
    @Path("register-status")
    @PermitAll
    public Response statusRegisterOnboard() {
        final String methodName = "statusRegisterOnboard";
        start(methodName);
        log.debug(methodName, "GET /onboard/register-status");
        String trackingId = getTrackingID();
        completed(methodName);
        return buildSuccessResponse(trackingId);

    }

    @GET
    @Path("register-complete")
    @PermitAll
    public Response completeRegisterOnBoard() {
        final String methodName = "completeRegisterOnBoard";
        start(methodName);
        log.debug(methodName, "GET /onboard/register-complete");
        // Clear old sessions
        if (getSession() != null) {
            getSession().invalidate();
        }
        completed(methodName);
        return buildSuccessResponse();

    }

    @GET
    @Path("login-start")
    public Response startLoginOnboard() {
        final String methodName = "startLoginOnboard";
        start(methodName);
        log.debug(methodName, "GET /onboard/login-start");
        // Clear old sessions
        if (getSession() != null) {
            getSession().invalidate();
        }
        setTrackingID();
        completed(methodName);
        return buildSuccessResponse();

    }

    @GET
    @Path("login-status")
    @PermitAll
    public Response statusLoginOnboard() {
        final String methodName = "statusLoginOnboard";
        start(methodName);
        log.debug(methodName, "GET /onboard/login-status");
        String trackingId = getTrackingID();
        completed(methodName);
        return buildSuccessResponse(trackingId);

    }

    @GET
    @Path("login-complete")
    @PermitAll
    public Response completeLoginOnBoard() {
        final String methodName = "completeLoginOnBoard";
        start(methodName);
        log.debug(methodName, "GET /onboard/login-complete");
        // Clear old sessions
        if (getSession() != null) {

        }
        completed(methodName);
        return buildSuccessResponse();

    }

    @GET
    @Path("environment")
    public Response environmentCheck() {
        final String methodName = "environmentCheck";
        start(methodName);
        log.debug(methodName, "GET /onboard/environment");

        HashMap<String, String> configMap = new HashMap<>();
        configMap.put("TimeZone", TimeZone.getDefault().getID());
        configMap.put("System Time", DateHelper.formatDateTime(LocalDateTime.now()));

        completed(methodName);
        return buildSuccessResponse(configMap);

    }

}
