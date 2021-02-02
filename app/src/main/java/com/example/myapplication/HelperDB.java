package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HelperDB extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME="dbexam.db";
    private static final int DATABASE_VERSION=8;
    String strCreate, strDelete;

    public HelperDB(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        strCreate="CREATE TABLE "+Users.TABLE_USERS;
        strCreate+=" ("+Users.KEY_ID+" INTEGER PRIMARY KEY,";
        strCreate+=" "+Users.NAME+" TEXT,";
        strCreate+=" "+Users.ADDRESS+" TEXT,";
        strCreate+=" "+Users.PHONE+" INTEGER,";
        strCreate+=" "+Users.HOME_PHONE+" INTEGER,";
        strCreate+=" "+Users.FATHER+" TEXT,";
        strCreate+=" "+Users.FATHER_PHONE+" INTEGER,";
        strCreate+=" "+Users.MOTHER+" TEXT,";
        strCreate+=" "+Users.MOTHER_PHONE+" TEXT,";
        strCreate+=" "+Users.ACTIVE+" INTEGER";
        strCreate+=");";
        db.execSQL(strCreate);

        strCreate="CREATE TABLE "+Grades.TABLE_GRADES;
        strCreate+=" ("+Grades.STUDENT+" INTEGER";
        strCreate+=" "+Grades.QUARTER+" INTEGER";
        strCreate+=" "+Grades.SUBJECT+" TEXT,";
        strCreate+=" "+Grades.GRADE+" INTEGER";
        strCreate+=");";
        db.execSQL(strCreate);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        strDelete="DROP TABLE IF EXISTS "+Users.TABLE_USERS;
        db.execSQL(strDelete);

        onCreate(db);
    }
}