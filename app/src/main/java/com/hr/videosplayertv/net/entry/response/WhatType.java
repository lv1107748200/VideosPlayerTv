package com.hr.videosplayertv.net.entry.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * lv
 */
public class WhatType implements Parcelable {

    /**
     * PID : 0,1,3
     * ClassName : 经典
     * Taxis :
     * IsIndex : true
     * Path : 0,1,3,31
     * AltLink :
     * ID :
     */

    private String PID;
    private String ClassName;
    private String Taxis;
    private boolean IsIndex;
    private String Path;
    private String AltLink;
    private String ID;


    public String getPID() {
        return PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String ClassName) {
        this.ClassName = ClassName;
    }

    public String getTaxis() {
        return Taxis;
    }

    public void setTaxis(String Taxis) {
        this.Taxis = Taxis;
    }

    public boolean isIsIndex() {
        return IsIndex;
    }

    public void setIsIndex(boolean IsIndex) {
        this.IsIndex = IsIndex;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String Path) {
        this.Path = Path;
    }

    public String getAltLink() {
        return AltLink;
    }

    public void setAltLink(String AltLink) {
        this.AltLink = AltLink;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.PID);
        dest.writeString(this.ClassName);
        dest.writeString(this.Taxis);
        dest.writeByte(this.IsIndex ? (byte) 1 : (byte) 0);
        dest.writeString(this.Path);
        dest.writeString(this.AltLink);
        dest.writeString(this.ID);
    }

    public WhatType() {
    }

    protected WhatType(Parcel in) {
        this.PID = in.readString();
        this.ClassName = in.readString();
        this.Taxis = in.readString();
        this.IsIndex = in.readByte() != 0;
        this.Path = in.readString();
        this.AltLink = in.readString();
        this.ID = in.readString();
    }

    public static final Creator<WhatType> CREATOR = new Creator<WhatType>() {
        @Override
        public WhatType createFromParcel(Parcel source) {
            return new WhatType(source);
        }

        @Override
        public WhatType[] newArray(int size) {
            return new WhatType[size];
        }
    };
}
