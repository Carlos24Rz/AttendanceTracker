package com.example.attendancetracker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendancetracker.data.AttendanceContract.AttendanceEntry;
import com.example.attendancetracker.data.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class AttendanceActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private DatabaseHelper mDatabaseHelper;
    private ArrayList<StudentModel> mStudentModelArrayList;

    private RecyclerView mRecyclerView;

    private int mBusNumber;
    private String mDataBaseDate;
    private String mMonth;
    private int mDatabaseMonth;
    private int mDay;
    private int mYear;
    private String mFullDate;

    private TextView mDate;
    private EditText mFirstName;
    private EditText mLastName;
    private Dialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_list);

        Intent mIntent = getIntent();
        mBusNumber = mIntent.getIntExtra(MainPage.BUS_ROUTE, 0);
        FloatingActionButton mAddStudent = findViewById(R.id.add_student);
        FloatingActionButton mRegisterAttendance = findViewById(R.id.register_attendance);
        mDialog = new Dialog(this);

        createActionBar();


        displayStudentInfo(mBusNumber);

        setDate();


        mAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddStudentEditor();
            }
        });



        mRegisterAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerAttendance();
            }
        });

    }

    private void createActionBar() {
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);

        View view = getSupportActionBar().getCustomView();
        ImageButton mBackButton = view.findViewById(R.id.back_button);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainPage.class);
                startActivity(intent);
                finish();
            }
        });

    }
    private void setDate()
    {
        Calendar mCalendar = Calendar.getInstance();
        mDatabaseMonth = mCalendar.get(Calendar.MONTH) + 1;
        mMonth = new SimpleDateFormat("MMMM").format(mCalendar.getTime());
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH);
        mYear = mCalendar.get(Calendar.YEAR);

        mDataBaseDate = Integer.toString(mDatabaseMonth) + mDay + mYear;
        mFullDate = mMonth + " " + mDay + ", " + mYear;

        mDate = findViewById(R.id.datePicker);

        mDate.setText(mFullDate);

        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        displayAttendanceInfo();

    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mYear = year;
        mDatabaseMonth = month + 1;
        mDay = dayOfMonth;
        if (month == 0)
        {
            mMonth = getString(R.string.month_january);
        } else if (month == 1)
        {
            mMonth = getString(R.string.month_february);
        } else if (month == 2)
        {
            mMonth = getString(R.string.month_march);
        }else if (month == 3)
        {
            mMonth = getString(R.string.month_april);
        }else if (month == 4)
        {
            mMonth = getString(R.string.month_may);
        }else if (month == 5)
        {
            mMonth = getString(R.string.month_june);
        }else if (month == 6)
        {
            mMonth = getString(R.string.month_july);
        }else if (month == 7)
        {
            mMonth = getString(R.string.month_august);
        }else if (month == 8)
        {
            mMonth = getString(R.string.month_september);
        }else if (month == 9)
        {
            mMonth = getString(R.string.month_october);
        }else if (month == 10)
        {
            mMonth = getString(R.string.month_november);
        }else if (month == 11)
        {
            mMonth = getString(R.string.month_december);
        }

        mDataBaseDate = Integer.toString(mDatabaseMonth) + mDay + mYear;
        mFullDate = mMonth + " " + mDay + ", " + mYear;
        mDate.setText(mFullDate);

        displayAttendanceInfo();


    }

    private void displayStudentInfo (int busRoute) {

        mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mDatabaseHelper = new DatabaseHelper(this);


        mStudentModelArrayList = mDatabaseHelper.getBusRouteStudents(busRoute);
        Log.v("Attendance 1", "Number of Students: " + mStudentModelArrayList.size());


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        CustomAdapter mCustomAdapter = new CustomAdapter(this, mStudentModelArrayList);
        mRecyclerView.setAdapter(mCustomAdapter);


    }

    private void showAddStudentEditor() {

        mDialog.setContentView(R.layout.activity_student_editor);
        ImageView closeAddDialog = mDialog.findViewById(R.id.add_close_image_view);
        mFirstName = mDialog.findViewById(R.id.first_name_edit_text);
        mLastName = mDialog.findViewById(R.id.last_name_edit_text);
        Button doneAddStudent = mDialog.findViewById(R.id.add_done_button);
        
        closeAddDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        doneAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertStudent();
            }
        });
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.show();
    }

    private void insertStudent() {
        String StudentName = mFirstName.getText().toString().trim() + " " + mLastName.getText().toString().trim();

        mDatabaseHelper = new DatabaseHelper(this);
        mDatabaseHelper.AddStudent(StudentName, mBusNumber);

        mDialog.dismiss();

        displayStudentInfo(mBusNumber);

        for (int i = 0; i < mRecyclerView.getAdapter().getItemCount(); i++) {
            StudentModel studentModel = mStudentModelArrayList.get(i);
            int studentID = studentModel.getId();

            boolean value = mDatabaseHelper.checkIfDataAlreadyExists(studentID, mDataBaseDate, mBusNumber);

            if ( value == false)
            {
                mDatabaseHelper.InitializeAttendance(studentID, mBusNumber, mDataBaseDate, AttendanceEntry.ATTENDANCE_NOT_PRESSED, AttendanceEntry.ATTENDANCE_NOT_PRESSED);
            }

            displayAttendanceInfo();

        }
    }

    private void registerAttendance()
    {

        mDatabaseHelper = new DatabaseHelper(this);
        mStudentModelArrayList = mDatabaseHelper.getBusRouteStudents(mBusNumber);
        Log.v("Attendance", "Number of Items: " + mStudentModelArrayList.size());


        for (int i = 0; i < mRecyclerView.getAdapter().getItemCount(); i++)
        {
            StudentModel studentModel = mStudentModelArrayList.get(i);
            int studentID = studentModel.getId();

            RadioGroup mMorning = mRecyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.radio_group_morning);
            RadioGroup mAfternoon = mRecyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.radio_group_afternoon);

            int idMorning = mMorning.getCheckedRadioButtonId();
            int idAfternoon = mAfternoon.getCheckedRadioButtonId();
            int morningValue;
            int afternoonValue;

            switch (idMorning){
                case R.id.morning_present:
                    morningValue = AttendanceEntry.ATTENDANCE_PRESENT;
                    break;
                case R.id.morning_absent:
                    morningValue = AttendanceEntry.ATTENDANCE_ABSENT;
                    break;
                default:
                    morningValue = AttendanceEntry.ATTENDANCE_NOT_PRESSED;
                    break;


            }
            switch (idAfternoon){
                case R.id.afternoon_present:
                    afternoonValue = AttendanceEntry.ATTENDANCE_PRESENT;
                    break;
                case R.id.afternoon_absent:
                    afternoonValue = AttendanceEntry.ATTENDANCE_ABSENT;
                    break;
                default:
                    afternoonValue = AttendanceEntry.ATTENDANCE_NOT_PRESSED ;
                    break;
            }

            boolean value = mDatabaseHelper.checkIfDataAlreadyExists(studentID, mDataBaseDate, mBusNumber);

            if (value)
            {
                Log.v("Register Attendance", "Value is TRUE");
                mDatabaseHelper.updateRow(morningValue, afternoonValue, studentID, mDataBaseDate);
            }
            else
            {
                Log.v("Register Attendance", "Value is FALSE");
                mDatabaseHelper.registerStudentsAttendance(studentID, mBusNumber, mDataBaseDate, morningValue, afternoonValue);
            }

        }

        displayAttendanceInfo();

    }

    private void displayAttendanceInfo() {

        mDatabaseHelper = new DatabaseHelper(this);

        ArrayList<StudentModel> mStudentAttendanceArrayList = mDatabaseHelper.getStudentAttendanceInformation(mBusNumber, mDataBaseDate);

        for (int i = 0; i < mRecyclerView.getAdapter().getItemCount(); i++) {

            mRecyclerView = findViewById(R.id.my_recycler_view);
            mRecyclerView.setHasFixedSize(true);

            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            CustomAdapter mCustomAdapter = new CustomAdapter(this, mStudentModelArrayList, mStudentAttendanceArrayList);
            mRecyclerView.setAdapter(mCustomAdapter);

        }

    }

}


