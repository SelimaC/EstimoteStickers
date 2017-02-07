package com.example.selima.stickers;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import 	android.provider.ContactsContract.Data;

import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.EstimoteSDK;
import com.estimote.sdk.Nearable;
import com.estimote.sdk.SystemRequirementsChecker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BeaconManager beaconManager;
    private String scanId;

    File sd;
    File f = new File(Environment.DIRECTORY_DOCUMENTS, "log_stickers.txt");;

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

                File file;
                FileOutputStream outputStream;
                try {
                    file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
                            "Log_stickers.txt");

                    outputStream = new FileOutputStream(file,true);
                    OutputStreamWriter write = new OutputStreamWriter(outputStream);

                    write.append(nearables.toString());
                    write.append("\n\n\n");

                    write.close();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

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
