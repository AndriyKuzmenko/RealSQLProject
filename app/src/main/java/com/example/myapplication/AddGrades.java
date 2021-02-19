package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class AddGrades extends AppCompatActivity
{
    SQLiteDatabase db;
    HelperDB hlp;
    EditText student,quarter,subject,grade;
    Cursor crsr;
    ArrayList<String> nameTBL;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grades);

        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();
        db.close();

        student=(EditText)findViewById(R.id.student);
        quarter=(EditText)findViewById(R.id.quarter);
        subject=(EditText)findViewById(R.id.subject);
        grade=(EditText)findViewById(R.id.grade);

        nameTBL=new ArrayList<>();
        read();
    }

    /**
     * Saves the grade.
     * @param view - the button that was pressed
     */

    public void save(View view)
    {
        ContentValues cv = new ContentValues();

        int id=findStudent(student.getText().toString());
        if(id==-1)
        {
            Toast toast=Toast.makeText(getApplicationContext(),"The student that you entered doesn't exist",Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        cv.put(Grades.STUDENT, id);
        cv.put(Grades.QUARTER, Integer.parseInt(quarter.getText().toString()));
        cv.put(Grades.SUBJECT, subject.getText().toString());
        cv.put(Grades.GRADE, Integer.parseInt(grade.getText().toString()));

        db = hlp.getWritableDatabase();

        db.insert(Grades.TABLE_GRADES, null, cv);

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
        else if(item.getItemId()==R.id.menuMain)
        {
            Intent i=new Intent(this,MainActivity.class);
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

        return true;
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
     * This method gets the name of a student and returns his id.
     *
     * @param name - the name of the student
     * @return - the student's id
     */

    public int findStudent(String name)
    {
        for(int i=0; i<nameTBL.size(); i++)
        {
            String[] temp=nameTBL.get(i).split(",   ");
            if(!temp[1].equals(name)) continue;
            int id=Integer.parseInt(temp[0]);
            return id;
        }

        return -1;
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