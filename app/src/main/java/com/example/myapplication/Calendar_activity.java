package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.*;


public class Calendar_activity extends AppCompatActivity {
    private java.util.Calendar current;
    private String current_date;
    private SimpleDateFormat dateFormat;
    private int current_day;
    private int current_month;
    private int current_year;
    private int current_hour;
    private int current_min;
    public static int database_number=1;
    public String meeting_id;
    public String meeting_name;


    private TextView time_text;
    private TextView end_time_text;
    private TextView whenDate;
    public String final_date;
    public String time_start;
    public String time_end;
    public int hour,min,hour2,min2;
    boolean sameday = false;



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_activity);
        final Bundle bundle = getIntent().getExtras();
        meeting_id = bundle.getString("meeting_id");
        meeting_name = bundle.getString("meeting_name");


        current = java.util.Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        current_date = dateFormat.format(current.getTime());
        current_month = Integer.parseInt(current_date.substring(0,2))-1;
        current_day = Integer.parseInt(current_date.substring(3,5));
        current_year = Integer.parseInt(current_date.substring(6,10));
        current_hour = Integer.parseInt(current_date.substring(11,13));
        current_min = Integer.parseInt(current_date.substring(14,16));



        time_text = (TextView) findViewById(R.id.textView2);
        end_time_text = (TextView) findViewById(R.id.textView4);
        whenDate = (TextView) findViewById(R.id.textView_1);
        TimePicker mTimePicker = (TimePicker) findViewById(R.id.timePicker);
        TimePicker mTimePicker2 = (TimePicker) findViewById(R.id.timePicker2);
        CalendarView calendar = (CalendarView) findViewById(R.id.calendarView);
        //TextView whenDate = (TextView) findViewById(R.id.textView_1);


        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                if( year< current_year)
                {
                    Toast.makeText(Calendar_activity.this, "The year chosen cannot be before the current year", Toast.LENGTH_SHORT).show();
                }
                else if ((year == current_year) && (month< current_month))
                {
                    Toast.makeText(Calendar_activity.this, "The month chosen cannot be before the current month", Toast.LENGTH_SHORT).show();
                }
                else if ( (year == current_year && month==current_month) && dayOfMonth<current_day)
                {
                    Toast.makeText(Calendar_activity.this, "The day chosen cannot be before the current day", Toast.LENGTH_SHORT).show();
                }
                else {
                    if((year == current_year && month==current_month) && dayOfMonth==current_day)
                            sameday = true;
                    String date = year + "/" + month + "/" + dayOfMonth;
                    whenDate.setText(date);
                    final_date = whenDate.getText().toString();
                }
            }
        });
        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

                hour = mTimePicker.getHour();
                min = mTimePicker.getMinute();


                if((hour < current_hour) && sameday )
                {
                    Toast.makeText(Calendar_activity.this, "Invalid start time chosen", Toast.LENGTH_SHORT).show();
                }
                else if ((hour == current_hour && min<=current_min)&& sameday)
                {
                    Toast.makeText(Calendar_activity.this, "Invalid start time chosen", Toast.LENGTH_SHORT).show();
                }
                else {

                    String time1 = hour + ":" + min;
                    time_text.setText(time1);
                    time_start = time_text.getText().toString();
                }
            }
        });
        mTimePicker2.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

                hour2 = mTimePicker2.getHour();
                min2 = mTimePicker2.getMinute();
                System.out.println(hour2);

                if((hour2 < current_hour) && sameday )
                {
                    Toast.makeText(Calendar_activity.this, "Invalid end time chosen", Toast.LENGTH_SHORT).show();
                }
                else if ((hour2 == current_hour && min2<=current_min)&& sameday)
                {
                    Toast.makeText(Calendar_activity.this, "Invalid end time chosen", Toast.LENGTH_SHORT).show();
                }
                else {

                    String time1 = hour2 + ":" + min2;
                    end_time_text.setText(time1);
                    time_end = end_time_text.getText().toString();
                }
            }
        });

    }
    public void finishClick(View view)
    {
        if(hour2 < hour)
        {
            Toast.makeText(Calendar_activity.this, "End time cannot be before the begin time", Toast.LENGTH_SHORT).show();
        }
        else if (hour2 == hour && min2<=min)
        {
            Toast.makeText(Calendar_activity.this, "End time cannot be before the begin time", Toast.LENGTH_SHORT).show();
        }
        else if (final_date == null)
        {
            Toast.makeText(Calendar_activity.this, "Choose date", Toast.LENGTH_SHORT).show();
        }
        else if (time_start == null || time_end == null)
        {
            Toast.makeText(Calendar_activity.this, "Choose valid time", Toast.LENGTH_SHORT).show();
        }
        else
        {
            System.out.println(meeting_id);
            System.out.println(time_start);
             FirebaseDatabase database = FirebaseDatabase.getInstance();
             DatabaseReference myRef = database.getReference();
            myRef.child( meeting_id).child(Integer.toString(database_number)).child("Num_of_Acceptors:").setValue("0");
            myRef.child( meeting_id).child(Integer.toString(database_number)).child("Acceptors:").setValue("");
            myRef.child( meeting_id).child(Integer.toString(database_number)).child("Date").setValue(final_date);
            myRef.child(meeting_id).child(Integer.toString(database_number)).child("Time").setValue(time_start+"-"+time_end);
            database_number++;

            Activity_list.timeDataList.add((new SampleData(final_date,time_start + "-" + time_end)));

            finish();
        }}


}

