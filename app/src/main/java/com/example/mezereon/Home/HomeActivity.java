package com.example.mezereon.Home;


import android.content.pm.ProviderInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.example.mezereon.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class HomeActivity extends AppCompatActivity {

    @Bind(R.id.tab_layout)
    TabLayout layout_tab;
    @Bind(R.id.viewpager)
    ViewPager vp;

    private FragmentPagerAdapter myAdapter;
    private ArrayList<Fragment> myFragments=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        init();
        layout_tab.setTabMode(TabLayout.MODE_FIXED);
        layout_tab.setBackgroundColor(Color.parseColor("#FFFFFF"));
        layout_tab.setSelectedTabIndicatorHeight(3);
        layout_tab.setTabTextColors(Color.parseColor("#6B6B6B"),Color.parseColor("#3399cc"));
        layout_tab.addTab(layout_tab.newTab().setText("实时校车").setIcon(R.drawable.map));
        layout_tab.addTab(layout_tab.newTab().setText("预定座位").setIcon(R.drawable.order));
        layout_tab.addTab(layout_tab.newTab().setText("个人信息").setIcon(R.drawable.info));
        layout_tab.setupWithViewPager(vp);
        layout_tab.getTabAt(0).setText("实时校车").setIcon(R.drawable.map);
        layout_tab.getTabAt(1).setText("预订座位").setIcon(R.drawable.order);
        layout_tab.getTabAt(2).setText("个人信息").setIcon(R.drawable.info);
    }

    private void init() {
        InfoFragment infofragment=new InfoFragment();
        MapFragment  mapfragment=new MapFragment();
        OrderFragment orderfragment=new OrderFragment();
        myFragments.add(mapfragment);
        myFragments.add(orderfragment);
        myFragments.add(infofragment);

        myAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return myFragments.get(position);
            }

            @Override
            public int getCount() {
                return myFragments.size();
            }
        };
        vp.setAdapter(myAdapter);

    }
}
