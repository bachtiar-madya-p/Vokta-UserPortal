package id.ic.vokta.rest.service;


import id.ic.vokta.controller.UserController;
import id.ic.vokta.rest.model.UserRequest;
import id.ic.vokta.rest.validator.UserValidator;
import id.ic.vokta.util.json.JsonHelper;
import id.ic.vokta.util.property.Constant;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("users")
@Singleton
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserService extends BaseService {

    @Inject
    private UserValidator validator;

    @Inject
    private UserController userController;

    public UserService() {
        log = getLogger(this.getClass());
    }

    @GET
    @PermitAll
    public Response retrieveUser() {
        final String methodName = "retrieveUser";
        start(methodName);
        log.debug(methodName, "GET /users");

        Response response = buildSuccessResponse();

        completed(methodName);
        return response;
    }
}
