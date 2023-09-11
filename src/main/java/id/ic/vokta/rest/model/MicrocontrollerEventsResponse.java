package id.ic.vokta.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import id.ic.vokta.model.MicrocontrollerEvent;

import javax.ws.rs.core.Response;

public class MicrocontrollerEventsResponse extends ServiceResponse{

    @JsonProperty("uid")
    private String uid;
    @JsonProperty("deviceId")
    private String deviceId;
    @JsonProperty("deviceName")
    private String deviceName;

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

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}
