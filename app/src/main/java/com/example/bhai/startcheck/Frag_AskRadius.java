package com.example.bhai.startcheck;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by bhai on 11/10/17.
 */

public class Frag_AskRadius extends Fragment {

    EditText radius ;
    Button find;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return (inflater.inflate(R.layout.frag_askradius,container,false));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        radius = (EditText)getView().findViewById(R.id.radius);
        find = (Button)getView().findViewById(R.id.ngonear);

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),NGONearYou.class);
                intent.putExtra("radius",radius.getText().toString().trim());
                startActivity(intent);
            }
        });
    }
}
