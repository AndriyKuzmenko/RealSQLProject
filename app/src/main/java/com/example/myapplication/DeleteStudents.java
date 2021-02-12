package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class DeleteStudents extends AppCompatActivity
{
    HelperDB hlp;
    SQLiteDatabase db;
    Cursor crsr;
    ArrayList<String> tbl = new ArrayList<>();
    ListView studentsList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_students);

        hlp=new HelperDB(this);
        db=hlp.getWritableDatabase();

        studentsList=(ListView)findViewById(R.id.studentsList);

        read();

    }

    public void read()
    {
        crsr = db.query(Users.TABLE_USERS, null, null, null, null, null, null);
        int idCol = crsr.getColumnIndex(Users.KEY_ID);
        int nameCol = crsr.getColumnIndex(Users.NAME);
        int activeCol = crsr.getColumnIndex(Users.ACTIVE);

        crsr.moveToFirst();
        while (!crsr.isAfterLast())
        {
            int key = crsr.getInt(idCol);
            String name = crsr.getString(nameCol);
            boolean active = crsr.getInt(activeCol)>0;
            if (!active) continue;
            String tmp=name;
            tbl.add(tmp);
            crsr.moveToNext();
        }
        crsr.close();
        db.close();

        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, tbl);
        studentsList.setAdapter(adp);
    }
}
