package com.example.amp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class FeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

    }
    public void Send(View view){
        EditText e1=(EditText)findViewById(R.id.editText);
        String msg=e1.getText().toString();
        String toAddress="7760919577";
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            SmsManager smsManager = SmsManager.getDefault();
            ArrayList<String> parts =smsManager.divideMessage(msg);
            smsManager.sendMultipartTextMessage(toAddress,null,parts,null,null);
            Toast.makeText(this,"Message Sent",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show();
        }
    }
}
