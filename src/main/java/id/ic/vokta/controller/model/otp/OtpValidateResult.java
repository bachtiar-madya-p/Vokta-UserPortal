package id.ic.vokta.controller.model.otp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OtpValidateResult {

    @JsonProperty("validate")
    private boolean validate;
    @JsonProperty("email")
    private String email;
    @JsonProperty("otp")
    private String otp;
    @JsonProperty("message")
    private String message;

    public OtpValidateResult() {
        // Empty constructor
    }

    public boolean isValidate() {
        return validate;
    }

    public void setValidate(boolean validate) {
        this.validate = validate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
