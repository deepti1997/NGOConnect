package com.example.bhai.startcheck;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UpcomingActivities extends AppCompatActivity {

    ListView listView;
    DatabaseReference dbActivities;
    List<Activity_Info> infoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_activities);

        listView = (ListView)findViewById(R.id.lvUpcoming);
        dbActivities = FirebaseDatabase.getInstance().getReference("Activities");
        infoList = new ArrayList<Activity_Info>();
    }

    @Override
    protected void onStart() {
        super.onStart();

        dbActivities.orderByChild("date").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                infoList.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    Activity_Info info = snapshot.getValue(Activity_Info.class);
                    infoList.add(info);
                }

                AdapterUpcomingView adapterUpcomingView = new AdapterUpcomingView(UpcomingActivities.this,infoList);
                listView.setAdapter(adapterUpcomingView);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
