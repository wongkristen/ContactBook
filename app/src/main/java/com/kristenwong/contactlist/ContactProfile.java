package com.kristenwong.contactlist;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by kristenwong on 10/16/17.
 */

public class ContactProfile implements Serializable {
    private String name;
    private String phoneNumber;
    private ArrayList<ContactProfile> relationships;
    private boolean isChecked;

    public void setRelationships(ArrayList<ContactProfile> relationships) {
        this.relationships = relationships;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ArrayList<ContactProfile> getRelationships() {
        return relationships;
    }


}
