package id.ic.vokta.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Microcontroller {

    @JsonProperty("uid")
    private String uid;
    @JsonProperty("deviceId")
    private String deviceId;
    @JsonProperty("deviceName")
    private String deviceName;
    @JsonProperty("brand")
    private String brand;
    @JsonProperty("capacity")
    private String capacity;
    @JsonProperty("capDiameter")
    private String capDiameter;
    @JsonProperty("diameter")
    private String diameter;
    @JsonProperty("height")
    private String height;
    @JsonProperty("modifyDt")
    private String modifyDt;
    @JsonProperty("createDt")
    private String createDt;

    public Microcontroller() {
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getCapDiameter() {
        return capDiameter;
    }

    public void setCapDiameter(String capDiameter) {
        this.capDiameter = capDiameter;
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

    public String getModifyDt() {
        return modifyDt;
    }

    public void setModifyDt(String modifyDt) {
        this.modifyDt = modifyDt;
    }

    public String getCreateDt() {
        return createDt;
    }

    public void setCreateDt(String createDt) {
        this.createDt = createDt;
    }
}
