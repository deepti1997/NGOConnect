package com.example.bhai.startcheck;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

import static android.app.Activity.RESULT_OK;

/**
 * Created by bhai on 9/10/17.
 */

public class Frag_Reg extends Fragment implements View.OnClickListener {

    private static final int PICK_IMAGE_REQUEST = 111;
    EditText ngoname,desc,cn,ph;
    TextView lcn,upld;
    Button b1;
    RadioGroup rg;
    RadioButton r1,r2,r3,r4,r5,r6;
    DatabaseReference myref;
    FirebaseDatabase db;
    private Uri filePath;
    StorageReference storageReference;
    String lat,lng;
    double[] loc;
    FirebaseDataMap obj = new FirebaseDataMap();
    HashMap<String, String> dataMap = obj.fireebaseMap();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return (inflater.inflate(R.layout.frag_reg,container,false));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ngoname=(EditText)getView().findViewById(R.id.ngoname);
        desc=(EditText)getView().findViewById(R.id.desc);
        cn=(EditText)getView().findViewById(R.id.cn);
        ph=(EditText)getView().findViewById(R.id.ph);
        lcn=(TextView)getView().findViewById(R.id.lcn);
        upld=(TextView)getView().findViewById(R.id.upld);
        rg=(RadioGroup)getView().findViewById(R.id.radiogroup);
        r1=(RadioButton)getView().findViewById(R.id.women);
        r2=(RadioButton)getView().findViewById(R.id.child);
        r3=(RadioButton)getView().findViewById(R.id.nature);
        r4=(RadioButton)getView().findViewById(R.id.animal);
        r5=(RadioButton)getView().findViewById(R.id.handicapped);
        r6=(RadioButton)getView().findViewById(R.id.othertype);
        b1= (Button)getView().findViewById(R.id.submit);


        db = FirebaseDatabase.getInstance();
        myref=db.getReference("NGO");
        storageReference= FirebaseStorage.getInstance().getReference("NGO");

        upld.setOnClickListener(this);
        b1.setOnClickListener(this);
        lcn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.upld:
                showFileChooser();
                break;
            case R.id.lcn:
                Intent i = new Intent(v.getContext(),MapsActivity.class);
                startActivityForResult(i,1);
                break;
            case R.id.submit:
            {
                String sngoname,sdesc,scat="",scn,sph;
                sngoname = ngoname.getText().toString();
                sdesc = desc.getText().toString();
                scn = cn.getText().toString();
                sph = ph.getText().toString();
                if(r1.isChecked()) {
                    scat = r1.getText().toString();
                }else if(r2.isChecked()){
                    scat = r2.getText().toString();
                }else if(r3.isChecked()){
                    scat = r3.getText().toString();
                }else if(r4.isChecked()){
                    scat = r4.getText().toString();
                }else if(r5.isChecked()){
                    scat = r5.getText().toString();
                }else if(r6.isChecked()){
                    scat = r6.getText().toString();
                }
                if(TextUtils.isEmpty(sngoname) || TextUtils.isEmpty(scat) || TextUtils.isEmpty(sdesc) || TextUtils.isEmpty(scn) || TextUtils.isEmpty(sph) || sph.length()>11)
                {
                    Toast.makeText(getContext(),"Enter all the fields correctly!",Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("myPref", Context.MODE_PRIVATE);
                    String email = sharedPreferences.getString("useremail","");
                    Toast.makeText(getContext(),email,Toast.LENGTH_SHORT).show();
                    String id = myref.push().getKey();
                    Ngo_Info info = new Ngo_Info(sngoname,scat,sdesc,scn,sph,lat,lng,email,id);

                    myref.child(id).setValue(info);
                    uploadFile(id);
                    Toast.makeText(getContext(),"Data successfully stored",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(),AfterLogin.class));
                }


            }
                break;

        }
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            filePath = data.getData();
            //uploadFile();
        }
        if (resultCode == RESULT_OK && requestCode == 1)
        {
            if (data.hasExtra("latitude") && data.hasExtra("longitude"))
            {
                lat= data.getExtras().getString("latitude");
                lng= data.getExtras().getString("longitude");
                lcn.setText("NGO location is saved. Tap to change");
            }
        }
    }

    private void uploadFile(String id) {
        //if there is a file to upload
        if (filePath != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            final StorageReference riversRef = storageReference.child(id);

            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying a success toast
                            Toast.makeText(getContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying error message
                            Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage


                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Uploading...");
                            dataMap.put("Image",filePath.toString());




                        }
                    });
        }
        //if there is not any file
        else {
            //you can display an error toast
        }
    }


}
