package com.etonn.attendance_autocounter.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

/**
 * Created by Cheng on 2014/11/6.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("Log:DBHelper", "Create Database");
        String sql1 = "CREATE TABLE IF NOT EXISTS [attendance] (" +
                "[attendance_id] INTEGER PRIMARY KEY AUTOINCREMENT," +
                "[student_id] INTEGER," +
                "[class_id] INTEGER," +
                "[date] DATE)";

        String sql2 = "CREATE TABLE IF NOT EXISTS [classes] (" +
                "[class_id] INTEGER PRIMARY KEY AUTOINCREMENT," +
                "[class_name] VARCHAR(64)," +
                "[headcount] INT(4))";

        String sql3 = "CREATE TABLE IF NOT EXISTS [students] (" +
                "[student_id] INTEGER PRIMARY KEY AUTOINCREMENT," +
                "[name] VARCHAR(32)," +
                "[mac] CHAR(24))";

        db.execSQL(sql1);
        db.execSQL(sql2);
        db.execSQL(sql3);
    }

    //如果DATABASE_VERSION值被改为2,系统发现现有数据库版本不同,即会调用onUpgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("Log:DBHelper", "update a Database");
        db.execSQL("ALTER TABLE students ADD COLUMN other STRING");
    }
}