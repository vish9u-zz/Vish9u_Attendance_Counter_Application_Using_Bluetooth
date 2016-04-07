package com.etonn.attendance_autocounter.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Cheng on 2014/11/7.
 */
public class BluetoothHelper {

    final public BluetoothAdapter mBluetoothAdapter;

    public BluetoothHelper() {

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    /**
     * detect Bluetooth
     *
     * @return boolean
     */
    public boolean detectBluetooth() {
        // detect whether device support Bluetooth
        if (mBluetoothAdapter == null) {
            Log.i("Log", "No Bluetooth");
            return false;
        } else {
            return true;
        }
    }

    /**
     * detect Bluetooth on or off
     *
     * @return
     */
    public boolean isBluetoothOpening() {
        if (mBluetoothAdapter.isEnabled()) {
            Log.i("Log", "Bluetooth is opening");
            return true;
        } else {
            Log.i("Log", "Bluetooth is closed");
            return false;
        }
    }

    /**
     * open Bluetooth
     *
     * @return
     */
    public void openBluetooth() {
        Log.i("Log", "Bluetooth opened");
        mBluetoothAdapter.enable();
    }

    public boolean isDiscovering() {
        Log.i("Log", "Bluetooth is Discovering");
        return mBluetoothAdapter.isDiscovering();
    }

    public void startDiscovery() {
        if (!isDiscovering()) {
            mBluetoothAdapter.startDiscovery();
        }
    }

}
