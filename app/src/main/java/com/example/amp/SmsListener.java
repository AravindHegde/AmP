package com.example.amp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SmsListener extends BroadcastReceiver {
    DatabaseHelper db;

    DataBaseHelperLog log;
    private SharedPreferences preferences;

    @Override
    public void onReceive(Context context, Intent intent) {


        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle = intent.getExtras();
            SmsMessage[] msgs = null;
            String msg_from;
            if (bundle != null){
                try{
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for(int i=0; i<msgs.length; i++){
                        msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        String msgBody = msgs[i].getMessageBody();
                        Log.d("msg",msgBody);
                        check(context,msg_from,msgBody);
                    }
                }catch(Exception e){
//                            Log.d("Exception caught",e.getMessage());
                }
            }
        }
    }
    public void check(Context ctx,String from,String msg){
        db=new DatabaseHelper(ctx);
        from=from.substring(3,from.length());
        String key="";
        Cursor cr=db.getCursour();
        cr.moveToFirst();
        if(cr.getCount()!=0){
            key=cr.getString(0);
        }
        String content[]=msg.split(" ");
        if(content[0].equals("AmP")){
            if(content[1].equals(key)){
                ExecuteFunctions ex=new ExecuteFunctions();
                log=new DataBaseHelperLog(ctx);
                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                if(content.length==3){
                    switch (content[2]){
                        case "getLocation":ex.getLocation(ctx,from);
                            log.addUser("getLocation",from, currentDate,currentTime);
                            break;
                        case "soundToMute":ex.Mute(ctx);
                            log.addUser("soundToMute",from, currentDate,currentTime);
                            break;
                        case "muteToSound":ex.Sound(ctx);
                            log.addUser("muteToSound",from, currentDate,currentTime);
                            break;
                        case "flashOn":ex.flashOn(ctx);
                            log.addUser("flashOn",from, currentDate,currentTime);
                            break;
                        case "flashOff":ex.flashOff(ctx);
                            log.addUser("flashOff",from, currentDate,currentTime);
                            break;
                        case "wifiOn":ex.wifiOn(ctx);
                            log.addUser("wifiOn",from, currentDate,currentTime);
                            break;
                        case "wifiOff":ex.wifiOff(ctx);
                            log.addUser("wifiOff",from, currentDate,currentTime);
                            break;
                    }
                }
                if(content.length==4&&content[2].equals("getContact")){
                    log.addUser("getContact",from, currentDate,currentTime);
                    ex.getContact(ctx,from,content[3]);
                }
            }

        }
    }
}