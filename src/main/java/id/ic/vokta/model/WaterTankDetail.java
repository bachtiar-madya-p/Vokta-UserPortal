package id.ic.vokta.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class WaterTankDetail {

    @JsonProperty("uid")
    private String uid;
    @JsonProperty("brandUid")
    private String brandUid;
    @JsonProperty("name")
    private String name;
    @JsonProperty("capacity")
    private String capacity;
    @JsonProperty("diameter")
    private String diameter;
    @JsonProperty("height")
    private String height;
    @JsonProperty("createDt")
    private String createDt;
    @JsonProperty("modifyDt")
    private String modifyDt;

    public WaterTankDetail() {
        //Empty constructor
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getBrandUid() {
        return brandUid;
    }

    public void setBrandUid(String brandUid) {
        this.brandUid = brandUid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getCreateDt() {
        return createDt;
    }

    public void setCreateDt(String createDt) {
        this.createDt = createDt;
    }

    public String getModifyDt() {
        return modifyDt;
    }

    public void setModifyDt(String modifyDt) {
        this.modifyDt = modifyDt;
    }
}
