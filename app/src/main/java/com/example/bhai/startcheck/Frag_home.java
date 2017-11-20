package com.example.bhai.startcheck;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by bhai on 8/10/17.
 */

public class Frag_home extends Fragment implements View.OnClickListener {

    private static ViewPager viewPager;
    private static int currentPage = 0;
    TextView textView;
    ImageButton btJoin,btReg,btPlan,btFind;
    private static final Integer[] images= {R.drawable.help,R.drawable.doggo,R.drawable.women,R.drawable.fifty,R.drawable.indiadrought};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return(inflater.inflate(R.layout.frag_home,container,false));

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Home");

        textView = (TextView)getView().findViewById(R.id.textView);
        viewPager = (ViewPager)getView().findViewById(R.id.viewPager);
        viewPager.setAdapter(new ViewPagerAdapter());
        viewPager.addOnPageChangeListener(viewListener);
        CircleIndicator indicator = (CircleIndicator)getView().findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);
        btJoin = (ImageButton)getView().findViewById(R.id.home_join);
        btJoin.setOnClickListener(this);
        btReg = (ImageButton)getView().findViewById(R.id.home_reg);
        btReg.setOnClickListener(this);
        btFind = (ImageButton)getView().findViewById(R.id.home_find);
        btFind.setOnClickListener(this);
        btPlan = (ImageButton)getView().findViewById(R.id.home_plan);
        btPlan.setOnClickListener(this);

    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        switch (v.getId())
        {
            case R.id.home_join:
                fragment= new Frag_Categories();
                break;
            case R.id.home_reg:
                fragment=new Frag_Reg();
                break;
            case R.id.home_find:
                fragment=new Frag_AskRadius();
                break;
            case R.id.home_plan:
                startActivity(new Intent(getActivity(),StartActivity.class));
        }
        if ( fragment != null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.content_parent, fragment);
            ft.commitNow();
        }

    }

    public class ViewPagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater= (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v=layoutInflater.inflate(R.layout.img_view,container,false);
            ImageView myImageView = (ImageView)v.findViewById(R.id.img);
            myImageView.setImageResource(images[position]);
            container.addView(v,0);
            return v;
        }

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View v = (View)object;
            container.removeView(v);
        }
    }

}
