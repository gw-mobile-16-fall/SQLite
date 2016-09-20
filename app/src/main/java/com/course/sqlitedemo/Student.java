package com.course.sqlitedemo;

/**
 * Created by Administrator on 2016/9/19.
 */
public class Student {
    private String mName;
    private int mAge;

    public Student(String mName, int mAge) {
        this.mName = mName;
        this.mAge = mAge;
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
