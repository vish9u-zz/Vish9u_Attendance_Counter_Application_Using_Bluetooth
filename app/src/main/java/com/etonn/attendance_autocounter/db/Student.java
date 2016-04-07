package com.etonn.attendance_autocounter.db;

/**
 * Created by Zeshi on 11/23/14.
 */
public class Student {
    private int student_id;
    private String name;
    private String mac;

    //because sometimes we want to build an empty constructor first, and then bind data
    public Student() {
    }

    public Student(String name, String mac) {
        this.name = name;
        this.mac = mac;
    }

    public int getStudent_id() {
        return student_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    @Override
    public String toString() {
        return this.name + " -> " + this.mac;
    }
}
