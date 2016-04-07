package com.etonn.attendance_autocounter;

import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.etonn.attendance_autocounter.bluetooth.BluetoothHelper;
import com.etonn.attendance_autocounter.db.DBManager;
import com.etonn.attendance_autocounter.db.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ScanningActivity extends ActionBarActivity {

    DBManager db;
    public static BluetoothHelper bluetooth = null;
    public static List<HashMap<String, Object>> studentsList = new ArrayList<HashMap<String, Object>>();
    public static Map<Integer, Student> selectedStudents = new HashMap<Integer, Student>();
    SimpleAdapter mSimpleAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanning);

        // init database
        db = new DBManager(ScanningActivity.this);
        // detect Bluetooth
        bluetooth = new BluetoothHelper();
        if (!bluetooth.detectBluetooth()) {
            // if user click button, close current activity
            new AlertDialog.Builder(this)
                    .setTitle("Message")
                    .setMessage("Your device does not support Bluetooth.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            ScanningActivity.this.finish();
                        }
                    })
                    .show();
        }
        // open Bluetooth
        if (!bluetooth.isBluetoothOpening()) {
            new AlertDialog.Builder(this)
                    .setTitle("Warning!")
                    .setMessage("Please turn on your Bluetooth.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            // open Bluetooth
                            bluetooth.openBluetooth();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            // return MainActivity
                            ScanningActivity.this.finish();
                        }
                    })
                    .show();
        }
        // Scan Student
        scanStudent();
        // register receiver for scanning
        IntentFilter mFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, mFilter);
        // register receiver for finish scanning
        mFilter = new IntentFilter(bluetooth.mBluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mReceiver, mFilter);
        // Scan Student Listener
        Button scanStudentButton = (Button) findViewById(R.id.buttonScan);
        scanStudentButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                scanStudent();
            }
        });
        // Save Student Listener
        Button saveStudentsButton = (Button) findViewById(R.id.buttonSaveStudents);
        saveStudentsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.i("Log", "Save Students to DB");
                if (!selectedStudents.isEmpty()) {
                    if (db.putStudents(selectedStudents)) {
                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scanning, menu);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // unregisterReceiver
        unregisterReceiver(mReceiver);
    }

    /**
     * Scan Student
     */
    public void scanStudent() {
        // scan Bluetooth device
        Toast.makeText(getApplicationContext(), "Scanning Students...", Toast.LENGTH_SHORT).show();
        bluetooth.startDiscovery();
    }

    /**
     * BroadcastReceiver to receive bluetooth devices
     */
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // scan device which has not bonded, build studentsList for SimpleAdapter
                Log.i("Log", device.getName());
                Log.i("Log", device.getAddress());
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("name", device.getName());
                map.put("address", device.getAddress());
                studentsList.add(map);
                // update listview
                ListView lv = (ListView) findViewById(R.id.listViewStudents);
                // bind listview with ArrayAdapter
                mSimpleAdapter = new SimpleAdapter(ScanningActivity.this,
                        studentsList,
                        R.layout.adapter_student_list,
                        new String[]{"name", "address"},
                        new int[]{R.id.checkedTextViewStudentName, R.id.textViewAddress}
                );
                lv.setAdapter(mSimpleAdapter);
                //lv.setAdapter(new ArrayAdapter<String>(ScanningActivity.this,
                //        android.R.layout.simple_list_item_multiple_choice,
                //        studentsList));
                lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

                // add Adapter listener
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Map<String, String> map = (Map<String, String>) ScanningActivity.this.mSimpleAdapter
                                .getItem(i);
                        Student student = new Student();
                        student.setName(map.get("name"));
                        student.setMac(map.get("address"));
                        Log.i("Log", i + student.getName() );
                        // save or remove mac address
                        if (selectedStudents.containsKey(i)) {
                            selectedStudents.remove(i);
                        } else {
                            selectedStudents.put(i, student);
                        }
                    }
                });
            }

        }
    };

}
