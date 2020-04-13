package com.example.amp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelperLog extends SQLiteOpenHelper {
    public static final String dbname="log.db";
    public static final String tab="Log";
    //public static final String col1="Id";
    public static final String col1="Operation";
    public static final String col2="Number";
    public static final String col3="Date";
    public static final String col4="Time";
    public DataBaseHelperLog(Context context) {
        super(context, dbname, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table Log(id INTEGER primary key autoincrement,Operation text,Number text,Date text,Time text)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
    public void addUser(String Operation,String Number,String Date,String Time){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("Operation",Operation);
        cv.put("Number",Number);
        cv.put("Date",Date);
        cv.put("Time",Time);
        long res=db.insert(tab,null,cv);
        db.close();
        //db.execSQL("insert into "+tab+" values("+id+",'"+email+"','"+psk+"')");
        //++id;
    }
    public Cursor getCursour(){
        SQLiteDatabase db=this.getReadableDatabase();
        String[] columns={col1,col2,col3,col4};
        String selection="";
        String[] selectArgs={};

        Cursor cursor=db.query(tab,columns,selection,selectArgs,null,null,null);
        return cursor;
    }
    public void clear(){
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("delete from "+ tab);
    }
}
