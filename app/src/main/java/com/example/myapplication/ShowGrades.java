package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class ShowGrades extends AppCompatActivity
{
    EditText studentIdET;
    Cursor crsr;
    ArrayList<String> tbl;
    SQLiteDatabase db;
    ArrayAdapter<String> adp;
    ListView gradesList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_grades);

        tbl=new ArrayList<>();

        studentIdET=(EditText)findViewById(R.id.studentIdET);
    }

    public void update(View view)
    {
        int student=Integer.parseInt(studentIdET.getText().toString());

    }

    public void read()
    {
        crsr=db.query(Grades.TABLE_GRADES, null, null, null, null, null, null);
        int studentCol=crsr.getColumnIndex(Grades.STUDENT);
        int quarterCol=crsr.getColumnIndex(Grades.QUARTER);
        int subjectCol=crsr.getColumnIndex(Grades.SUBJECT);
        int gradeCol=crsr.getColumnIndex(Grades.GRADE);
        int fatherCol=crsr.getColumnIndex(Users.FATHER);
        int fatherPhoneCol=crsr.getColumnIndex(Users.FATHER_PHONE);
        int motherCol=crsr.getColumnIndex(Users.MOTHER);
        int motherPhoneCol=crsr.getColumnIndex(Users.MOTHER_PHONE);

        crsr.moveToFirst();
        while (!crsr.isAfterLast())
        {
            int student = crsr.getInt(studentCol);
            int quarter=crsr.getInt(quarterCol);
            int subject=crsr.getInt(subjectCol);
            String grade=crsr.getString(gradeCol);

            String tmp=student+",   "+quarter+",   "+subject+",   "+grade;
            tbl.add(tmp);
            crsr.moveToNext();
        }
        crsr.close();
        db.close();

        adp=new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, tbl);
        gradesList.setAdapter(adp);
    }
}
