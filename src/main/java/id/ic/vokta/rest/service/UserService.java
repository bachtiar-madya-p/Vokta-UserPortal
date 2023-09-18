package id.ic.vokta.rest.service;


import id.ic.vokta.controller.UserController;
import id.ic.vokta.model.User;
import id.ic.vokta.rest.model.UserUpdateRequest;
import id.ic.vokta.rest.validator.UserValidator;
import id.ic.vokta.util.json.JsonHelper;

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
    public Response retrieveUser(@HeaderParam("Authorization") String authorizationHeader) {
        final String methodName = "retrieveUser";
        start(methodName);
        log.debug(methodName, "GET /users");
        Response response = buildBadRequestResponse();
        String userId = extractUid(authorizationHeader);

        User user = userController.getUserInformation(userId);

        response = buildSuccessResponse(user);

        completed(methodName);
        return response;
    }

    @PUT
    public Response updateUser(@HeaderParam("Authorization") String authorizationHeader, UserUpdateRequest request) {
        final String methodName = "updateUser";
        start(methodName);
        log.debug(methodName, "PUT /users");
        log.debug(methodName, JsonHelper.toJson(request));

        Response response = buildBadRequestResponse();
        boolean validPayload = validator.validate(request);
        log.debug(methodName, "Validate payload : " + validPayload);
        if (validPayload) {
            String userId = extractUid(authorizationHeader);


            String fullname = "";
            if (request.getLastname().equals("")) {
                fullname = request.getFirstname();
            } else {
                fullname = request.getFirstname() + " " + request.getLastname();
            }
            User user = new User();
            user.setUid(userId);
            user.setFullname(fullname);
            user.setFirstname(request.getFirstname());
            user.setLastname(request.getLastname());
            user.setEmail(request.getEmail());
            user.setMobileNo(request.getMobileNo());
            user.setAddress(request.getAddress());

            boolean update = userController.updateUser(user);
            if (update) {
                response = buildSuccessResponse(user);
            }

        }
        completed(methodName);
        return response;
    }
}
