package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;

import java.util.ArrayList;

public class UpdateActivity extends AppCompatActivity
{
    SQLiteDatabase db;
    HelperDB hlp;
    Cursor crsr;
    ArrayList<String> nameTBL;
    EditText nameUpdate, addressUpdate, phoneUpdate, homePhoneUpdate, fatherUpdate, fatherPhoneUpdate, motherUpdate, motherPhoneUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();
        db.close();
        nameTBL=new ArrayList<>();

        nameUpdate=(EditText)findViewById(R.id.nameUpdate);
        addressUpdate=(EditText)findViewById(R.id.addressUpdate);
        phoneUpdate=(EditText)findViewById(R.id.phoneUpdate);
        homePhoneUpdate=(EditText)findViewById(R.id.homePhoneUpdate);
        fatherUpdate=(EditText)findViewById(R.id.fatherUpdate);
        fatherPhoneUpdate=(EditText)findViewById(R.id.fatherPhoneUpdate);
        motherUpdate=(EditText)findViewById(R.id.motherUpdate);
        motherPhoneUpdate=(EditText)findViewById(R.id.motherPhoneUpdate);

        read();
    }

    /**
     * Reads all the information about the students from the SQL file and puts it in an arrayList.
     */

    public void read()
    {
        db=hlp.getWritableDatabase();
        crsr=db.query(Users.TABLE_USERS, null, null, null, null, null, null);
        int idCol=crsr.getColumnIndex(Users.KEY_ID);
        int nameCol=crsr.getColumnIndex(Users.NAME);

        crsr.moveToFirst();
        while (!crsr.isAfterLast())
        {
            int key=crsr.getInt(idCol);
            String name = crsr.getString(nameCol);

            String temp=key+",   "+name;
            nameTBL.add(temp);
            crsr.moveToNext();
        }
        crsr.close();
        db.close();
    }
}