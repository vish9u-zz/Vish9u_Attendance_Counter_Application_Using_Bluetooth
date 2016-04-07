package com.etonn.attendance_autocounter;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.etonn.attendance_autocounter.db.DBManager;
import com.etonn.attendance_autocounter.db.Student;

import java.util.ArrayList;

/**
 * Created by vish9u on 06/04/2016.
 */
public class EmailAbsenteeActivity extends ActionBarActivity {

    DBManager db;
    ArrayList<Student> absenteesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_absentee);

        db = new DBManager(this);
        //todo assuming absentees are all students because didn't implement necessary activity
        absenteesList = db.getAllStudentsList();

        Intent intent = new Intent(Intent.ACTION_SEND);
        String[] tos = new String[absenteesList.size()];

        int i = 0;
        for(Student absentee : absenteesList){
            tos[i] = absentee.getName().replace(" ", "_")+"@gmail.com";
            i++;
        }

        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, tos);
        intent.putExtra(Intent.EXTRA_SUBJECT,"You were absent for Database security course");
        intent.putExtra(Intent.EXTRA_TEXT,
                "Dear student," +
                        "\n\nYou were absent for the last android class, be careful as Attendance carries 20% of your grade." +
                        "\n\nBut if you already informed me before, and I approved it, please ignore this email." +
                        "\n\nRegards,\nYour Professor.");
        startActivity(Intent.createChooser(intent,"Your Client"));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_email_absentee, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
