package id.ic.vokta.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterVerifyRequest {

    @JsonProperty("email")
    private String email;

    @JsonProperty("otp")
    private String otp;

    public RegisterVerifyRequest() {
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
