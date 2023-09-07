package id.ic.vokta.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmailRequest {

    @JsonProperty("email")
    private String mail;

    public EmailRequest() {
        //Empty constructor
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
