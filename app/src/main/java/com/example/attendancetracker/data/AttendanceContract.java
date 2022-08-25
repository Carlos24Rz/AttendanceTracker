package com.example.attendancetracker.data;

import android.provider.BaseColumns;

public final class AttendanceContract {

    private AttendanceContract()
    {

    }

    public static class AttendanceEntry implements BaseColumns
    {
        static final String TABLE_STUDENT = "Student";
        static final String TABLE_ATTENDANCE = "Attendance";
        static final String _ID = BaseColumns._ID;

        static final String COLUMN_NAME_STUDENT = "StudentName";
        static final String COLUMN_NAME_BUS = "BusRoute";

        static final String COLUMN_ATTENDANCE_ID = "AttendanceID";
        static final String COLUMN_STUDENT_ID = "StudentID";
        static final String COLUMN_NAME_DATE = "DatabaseDate";
        static final String COLUMN_NAME_MORNING = "Morning";
        static final String COLUMN_NAME_AFTERNOON = "Afternoon";

        public static final int ATTENDANCE_PRESENT = 1;
        public static final int ATTENDANCE_ABSENT = 0;
        public static final int ATTENDANCE_NOT_PRESSED = 3;
        public static final int BUS_ROUTE_ONE = 1;
        public static final int BUS_ROUTE_TWO = 2;
        public static final int BUS_ROUTE_THREE = 3;
        public static final int BUS_ROUTE_FOUR = 4;
        public static final int BUS_ROUTE_FIVE = 5;
        public static final int BUS_ROUTE_SIX = 6;
        public static final int BUS_ROUTE_SEVEN = 7;
        public static final int BUS_ROUTE_EIGHT = 8;
        public static final int BUS_ROUTE_NINE = 9;
        public static final int BUS_ROUTE_TEN = 10;
    }
}
