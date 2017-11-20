package com.example.bhai.startcheck;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button b1,b2;
    private EditText e1,e2;
    //private TextView t1;
    private ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        b1=(Button)findViewById(R.id.bt1);
        e1=(EditText)findViewById(R.id.ETemail);
        e2=(EditText)findViewById(R.id.ETpass);
        b2=(Button)findViewById(R.id.bt2);
        // t1=(TextView)findViewById(R.id.tv1);
        progressDialog=new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null)
        {
            finish();
           startActivity(new Intent(this,AfterLogin.class));
        }

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
    }

    private void logIn()
    {
        final String email = e1.getText().toString().trim();
        String password = e2.getText().toString().trim();

        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this,"Enter Your Email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"Enter Your Password",Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences("myPref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("useremail",e1.getText().toString().trim());
                            editor.commit();
                            editor.apply();

                            Toast.makeText(LoginActivity.this,"Login Complete",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            finish();
                           startActivity(new Intent(LoginActivity.this,AfterLogin.class));
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this,"Failed to login",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }



    @Override
    public void onClick(View view) {
        if(view ==b1)
            logIn();

        if(view==b2) {
            finish();
            startActivity(new Intent(this, SignUpActivity.class));
        }
    }
}
