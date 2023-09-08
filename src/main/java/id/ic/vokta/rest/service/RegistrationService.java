package id.ic.vokta.rest.service;

import id.ic.vokta.controller.AuthenticationController;
import id.ic.vokta.controller.OtpController;
import id.ic.vokta.controller.UserController;
import id.ic.vokta.controller.model.otp.OtpResponse;
import id.ic.vokta.controller.model.otp.OtpValidateResponse;
import id.ic.vokta.manager.EncryptionManager;
import id.ic.vokta.model.User;
import id.ic.vokta.rest.model.EmailRequest;
import id.ic.vokta.rest.model.RegisterVerifyRequest;
import id.ic.vokta.rest.model.UserRequest;
import id.ic.vokta.rest.validator.RegisterValidator;
import id.ic.vokta.util.date.DateHelper;
import id.ic.vokta.util.json.JsonHelper;
import id.ic.vokta.util.property.Constant;
import id.ic.vokta.util.property.Property;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;

import java.util.UUID;

@Path("register")
@Singleton
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RegistrationService extends BaseService {

    @Inject
    private RegisterValidator validator;

    @Inject
    private UserController userController;

    @Inject
    private AuthenticationController authenticationController;

    @Inject
    private OtpController otpController;

    public RegistrationService() {
        log = getLogger(this.getClass());
    }

    @POST
    @Path("validate/email")
    @PermitAll
    public Response validateEmail(EmailRequest request) {
        final String methodName = "validateEmail";
        start(methodName);
        log.debug(methodName, "POST /register/email/validate");
        log.debug(methodName, JsonHelper.toJson(request));
        Response response = buildBadRequestResponse(Constant.ERROR_INVALID_PAYLOAD);

        boolean validPayload = validator.validate(request);
        log.debug(methodName, "Validate payload : " + validPayload);
        if (validPayload) {
            boolean emailExist = userController.validateEmail(request.getMail());
            log.debug(methodName, "Email exist: " + emailExist);
            if (!emailExist) {
                response = buildSuccessResponse();
            } else {
                response = buildConflictResponse();
            }
        }
        completed(methodName);
        return response;
    }

    @POST
    @Path("store")
    @PermitAll
    public Response store(UserRequest request) {
        final String methodName = "store";
        start(methodName);
        log.debug(methodName, "POST /register/store");
        log.debug(methodName, JsonHelper.toJson(request));
        Response response = buildBadRequestResponse(Constant.ERROR_INVALID_PAYLOAD);
        boolean validPayload = validator.validate(request);
        log.debug(methodName, "Valid payload :" + validPayload);
        if (validPayload) {
            boolean emailExist = userController.validateEmail(request.getEmail());
            log.debug(methodName, "Email exist: " + emailExist);
            if (!emailExist) {

                String fullname = "";
                if (request.getLastname().equals("")) {
                    fullname = request.getFirstname();
                } else {
                    fullname = request.getFirstname() + " " + request.getLastname();
                }
                User user = new User();
                user.setFullname(fullname);
                user.setFirstname(request.getFirstname());
                user.setLastname(request.getLastname());
                user.setEmail(request.getEmail());
                user.setMobileNo(request.getMobileNo());
                user.setAddress(request.getAddress());
                user.setPassword(request.getPassword());

                setSessionAttribute(Constant.SESSION_USER_DETAILS, user);
                setSessionAttribute(Constant.SESSION_EMAIL, request.getEmail());
                setSessionAttribute(Constant.SESSION_EMAIL_VERIFIED, false);

                LocalDateTime ldt = LocalDateTime.now();
                log.debug(methodName, "Current time : " + DateHelper.formatDateTime(ldt));

                LocalDateTime resendInterval = ldt.plusSeconds(getIntProperty(Property.OTP_RESEND_INTERVAL));
                log.debug(methodName, "Resend intervals : " + DateHelper.formatDateTime(resendInterval));
                setSessionAttribute(Constant.SESSION_OTP_SEND_INTERVAL, DateHelper.formatDateTime(resendInterval));

                // send OTP immediately
                executor.execute(() -> {
                    log.debug(methodName, "Request OTP for user: " + request.getEmail());
                    OtpResponse otpResponse = otpController.send(request.getEmail(), request.getFirstname());
                    log.debug(methodName, JsonHelper.toJson(otpResponse));
                });
                response = buildSuccessResponse();
            } else {
                response = buildConflictResponse();
            }
        }
        completed(methodName);
        return response;
    }

    @POST
    @Path("otp/resend")
    @PermitAll
    public Response resendOtp() {
        final String methodName = "resendOtp";
        start(methodName);
        log.debug(methodName, "POST /register/otp/resend");
        boolean sessionUser = hasSessionAttribute(Constant.SESSION_USER_DETAILS);
        Response response = buildBadRequestResponse();
        if (sessionUser) {

            User user = getSessionAttribute(Constant.SESSION_USER_DETAILS, User.class);
            LocalDateTime ldt = LocalDateTime.now();
            log.debug(methodName, "Current time : " + DateHelper.formatDateTime(ldt));
            String intervalStr = getSessionAttribute(Constant.SESSION_OTP_SEND_INTERVAL, String.class);
            LocalDateTime resendInterval = DateHelper.parseDateTime(intervalStr);
            log.debug(methodName, "Resend intervals : " + DateHelper.formatDateTime(resendInterval));
            if (resendInterval.isBefore(ldt)) {
                log.debug(methodName, "OK resend new OTP");
                // send OTP immediately
                executor.execute(() -> {
                    log.debug(methodName, "Request resend OTP for user: " + user.getEmail());
                    OtpResponse otpResponse = otpController.send(user.getEmail(), user.getFirstname());
                    log.debug(methodName, JsonHelper.toJson(otpResponse));
                });
                response = buildSuccessResponse();
            } else {
                log.debug(methodName, "Resend intervals is before current time, Skip resend new OTP!!");
                buildBadRequestResponse();
            }
        }

        completed(methodName);
        return response;
    }

    @POST
    @Path("verify")
    @PermitAll
    public Response verifyRegistration(RegisterVerifyRequest request) {
        final String methodName = "verifyRegistration";
        start(methodName);
        log.debug(methodName, "POST /register/verify");
        log.debug(methodName, JsonHelper.toJson(request));
        Response response = buildBadRequestResponse(Constant.ERROR_INVALID_PAYLOAD);
        boolean validPayload = validator.validate(request);
        log.debug(methodName, "Valid payload :" + validPayload);
        if (validPayload) {
            boolean sessionUser = hasSessionAttribute(Constant.SESSION_USER_DETAILS);
            if (sessionUser) {
                log.debug(methodName, "Validate OTP for user: " + request.getEmail());
                OtpValidateResponse otpResponse = otpController.validateOTPBeforeCreateUser(request.getEmail(), request.getOtp());
                log.debug(methodName, JsonHelper.toJson(otpResponse));

                if (otpResponse.getResult().isValidate()) {
                    User user = getSessionAttribute(Constant.SESSION_USER_DETAILS, User.class);

                    String salt = EncryptionManager.getInstance().generateRandomString(6);
                    String encryptPassword = EncryptionManager.getInstance().hash(user.getPassword(), salt);

                    user.setUid(UUID.randomUUID().toString());
                    user.setSalt(salt);
                    user.setPassword(encryptPassword);

                    boolean createUser = userController.createUser(user);
                    log.debug(methodName, "Create user to DB : " + createUser);
                    if (createUser) {
                        boolean createAuthentication = authenticationController.createAuthentication(user);
                        log.debug(methodName, "Create authentication user to DB : " + createAuthentication);
                        response = buildSuccessResponse();
                    } else {
                        response = buildErrorResponse();
                    }

                } else {
                    response = buildBadRequestResponse(otpResponse.getMessage());
                }
            }
        }
        completed(methodName);
        return response;
    }
}
