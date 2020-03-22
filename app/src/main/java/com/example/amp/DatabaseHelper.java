package com.example.amp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String dbname="register.db";
    public static final String tab="Register";
    //public static final String col1="Id";
    public static final String col1="myKey";
    public static final String col2="ques";
    public static final String col3="ans";
    public DatabaseHelper(Context context) {
        super(context, dbname, null, 1);

    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table Register(myKey text primary key,ques text,ans text)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
    public void addUser(String mykey,String ques,String ans){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("myKey",mykey);
        cv.put("ques",ques);
        cv.put("ans",ans);
        long res=db.insert(tab,null,cv);
        db.close();
        //db.execSQL("insert into "+tab+" values("+id+",'"+email+"','"+psk+"')");
        //++id;
    }
    public boolean Exists(){
        SQLiteDatabase db=this.getReadableDatabase();
        String[] columns={col1,col2,col3};
        String selection="";
        String[] selectArgs={};

        Cursor cursor=db.query(tab,columns,selection,selectArgs,null,null,null);
        int count=cursor.getCount();
        if(count>=1){
            return true;
        }
        else{
            return false;
        }
    }
    public Cursor getCursour(){
        SQLiteDatabase db=this.getReadableDatabase();
        String[] columns={col1,col2,col3};
        String selection="";
        String[] selectArgs={};

        Cursor cursor=db.query(tab,columns,selection,selectArgs,null,null,null);
        return cursor;
    }
    public void deleteandInsert(String key,String newKey,String Ques,String ans){
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("delete from "+tab+" where myKey='"+key+"'");
        addUser(newKey,Ques,ans);
    }
}
