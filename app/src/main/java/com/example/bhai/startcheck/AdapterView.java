package com.example.bhai.startcheck;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by bhai on 10/10/17.
 */

public class AdapterView extends ArrayAdapter<Ngo_Info> {

    private Activity context;
    private List<Ngo_Info> ngoInfoList;

    public AdapterView(Activity context,List<Ngo_Info> ngoInfoList){
        super(context,R.layout.listnode_layout,ngoInfoList);
        this.context=context;
        this.ngoInfoList=ngoInfoList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.listnode_layout,null,true);


        TextView textView1 = (TextView)listViewItem.findViewById(R.id.textViewName);
        TextView textView2 = (TextView)listViewItem.findViewById(R.id.textViewDesc);

        if (ngoInfoList.isEmpty())
        {
            textView1.setText("No Items to show , press back to continue. :/");
        }
        else {
            Ngo_Info info = ngoInfoList.get(position);

            textView1.setText(info.getNgoname());
            textView2.setText(info.getCo_ord()+" : "+info.getCo_ordemail());
        }

        return listViewItem;
    }
}
