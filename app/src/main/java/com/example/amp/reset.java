package com.example.amp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class reset extends AppCompatActivity {
    EditText e1,e2,e3;
    DatabaseHelper db;
    String oldKey="",newKey="",reKey="",Ques="",ans="";
    Cursor cr=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
    }
    public void Reset(View v){
        db=new DatabaseHelper(this);
        e1=(EditText)findViewById(R.id.editText);
        e2=(EditText)findViewById(R.id.editText1);
        e3=(EditText)findViewById(R.id.editText2);
        oldKey=e1.getText().toString();
        newKey=e2.getText().toString();
        reKey=e3.getText().toString();
        boolean b=true;
        if(oldKey.equals("")){
            b=false;
            e1.setError("This Field can't be empty");
        }
        if(newKey.equals("")){
            b=false;
            e2.setError("This Field can't be empty");
        }
        if(reKey.equals("")){
            b=false;
            e3.setError("This Field can't be empty");
        }
        cr=db.getCursour();
        cr.moveToFirst();
        String dbKey=cr.getString(0);
        Ques=cr.getString(1);
        ans=cr.getString(2);
        if(b&&!dbKey.equals(oldKey)){
            b=false;
            e1.setError("Incorrect Key");
        }
        if(b&&newKey.length()!=4){
            b=false;
            e2.setError("Key should be 4 digit");
        }
        if(b&&!newKey.equals(reKey)){
            b=false;
            e3.setError("Key didn't match");
        }
        if(b){
            db.deleteandInsert(oldKey,newKey,Ques,ans);
            finish();
        }
    }
}
