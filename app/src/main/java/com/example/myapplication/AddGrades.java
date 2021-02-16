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

public class AddGrades extends AppCompatActivity
{
    SQLiteDatabase db;
    HelperDB hlp;
    EditText student,quarter,subject,grade;

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
    }

    public void save(View view)
    {
        ContentValues cv = new ContentValues();

        cv.put(Grades.STUDENT, Integer.parseInt(student.getText().toString()));
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

        return true;
    }
}