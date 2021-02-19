package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class UpdateActivity extends AppCompatActivity
{
    SQLiteDatabase db;
    HelperDB hlp;
    Cursor crsr;
    ArrayList<String> nameTBL;
    EditText nameUpdate, addressUpdate, phoneUpdate, homePhoneUpdate, fatherUpdate, fatherPhoneUpdate, motherUpdate, motherPhoneUpdate;
    int student;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();
        db.close();
        nameTBL=new ArrayList<>();

        nameUpdate=(EditText)findViewById(R.id.nameUpdate);
        addressUpdate=(EditText)findViewById(R.id.addressUpdate);
        phoneUpdate=(EditText)findViewById(R.id.phoneUpdate);
        homePhoneUpdate=(EditText)findViewById(R.id.homePhoneUpdate);
        fatherUpdate=(EditText)findViewById(R.id.fatherUpdate);
        fatherPhoneUpdate=(EditText)findViewById(R.id.fatherPhoneUpdate);
        motherUpdate=(EditText)findViewById(R.id.motherUpdate);
        motherPhoneUpdate=(EditText)findViewById(R.id.motherPhoneUpdate);

        read();
        student=-1;
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

            String tmp=key+",   "+name+",   "+address+",   "+phone+",   "+home+",   "+father+",   "+fatherPhone+",   "+mother+",   "+motherPhone;
            nameTBL.add(tmp);
            crsr.moveToNext();
        }
        crsr.close();
        db.close();
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
     * This method rund when findStudent button is pressed. It
     * searches for this student in the arrayList.
     * @param view - the button that was pressed
     */

    public void findStudentOnCick(View view)
    {
        student=findStudent(nameUpdate.getText().toString());

        if(student==-1)
        {
            Toast toast=Toast.makeText(getApplicationContext(),"The student that you entered doesn't exist",Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        String[] temp=nameTBL.get(student-1).split(",   ");
        addressUpdate.setText(temp[2]);
        phoneUpdate.setText(temp[3]);
        homePhoneUpdate.setText(temp[4]);
        fatherUpdate.setText(temp[5]);
        fatherPhoneUpdate.setText(temp[6]);
        motherUpdate.setText(temp[7]);
        motherPhoneUpdate.setText(temp[8]);
    }
}