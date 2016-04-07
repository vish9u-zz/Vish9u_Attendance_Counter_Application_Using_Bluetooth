package com.etonn.attendance_autocounter;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.etonn.attendance_autocounter.db.DBManager;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    DBManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadClass();
    }

    @Override
    public void onResume() {
        super.onResume();

        loadClass();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    /**
     * start ClassActivity
     *
     * @param view
     */
    public void startClassActivity(View view) {
        Intent intent = new Intent(this, ClassActivity.class);
        startActivity(intent);
    }

    /**
     * start ScanningActivity
     *
     * @param view
     */
    public void startScanningActivity(View view) {
        // todo attach the class id from spinner
        Intent intent = new Intent(this, ScanningActivity.class);
        startActivity(intent);
    }

    public void startEmailAbsenteeActivity(View view) {
        Intent intent = new Intent(this, EmailAbsenteeActivity.class);
        startActivity(intent);
    }

    public void startRoportActivity(View view) {
        Intent intent = new Intent(this, ReportActivity.class);
        startActivity(intent);
    }

    public void loadClass() {
        // init database
        db = new DBManager(this);
        ArrayList arrayListClasses = db.getClassList();
        // add data into spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinnerClasses);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item,
                arrayListClasses
        );
        // set up style of dropdown list
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // add adapter into spinner
        spinner.setAdapter(adapter);
        // add Listener
        //spinner.setOnItemSelectedListener();
        // set up default value
        spinner.setVisibility(View.VISIBLE);
    }
}
