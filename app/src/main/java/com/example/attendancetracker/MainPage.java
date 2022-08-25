package com.example.attendancetracker;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.attendancetracker.data.AttendanceContract.AttendanceEntry;

public class MainPage extends AppCompatActivity {


    private CardView mTrack;
    private Dialog mBusDialog;
    private Spinner mBusSpinner;

    private int mBusNumber = 0;
    public static final String BUS_ROUTE = "Bus Route";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        CardView mAttendance = findViewById(R.id.cardView);
        mTrack = findViewById(R.id.cardView2);
        mBusDialog = new Dialog(this);

        mAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ShowBusSelectionPopup();

            }
        });

    }

    private void ShowBusSelectionPopup() {

        mBusDialog.setContentView(R.layout.activity_bus_selector);
        ImageView mCloseBusDialog = mBusDialog.findViewById(R.id.close_image_view);
        mBusSpinner = mBusDialog.findViewById(R.id.spinner);
        setBusSpinner();
        Button mDoneBusSelection = mBusDialog.findViewById(R.id.done_button);

        mCloseBusDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBusDialog.dismiss();
            }
        });

        mDoneBusSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OpenBusAttendance();
            }
        });

        mBusDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mBusDialog.show();

    }

    private void OpenBusAttendance() {

        Intent intent = new Intent(this, AttendanceActivity.class);
        intent.putExtra(BUS_ROUTE, mBusNumber);
        startActivity(intent);
        mBusDialog.dismiss();

    }

    private void setBusSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.bus_route_array, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mBusSpinner.setAdapter(adapter);

        mBusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String number = parent.getItemAtPosition(position).toString();
                if (!TextUtils.isEmpty(number)) {
                    if (number.equals(getString(R.string.bus_one))) {
                        mBusNumber = AttendanceEntry.BUS_ROUTE_ONE;
                    } else if (number.equals(getString(R.string.bus_two))) {
                        mBusNumber = AttendanceEntry.BUS_ROUTE_TWO;
                    } else if (number.equals(getString(R.string.bus_three))) {
                        mBusNumber = AttendanceEntry.BUS_ROUTE_THREE;
                    } else if (number.equals(getString(R.string.bus_four))) {
                        mBusNumber = AttendanceEntry.BUS_ROUTE_FOUR;
                    } else if (number.equals(getString(R.string.bus_five))) {
                        mBusNumber = AttendanceEntry.BUS_ROUTE_FIVE;
                    } else if (number.equals(getString(R.string.bus_six))) {
                        mBusNumber = AttendanceEntry.BUS_ROUTE_SIX;
                    } else if (number.equals(getString(R.string.bus_seven))) {
                        mBusNumber = AttendanceEntry.BUS_ROUTE_SEVEN;
                    } else if (number.equals(getString(R.string.bus_eight))) {
                        mBusNumber = AttendanceEntry.BUS_ROUTE_EIGHT;
                    } else if (number.equals(getString(R.string.bus_nine))) {
                        mBusNumber = AttendanceEntry.BUS_ROUTE_NINE;
                    } else if (number.equals(getString(R.string.bus_ten))) {
                        mBusNumber = AttendanceEntry.BUS_ROUTE_TEN;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                mBusNumber = 0;

            }
        });

    }
}
