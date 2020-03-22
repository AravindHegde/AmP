package com.example.amp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.prefs.Preferences;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper db;
    public Spinner spinner;
    public EditText et1,et2,et3;
    public  String question="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db=new DatabaseHelper(this);
        if(db.Exists()){
            Intent intent=new Intent(this,Process.class);
            startActivity(intent);
            finish();
        }
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                android.Manifest.permission.READ_CONTACTS,
                android.Manifest.permission.READ_SMS,
                android.Manifest.permission.RECEIVE_SMS,
                android.Manifest.permission.SEND_SMS,
                android.Manifest.permission.ACCESS_WIFI_STATE,
                android.Manifest.permission.CHANGE_WIFI_STATE,
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION

        };

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
        spinner=(Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String>   myAdapter=new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.names));
        myAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(myAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                question=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }
    public void Register(View v){
        et1=(EditText)findViewById(R.id.editText);
        et2=(EditText)findViewById(R.id.editText1);
        et3=(EditText)findViewById(R.id.editText2);
        String psk1=et1.getText().toString();
        String psk2=et2.getText().toString();
        String ans=et3.getText().toString();
        boolean b=true;
        if(psk1.equals("")){
            b=false;
            et1.setError("This field can't be empty");
        }
        if(psk2.equals("")){
            b=false;
            et2.setError("This field can't be empty");
        }
        if(ans.equals("")){
            b=false;
            et3.setError("This field can't be empty");
        }
        if(b&&psk1.length()!=4){
            b=false;
            et1.setError("Key should be 4 digit");
        }
        else if(b&&!psk1.equals(psk2)){
            b=false;
            et2.setError("Key didn't match");
        }

        if(b){
            db.addUser(psk1,question,ans);
            Intent intent=new Intent(this,Process.class);
            startActivity(intent);
            finish();
        }
    }
    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
