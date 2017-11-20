package com.example.bhai.startcheck;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhai on 12/10/17.
 */

public class AdapterUpcomingView extends ArrayAdapter<Activity_Info> {

    private Activity context;
    private List<Activity_Info> infos;

    public AdapterUpcomingView(Activity context,List<Activity_Info>infos){
        super(context,R.layout.upcoming_node,infos);
        this.context=context;
        this.infos=infos;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.upcoming_node,null,true);

        TextView tvTitle = (TextView)listViewItem.findViewById(R.id.tvTitle);
        TextView tvDate = (TextView)listViewItem.findViewById(R.id.tvdate);
        TextView tvPlace = (TextView)listViewItem.findViewById(R.id.tvplace);
        TextView tvDesc = (TextView)listViewItem.findViewById(R.id.tvdesc);
        TextView tvContac = (TextView)listViewItem.findViewById(R.id.tvcontact);

        Activity_Info activityInfo = infos.get(position);

        tvTitle.setText(activityInfo.getTitle());
        tvDate.setText(activityInfo.getDate());
        tvPlace.setText(activityInfo.getLocality());
        tvDesc.setText(activityInfo.getDesc());
        tvContac.setText(activityInfo.getContact());

        return listViewItem;
    }
}
