package id.ic.vokta.rest.service;

import id.ic.vokta.controller.AuthenticationController;
import id.ic.vokta.controller.UserController;
import id.ic.vokta.manager.EncryptionManager;
import id.ic.vokta.rest.model.EmailRequest;
import id.ic.vokta.rest.model.LoginRequest;
import id.ic.vokta.rest.validator.LoginValidator;
import id.ic.vokta.rest.validator.RegisterValidator;
import id.ic.vokta.util.json.JsonHelper;
import id.ic.vokta.util.property.Constant;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("auth")
@Singleton
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthenticationService extends BaseService{

    @Inject
    private LoginValidator validator;

    @Inject
    private UserController userController;

    @Inject
    private AuthenticationController authenticationController;

    public AuthenticationService() {
        log = getLogger(this.getClass());
    }

    @POST
    public Response login(LoginRequest request) {
        final String methodName = "login";
        start(methodName);
        log.debug(methodName, "POST /auth");
        log.debug(methodName, JsonHelper.toJson(request));
        Response response = buildUnauthorizedResponse();

        boolean validPayload = validator.validate(request);
        log.debug(methodName, "Validate payload : " + validPayload);
        if (validPayload) {
            boolean emailExist = userController.validateEmail(request.getEmail());
            log.debug(methodName, "Email ("+request.getEmail()+") exist: " + emailExist);
            if (emailExist) {
                String userId = userController.getUserUid(request.getEmail());
                if(!userId.equals("")) {
                    String salt = authenticationController.getUserSalt(userId);
                    log.debug(methodName, "User salt : " + salt);
                    String hashedPassword = EncryptionManager.getInstance().hash(request.getPassword(), salt);
                    log.debug(methodName, "User uid : " + userId);
                    log.debug(methodName, "User password : " + hashedPassword);
                    boolean authenticate = authenticationController.authenticate(userId, hashedPassword);
                    if(authenticate) {
                        response = buildSuccessResponse();
                    }
                }
            } else {
                response = buildBadRequestResponse(Constant.ERROR_USER_NOT_FOUND);
            }
        }
        completed(methodName);
        return response;
    }
}
