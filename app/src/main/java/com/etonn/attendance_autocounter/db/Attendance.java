package com.etonn.attendance_autocounter.db;

import java.util.Date;

/**
 * Created by Zeshi on 11/23/14.
 */
public class Attendance {
    private int attendance_id;
    private int student_id;
    private int course_id;
    private Date date;

    public Attendance(int student_id, int course_id, Date date) {
        this.student_id = student_id;
        this.course_id = course_id;
        this.date = date;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
