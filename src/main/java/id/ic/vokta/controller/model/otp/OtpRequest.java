package id.ic.vokta.controller.model.otp;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OtpRequest {

    @JsonProperty("email")
    private String email;
    @JsonProperty("firstname")
    private String firstname;

    public OtpRequest() {
        //Empty constructor
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
}
