package com.app.golfapp.ui.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
/**
 * Created by gfairwani on 8/10/2018.
 */
public class GeoModel implements Serializable {
    @SerializedName("coordinates")
    @Expose
    String Cordinates;

    @SerializedName("radius")
    @Expose
    String radius;

    @SerializedName("name")
    @Expose
    String name;

    @SerializedName("hole")
    @Expose
    String hole;
    @SerializedName("camera")
    @Expose
    String camera;

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    String temp;

    public String getCamera() {
        return camera;
    }
    public void setCamera(String camera) {
        this.camera = camera;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @SerializedName("type")
    @Expose
    String type;

    public String getCordinates() {
        return Cordinates;
    }

    public void setCordinates(String cordinates) {
        Cordinates = cordinates;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHole() {
        return hole;
    }

    public void setHole(String hole) {
        this.hole = hole;
    }
}
