package com.example.selima.stickers;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.EstimoteSDK;
import com.estimote.sdk.Nearable;
import com.estimote.sdk.SystemRequirementsChecker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

        final List<Fields> fields = new ArrayList<Fields>();

        beaconManager.setNearableListener(new BeaconManager.NearableListener() {
            @Override public void onNearablesDiscovered(List<Nearable> nearables) {
                Log.d("TAG", "Discovered nearables: " + nearables);

                for(Nearable n : nearables){
                    Fields f = new Fields(n.identifier, n.isMoving, n.lastMotionStateDuration, n.currentMotionStateDuration,
                                          n.xAcceleration + "  " + n.yAcceleration + "  " + n.zAcceleration,
                                          n.temperature, n.batteryLevel.toString(), n.color.toString(), n.type.toString());
                    fields.add(f);
                }


            }
        });

        Button save = (Button) findViewById(R.id.saveButton);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Boolean> check = new ArrayList<Boolean>();
                File file;
                FileOutputStream outputStream;
                int counter = 0;
                boolean serverC = true;

                EditText serverName = (EditText) findViewById(R.id.serverName);
                CheckBox idC = (CheckBox) findViewById(R.id.idC);
                CheckBox motionC = (CheckBox) findViewById(R.id.motionC);
                CheckBox lastC = (CheckBox) findViewById(R.id.lastC);
                CheckBox currentC = (CheckBox) findViewById(R.id.currentC);
                CheckBox accelerationC = (CheckBox) findViewById(R.id.accelerationC);
                CheckBox temperatureC = (CheckBox) findViewById(R.id.temperatureC);
                CheckBox batteryC = (CheckBox) findViewById(R.id.batteryC);
                CheckBox colorC = (CheckBox) findViewById(R.id.colorC);
                CheckBox typeC = (CheckBox) findViewById(R.id.typeC);

                if(idC.isChecked()) {check.add(true); counter++;} else check.add(false);
                if(motionC.isChecked()) {check.add(true); counter++;} else check.add(false);
                if(lastC.isChecked()) {check.add(true); counter++;} else check.add(false);
                if(currentC.isChecked()) {check.add(true); counter++;} else check.add(false);
                if(accelerationC.isChecked()) {check.add(true); counter++;} else check.add(false);
                if(temperatureC.isChecked()) {check.add(true); counter++;} else check.add(false);
                if(batteryC.isChecked()) {check.add(true); counter++;} else check.add(false);
                if(colorC.isChecked()) {check.add(true); counter++;} else check.add(false);
                if(typeC.isChecked()) {check.add(true); counter++;} else check.add(false);

                if(serverName.getText().toString().equals("") || serverName.getText()==null){
                    serverC = false;
                    serverName.setError("Enter server name");
                }

                if(serverC) {
                    if (counter != 0) {
                        try {
                            file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
                                    serverName.getText() + ".txt");

                            outputStream = new FileOutputStream(file, true);
                            OutputStreamWriter write = new OutputStreamWriter(outputStream);

                            for (Fields f : fields) {
                                write.append(f.onlyChecked(check));
                                write.append("\n\n");
                            }

                            write.append("\n\n\n");
                            write.close();
                            outputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(getApplicationContext(), "Data were saved", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getApplicationContext(), "No field selected", Toast.LENGTH_SHORT).show();
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
