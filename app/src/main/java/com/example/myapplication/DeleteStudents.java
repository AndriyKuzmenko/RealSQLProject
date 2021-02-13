package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class DeleteStudents extends AppCompatActivity implements AdapterView.OnItemClickListener
{
    HelperDB hlp;
    SQLiteDatabase db;
    Cursor crsr;
    ArrayList<String> tbl = new ArrayList<>();
    ListView studentsList;
    AlertDialog.Builder adb;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_students);

        hlp=new HelperDB(this);
        db=hlp.getWritableDatabase();

        studentsList=(ListView)findViewById(R.id.studentsList);

        read();

        studentsList.setOnItemClickListener(this);
        studentsList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        finish();
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


    /**
     * @param menu  - the menu
     * @return      - shows the main menu
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    /**
     *
     * @param item - the item that was selected
     * @return     - Starts the activity that the user selected.
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId()==R.id.menuMain)
        {
            Intent i=new Intent(this,MainActivity.class);
            startActivity(i);
        }

        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        adb=new AlertDialog.Builder(this);
        adb.setTitle("Warning");
        adb.setMessage("Are you sure you want to delete "+parent.getItemAtPosition(position)+"?");

        adb.setNegativeButton("No", new DialogInterface.OnClickListener()
        {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {

                }
            }
        );

        adb.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                db = hlp.getWritableDatabase();
                db.delete(Users.TABLE_USERS, "=?", new String[]{Integer.toString(position + 1)});
                db.close();
            }
        });

        AlertDialog ad=adb.create();
        ad.show();
    }
}