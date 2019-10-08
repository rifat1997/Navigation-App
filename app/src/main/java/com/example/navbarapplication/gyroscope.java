package com.example.navbarapplication;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class gyroscope extends AppCompatActivity implements SensorEventListener {

    private TextView tv_x1,tv_y1,tv_z1;
    private Sensor mySensor1;
    private SensorManager SM;
    private ConstraintLayout bg;
    boolean saveGyroscopeDataStatus=false;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference SensorData = db.collection("SensorData");
    private DocumentReference gyroscopeData=SensorData.document();


    private void saveGyroscopeData(String direction, int value)
    {
        SensorData sd =new SensorData("Gyroscope",direction,value);

        try{
            gyroscopeData.set(sd);
            Toast.makeText(this,
                    "Data saved!!",
                    Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(this,
                    "Data didn't save!!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyroscope);

        SM= (SensorManager) getSystemService(SENSOR_SERVICE);

        mySensor1=SM.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        SM.registerListener(this,mySensor1,SensorManager.SENSOR_DELAY_NORMAL);


        tv_x1=findViewById(R.id.gyro_x_tv);
        tv_y1=findViewById(R.id.gyro_y_tv);
        tv_z1=findViewById(R.id.gyro_z_tv);
        bg=findViewById(R.id.bg2);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int x = (int) event.values[0]*100;
        int y = (int) event.values[1]*100;
        int z = (int) event.values[2]*100;

            if (!saveGyroscopeDataStatus)
            {
                if (z<0)
                {
                    saveGyroscopeData("Clock Wise",z);
                }
                else
                {
                    saveGyroscopeData("Counter Clock Wise",z);
                }
                saveGyroscopeDataStatus=!saveGyroscopeDataStatus;
            }



        tv_x1.setText(String.valueOf("X is: "+x));
        tv_y1.setText(String.valueOf("Y is: "+y));
        tv_z1.setText(String.valueOf("Z is: "+z));

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    protected  void onResume()
    {
        super.onResume();
        SM.registerListener(this,mySensor1,SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected  void onPause()
    {
        super.onPause();

        SM.unregisterListener(this);
    }
}
