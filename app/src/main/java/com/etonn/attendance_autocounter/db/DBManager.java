package com.etonn.attendance_autocounter.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Cheng on 2014/11/6.
 */
public class DBManager extends DBHelper {

    SQLiteDatabase db;

    public DBManager(Context context) {
        super(context);
        db = this.getReadableDatabase();
    }

    public ArrayList getClassList() {
        String sql = "select * from classes order by class_id DESC";
        Log.i("Log:DBManager", sql);
        Cursor cursor = db.rawQuery(sql, null);
        ArrayList al = new ArrayList();
        while (cursor.moveToNext()) {
            //id = cursor.getString(cursor.getColumnIndex("id"));
            al.add(
                    cursor.getString(cursor.getColumnIndex("class_name")) +
                            " (" + cursor.getString(cursor.getColumnIndex("headcount")) + ")"
            );
        }
        return al;
    }

    public void putClass(ContentValues contentValues) {
        Log.i("Log:DBManager", "insert data");
        try {
            db.insert("classes", null, contentValues);
        } catch (android.database.SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * save data into database
     *
     * @param students
     */
    public boolean putStudents(Map<Integer, Student> students) {
        Iterator iter = students.keySet().iterator();
        while (iter.hasNext()) {
            Object key = iter.next();
            Student value = students.get(key);
            Log.i("Log:DBManager", "insert data");
            ContentValues mContentValues = new ContentValues();
            mContentValues.put("mac", value.getMac());
            mContentValues.put("name", value.getName());
            try {
                db.insert("students", null, mContentValues);
            } catch (android.database.SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    //DB students operations--------------------------------------------------
    public void addStudents(List<Student> students) {
        db.beginTransaction();
        try {
            for (Student student : students) {
                db.execSQL("INSERT INTO students VALUES(null, ?, ?)",
                        new Object[]{student.getName(), student.getMac()});
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public void deleteStudents(List<Student> students) {
        for (Student student : students) {
            db.delete("students", "name = ?", new String[]{student.getName()});
        }
    }

    public void deleteOneStudent(Student student) {
        db.delete("students", "name = ?", new String[]{student.getName()});
    }

    //query and get all students
    public Cursor getAllStudentsCursor() {
        Cursor c = db.rawQuery("SELECT * FROM students", null);
        return c;
    }

    public ArrayList<Student> getAllStudentsList() {
        ArrayList<Student> arrayList = new ArrayList<Student>();
        Cursor cursor = getAllStudentsCursor();
        while (cursor.moveToNext()) {
            Student student = new Student();
            student.setName(cursor.getString(cursor.getColumnIndex("name")));
            student.setMac(cursor.getString(cursor.getColumnIndex("mac")));
            arrayList.add(student);
        }
        cursor.close();
        return arrayList;
    }

    public int getStudentId(String mac) {
        Cursor c = db.rawQuery("SELECT * FROM students WHERE mac = ?", new String[]{mac});
        c.moveToNext();
        return c.getInt(c.getColumnIndex("student_id"));
    }

    //DB attendance operations------------------------------------------------
    public void addAttendees(List<Student> students, int courseId) {
        db.beginTransaction();
        try {
            for (Student student : students) {
//                Log.d("ffff", student.getMac());
                int studentId = getStudentId(student.getMac());

                db.execSQL("INSERT INTO attendances VALUES(null, ?, ?, ?)",
                        new Object[]{studentId, courseId, new Date()});
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    //DB courses operations----------------------------------------------------
    public int getCourseId(String courseName) {
        Cursor c = db.rawQuery("SELECT * FROM classes WHERE class_name = ?", new String[]{courseName});
        return c.getInt(c.getColumnIndex("courses_id"));
    }

    public ArrayList<Course> getCoursesList() {
        String sql = "select * from classes order by class_id DESC";
        Log.i("Log:DBManager", sql);
        Cursor cursor = db.rawQuery(sql, null);
        ArrayList al = new ArrayList();
        while (cursor.moveToNext()) {
            Course mCourse = new Course(cursor.getInt(cursor.getColumnIndex("class_id")), cursor.getString(cursor.getColumnIndex("class_name")), cursor.getInt(cursor.getColumnIndex("headcount")));
            al.add(mCourse);
        }
        cursor.close();
        return al;
    }

    public void putCourse(ContentValues contentValues) {
        Log.i("Log:DBManager", "insert data");
        try {
            db.insert("courses", null, contentValues);
        } catch (android.database.SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCourseByName(String name, int headcount) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("headcount", headcount);
        db.update("classes", contentValues, "class_name=?", new String[]{name});
    }

    public Course queryCourseByName(String name) {
        Course mCourse = null;
        Cursor cursor = db.rawQuery("SELECT * FROM classes WHERE class_name = ?", new String[]{name});
        while (cursor.moveToNext()) {
            mCourse = new Course(cursor.getInt(cursor.getColumnIndex("classes_id")), cursor.getString(cursor.getColumnIndex("class_name")), cursor.getInt(cursor.getColumnIndex("headcount")));
        }
        cursor.close();
        return mCourse;
    }
    //DB course operations end----------------------------------------------------

}
