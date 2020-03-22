package com.example.amp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        tv=(TextView)findViewById(R.id.textView);
        tv.setText("This app helps you to access your phone remotely using SMSManager\n" +
                "This app works without Internet\n\n" +
                "User Guide:\n" +
                "1)Install your application on target Mobile and Set up the SecretKey\n" +
                "2)Send SMS in the following format:\n" +
                "\t1)To Get the Contact : AmP SecretKey getContact ContactName\n" +
                "\t2)To Get the Location : AmP SecretKey getLocation\n" +
                "\t3)Sound profile to Mute Profile: AmP SecretKey soundToMute\n" +
                "\t4)Mute profile to Sound Profile: AmP SecretKey muteToSound\n" +
                "\t5)Turn on FlashLight: AmP SecretKey flashOn\n" +
                "\t6)Turn off FlashLight: AmP SecretKey flashOff\n"+
                "\t7)Turn on Wifi: AmP SecretKey wifiOn\n"+
                "\t8)Turn off Wifi: AmP SecretKey wifiOff\n" +
                "\tExample :\n" +
                "\t\t1)AmP 1234 getContact Aravind\n" +
                "\t\t2)AmP 1234 getLocation\n" +
                "3)Delete the SMS\n");
    }
}

