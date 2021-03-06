package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity
{
    SQLiteDatabase db;
    HelperDB hlp;

    EditText name, address, phone, homePhone, father, fatherPhone, mother, motherPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();
        db.close();

        name=(EditText)findViewById(R.id.name);
        address=(EditText)findViewById(R.id.address);
        phone=(EditText)findViewById(R.id.phone);
        homePhone=(EditText)findViewById(R.id.homePhone);
        father=(EditText)findViewById(R.id.father);
        fatherPhone=(EditText)findViewById(R.id.fatherPhone);
        mother=(EditText)findViewById(R.id.mother);
        motherPhone=(EditText)findViewById(R.id.motherPhone);

    }

    /**
     * Deletes the info about this acticity once the user exits.
     */
    @Override
    protected void onPause()
    {
        super.onPause();

        finish();
    }

    /**
     * Saves the information that the user typed
     * @param view - the button that was pressed
     */

    public void save(View view)
    {
        ContentValues cv = new ContentValues();

        cv.put(Users.NAME, name.getText().toString());
        cv.put(Users.ADDRESS, address.getText().toString());
        cv.put(Users.PHONE, Integer.parseInt(phone.getText().toString()));
        cv.put(Users.HOME_PHONE, Integer.parseInt(homePhone.getText().toString()));
        cv.put(Users.FATHER, father.getText().toString());
        cv.put(Users.FATHER_PHONE, Integer.parseInt(fatherPhone.getText().toString()));
        cv.put(Users.MOTHER, mother.getText().toString());
        cv.put(Users.MOTHER_PHONE, Integer.parseInt(motherPhone.getText().toString()));

        db = hlp.getWritableDatabase();

        db.insert(Users.TABLE_USERS, null, cv);

        db.close();
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
     * @return     - Starts the activity that the user selected
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId()==R.id.deleteStudents)
        {
            Intent i=new Intent(this,DeleteStudents.class);
            startActivity(i);
        }
        else if(item.getItemId()==R.id.addGrades)
        {
            Intent i=new Intent(this,AddGrades.class);
            startActivity(i);
        }
        else if(item.getItemId()==R.id.showGrades)
        {
            Intent i=new Intent(this,ShowGrades.class);
            startActivity(i);
        }
        else if(item.getItemId()==R.id.menuCredits)
        {
            Intent i=new Intent(this,CreditsActivity.class);
            startActivity(i);
        }
        else if(item.getItemId()==R.id.updateMenu)
        {
            Intent i=new Intent(this,UpdateActivity.class);
            startActivity(i);
        }

        return true;
    }
}
