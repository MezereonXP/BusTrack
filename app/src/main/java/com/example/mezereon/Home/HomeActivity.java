package com.example.mezereon.Home;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.example.mezereon.R;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class HomeActivity extends AppCompatActivity {

    @Bind(R.id.tab_layout)
    TabLayout layout_tab;
    @Bind(R.id.viewpager)
    ViewPager vp;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.title)
    TextView title;

    private FragmentPagerAdapter myAdapter;
    private ArrayList<Fragment> myFragments=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        getWindow().setStatusBarColor(Color.parseColor("#004d40"));
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

        layout_tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String name= (String) tab.getText();
                if(name.equals("实时校车")){
                    title.setText("实时");
                }else if(name.equals("预订座位")){
                    title.setText("预订");
                }else if(name.equals("个人信息")){
                    title.setText("个人");
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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
        vp.setOffscreenPageLimit(2);

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(HomeActivity.this).setTitle("系统提示")//设置对话框标题

                .setMessage("确认退出BusTrack？")//设置显示的内5容

                .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮



                    @Override

                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件

                        finish();

                    }

                }).setNegativeButton("取消",new DialogInterface.OnClickListener() {//添加返回按钮



            @Override

            public void onClick(DialogInterface dialog, int which) {//响应事件



            }

        }).show();//在按键响应事件中显示此对话框
    }
}
