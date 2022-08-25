package com.example.attendancetracker.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.attendancetracker.StudentModel;
import com.example.attendancetracker.data.AttendanceContract.AttendanceEntry;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "BusAttendance.db";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_STUDENT_TABLE = "CREATE TABLE " + AttendanceEntry.TABLE_STUDENT + "("
            + AttendanceEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + AttendanceEntry.COLUMN_NAME_STUDENT + " TEXT NOT NULL, "
            + AttendanceEntry.COLUMN_NAME_BUS + " INTEGER NOT NULL );";
    private static final String CREATE_ATTENDANCE_TABLE = "CREATE TABLE " + AttendanceEntry.TABLE_ATTENDANCE + "("
            + AttendanceEntry.COLUMN_ATTENDANCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + AttendanceEntry.COLUMN_STUDENT_ID + " INTEGER NOT NULL, "
            + AttendanceEntry.COLUMN_NAME_BUS + " INTEGER NOT NULL, "
            + AttendanceEntry.COLUMN_NAME_DATE + " TEXT , "
            + AttendanceEntry.COLUMN_NAME_MORNING + " INTEGER , "
            + AttendanceEntry.COLUMN_NAME_AFTERNOON + " INTEGER);";

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_STUDENT_TABLE);
        db.execSQL(CREATE_ATTENDANCE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
     //Database has not been updated
    }

    public void AddStudent(String StudentName, int BusRoute)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AttendanceEntry.COLUMN_NAME_STUDENT, StudentName);
        values.put(AttendanceEntry.COLUMN_NAME_BUS, BusRoute);
        db.insert(AttendanceEntry.TABLE_STUDENT, null, values);


    }

    public void InitializeAttendance(int studentID, int busRoute, String date, int morning, int afternoon)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AttendanceEntry.COLUMN_STUDENT_ID, studentID);
        values.put(AttendanceEntry.COLUMN_NAME_BUS, busRoute);
        values.put(AttendanceEntry.COLUMN_NAME_DATE, date);
        values.put(AttendanceEntry.COLUMN_NAME_MORNING, morning);
        values.put(AttendanceEntry.COLUMN_NAME_AFTERNOON, afternoon);
        db.insert(AttendanceEntry.TABLE_ATTENDANCE, null, values);
    }

    public ArrayList<StudentModel> getBusRouteStudents(int busRoute)
 {
        ArrayList<StudentModel> studentModelArrayList = new ArrayList<StudentModel>();
        String selectQuery = "SELECT * FROM " + AttendanceEntry.TABLE_STUDENT + " WHERE " + AttendanceEntry.COLUMN_NAME_BUS + " = " + busRoute;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        //loop all rows
        if (cursor.moveToFirst()) {

            do {
                StudentModel studentModel = new StudentModel();
                studentModel.setId(cursor.getInt(cursor.getColumnIndex(AttendanceEntry._ID)));
                studentModel.setStudent(cursor.getString(cursor.getColumnIndex(AttendanceEntry.COLUMN_NAME_STUDENT)));
                studentModel.setBus(cursor.getInt(cursor.getColumnIndex(AttendanceEntry.COLUMN_NAME_BUS)));

                studentModelArrayList.add(studentModel);

            } while (cursor.moveToNext());

        }
        cursor.close();
        return studentModelArrayList;


    }

    public void registerStudentsAttendance(int studentID, int busRoute, String date, int morning, int afternoon)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AttendanceEntry.COLUMN_STUDENT_ID, studentID);
        values.put(AttendanceEntry.COLUMN_NAME_BUS, busRoute);
        values.put(AttendanceEntry.COLUMN_NAME_DATE, date);
        values.put(AttendanceEntry.COLUMN_NAME_MORNING, morning);
        values.put(AttendanceEntry.COLUMN_NAME_AFTERNOON, afternoon);
        db.insert(AttendanceEntry.TABLE_ATTENDANCE, null, values);
    }

    public boolean checkIfDataAlreadyExists(int studentID, String date,int busRoute)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + AttendanceEntry.TABLE_ATTENDANCE + " WHERE " + AttendanceEntry.COLUMN_STUDENT_ID + " = " + studentID + " AND " + AttendanceEntry.COLUMN_NAME_BUS + " = " + busRoute + " AND " + AttendanceEntry.COLUMN_NAME_DATE + " = " + date;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() > 0)
        {
            cursor.close();
            return true;
        }
        else
        {
            cursor.close();
            return false;
        }

    }

    public void updateRow(int morning, int afternoon, int studentID, String date)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AttendanceEntry.COLUMN_NAME_MORNING, morning);
        values.put(AttendanceEntry.COLUMN_NAME_AFTERNOON, afternoon);
        db.update(AttendanceEntry.TABLE_ATTENDANCE,
                values,
                AttendanceEntry.COLUMN_STUDENT_ID + " = ? AND " + AttendanceEntry.COLUMN_NAME_DATE + " = ? ",
                new String[]{Integer.toString(studentID), date});

    }

    public ArrayList<StudentModel> getStudentAttendanceInformation(int busRoute, String date)
    {
        ArrayList<StudentModel> studentModelArrayList = new ArrayList<StudentModel>();
        String selectQuery = "SELECT * FROM " + AttendanceEntry.TABLE_ATTENDANCE + " WHERE " + AttendanceEntry.COLUMN_NAME_BUS + " = " + busRoute + " AND " + AttendanceEntry.COLUMN_NAME_DATE + " = " + date;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        //loop all rows
        if (cursor.moveToFirst())
        {
            do {
                StudentModel studentModel = new StudentModel();
                studentModel.setId(cursor.getInt(cursor.getColumnIndex(AttendanceEntry.COLUMN_STUDENT_ID)));
                studentModel.setBus(cursor.getInt(cursor.getColumnIndex(AttendanceEntry.COLUMN_NAME_BUS)));
                studentModel.setDate(cursor.getString(cursor.getColumnIndex(AttendanceEntry.COLUMN_NAME_DATE)));
                studentModel.setMorningValue(cursor.getInt(cursor.getColumnIndex(AttendanceEntry.COLUMN_NAME_MORNING)));
                studentModel.setAfternoonValue(cursor.getInt(cursor.getColumnIndex(AttendanceEntry.COLUMN_NAME_AFTERNOON)));

                studentModelArrayList.add(studentModel);

            } while (cursor.moveToNext());
        }

        return studentModelArrayList;
    }


}
