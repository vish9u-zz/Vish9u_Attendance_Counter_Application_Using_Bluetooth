package com.etonn.attendance_autocounter;

import android.content.ContentValues;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.etonn.attendance_autocounter.db.DBManager;

import java.util.ArrayList;


public class ClassActivity extends ActionBarActivity {

    ListView listViewClass; // for Class name listview
    DBManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);

        // init database
        db = new DBManager(ClassActivity.this);
        this.showClassList();

        // add listener
        listViewClass.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CourseModifyDialog mDialog = new CourseModifyDialog();
                mDialog.show(getFragmentManager(), "dialogCourse");
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_class, menu);
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
     * show Classes list
     */
    private void showClassList() {
        // get new data from db
        ArrayList al = db.getClassList();
        // update list
        listViewClass = (ListView) findViewById(R.id.listViewClasses);
        // bind listview with ArrayAdapter
        listViewClass.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al));
    }

    /**
     * add class into db
     *
     * @param view
     */
    public void addClass(View view) {
        // get Class name
        EditText editTextClassName = (EditText) findViewById(R.id.editTextClassName);
        String className = editTextClassName.getText().toString();
        EditText editTextHeadcount = (EditText) findViewById(R.id.editTextHeadcount);
        String headcount = editTextHeadcount.getText().toString();
        if (className.isEmpty() || headcount.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please fill in Class name and Headcount.", Toast.LENGTH_SHORT).show();
        } else {
            // save to DB
            ContentValues contentValues = new ContentValues();
            contentValues.put("class_name", className);
            contentValues.put("headcount", Integer.parseInt(headcount));
            db.putClass(contentValues);

            // show Classes list
            this.showClassList();

            // empty EditText
            editTextClassName.setText("");
            editTextHeadcount.setText("");
        }

    }

}