package com.project.mynoteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	//define all the constants
    static String DATABASE_NAME="note";
    public static final String TABLE_NAME="notes";
    //these are the lit of fields in the table
    public static final String STAFID="id";
    public static final String NAMA="header";
    public static final String JBT="paragraph";
    
    public DBHelper(Context context) {
    	//create the database
        super(context, DATABASE_NAME, null, 1);
       
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    	//create the table
        String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+" ("+STAFID+" INTEGER PRIMARY KEY, "+NAMA+" TEXT, "+JBT+" TEXT)";
        db.execSQL(CREATE_TABLE);
        db.execSQL("create Table users(username TEXT primary key, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	//onUpgrade remove the existing table, and recreate and populate new data
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
        db.execSQL("drop Table if exists users");

    }
    public Boolean insertData(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = MyDB.insert("users", null, contentValues);
        if(result==-1) return false;
        else
            return true;
    }
    public Boolean checkusernamepassword(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ? and password = ?", new String[] {username,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

}


