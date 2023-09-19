package id.ic.vokta.controller.model;

public class MicrocontrollerSchema {
    private String uid;
    private String userUid;
    private String sensorUid;
    private String createDt;

    public MicrocontrollerSchema() {
        //Empty Constructor
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getSensorUid() {
        return sensorUid;
    }

    public void setSensorUid(String sensorUid) {
        this.sensorUid = sensorUid;
    }

    public String getCreateDt() {
        return createDt;
    }

    public void setCreateDt(String createDt) {
        this.createDt = createDt;
    }
}
