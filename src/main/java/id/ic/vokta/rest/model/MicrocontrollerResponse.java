package id.ic.vokta.rest.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import id.ic.vokta.model.Microcontroller;

import javax.ws.rs.core.Response;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MicrocontrollerResponse extends ServiceResponse {

    @JsonProperty("items")
    private List<Microcontroller> microcontrollers;

    public MicrocontrollerResponse() {
        super(Response.Status.OK);
    }

    public List<Microcontroller> getMicrocontrollers() {
        return microcontrollers;
    }

    public void setMicrocontrollers(List<Microcontroller> microcontrollers) {
        this.microcontrollers = microcontrollers;
    }
}
