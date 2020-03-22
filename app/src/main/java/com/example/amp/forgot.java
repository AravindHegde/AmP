package com.example.amp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class forgot extends AppCompatActivity {
    Cursor cr;
    TextView t1,t2;
    EditText editText;
    String ques="",ans="",key="";
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        db=new DatabaseHelper(this);
        cr=db.getCursour();
        cr.moveToFirst();
        key=cr.getString(0);
        ques=cr.getString(1);
        ans=cr.getString(2);
        t1=(TextView)findViewById(R.id.textView);
        t1.setText(ques);
        t2=(TextView)findViewById(R.id.textView1);
        editText=(EditText)findViewById(R.id.editText);
    }
    public void Forgot(View v){
        String myAns=editText.getText().toString();
        if(!myAns.equals(ans)){
            editText.setError("Incorrect answer");
        }
        else{
            t2.setText("Your Key is:"+key);
        }
    }
}
