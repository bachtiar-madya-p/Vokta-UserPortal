package id.ic.vokta.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MicrocontrollerEvent {

    @JsonProperty("uid")
    private String uid;
    @JsonProperty("deviceId")
    private String deviceId;
    @JsonProperty("latitude")
    private String latitude;
    @JsonProperty("longitude")
    private String longitude;
    @JsonProperty("temperature")
    private String temperature;
    @JsonProperty("level")
    private String level;
    @JsonProperty("levelInLiters")
    private String levelInLiters;
    @JsonProperty("levelInPercents")
    private String levelInPercents;
    @JsonProperty("ph")
    private String ph;
    @JsonProperty("turbidity")
    private String turbidity;
    @JsonProperty("tds")
    private String tds;
    @JsonProperty("createDt")
    private String createDt;

    public MicrocontrollerEvent() {
        //Empty Constructor
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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevelInLiters() {
        return levelInLiters;
    }

    public void setLevelInLiters(String levelInLiters) {
        this.levelInLiters = levelInLiters;
    }

    public String getLevelInPercents() {
        return levelInPercents;
    }

    public void setLevelInPercents(String levelInPercents) {
        this.levelInPercents = levelInPercents;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public String getTurbidity() {
        return turbidity;
    }

    public void setTurbidity(String turbidity) {
        this.turbidity = turbidity;
    }

    public String getTds() {
        return tds;
    }

    public void setTds(String tds) {
        this.tds = tds;
    }

    public String getCreateDt() {
        return createDt;
    }

    public void setCreateDt(String createDt) {
        this.createDt = createDt;
    }
}
