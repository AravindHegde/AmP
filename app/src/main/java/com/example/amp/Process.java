package com.example.amp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class Process extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private ActionBarDrawerToggle mToggle;
    DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process);
        drawer= findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mToggle=new ActionBarDrawerToggle(this,drawer,R.string.open,R.string.close);
        drawer.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        switch(item.getItemId()){
            case R.id.item1:
                Intent i=new Intent(this,forgot.class);
                startActivity(i);
                break;
            case R.id.item2:
                Intent intent=new Intent(this,reset.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.my_menu,menu);
        return true;
    }
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.log:
                drawer.closeDrawers();
                Intent iLog=new Intent(this,LogActivity.class);
                startActivity(iLog);

                return true;
            case R.id.about:
                drawer.closeDrawers();
                Intent iAbout=new Intent(this,AboutActivity.class);
                startActivity(iAbout);
                return true;
            case R.id.feedback:
                drawer.closeDrawers();
                Intent iFeed=new Intent(this,FeedbackActivity.class);
                startActivity(iFeed);
                return true;
            case R.id.help:
                drawer.closeDrawers();
                Intent iHelp=new Intent(this,HelpActivity.class);
                startActivity(iHelp);
                return true;
        }
        return true;
    }
    public void getContact(View v){
        Intent i=new Intent(this,ContactActivity.class);
        i.putExtra("name","getContact");
        startActivity(i);
    }
    public void getLocation(View v){
        Intent i=new Intent(this,OtherActivity.class);
        i.putExtra("name","getLocation");
        startActivity(i);
    }

    public void muteToSound(View v){
        Intent i=new Intent(this,OtherActivity.class);
        i.putExtra("name","muteToSound");
        startActivity(i);
    }
    public void soundToMute(View v){
        Intent i=new Intent(this,OtherActivity.class);
        i.putExtra("name","soundToMute");
        startActivity(i);
    }
    public void flashOn(View v){
        Intent i=new Intent(this,OtherActivity.class);
        i.putExtra("name","flashOn");
        startActivity(i);
    }
    public void flashOff(View v){
        Intent i=new Intent(this,OtherActivity.class);
        i.putExtra("name","flashOff");
        startActivity(i);
    }

}
