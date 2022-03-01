package com.unittest.firebasereview;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String activityArea;
    private String department;
    private Long id;
    private String interesting;
    private String name;
    private String university;
    public User(){

    }

    public User(String activityArea, String department, Long id, String interesting, String name, String university) {
        this.activityArea = activityArea;
        this.department = department;
        this.id = id;
        this.interesting = interesting;
        this.name = name;
        this.university = university;
    }

    protected User(Parcel in) {
        activityArea = in.readString();
        department = in.readString();
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        interesting = in.readString();
        name = in.readString();
        university = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getActivityArea() {
        return activityArea;
    }

    public void setActivityArea(String activityArea) {
        this.activityArea = activityArea;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInteresting() {
        return interesting;
    }

    public void setInteresting(String interesting) {
        this.interesting = interesting;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(activityArea);
        parcel.writeString(department);
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(id);
        }
        parcel.writeString(interesting);
        parcel.writeString(name);
        parcel.writeString(university);
    }
}
