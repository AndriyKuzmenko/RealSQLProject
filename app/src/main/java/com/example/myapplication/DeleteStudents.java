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
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.util.ArrayList;

public class DeleteStudents extends AppCompatActivity implements AdapterView.OnItemClickListener
{
    HelperDB hlp;
    SQLiteDatabase db;
    Cursor crsr;
    ArrayList<String> tbl;
    ArrayList<String> nameTBL;
    ListView studentsList;
    int position,id;
    TextView text;
    ArrayAdapter<String> adp;

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
        id=-1;
        text=(TextView)findViewById(R.id.text);
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
     * Reads all the data about all the students from SQL.
     */

    public void read()
    {
        tbl=new ArrayList<>();
        nameTBL=new ArrayList<>();

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

            String tmp=key+".,   "+name+",   "+address+",   "+phone+",   "+home+",   "+father+",   "+fatherPhone+",   "+mother+",   "+motherPhone;
            tbl.add(tmp);
            nameTBL.add(key+". "+name);
            crsr.moveToNext();
        }
        crsr.close();

        adp=new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, nameTBL);
        studentsList.setAdapter(adp);

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

        return true;
    }

    /**
     *
     * @param parent - The ListView
     * @param view - The item that was pressed
     * @param position - position of the item that was pressed
     * @param id - the line of the item
     */

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        this.position=position;

        String[] temp=((String)parent.getItemAtPosition(position)).split(" ");
        this.id=Integer.parseInt(temp[0].substring(0,temp[0].length()-1));
        String[] temp1;
        for (int i=0; i<tbl.size(); i++)
        {
            temp1=tbl.get(i).split(",   ");
            if(!temp[0].equals(temp1[0]))
            {
                continue;
            }
            text.setText("Name: "+temp1[1]+"\nAddrress: "+temp1[2]+"\nPhone number: "+temp1[3]+"\nHome phone: "+temp1[4]+"\nFather: "+temp1[5]+"\nFather's phone: "+temp1[6]+"\nMother: "+temp1[7]+"\nMother's phone: "+temp1[8]);
            break;
        }
    }

    /**
     * Deletes the student that was selected
     * @param view - the button that was pressed
     */

    public void delete(View view)
    {
        if(position==-1||id==-1) return;

        if(text.getText().toString().equals(""))
        {
            Toast toast=Toast.makeText(getApplicationContext(),"Please press on the student you want to delete",Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        db=hlp.getWritableDatabase();
        db.delete(Users.TABLE_USERS, Users.KEY_ID+"=?", new String[]{Integer.toString(id)});
        db.delete(Grades.TABLE_GRADES, Grades.STUDENT+"=?", new String[]{Integer.toString(id)});
        db.close();
        nameTBL.remove(position);
        tbl.remove(position);
        adp.notifyDataSetChanged();
        text.setText("");
    }
}