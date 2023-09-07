package id.ic.vokta.controller;

@Controller
public class UserController extends BaseController {

    public UserController() {
        log = getLogger(this.getClass());
    }
/*
    public boolean create(User user) {
        final String methodName = "create";
        boolean result = false;
        start(methodName);

        CreateUserRequest request = new CreateUserRequest(user);

        String encryptedRequest = encryptRequest(request);

        logRequest(methodName, request);

        HTTPRequest httpRequest = buildAPIRequest(getProperty(Property.API_USERS_CREATE_URL));
        log.debug(methodName, "URL : " + httpRequest.getUrl());

        HTTPResponse httpResponse = HTTPClient.post(httpRequest, encryptedRequest);

        CreateUserResponse response = decryptResponse(httpResponse, CreateUserResponse.class);

        logResponse(methodName, response);

        result = isSuccessResponse(httpResponse);

        completed(methodName);
        return result;
    }

    public boolean validateEmail(String email) {
        final String methodName = "validateEmail";
        boolean result = false;
        start(methodName);

        ValidateEmailRequest request = new ValidateEmailRequest(new USSUser("uid", email));

        logRequest(methodName, request);

        HTTPRequest httpRequest = buildAPIRequest(getProperty(Property.API_EMAIL_VALIDATE_URL));

        HTTPResponse httpResponse = HTTPClient.post(httpRequest, toJson(request));

        logResponse(methodName, httpResponse);

        result = isSuccessResponse(httpResponse);

        completed(methodName);
        return result;

    }*/
}
