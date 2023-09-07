package id.ic.vokta.controller.model.otp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OtpValidateResponse extends BaseOtpResponse {

    @JsonProperty("result")
    private OtpValidateResult result;

    public OtpValidateResponse() {
        //Empty constructor
    }

    public OtpValidateResult getResult() {
        return result;
    }

    public void setResult(OtpValidateResult result) {
        this.result = result;
    }
}
