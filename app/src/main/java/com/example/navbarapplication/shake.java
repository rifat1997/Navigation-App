package com.example.navbarapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import android.widget.Toast;

import android.graphics.Color;
import android.hardware.SensorManager;
import android.support.constraint.ConstraintLayout;

import android.widget.TextView;

import com.squareup.seismic.ShakeDetector;

public class shake extends AppCompatActivity implements ShakeDetector.Listener {

    private static final int PERMISSION_REQUEST_CODE = 1;

    private SensorManager SM;
    ShakeDetector shakeDetector;
    TextView shake_tv;
    Boolean f=false;
    ConstraintLayout bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission()) {
                Log.e("permission", "Permission already granted.");
            }
            else {
                requestPermission();
            }
        }
        shake_tv=findViewById(R.id.shake_tv);
        bg=findViewById(R.id.bg4);

        SM= (SensorManager) getSystemService(SENSOR_SERVICE);
        shakeDetector=new ShakeDetector(this);
        shakeDetector.start(SM);
    }

    @Override
    public void hearShake() {

        if(f)
        {
            shake_tv.setText("Its shaking !!!");
            bg.setBackgroundColor(Color.RED);
            String phoneNum = "999";
            if(!TextUtils.isEmpty(phoneNum)) {
                String dial = "tel:" + phoneNum;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
            else {
                Toast.makeText(shake.this, "Please enter a valid telephone number", Toast.LENGTH_SHORT).show();
            }

            f=!f;
        }
        else
        {
            shake_tv.setText("Shake Removed!!!");
            bg.setBackgroundColor(Color.rgb(255,255,255));
            f=!f;
        }

    }

    protected  void onResume()
    {
        super.onResume();
        shakeDetector.start(SM);

    }

    @Override
    protected  void onPause()
    {
        super.onPause();
        shakeDetector.stop();
    }

    public boolean checkPermission() {

        int CallPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE);

        return CallPermissionResult == PackageManager.PERMISSION_GRANTED;

    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(shake.this, new String[]
                {
                        Manifest.permission.CALL_PHONE
                }, PERMISSION_REQUEST_CODE);

    }
}
