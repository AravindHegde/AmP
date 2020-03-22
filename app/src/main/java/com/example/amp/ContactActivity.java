package com.example.amp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ContactActivity extends AppCompatActivity {
    final int permissionCode=1;
    EditText editText,editText1,editText2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
    }
    public void Perform(View v){
        if(!(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED)){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},permissionCode);
        }
        editText=(EditText)findViewById(R.id.editText);
        editText1=(EditText)findViewById(R.id.editText1);
        editText2=(EditText)findViewById(R.id.editText2);
        String psk=editText.getText().toString();
        String phone=editText1.getText().toString();
        String ContactName=editText2.getText().toString();
        boolean b=true;
        if(psk.equals("")){
            editText.setError("This Field can't be empty");
            b=false;
        }
        if(b&&phone.equals("")){
            editText1.setError("This Field can't be empty");
            b=false;
        }
        if(b&&ContactName.equals("")){
            editText2.setError("This Field can't be empty");
            b=false;
        }
        if(b&&psk.length()!=4){
            editText.setError("Key should be 4 digit");
            b=false;
        }
        if(b) {
            String message="AmP";
            String type=getIntent().getExtras().getString("name");
            message=message+" "+psk+" "+type+" "+ContactName;
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                SmsManager smsManager=SmsManager.getDefault();
                smsManager.sendTextMessage(phone,null,message,null,null);
                Toast.makeText(this,"Message sent",Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Permission Denied For SMS", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
