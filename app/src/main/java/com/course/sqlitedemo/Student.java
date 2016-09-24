package com.course.sqlitedemo;

/**
 * Created by Administrator on 2016/9/19.
 */
public class Student {
    private int mId;
    private String mName;
    private int mAge;

    public Student(String mName, int mAge) {
        this.mName = mName;
        this.mAge = mAge;
    }

    public Student(int mId, String mName, int mAge) {
        this.mId = mId;
        this.mName = mName;
        this.mAge = mAge;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmAge() {
        return mAge;
    }

    public void setmAge(int mAge) {
        this.mAge = mAge;
    }
}
