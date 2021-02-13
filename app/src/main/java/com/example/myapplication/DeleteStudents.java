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
import android.widget.TextView;

import java.util.ArrayList;

public class DeleteStudents extends AppCompatActivity implements AdapterView.OnItemClickListener
{
    HelperDB hlp;
    SQLiteDatabase db;
    Cursor crsr;
    ArrayList<String> tbl = new ArrayList<>();
    ArrayList<String> nameTBL=new ArrayList<>();
    ListView studentsList;
    AlertDialog.Builder adb;
    int position;
    TextView text;

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
        position=-1;
        text=(TextView)findViewById(R.id.text);
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        finish();
    }

    public void read()
    {
        crsr=db.query(Users.TABLE_USERS, null, null, null, null, null, null);
        int idCol=crsr.getColumnIndex(Users.KEY_ID);
        int nameCol=crsr.getColumnIndex(Users.NAME);
        int addressCol=crsr.getColumnIndex(Users.ADDRESS);
        int phoneCol=crsr.getColumnIndex(Users.PHONE);
        int homeCol=crsr.getColumnIndex(Users.HOME_PHONE);
        int fatherCol=crsr.getColumnIndex(Users.FATHER);
        int fatherPhoneCol=crsr.getColumnIndex(Users.FATHER_PHONE);
        int motherCol=crsr.getColumnIndex(Users.MOTHER);
        int motherPhoneCol=crsr.getColumnIndex(Users.MOTHER_PHONE);

        crsr.moveToFirst();
        while (!crsr.isAfterLast())
        {
            int key=crsr.getInt(idCol);
            String name = crsr.getString(nameCol);
            String address=crsr.getString(addressCol);
            int phone=crsr.getInt(phoneCol);
            int home=crsr.getInt(homeCol);
            String father=crsr.getString(fatherCol);
            int fatherPhone=crsr.getInt(fatherPhoneCol);
            String mother=crsr.getString(motherCol);
            int motherPhone=crsr.getInt(motherPhoneCol);

            String tmp=key+",   "+name+", "+address+",   "+phone+",  "+home+",   "+father+",   "+fatherPhone+",   "+mother+",   "+motherPhone;
            tbl.add(tmp);
            nameTBL.add(key+". "+name);
            crsr.moveToNext();
        }
        crsr.close();
        db.close();

        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, nameTBL);
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
        this.position=position;

        String temp=(String)parent.getItemAtPosition(position);
        for (int i=0; i<tbl.size(); i++)
        {
            if(!temp.split(",   ")[0].equals(tbl.get(i).split(",   ")[0]))
            {
                continue;
            }
        }
    }
}