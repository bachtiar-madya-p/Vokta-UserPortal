package id.ic.vokta.rest.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import id.ic.vokta.model.Microcontroller;

import javax.ws.rs.core.Response;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MicrocontrollerDetailResponse extends ServiceResponse{

    @JsonProperty("microcontroller")
    private Microcontroller microcontroller;

    public MicrocontrollerDetailResponse() {
        super(Response.Status.OK);
    }

    public Microcontroller getMicrocontroller() {
        return microcontroller;
    }

    public void setMicrocontroller(Microcontroller microcontroller) {
        this.microcontroller = microcontroller;
    }
}
