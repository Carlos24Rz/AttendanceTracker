package com.example.attendancetracker;

public class StudentModel {

    private int mId;
    private String mStudent;
    private int mBus;
    private String mDate;
    private int mMorning;
    private int mAfternoon;

    public void setId(int Id)
    {
        mId = Id;
    }

    public int getId()
    {
        return mId;
    }

    public void setStudent(String Student)
    {
        mStudent = Student;
    }

    String getStudent()
    {
        return mStudent;
    }

    public void setBus(int Bus)
    {
        mBus = Bus;
    }

    public int getBus()
    {
        return mBus;
    }

    public void setDate(String date)
    {
        mDate = date;
    }

    public String getDate()
    {
        return mDate;
    }

    public void setMorningValue (int morningValue)
    {
        mMorning = morningValue;
    }

    int getMorningValue()
    {
        return mMorning;
    }

    public void setAfternoonValue (int afternoonValue)
    {
        mAfternoon = afternoonValue;
    }

    int getAfternoonValue()
    {
        return mAfternoon;
    }





}
