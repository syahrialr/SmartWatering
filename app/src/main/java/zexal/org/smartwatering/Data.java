package zexal.org.smartwatering;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by syahr on 14/04/2017.
 */

public class Data {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("temp")
    @Expose
    private String temp;
    @SerializedName("humi")
    @Expose
    private String humi;
    @SerializedName("kondisisoil")
    @Expose
    private String kondisisoil;
    @SerializedName("sensorsoil")
    @Expose
    private String sensorsoil;
    @SerializedName("flowrate")
    @Expose
    private String flowrate;
    @SerializedName("currentliquid")
    @Expose
    private String currentliquid;
    @SerializedName("solenoid")
    @Expose
    private String solenoid;

    public Data(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getHumi() {
        return humi;
    }

    public void setHumi(String humi) {
        this.humi = humi;
    }

    public String getKondisisoil() {
        return kondisisoil;
    }

    public void setKondisisoil(String kondisisoil) {
        this.kondisisoil = kondisisoil;
    }

    public String getSensorsoil() {
        return sensorsoil;
    }

    public void setSensorsoil(String sensorsoil) {
        this.sensorsoil = sensorsoil;
    }

    public String getFlowrate() {
        return flowrate;
    }

    public void setFlowrate(String flowrate) {
        this.flowrate = flowrate;
    }

    public String getCurrentliquid() {
        return currentliquid;
    }

    public void setCurrentliquid(String currentliquid) {
        this.currentliquid = currentliquid;
    }

    public String getSolenoid() {
        return solenoid;
    }

    public void setSolenoid(String solenoid) {
        this.solenoid = solenoid;
    }
}
