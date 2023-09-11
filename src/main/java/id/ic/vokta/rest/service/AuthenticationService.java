package id.ic.vokta.rest.service;

import id.ic.vokta.controller.AuthenticationController;
import id.ic.vokta.controller.UserController;
import id.ic.vokta.manager.EncryptionManager;
import id.ic.vokta.manager.PropertyManager;
import id.ic.vokta.model.User;
import id.ic.vokta.rest.model.AuthenticationResponse;
import id.ic.vokta.rest.model.LoginRequest;
import id.ic.vokta.rest.validator.LoginValidator;
import id.ic.vokta.util.date.DateHelper;
import id.ic.vokta.util.helper.JWTHelper;
import id.ic.vokta.util.json.JsonHelper;
import id.ic.vokta.util.property.Constant;
import id.ic.vokta.util.property.Property;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;

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
                    String hashedPassword = EncryptionManager.getInstance().hash(request.getPassword(), salt);
                    boolean authenticate = authenticationController.authenticate(userId, hashedPassword);
                    if(authenticate) {
                        User user = userController.getUserInformation(userId);
                        log.debug(methodName, JsonHelper.toJson(user));
                        String token = JWTHelper.createJWT(userId);

                        long expireMin = PropertyManager.getInstance().getLongProperty(Property.JWT_AUTH_EXPIRE_INTERVAL);
                        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(expireMin);

                        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
                        authenticationResponse.setAccessToken(token);
                        authenticationResponse.setScope("user");
                        authenticationResponse.setExpiryDate(DateHelper.formatDateTime(expiryTime));
                        response = buildSuccessResponse(authenticationResponse);
                    }
                }
            } else {
                response = buildBadRequestResponse(Constant.ERROR_USER_NOT_FOUND);
            }
        }
        completed(methodName);
        return response;
    }

    @POST
    @Path("logout")
    public Response logout(@HeaderParam("Authorization") String authorizationHeader) {
        final String methodName = "logout";
        start(methodName);
        log.debug(methodName, "POST /auth/logout");

        String token = extractToken(authorizationHeader);
        if (token != null) {
            JWTHelper.blacklistToken(token);
        }
        Response response = buildSuccessResponse();
        completed(methodName);
        return response;
    }
}
