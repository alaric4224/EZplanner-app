package com.example.myapplication;


import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Final_date_time extends AppCompatActivity {

    public static ArrayList<SampleData> finalDataList;
    private Myadapter simpleadapter;

    String meeting_id;
    String meeting_name;
    ArrayList<String> date_arr = new ArrayList<String>();
    ArrayList<String> time_arr = new ArrayList<String>();
    ArrayList<String> acceptor_names = new ArrayList<String>();
    ArrayList<String> timenacceptors = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_date_time);
        this.InitializeTimeData();

        ListView listView = (ListView)findViewById(R.id.listView);
        simpleadapter = new Myadapter(this,finalDataList);
        listView.setAdapter(simpleadapter);

        TextView code=findViewById(R.id.code);
        TextView name=findViewById(R.id.name);
        TextView group_name=findViewById(R.id.GroupName);

        TextView final_date_time=findViewById(R.id.Final_date_time);

        Button main_page=findViewById(R.id.Button9);


        group_name.setText("Meeting Name: "+meeting_name);
        code.setText("Meeting ID: "+meeting_id);


    }
    private void InitializeTimeData() {
        Bundle bundle = getIntent().getExtras();
        meeting_id = bundle.getString("meeting_id");
        meeting_name = bundle.getString("meeting_name");
        date_arr= bundle.getStringArrayList("date_arr");
        time_arr= bundle.getStringArrayList("time_arr");
        acceptor_names= bundle.getStringArrayList("acceptor_names");

        for( int i=0; i< time_arr.size(); i++)
        {
            timenacceptors.add(time_arr.get(i)+ "\n("+ acceptor_names.get(i)+")");
            System.out.println(timenacceptors.get(i));
            System.out.println(date_arr.get(i));

        }

        finalDataList = new ArrayList<SampleData>();

       for(int j=0; j<time_arr.size(); j++) {
           finalDataList.add(new SampleData(date_arr.get(j), timenacceptors.get(j)));
       }



    }

}

