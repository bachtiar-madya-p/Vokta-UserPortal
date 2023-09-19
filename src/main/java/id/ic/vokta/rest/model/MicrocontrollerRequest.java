package id.ic.vokta.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import id.ic.vokta.model.Microcontroller;

import javax.ws.rs.core.Response;
import java.util.List;

public class MicrocontrollerRequest {

    @JsonProperty("uid")
    private String uid;

    @JsonProperty("sensorId")
    private String sensorId;

    @JsonProperty("sensorName")
    private String sensorName;

    @JsonProperty("tankBrand")
    private String tankBrand;

    @JsonProperty("tankType")
    private String tankType;

    @JsonProperty("capacity")
    private String capacity;

    @JsonProperty("diameter")
    private String diameter;

    @JsonProperty("height")
    private String height;

    public MicrocontrollerRequest() {
        //Empty constructor
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

    public String getTankBrand() {
        return tankBrand;
    }

    public void setTankBrand(String tankBrand) {
        this.tankBrand = tankBrand;
    }

    public String getTankType() {
        return tankType;
    }

    public void setTankType(String tankType) {
        this.tankType = tankType;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getDiameter() {
        return diameter;
    }

    public void setDiameter(String diameter) {
        this.diameter = diameter;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}
