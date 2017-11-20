package com.example.bhai.startcheck;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by bhai on 9/10/17.
 */

public class Frag_Categories extends Fragment implements View.OnClickListener {

    TextView catchild,catanimal,catwomen,catenvironment,cathand,catother;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return (inflater.inflate(R.layout.frag_categories,container,false));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        catchild = (TextView)getView().findViewById(R.id.catchild);
        catanimal = (TextView)getView().findViewById(R.id.catanimal);
        catwomen = (TextView)getView().findViewById(R.id.catwomen);
        catenvironment = (TextView)getView().findViewById(R.id.catenvironment);
        cathand = (TextView)getView().findViewById(R.id.cathand);
        catother = (TextView)getView().findViewById(R.id.catother);

        catchild.setOnClickListener(this);
        catenvironment.setOnClickListener(this);
        catother.setOnClickListener(this);
        cathand.setOnClickListener(this);
        catwomen.setOnClickListener(this);
        catanimal.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("myPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String currentcategory="";
        switch (v.getId())
        {
            case R.id.catchild:
                currentcategory = "Child Welfare";
                break;
            case R.id.catwomen:
                currentcategory = "Women Empowerment";
                break;
            case R.id.catanimal:
                currentcategory = "Animal Welfare";
                break;
            case R.id.catenvironment:
                currentcategory = "Nature";
                break;
            case R.id.cathand:
                currentcategory = "Handicapped";
                break;
            case R.id.catother:
                currentcategory = "Others";
                break;

        }
        editor.putString("CurrentCat",currentcategory);
        editor.commit();
        //getActivity().finish();
//        Fragment fragment = new Frag_singleActivity();
//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.content_parent,fragment);
//        fragmentTransaction.commit();
        startActivity(new Intent(getActivity(),Frag_singleActivity.class));
    }
}
