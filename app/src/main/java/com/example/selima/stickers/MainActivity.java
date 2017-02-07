package com.example.selima.stickers;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Window;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.EstimoteSDK;
import com.estimote.sdk.Nearable;
import com.estimote.sdk.Region;
import com.estimote.sdk.SystemRequirementsChecker;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BeaconManager beaconManager;
    private String scanId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EstimoteSDK.initialize(getApplicationContext(), "stickers-ozb", "6c9e4aa20747b60819f37d5a15c11f6b");

        beaconManager = new BeaconManager(this);
        beaconManager.setForegroundScanPeriod(10000, 10000);


        beaconManager.setNearableListener(new BeaconManager.NearableListener() {
            @Override public void onNearablesDiscovered(List<Nearable> nearables) {
                Log.d("TAG", "Discovered nearables: " + nearables);
            }
        });

    }

    @Override
    protected void onStart(){
       super.onStart();
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override public void onServiceReady() {
                scanId = beaconManager.startNearableDiscovery();
            }
        });
    }

    @Override
    protected void onStop(){
        super.onStop();
        beaconManager.stopNearableDiscovery(scanId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SystemRequirementsChecker.checkWithDefaultDialogs(this);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        beaconManager.disconnect();
    }
}
