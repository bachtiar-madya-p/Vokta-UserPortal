package id.ic.vokta.util.property;

public class Constant {

    private Constant() {}

    public static final String COMMON_PROPERTY_FILENAME = "application.common.properties";

    public static final String PROPERTY_FILENAME = "application.properties";

    public static final String SESSION_TRACKING_ID = "vp_tracking_id";
    public static final String SESSION_USER_DETAILS = "vp_user_detail";
    public static final String SESSION_EMAIL="vp_email";
    public static final String SESSION_EMAIL_VERIFIED = "vp_email_verified";
    public static final String SESSION_OTP_SEND_INTERVAL = "vp_otp_send_interval";

    //Error constants
    public static final String ERROR_INVALID_PAYLOAD = "Invalid payload";
    public static final String ERROR_USER_NOT_FOUND = "User not found";
    public static final String ERROR_EXPECTED_ONE_ELEMENT = "Expected one element, but found none";
}
