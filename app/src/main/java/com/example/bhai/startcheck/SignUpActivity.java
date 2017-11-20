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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private Button b1,b2;
    private EditText eemail,epass,euname,ename;
    //private TextView t1;
    private ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    DatabaseReference dbUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        b1=(Button)findViewById(R.id.bt1);
        eemail=(EditText)findViewById(R.id.ETemail);
        epass=(EditText)findViewById(R.id.ETpass);
        ename=(EditText)findViewById(R.id.ETname);
        euname=(EditText)findViewById(R.id.ETuname);
        b2=(Button)findViewById(R.id.bt2);
        // t1=(TextView)findViewById(R.id.tv1);
        progressDialog=new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        dbUser= FirebaseDatabase.getInstance().getReference("Users");

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
    }

    private void signUp()
    {
        final String email = eemail.getText().toString().trim();
        final String password = epass.getText().toString().trim();
        final String name = ename.getText().toString().trim();
        final String uname = euname.getText().toString().trim();

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

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            String id = dbUser.push().getKey();
                            User_Info user_info = new User_Info(name,uname,email,password,id);
                            dbUser.child(id).setValue(user_info);
                            SharedPreferences sharedPreferences = SignUpActivity.this.getSharedPreferences("myPref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("useremail",email);
                            editor.commit();
                            editor.apply();
                            Toast.makeText(SignUpActivity.this,"Signup Complete",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            finish();
                            startActivity(new Intent(SignUpActivity.this,AfterLogin.class));
                        }
                        else
                        {
                            Toast.makeText(SignUpActivity.this,"Failed to signup",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if(view ==b1)
            signUp();

        if(view==b2) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}


