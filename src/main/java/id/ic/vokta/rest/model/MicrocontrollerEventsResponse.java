package id.ic.vokta.rest.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import id.ic.vokta.model.MicrocontrollerEvent;

import javax.ws.rs.core.Response;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MicrocontrollerEventsResponse extends ServiceResponse{

    @JsonProperty("uid")
    private String uid;
    @JsonProperty("sensorId")
    private String sensorId;
    @JsonProperty("sensorName")
    private String sensorName;
    @JsonProperty("capacity")
    private String capacity;

    @JsonProperty("events")
    private MicrocontrollerEvent event;

    public MicrocontrollerEventsResponse() {
        super(Response.Status.OK);
    }

    public MicrocontrollerEvent getEvent() {
        return event;
    }

    public void setEvent(MicrocontrollerEvent event) {
        this.event = event;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }
}
