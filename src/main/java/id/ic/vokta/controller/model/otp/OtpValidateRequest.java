package id.ic.vokta.controller.model.otp;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OtpValidateRequest {

    @JsonProperty("email")
    private String email;

    @JsonProperty("otp")
    private String otp;

    public OtpValidateRequest() {
        //Empty constructor
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
}

