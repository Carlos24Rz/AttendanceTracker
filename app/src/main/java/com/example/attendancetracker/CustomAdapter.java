package com.example.attendancetracker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.attendancetracker.data.AttendanceContract.AttendanceEntry;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<StudentModel> studentModelArrayList;
    private ArrayList<StudentModel> studentAttendanceModelArrayList;


    CustomAdapter(Context mContext, ArrayList<StudentModel> studentModelArrayList, ArrayList<StudentModel> studentAttendanceModelArrayList) {
        this.mContext = mContext;
        this.studentModelArrayList = studentModelArrayList;
        this.studentAttendanceModelArrayList = studentAttendanceModelArrayList;
    }

    CustomAdapter(Context mContext, ArrayList<StudentModel> studentModelArrayList)
    {
        this.mContext = mContext;
        this.studentModelArrayList = studentModelArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_item, null);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {

        StudentModel studentModel = studentModelArrayList.get(i);

        if ( studentAttendanceModelArrayList == null)
        {
            myViewHolder.mStudentName.setText(studentModel.getStudent());
        }
        else
        {
            if ( studentAttendanceModelArrayList.isEmpty()) {

                myViewHolder.mStudentName.setText(studentModel.getStudent());

            }
            else
                {
                    if (studentAttendanceModelArrayList.size() == 0)
                    {
                        myViewHolder.mStudentName.setText(studentModel.getStudent());
                    }

                    else
                    {


                        StudentModel studentAttendanceModel = studentAttendanceModelArrayList.get(i);

                        int valueMorning = studentAttendanceModel.getMorningValue();
                        int valueAfternoon = studentAttendanceModel.getAfternoonValue();

                        Log.v("CUSTOM ADAPTER: ", "Morning for i = " + i + " : " + studentAttendanceModel.getMorningValue());
                        Log.v("CUSTOM ADAPTER: ", "Afternoon for i = " + i + " : " +  studentAttendanceModel.getAfternoonValue());

                        myViewHolder.mStudentName.setText(studentModel.getStudent());
                        if (valueMorning == AttendanceEntry.ATTENDANCE_PRESENT) {
                            myViewHolder.mMorning.check(R.id.morning_present);
                        } else if (valueMorning == AttendanceEntry.ATTENDANCE_ABSENT) {
                            myViewHolder.mMorning.check(R.id.morning_absent);
                        }
                        else if (valueMorning == AttendanceEntry.ATTENDANCE_NOT_PRESSED )
                        {
                            myViewHolder.mMorning.clearCheck();
                        }

                        if (valueAfternoon == AttendanceEntry.ATTENDANCE_PRESENT) {
                            myViewHolder.mAfternoon.check(R.id.afternoon_present);
                        }
                        else if (valueAfternoon == AttendanceEntry.ATTENDANCE_ABSENT){
                            myViewHolder.mAfternoon.check(R.id.afternoon_absent);
                        }
                        else if (valueAfternoon == AttendanceEntry.ATTENDANCE_NOT_PRESSED)
                        {
                            myViewHolder.mAfternoon.clearCheck();
                        }
                    }
            }
        }



    }

    @Override
    public int getItemCount() {
        return studentModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView mStudentName;
        RadioGroup mMorning;
        RadioGroup mAfternoon;


        MyViewHolder(View itemView) {
            super(itemView);

            mStudentName = itemView.findViewById(R.id.student_name_text_view);
            mMorning = itemView.findViewById(R.id.radio_group_morning);
            mAfternoon = itemView.findViewById(R.id.radio_group_afternoon);
        }
    }
}
