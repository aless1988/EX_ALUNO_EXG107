package com.example.alessandro.student;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Editable;


/**
 * Created by Alessandro on 5/26/16.
 */
public class StudentHelperDb {

    private final Student context;
    private final static String NAMEDATABASE = "student.db";
    private SQLiteDatabase db;
    private  static Integer[] ids;


    public StudentHelperDb(Student context) {this.context = context; }

    public void open() {

        this.db = context.getBaseContext().openOrCreateDatabase(NAMEDATABASE, Context.MODE_WORLD_WRITEABLE, null);

        this.createTable();
    }

    private void createTable(){

        String sql = "CREATE TABLE IF NOT EXISTS Student ("+
                "Id integer primary key autoincrement, " +
                "Code text not null," +
                "Name text not null," +
                "Email text not null);";
        this.db.execSQL(sql);
    }

    public void removeAllStudents (){

        String sql = "DROP TABLE IF EXISTS Student;";
        this.db.execSQL(sql);

        this.createTable();
    }


    public void insertStudent(String code, String name, String email) {

        ContentValues initialValues = new ContentValues();
        initialValues.put("Code", code);
        initialValues.put("Name", name);
        initialValues.put("Email", email);

        db.insert("Student",null, initialValues);
    }

    public String[] getListStudents(){

        String[] fields = {"Name", "Id", "Email"};
        Cursor ret = db.query("Student", fields, null, null, null, null, null);

        String[] values = new String[ret.getCount()];
        ids = new Integer[ret.getCount()];

        boolean hasRecord = ret.moveToFirst();
        int pos = 0;


        while ( hasRecord ) {
            ids[pos] = ret.getInt(ret.getColumnIndex("Id"));
            values[pos++] = ret.getString(ret.getColumnIndex("Name"));

            hasRecord = ret.moveToNext();
        }

        return values;
    }

    public static Integer getIdStudent(int position) {return ids[position]; }

    public Cursor getStudent (int id) {
        String[] fields = {"Id", "Code", "Name", "Email"};
        Cursor ret = db.query("Student", fields, "Id = " + id, null,null,null,null);

        ret.moveToFirst();

        return ret;

    }

    public void updateStudent (String id, String code, String name, String email) {

        ContentValues values = new ContentValues();
        values.put("Code", code);
        values.put("Name", name);
        values.put("Email", email);

        db.update("Student", values, "Id = ? ", new String[]{id});



    }

    public void deleteStudent(String id) { db.delete("Student", "Id = ? ", new String[]{id});}

}
