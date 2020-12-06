package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class First extends AppCompatActivity implements View.OnClickListener{

    private EditText name;
    private EditText code;
    private String your_name;
    private String your_code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first);

        Button button_desc=findViewById(R.id.button1);
        Button button_confirm_details=findViewById(R.id.button3);
        Button button_create_code=findViewById(R.id.button4);
        name=findViewById(R.id.name);
        code=findViewById(R.id.code);

        button_desc.setOnClickListener(this);
        button_confirm_details.setOnClickListener(this);
        button_create_code.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.button1: {
                Intent intent = new Intent(First.this, Description.class);
                startActivity(intent);
            }
            break;

            case R.id.button3:
            {
                your_code=code.getText().toString();
                your_name=name.getText().toString();
                if((your_code.equals("Enter group code") || your_code.equals("")) && (your_name.equals("Enter your name") || your_name.equals("")))
                    Toast.makeText(getApplicationContext(), "Please enter your name and group code", Toast.LENGTH_LONG).show();

                else if(your_name.equals("Enter your name") || your_name.equals(""))
                    Toast.makeText(getApplicationContext(), "Please enter your name", Toast.LENGTH_LONG).show();

                else if(your_code.equals("Enter group code") || your_code.equals(""))
                    Toast.makeText(getApplicationContext(), "Please enter your group code", Toast.LENGTH_LONG).show();

                else
                {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    Query checkUser = ref.orderByChild("MEETING_ID").equalTo(your_code);

                    checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists())
                            {
                               Toast.makeText(getApplicationContext(), "Your details are confirmed", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(First.this, Options.class);
                                intent.putExtra("meeting_id", your_code);
                                intent.putExtra("name", your_name);

                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Invalid Group ID", Toast.LENGTH_LONG).show();

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }
            break;

            case R.id.button4:
            {
                Intent intent = new Intent(First.this, CreatesCode.class);
                startActivity(intent);
            }
            break;
        }


    }}
