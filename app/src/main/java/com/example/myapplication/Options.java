package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import android.widget.LinearLayout;


import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import java.util.Objects;

public class Options extends AppCompatActivity {
    public ArrayList<String> date_arr = new ArrayList<String>();
    public ArrayList<String> time_arr = new ArrayList<String>();
    public ArrayList<String> acceptors_names = new ArrayList<String>();
    public ArrayList<Integer> acceptors_no = new ArrayList<Integer>();

    ArrayList<CheckBox> checkBoxes = new ArrayList<CheckBox>();
    Button end;
    String meeting_id;
    String name;
    String meeting_name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.options);

        LinearLayout linearLayout= findViewById(R.id.lyout);

        final Bundle bundle = getIntent().getExtras();
        meeting_id = bundle.getString("meeting_id");
        name = bundle.getString("name");



        end = (Button) findViewById(R.id.finish_options);



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        DatabaseReference a = myRef.getDatabase().getReference(meeting_id);



        //Gets all the time and date values in that meeting id and stores to the array list
        a.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long counter = snapshot.getChildrenCount();
                meeting_name = snapshot.child("MEETING_NAME").getValue().toString();
                for (int i = 1; i <= (counter-2); i++) {
                    date_arr.add(Objects.requireNonNull(snapshot.child(Integer.toString(i)).child("Date").getValue()).toString());
                    time_arr.add(Objects.requireNonNull(snapshot.child(Integer.toString(i)).child("Time").getValue()).toString());
                    acceptors_names.add(snapshot.child(Integer.toString(i)).child("Acceptors:").getValue().toString());
                    acceptors_no.add(Integer.parseInt(snapshot.child(Integer.toString(i)).child("Num_of_Acceptors:").getValue().toString()));
                    checkBoxes.add(new CheckBox(getApplicationContext()));
                    (checkBoxes.get(i-1)).setLayoutParams(new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                    (checkBoxes.get(i-1)).setText(date_arr.get(i-1) +"\n"+time_arr.get(i-1));
                    (checkBoxes.get(i-1)).setId(i);
                    if((checkBoxes.get(i-1)).getParent() !=null)
                    {
                        linearLayout.removeView(checkBoxes.get(i-1));
                    }

                     linearLayout.addView(checkBoxes.get(i - 1));




                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });




        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    CheckBox satView= new CheckBox(getApplicationContext());
                for (int num = 1; num <= checkBoxes.size(); num++) {
                    satView= findViewById(num);
                    if(satView.isChecked()) {
                        myRef.child(meeting_id).child(Integer.toString(num)).child("Num_of_Acceptors:").setValue(Integer.toString(acceptors_no.get(num-1)+1));
                        myRef.child(meeting_id).child(Integer.toString(num)).child("Acceptors:").setValue(acceptors_names.get(num-1)+ " "+ name);
                        acceptors_no.set(num-1,acceptors_no.get(num-1)+1);
                        acceptors_names.set(num-1, acceptors_names.get(num-1)+ " "+ name);
                    }

                }
                Intent Final_DnT = new Intent( Options.this, Final_date_time.class);
                Final_DnT.putExtra("date_arr",date_arr);
                Final_DnT.putExtra("time_arr", time_arr);
                Final_DnT.putExtra("meeting_id", meeting_id);
                Final_DnT.putExtra("meeting_name", meeting_name);
                Final_DnT.putExtra("acceptor_names", acceptors_names);
                startActivity(Final_DnT);
                finish();
            }
        });


    }


}
