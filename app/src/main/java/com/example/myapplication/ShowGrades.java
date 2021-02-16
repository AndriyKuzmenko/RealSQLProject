package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class ShowGrades extends AppCompatActivity
{
    EditText studentIdET;
    Cursor crsr;
    ArrayList<String> tbl, showTBL;
    SQLiteDatabase db;
    ArrayAdapter<String> adp;
    ListView gradesList;
    int student;
    HelperDB hlp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_grades);

        tbl=new ArrayList<>();

        studentIdET=(EditText)findViewById(R.id.studentIdET);
        gradesList=(ListView)findViewById(R.id.gradesList);

        hlp=new HelperDB(this);
        db=hlp.getWritableDatabase();

        read();
    }

    public void update(View view)
    {
        student=Integer.parseInt(studentIdET.getText().toString());
        showTBL=new ArrayList<>();

        for(int i=0; i<tbl.size(); i++)
        {
            int temp=Integer.parseInt(tbl.get(i).split(",   ")[0]);
            if(temp!=student)continue;
            String[] t=tbl.get(i).split(",   ");
            showTBL.add(t[2]+". "+t[3]);
        }
        adp=new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, showTBL);
        gradesList.setAdapter(adp);
    }

    public void read()
    {
        crsr=db.query(Grades.TABLE_GRADES, null, null, null, null, null, null);
        int studentCol=crsr.getColumnIndex(Grades.STUDENT);
        int quarterCol=crsr.getColumnIndex(Grades.QUARTER);
        int subjectCol=crsr.getColumnIndex(Grades.SUBJECT);
        int gradeCol=crsr.getColumnIndex(Grades.GRADE);

        crsr.moveToFirst();
        while (!crsr.isAfterLast())
        {
            int student = crsr.getInt(studentCol);
            int quarter=crsr.getInt(quarterCol);
            String subject=crsr.getString(subjectCol);
            int grade=crsr.getInt(gradeCol);

            String tmp=student+",   "+quarter+",   "+subject+",   "+grade;
            tbl.add(tmp);
            crsr.moveToNext();
        }
        crsr.close();
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
        else if(item.getItemId()==R.id.menuMain)
        {
            Intent i=new Intent(this,MainActivity.class);
            startActivity(i);
        }

        return true;
    }
}
