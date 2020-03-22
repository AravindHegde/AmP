package com.example.amp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        tv=(TextView)findViewById(R.id.textView);
        tv.setText("For any queries Contact:\nAravind Hegde\nPhone : 7760919577\nEmail : aravindrhegde10@gmail.com");
    }
}
