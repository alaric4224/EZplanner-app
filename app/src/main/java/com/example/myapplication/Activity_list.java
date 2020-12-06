package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.ref.Reference;
import java.util.ArrayList;

public class Activity_list extends AppCompatActivity {

    public static ArrayList<SampleData> timeDataList;
    public String meeting_id;
    public String meeting_name;
    private Myadapter simpleadapter;
    public static int counter=0;

    private Button finisher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        final Bundle bundle = getIntent().getExtras();

        meeting_id= bundle.getString("meeting_id");
        meeting_name= bundle.getString("meeting_name");

        this.InitializeTimeData();


        finisher = findViewById(R.id.finish);
        ListView listView = (ListView)findViewById(R.id.listView);
        simpleadapter = new Myadapter(this,timeDataList);

        listView.setAdapter(simpleadapter);


    }

    private void InitializeTimeData() {
        timeDataList = new ArrayList<SampleData>();
        //timeDataList.add(new SampleData("2020/10/20","10:30-12:30"));
    }

    public void buttonClick(View view)
    {
        Intent intent = new Intent(Activity_list.this, Calendar_activity.class);
        intent.putExtra("meeting_id", meeting_id);
        intent.putExtra("meeting_name", meeting_name);
        startActivityForResult(intent,0);
    }
    @Override
    protected void onResume() {
        super.onResume();
        simpleadapter.notifyDataSetChanged();
    }


    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        String response = data.getStringExtra("data");
//        list.add(response);
//        simpleAdapter.notifyDataSetChanged();
//    }

