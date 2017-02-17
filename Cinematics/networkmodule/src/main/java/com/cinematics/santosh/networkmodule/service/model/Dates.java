package com.cinematics.santosh.networkmodule.service.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by santosh on 2/14/17.
 */

public class Dates implements Parcelable {

    private String maximum;
    private String minimum;

    public String getMaximum() {
        return maximum;
    }

    public void setMaximum(String maximum) {
        this.maximum = maximum;
    }

    public String getMinimum() {
        return minimum;
    }

    public void setMinimum(String minimum) {
        this.minimum = minimum;
    }

    protected Dates(Parcel in) {
        this.maximum = in.readString();
        this.minimum = in.readString();
    }

    public static final Creator<Dates> CREATOR = new Creator<Dates>() {
        @Override
        public Dates createFromParcel(Parcel in) {
            return new Dates(in);
        }

        @Override
        public Dates[] newArray(int size) {
            return new Dates[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.maximum);
        dest.writeString(this.minimum);
    }

}
