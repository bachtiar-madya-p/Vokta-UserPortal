package id.ic.vokta.controller.model;

public class MicrocontrollerSchema {
    private String uid;
    private String userId;
    private String microcontrollerId;
    private String createDt;

    public MicrocontrollerSchema() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMicrocontrollerId() {
        return microcontrollerId;
    }

    public void setMicrocontrollerId(String microcontrollerId) {
        this.microcontrollerId = microcontrollerId;
    }

    public String getCreateDt() {
        return createDt;
    }

    public void setCreateDt(String createDt) {
        this.createDt = createDt;
    }
}
