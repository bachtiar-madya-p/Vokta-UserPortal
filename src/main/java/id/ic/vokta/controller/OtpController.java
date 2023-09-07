package id.ic.vokta.controller;

import id.ic.vokta.controller.model.otp.*;
import id.ic.vokta.util.http.HTTPClient;
import id.ic.vokta.util.http.model.HTTPContentType;
import id.ic.vokta.util.http.model.HTTPRequest;
import id.ic.vokta.util.http.model.HTTPResponse;
import id.ic.vokta.util.json.JsonHelper;
import id.ic.vokta.util.property.Property;
import org.apache.http.HttpStatus;

@Controller
public class OtpController extends BaseController{

    private static String otpBaseUrl;
    private static final String OTP_RETRY    = "/retry-count";
    private static final String OTP_SEND     = "/request";
    private static final String OTP_VALIDATE = "/validate";
    private static final String OTP_VALIDATE_BEFORE_USER_CREATE = "/validate-second-level";

    public OtpController() {
        log = getLogger(this.getClass());
        otpBaseUrl = getProperty(Property.OTP_URL);
    }

    public int getOTPRetryCount() {
        final String methodName = "getOTPRetryCount";
        start(methodName);

        int count  = getIntProperty(Property.OTP_RETRY_COUNT);
        String uri = otpBaseUrl + OTP_RETRY;

        HTTPResponse httpResponse = HTTPClient.get(buildHTTPRequest(uri));
        log.debug(methodName, httpResponse);

        OTPRetryCountResponse response = JsonHelper.fromJson(httpResponse.getBody(), OTPRetryCountResponse.class);
        if (httpResponse.getCode() == HttpStatus.SC_OK && response != null) {
            count = response.getCount();
        }

        log.debug(methodName, "OTP Retry Count : " + count);

        completed(methodName);
        return count;
    }

    public OtpResponse send(String email, String firstName) {
        final String methodName = "send";
        start(methodName);

        String uri = otpBaseUrl + OTP_SEND;

        OtpRequest payload = new OtpRequest();
        payload.setEmail(email);
        payload.setFirstname(firstName);

        log.debug(methodName, JsonHelper.toJson(payload));

        HTTPResponse httpResponse = HTTPClient.post(buildHTTPRequest(uri), JsonHelper.toJson(payload));

        log.debug(methodName, httpResponse.getBody());

        OtpResponse response = JsonHelper.fromJson(httpResponse.getBody(), OtpResponse.class);

        completed(methodName);
        return response;
    }

    public OtpValidateResponse validate(String email, String otp) {
        final String methodName = "validate";
        start(methodName);

        String uri = otpBaseUrl + OTP_VALIDATE;

        OtpValidateRequest payload = new OtpValidateRequest();
        payload.setEmail(email);
        payload.setOtp(otp);

        log.debug(methodName, payload);

        HTTPResponse httpResponse = HTTPClient.post(buildHTTPRequest(uri), JsonHelper.toJson(payload));

        log.debug(methodName, httpResponse.getBody());

        OtpValidateResponse response = JsonHelper.fromJson(httpResponse.getBody(), OtpValidateResponse.class);

        completed(methodName);
        return response;
    }

    public OtpValidateResponse validateOTPBeforeCreateUser(String email, String otp) {
        final String methodName = "validateOTPBeforeCreateUser";
        start(methodName);

        String uri = otpBaseUrl + OTP_VALIDATE_BEFORE_USER_CREATE;

        OtpValidateRequest payload = new OtpValidateRequest();
        payload.setEmail(email);
        payload.setOtp(otp);

        log.debug(methodName, payload);

        HTTPResponse httpResponse = HTTPClient.post(buildHTTPRequest(uri), JsonHelper.toJson(payload));

        log.debug(methodName, httpResponse.getBody());

        OtpValidateResponse response = JsonHelper.fromJson(httpResponse.getBody(), OtpValidateResponse.class);

        completed(methodName);
        return response;
    }

    private HTTPRequest buildHTTPRequest(String uri) {

        return new HTTPRequest.Builder(uri)
                .setContentType(HTTPContentType.APPLICATION_JSON)
                .addHeader(getProperty(Property.OTP_KEY_HEADER), getProperty(Property.OTP_KEY_VALUE))
                .build();
    }
}
