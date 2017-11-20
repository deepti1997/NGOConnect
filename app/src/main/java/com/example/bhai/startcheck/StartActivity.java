package com.example.bhai.startcheck;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Locale;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    EditText activityname,locality,contactinfo,description,date;
    Button startactivitybt;
    DatabaseReference dbActivity;
    Activity_Info info = new Activity_Info();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        activityname = (EditText)findViewById(R.id.activityname);
        locality = (EditText)findViewById(R.id.locality);
        contactinfo = (EditText)findViewById(R.id.contactinfo);
        description = (EditText)findViewById(R.id.description);
        date = (EditText)findViewById(R.id.date);
        startactivitybt = (Button)findViewById(R.id.startactivity);

        dbActivity = FirebaseDatabase.getInstance().getReference("Activities");

        startactivitybt.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.startactivity)
        {
            info.setTitle(activityname.getText().toString().trim());
            info.setDesc(description.getText().toString().trim());
            info.setContact(contactinfo.getText().toString().trim());
            info.setDate(date.getText().toString().trim());
            info.setLocality(locality.getText().toString().trim());

            String id = dbActivity.push().getKey();
            info.setId(id);

            if(TextUtils.isEmpty(info.getTitle()) || TextUtils.isEmpty(info.getContact()) || TextUtils.isEmpty(info.getDate()) ||TextUtils.isEmpty(info.getDesc()) || TextUtils.isEmpty(info.getLocality()) )
            {
                Toast.makeText(this,"Enter All Fields Correctly!",Toast.LENGTH_LONG).show();
            }
            else {
                dbActivity.child(id).setValue(info);
                startActivity(new Intent(this,AfterLogin.class));
                Toast.makeText(this,"Activity Created",Toast.LENGTH_SHORT).show();
            }

        }
    }
}
