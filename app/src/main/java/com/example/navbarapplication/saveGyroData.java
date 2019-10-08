package com.example.navbarapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class saveGyroData extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView lst;
    SensorData sd[];
    String nm[];

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference SensorData = db.collection("SensorData");
    private DocumentReference gyroscopeData=SensorData.document();

    private void fetchHistory(final Context myContext)
    {
        Log.d("fetch", "Error getting documents: log ");
        SensorData.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                           int sz= Objects.requireNonNull(task.getResult()).size(),ii=0;
                            String myData[] =new String[sz];
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {

                                SensorData op_obj=document.toObject(SensorData.class);
                                myData[ii]=op_obj.getName()+" "+op_obj.getDirection()+" "+op_obj.getValue()+"\n";
                                ii++;

                            }

                            ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(myContext,android.R.layout.simple_list_item_1,myData);
                            lst.setAdapter(arrayAdapter);



                        } else {
                            Log.d("fetch", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyro_data);

        lst=findViewById(R.id.lst);
        fetchHistory(this);
        lst.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        ArrayAdapter<String> md;
        md=(ArrayAdapter<String>) parent.getAdapter();
        String dt=md.getItem(position);


        Toast.makeText(this,dt,Toast.LENGTH_SHORT).show();

    }
}
