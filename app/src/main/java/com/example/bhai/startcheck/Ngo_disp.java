package com.example.bhai.startcheck;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Ngo_disp extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference dbRef;
    private ListView listView,listViewmain;
    private List<Ngo_Info> ngoInfoList;
    AdapterView adapterView;

    Button joinngo;
    TextView title;
    ImageView icon;
    TextView desc,getdirections;
    TextView contact;
    String ngoname;
    String ngodesc;
    String cat;
    String lat="",lang="",name="";
    String contacttext="";
    ImageView ngoimage;
    long n;
    String desc1 = "Desc";
    Ngo_Info info1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngo_disp);

        title = (TextView) findViewById(R.id.ngoTitle);
        desc = (TextView) findViewById(R.id.ngodesc);
        icon = (ImageView) findViewById(R.id.ngoicon);
        contact = (TextView) findViewById(R.id.ngocontact);
        ngoimage = (ImageView)findViewById(R.id.ngoicon);
        getdirections=(TextView) findViewById(R.id.getdirections);
        joinngo = (Button)findViewById(R.id.joinbutton);
        joinngo.setOnClickListener(this);
        //listViewmain = (ListView)findViewById(R.id.finalview);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ngoname = bundle.getString("NgoName");
        ngodesc = bundle.getString("NgoDesc");
        cat = bundle.getString("Cat");
        title.setText(ngoname);

       // Toast.makeText(this,ngodesc,Toast.LENGTH_SHORT).show();

        dbRef = FirebaseDatabase.getInstance().getReference("NGO");
        listView = (ListView)findViewById(R.id.lv);
        ngoInfoList = new ArrayList<Ngo_Info>();

      //  contact.setText(ngoInfoList.size());


    }


    public void storeinfo(final String name, final String lat, final String lang)
    {
        getdirections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==getdirections)
                {
                    if(lat==null || lang == null)
                    {
                        Toast.makeText(Ngo_disp.this,"The location of the Ngo was not registered,Sorry :/",Toast.LENGTH_LONG).show();
                    }
                    else {
                        Intent i1 = new Intent(Ngo_disp.this, LocateNGO.class);
                        i1.putExtra("name", name);
                        i1.putExtra("latitude", lat);
                        i1.putExtra("longitude", lang);
                        startActivity(i1);
                    }
                }
            }
        });
    }
    public void getObjDetails(Ngo_Info info,String id)
    {
        info1 = new Ngo_Info(info.getNgoname(),info.getCategory(),info.getDesc(),info.getCo_ord(),info.getPhno(),info.getLat(),info.getLang(),info.getCo_ordemail(),id);
        info1.setVolunteers(info.getVolunteers());
    }
    @Override
    public void onStart() {
        super.onStart();

        Query query = dbRef.orderByChild("ngoname").equalTo(ngoname);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ngoInfoList.clear();

                for(DataSnapshot ngosnapShot : dataSnapshot.getChildren()){
                    Ngo_Info info = ngosnapShot.getValue(Ngo_Info.class);
                    name=info.getNgoname();
                    contacttext= info.getCo_ord();
                    lang = info.getLang();
                    lat = info.getLat();
                    storeinfo(name,lat,lang);
                    getObjDetails(info,info.getId());
                    desc.setText(info.getDesc());
                    contact.setText(contacttext+" : "+info.getPhno()+" "+info.getCo_ordemail());
                    ngoInfoList.add(info);
                }

              //  adapterView = new AdapterView(Ngo_disp.this,ngoInfoList);
               // listView.setAdapter(adapterView);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.joinbutton:
                SharedPreferences sharedPreferences = this.getSharedPreferences("myPref", Context.MODE_PRIVATE);
                String email = sharedPreferences.getString("useremail","hey");
                int flag=0;
               for(int i =0;i<info1.getVolunteers().size();i++)
                {
                    String x = (String)info1.getVolunteers().get(i);
                    if(x.equalsIgnoreCase(email))
                        flag = 1;
                }
                if(flag == 1)
                   Toast.makeText(this,"Already Registered for this NGO",Toast.LENGTH_LONG).show();
                else
                {
                    DatabaseReference db = FirebaseDatabase.getInstance().getReference("NGO").child(info1.getId());
                    DatabaseReference dbuser = FirebaseDatabase.getInstance().getReference("Users");
                    ArrayList<String> test = info1.getVolunteers();
                    test.add(email);
                    info1.setVolunteers(test);
                    Toast.makeText(this,"Registered",Toast.LENGTH_LONG);
                    db.setValue(info1);


            }
        }

    }
}