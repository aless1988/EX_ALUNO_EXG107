package com.example.alessandro.student;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Student extends AppCompatActivity {

    private static final int RETURNCODE = 200;
    private static StudentHelperDb studentDB;
    private ListView listStudent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        this.studentDB = new StudentHelperDb(this);
        this.studentDB.open();

        this.showStudents();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);

        menu.add(0, 0, 0, "Incluir");

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);

        switch (item.getItemId()){
            case 0:
                Intent i = new Intent(this, student_entry.class);
                startActivityForResult(i, RETURNCODE);
                return true;
        }

        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        this.showStudents();
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void showStudents() {

        listStudent = (ListView)findViewById(R.id.student_view);

        String[] values = this.studentDB.getListStudents();

        ArrayAdapter<String> studentsData = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);


        listStudent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Integer idStudent = StudentHelperDb.getIdStudent(position);

                Intent i = new Intent(view.getContext(),student_entry.class);
                i.putExtra("ID", idStudent);

                startActivityForResult(i,RETURNCODE);
            }
        });


        listStudent.setAdapter(studentsData);

    }

    public static StudentHelperDb getStudentDB(){

        return studentDB;
    }


}
