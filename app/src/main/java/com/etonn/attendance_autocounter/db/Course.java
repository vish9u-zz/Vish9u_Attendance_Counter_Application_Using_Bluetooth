package com.etonn.attendance_autocounter.db;

/**
 * Created by Zeshi on 11/23/14.
 */
public class Course {
    private int courses_id;
    private String course_name;
    private int headcount;

    public Course(String course_name, int headcount) {
        this.course_name = course_name;
        this.headcount = headcount;
    }

    public Course(int courses_id, String course_name, int headcount) {
        this.courses_id = courses_id;
        this.course_name = course_name;
        this.headcount = headcount;
    }

    public int getCoursesId() {
        return courses_id;
    }

    public int getHeadcount() {
        return headcount;
    }

    public void setHeadcount(int headcount) {
        this.headcount = headcount;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    @Override
    public String toString() {
        return this.course_name + " - (" + this.headcount + ")";
    }
}
