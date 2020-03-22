package com.example.amp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsListener extends BroadcastReceiver {
    DatabaseHelper db;
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
                if(content.length==3){
                    switch (content[2]){
                        case "getLocation":ex.getLocation(ctx,from);
                            break;
                        case "soundToMute":ex.Mute(ctx);
                            break;
                        case "muteToSound":ex.Sound(ctx);
                            break;
                        case "flashOn":ex.flashOn(ctx);
                            break;
                        case "flashOff":ex.flashOff(ctx);
                            break;
                        case "wifiOn":ex.wifiOn(ctx);
                            break;
                        case "wifiOff":ex.wifiOff(ctx);
                            break;
                    }
                }
                if(content.length==4&&content[2].equals("getContact")){
                    ex.getContact(ctx,from,content[3]);
                }
            }

        }
    }
}