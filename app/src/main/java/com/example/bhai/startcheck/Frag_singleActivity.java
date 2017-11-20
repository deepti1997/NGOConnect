package com.example.bhai.startcheck;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhai on 10/10/17.
 */

public class Frag_singleActivity extends AppCompatActivity {

    private DatabaseReference dbRef;
    private ListView listView;
    private TextView title;
    private List<Ngo_Info> ngoInfoList;
    AdapterView adapterView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_single_category);


        dbRef = FirebaseDatabase.getInstance().getReference("NGO");
        listView = (ListView)findViewById(R.id.listviewngo);
        title = (TextView)findViewById(R.id.cat_title);
        ngoInfoList = new ArrayList<Ngo_Info>();

        SharedPreferences sharedPreferences = this.getSharedPreferences("myPref", Context.MODE_PRIVATE);
        title.setText(sharedPreferences.getString("CurrentCat",""));

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(android.widget.AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView)view.findViewById(R.id.textViewName);
                String ngoTitle = tv.getText().toString();
                String ngoDesc = ((TextView)view.findViewById(R.id.textViewDesc)).getText().toString();
                final Intent intent = new Intent(Frag_singleActivity.this,Ngo_disp.class);
                intent.putExtra("NgoName",ngoTitle);
                intent.putExtra("NgoDesc",ngoDesc);
                intent.putExtra("Cat",title.getText().toString());
                startActivity(intent);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
    Query query = dbRef.orderByChild("category").equalTo(title.getText().toString());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ngoInfoList.clear();

                long n = dataSnapshot.getChildrenCount();
                if(n == 0)
                {
                    Toast.makeText(Frag_singleActivity.this,"No Ngos to Show in this category :/ Press back to Continue!",Toast.LENGTH_LONG).show();
                }
                else {

                    for (DataSnapshot ngosnapShot : dataSnapshot.getChildren()) {
                        Ngo_Info info = ngosnapShot.getValue(Ngo_Info.class);
                        ngoInfoList.add(info);
                    }

                    adapterView = new AdapterView(Frag_singleActivity.this, ngoInfoList);
                    listView.setAdapter(adapterView);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
