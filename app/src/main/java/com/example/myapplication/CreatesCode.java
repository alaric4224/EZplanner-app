package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class CreatesCode extends Activity {

    private TextView code_text;
    private Button confirm_details;
    private EditText enter_code;
    private EditText enter_name;
    private String your_code;
    private String your_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createscode);

        code_text=findViewById(R.id.group_code_message);
        confirm_details=findViewById(R.id.button5);
        enter_code=findViewById(R.id.enter_code);
        enter_name=findViewById(R.id.enter_name);

        confirm_details.setOnClickListener(v -> {
            your_code=enter_code.getText().toString();
            your_name=enter_name.getText().toString();
            if((your_code.equals("Enter group code") || your_code.equals("")) && (your_name.equals("Enter group name") || your_name.equals("")))
                Toast.makeText(getApplicationContext(), "Please enter group name and code", Toast.LENGTH_LONG).show();

            else if(your_name.equals("Enter group name") || your_name.equals(""))
                Toast.makeText(getApplicationContext(), "Please enter your group name", Toast.LENGTH_LONG).show();

            else if(your_code.equals("Enter group code") || your_code.equals(""))
                Toast.makeText(getApplicationContext(), "Please enter your group code", Toast.LENGTH_LONG).show();

            else
            {
                Toast.makeText(getApplicationContext(), "Your details are confirmed", Toast.LENGTH_LONG).show();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();
                myRef.child(your_code).child("MEETING_ID").setValue(your_code);
                myRef.child(your_code).child("MEETING_NAME").setValue(your_name);
                Intent intent = new Intent(CreatesCode.this, Activity_list.class);
                intent.putExtra("meeting_id", your_code);
                intent.putExtra("meeting_name", your_name);
                startActivity(intent);
                finish();
            }
        });

    }
}