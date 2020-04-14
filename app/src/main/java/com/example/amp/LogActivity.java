package com.example.amp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class LogActivity extends AppCompatActivity {
    DataBaseHelperLog db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        ListView listView=(ListView)findViewById(R.id.listView);
        ArrayList<String> array=new ArrayList<String>();
        db=new DataBaseHelperLog(this);
        Cursor cr=db.getCursour();
        cr.moveToLast();
        int len=cr.getCount();
        for(int i=0;i<len;i++){
            String str="";
            str=cr.getString(0)+" Requested by:"+cr.getString(1)+"\nDate:"+cr.getString(2)+" Time:"+cr.getString(3);
            array.add(str);
            cr.moveToPrevious();
        }
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,array);
        listView.setAdapter(adapter);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.item: db.clear();
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.log_menu,menu);
        return true;
    }
}
