package com.example.makris.bikefinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    // UI
    TextView distanceTextView, signalDBTextView;
    Button turnOnBtn, turnOffBtn, visibleBtn, listBtn;

    // create adapter
    private BluetoothAdapter bluetoothAdapter;
    private Set<BluetoothDevice> pairedDevices;
    ListView devicesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        distanceTextView = (TextView)findViewById(R.id.distanceNumTextView);
        signalDBTextView = (TextView)findViewById(R.id.signalDBTextView);

        turnOnBtn = (Button)findViewById(R.id.turnOnBtn);
        turnOffBtn = (Button)findViewById(R.id.turnOffBtn);
        visibleBtn = (Button)findViewById(R.id.visibleBtn);
        listBtn = (Button)findViewById(R.id.listBtn);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        devicesListView = (ListView)findViewById(R.id.deviceListView);
    }

    public void turnOnBluetooth(View v){
        if(!bluetoothAdapter.isEnabled()){
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOn, 0);
            Toast.makeText(getApplicationContext(), "Turned on", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(), "Already on", Toast.LENGTH_LONG).show();
        }
    }

    public void turnOffBluetooth(View v){
        this.bluetoothAdapter.disable();
        Toast.makeText(getApplicationContext(), "Turned off", Toast.LENGTH_LONG).show();
    }

    public void makeDeviceVisible(View v){
        Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        startActivityForResult(getVisible, 0);
    }

    public void listLinkedDevices(View v){
        pairedDevices = bluetoothAdapter.getBondedDevices();

        ArrayList list = new ArrayList();

        for(BluetoothDevice bt: pairedDevices) list.add(bluetoothAdapter.getName());

        Toast.makeText(getApplicationContext(), "Showing Paired Devices", Toast.LENGTH_LONG).show();

        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);

        devicesListView.setAdapter(adapter);
    }
}
