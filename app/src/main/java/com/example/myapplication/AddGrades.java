package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class AddGrades extends AppCompatActivity
{
    EditText student,address,subject,grade;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grades);

        student=(EditText)findViewById(R.id.student);
        address=(EditText)findViewById(R.id.address);
        subject=(EditText)findViewById(R.id.subject);
        grade=(EditText)findViewById(R.id.grade);
    }
}
