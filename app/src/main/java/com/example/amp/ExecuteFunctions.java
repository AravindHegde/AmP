package com.example.amp;

import android.Manifest;
import android.content.Context;


import android.content.pm.PackageManager;
import android.database.Cursor;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.provider.ContactsContract;
import android.telephony.SmsManager;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.AUDIO_SERVICE;

public class ExecuteFunctions {
    private AudioManager mAudioManager;
    private CameraManager mCameraManager;
    private String mCameraId;
    public void preprocessFlashLight(Context ctx){
        mCameraManager = (CameraManager) ctx.getSystemService(Context.CAMERA_SERVICE);
        try {
            mCameraId = mCameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
    public void getContact(Context ctx,String from,String contact){
        HashMap<String,String> Contacts=new HashMap<>();
        ArrayList<String> ContactNames=new ArrayList<>();
        if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            Cursor cursor = ctx.getContentResolver()
                    .query(android.provider.ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            new String[]{
                                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME},
                            "display_name LIKE ?",
                            new String[]{"%" + contact + "%" }, null);
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                String name=cursor.getString(2);
                if(Contacts.containsKey(name)){
                    String numbers=Contacts.get(name);
                    numbers+=", "+cursor.getString(0);
                    Contacts.put(name,numbers);
                }
                else{
                    Contacts.put(name,name+" : "+cursor.getString(0));
                    ContactNames.add(name);
                }
            }
            String msg="";
            for(int i=0;i<ContactNames.size();i++){
                String name=ContactNames.get(i);
                msg+=Contacts.get(name)+"\n";
            }
            if(msg.equals("")){
                msg="No Contacts Found :(";
            }
            else{
                msg=msg.substring(0,msg.length()-1);
            }
            if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                SmsManager smsManager = SmsManager.getDefault();
                ArrayList<String> parts =smsManager.divideMessage(msg);
                smsManager.sendMultipartTextMessage(from,null,parts,null,null);
            }
        }
    }
    public void getLocation(Context ctx,String from){
        GPSTracker gps=new GPSTracker(ctx);
        if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            SmsManager smsManager = SmsManager.getDefault();
            ArrayList<String> parts =smsManager.divideMessage(gps.loc);
            smsManager.sendMultipartTextMessage(from,null,parts,null,null);
        }
    }

    public void Sound(Context ctx){
        mAudioManager = (AudioManager) ctx.getSystemService(AUDIO_SERVICE);
        mAudioManager.setStreamVolume(AudioManager.STREAM_RING,mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC),AudioManager.FLAG_SHOW_UI);
    }
    public void Mute(Context ctx){
        mAudioManager = (AudioManager) ctx.getSystemService(AUDIO_SERVICE);
        mAudioManager.setStreamVolume(AudioManager.STREAM_RING,0,AudioManager.FLAG_SHOW_UI);
    }
    public void flashOn(Context ctx){
        boolean isFlashAvailable = ctx.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT);
        if(isFlashAvailable){
            preprocessFlashLight(ctx);
            try {
                mCameraManager.setTorchMode(mCameraId, true);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
    }
    public void flashOff(Context ctx){
        boolean isFlashAvailable = ctx.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT);
        if(isFlashAvailable){
            preprocessFlashLight(ctx);
            try {
                mCameraManager.setTorchMode(mCameraId, false);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
    }
    public void wifiOn(Context ctx){
        WifiManager wifiManager = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(true);
    }
    public void wifiOff(Context ctx){
        WifiManager wifiManager = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(false);
    }
}
