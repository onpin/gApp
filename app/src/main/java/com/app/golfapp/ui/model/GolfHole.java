package com.app.golfapp.ui.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by nnarayan on 7/21/2018.
 */

public class GolfHole implements Parcelable {
    public static GolfHole CREATOR=new GolfHole();

    @SerializedName("hcod1")
    @Expose
    String Hcod1;
    @SerializedName("hcod2")
    @Expose
            String Hcod2;
    @SerializedName("hcod3")
    @Expose
            String Hcod3;

    @SerializedName("hcod4")
    @Expose
            String Hcod4;
    @SerializedName("hnumber")
    @Expose
    String Hnumber;
    @SerializedName("hpar")
    @Expose
    String Hpar;
    @SerializedName("hgreenback")
    @Expose
    String HgreenBack;
    @SerializedName("hgreenFront")
    @Expose
    String HgreenFront;
    @SerializedName("hgreencenter")
    @Expose
    String HgreenCenter;
    @SerializedName("GolfImage")
    Bitmap images;


    @SerializedName("coordinates")
    @Expose
    String Cordinates;

    @SerializedName("radius")
    @Expose
    String radius;

    @SerializedName("name")
    @Expose
    String name;

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    String temp;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @SerializedName("type")
    @Expose
    String type;
    @SerializedName("hole")
    @Expose
    String hole;




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




    public String getId() {
        return id;
    }


    public Bitmap getImages() {
        return images;
    }

    public void setImages(Bitmap images) {
        this.images = images;
    }

    public void setId(String id) {
        this.id = id;
    }

    @SerializedName("id")
    @Expose
    String id;

//    public  GolfHole(Parcel in) {
//        Hcod1 = in.readString();
//        Hcod2 = in.readString();
//        Hcod3 = in.readString();
//        Hcod4 = in.readString();
//        Hnumber = in.readString();
//        Hpar = in.readString();
//        HgreenBack = in.readString();
//        HgreenFront = in.readString();
//        HgreenCenter = in.readString();
//    }

//    public static final Creator<GolfHole> CREATOR = new Creator<GolfHole>() {
//        @Override
//        public GolfHole createFromParcel(Parcel in) {
//            return new GolfHole(in);
//        }
//
//        @Override
//        public GolfHole[] newArray(int size) {
//            return new GolfHole[size];
//        }
//    };

    public String getHcod1() {
        return Hcod1;
    }

    public void setHcod1(String hcod1) {
        Hcod1 = hcod1;
    }

    public String getHcod2() {
        return Hcod2;
    }

    public void setHcod2(String hcod2) {
        Hcod2 = hcod2;
    }

    public String getHcod3() {
        return Hcod3;
    }

    public void setHcod3(String hcod3) {
        Hcod3 = hcod3;
    }

    public String getHcod4() {
        return Hcod4;
    }

    public void setHcod4(String hcod4) {
        Hcod4 = hcod4;
    }

    public String getHnumber() {
        return Hnumber;
    }

    public void setHnumber(String hnumber) {
        Hnumber = hnumber;
    }

    public String getHpar() {
        return Hpar;
    }

    public void setHpar(String hpar) {
        Hpar = hpar;
    }

    public String getHgreenBack() {
        return HgreenBack;
    }

    public void setHgreenBack(String hgreenBack) {
        HgreenBack = hgreenBack;
    }

    public String getHgreenFront() {
        return HgreenFront;
    }

    public void setHgreenFront(String hgreenFront) {
        HgreenFront = hgreenFront;
    }

    public String getHgreenCenter() {
        return HgreenCenter;
    }

    public void setHgreenCenter(String hgreenCenter) {
        HgreenCenter = hgreenCenter;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }


//    @Override
//    public int describeContents() {
//        return 0;
//    }

//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeValue(Hcod1);
//        dest.writeValue(Hcod2);
//        dest.writeValue(Hcod3);
//        dest.writeValue(Hcod4);
//        dest.writeValue(Hnumber);
//        dest.writeValue(Hpar);
//        dest.writeValue(HgreenBack);
//        dest.writeValue(HgreenCenter);
//        dest.writeValue(HgreenFront);
//
//
//    }
}
