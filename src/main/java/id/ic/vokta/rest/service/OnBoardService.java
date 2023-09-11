package id.ic.vokta.rest.service;


import javax.annotation.security.PermitAll;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
            //getSession().invalidate();
        }
        completed(methodName);
        return buildSuccessResponse();

    }

}
