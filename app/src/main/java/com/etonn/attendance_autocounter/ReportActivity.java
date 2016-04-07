package com.etonn.attendance_autocounter;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.etonn.attendance_autocounter.db.DBManager;
import com.etonn.attendance_autocounter.db.Student;

import java.util.ArrayList;


public class ReportActivity extends ActionBarActivity {

    ListView listView;
    DBManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        db = new DBManager(this);
        showCoursesList();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_report, menu);
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

    private void showCoursesList() {
        ArrayList<Student> arrayList = db.getAllStudentsList();
        listView = (ListView) findViewById(R.id.listViewStudents);
        listView.setAdapter(new ArrayAdapter<Student>(this,android.R.layout.simple_list_item_1, arrayList));
    }

}
