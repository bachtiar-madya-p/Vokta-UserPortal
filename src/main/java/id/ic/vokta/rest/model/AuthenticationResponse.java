package id.ic.vokta.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.ws.rs.core.Response;

public class AuthenticationResponse extends ServiceResponse {

    @JsonProperty("access-token")
    private String accessToken;
    @JsonProperty("scope")
    private String scope;
    @JsonProperty("expiry-date")
    private String expiryDate;

    public AuthenticationResponse() {
        super(Response.Status.OK);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
