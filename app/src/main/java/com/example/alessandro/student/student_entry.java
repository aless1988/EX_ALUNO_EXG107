package com.example.alessandro.student;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class student_entry extends AppCompatActivity {

    private int id;
    private boolean alterado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_entry);

        if (this.getIntent().hasExtra("ID")) {
            alterado = true;
            this.id = this.getIntent().getIntExtra("ID", -1);

            this.showFields(id);
        } else {
            alterado = false;
        }


        Button btnSave = (Button) findViewById(R.id.salvar);
        Button btnDelete = (Button) findViewById(R.id.deletar);

        if (alterado == true) {
            btnDelete.setVisibility(View.VISIBLE);
        }else{
            btnDelete.setVisibility(View.INVISIBLE);
        }

        btnSave.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v){

                EditText idField = (EditText) findViewById(R.id.id);
                EditText code = (EditText) findViewById(R.id.code);
                EditText name = (EditText) findViewById(R.id.name);
                EditText email = (EditText) findViewById(R.id.email);

                if (!isFildEmppty(code, name, email)) {

                saveRecord(code, name, email, idField);

                finish();
            }

        }

    private void saveRecord(EditText code, EditText name, EditText email, EditText idField) {

        if (TextUtils.isEmpty(idField.getText())) {

            Student.getStudentDB().insertStudent(code.getText().toString(), name.getText().toString(), email.getText().toString());
        } else {
            Student.getStudentDB().updateStudent(idField.getText().toString(), code.getText().toString(), name.getText().toString(), email.getText().toString());
        }

        }

    private boolean isFildEmppty(EditText... fields) {
        boolean empty = false;

        for (EditText field : fields) {

            if (TextUtils.isEmpty(field.getText().toString())) {
                empty = true;
                field.setError("campo deve ser preenchido");
            }
        }

        return empty;
    }

    });

        btnDelete.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText idField = (EditText) findViewById(R.id.id);

                Student.getStudentDB().deleteStudent(idField.getText().toString());
                finish();
            }


        });


}

    private void showFields (int id) {
        Cursor student = Student.getStudentDB().getStudent(id);

        EditText idField = (EditText) findViewById(R.id.id);
        EditText code = (EditText) findViewById(R.id.code);
        EditText name = (EditText) findViewById(R.id.name);
        EditText email = (EditText) findViewById(R.id.email);

        idField.setText(new Integer(id).toString());
        code.setText(student.getString(student.getColumnIndex("Code")));
        name.setText(student.getString(student.getColumnIndex("Name")));
        email.setText(student.getString(student.getColumnIndex("Email")));
    }



}
