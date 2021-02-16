package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Collections;

public class ShowGrades extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    EditText studentIdET, subject;
    Cursor crsr;
    ArrayList<String> tbl, showTBL;
    SQLiteDatabase db;
    ArrayAdapter<String> adp;
    ListView gradesList;
    int student;
    HelperDB hlp;
    int quarter;
    Spinner quarters;
    String[] qList={"All Quarters","Quarter 1","Quarter 2","Quarter 3","Quarter 4"};
    ToggleButton tb;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_grades);

        tbl=new ArrayList<>();

        studentIdET=(EditText)findViewById(R.id.studentIdET);
        gradesList=(ListView)findViewById(R.id.gradesList);
        quarters=(Spinner)findViewById(R.id.quarters);
        subject=(EditText)findViewById(R.id.subject);
        tb=(ToggleButton)findViewById(R.id.tb);

        hlp=new HelperDB(this);
        db=hlp.getWritableDatabase();

        read();
        quarter=-1;

        ArrayAdapter<String> adp = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,qList);
        quarters.setAdapter(adp);

        quarters.setOnItemSelectedListener(this);
    }

    /**
     * Runs when "Show Grades" button is pressed. Shows the grades of the specified student.
     * @param view
     */

    public void update(View view)
    {
        student=Integer.parseInt(studentIdET.getText().toString());
        showTBL=new ArrayList<>();

        for(int i=0; i<tbl.size(); i++)
        {
            int temp=Integer.parseInt(tbl.get(i).split(",   ")[0]);
            if(temp!=student)continue;

            int q=Integer.parseInt(tbl.get(i).split(",   ")[1]);
            if(quarter!=-1 && q!=quarter) continue;

            String[] t=tbl.get(i).split(",   ");

            if(!(t[2].equals(subject.getText().toString())||subject.getText().toString().equals(""))) continue;
            showTBL.add(t[2]+": "+t[3]);
        }

        sort(showTBL,!tb.isChecked());

        adp=new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, showTBL);
        gradesList.setAdapter(adp);
    }

    /**
     * Reads all the grades from the SQL file and puts them in an arrayList.
     */

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
        else if(item.getItemId()==R.id.menuCredits)
        {
            Intent i=new Intent(this,CreditsActivity.class);
            startActivity(i);
        }

        return true;
    }

    /**
     * Saves information about the item that was pressed in a variable.
     * @param parent - The spinner
     * @param view - The item that was pressed
     * @param position - The position of the item
     * @param id - The line of the item
     */

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        if(position==0)
        {
            quarter=-1;
            return;
        }
        quarter=position;
    }

    /**
     * Runs if nothing is selected
     * @param parent - The spinner
     */

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }

    /**
     *
     * @param a - the ArrayList
     * @param b - a boolean variable that tells in what order to sort. true->Ascending   false->Descending
     */

    public void sort(ArrayList<String> a, boolean b)
    {
        if(b)
        {
            for(int i=0;i<a.size()-1;i++)
            {
                for(int j=0;j<a.size()-1;j++)
                {
                    int x1 = Integer.parseInt(a.get(j).split(": ")[1]);
                    int x2 = Integer.parseInt(a.get(j+1).split(": ")[1]);
                    if (x1>x2)
                    {
                        Collections.swap(a,j,j+1);
                    }
                }
            }
        }
        else
        {
            for(int i=0;i<a.size()-1;i++)
            {
                for(int j=0;j<a.size()-1;j++)
                {
                    int x1 = Integer.parseInt(a.get(j).split(": ")[1]);
                    int x2 = Integer.parseInt(a.get(j+1).split(": ")[1]);
                    if (x1<x2)
                    {
                        Collections.swap(a,j,j+1);
                    }
                }
            }
        }
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
}
