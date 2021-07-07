package com.example.mezereon.Home;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.Circle;
import com.example.mezereon.Home.Adapter.StationAdapter;
import com.example.mezereon.Home.Model.Myline;
import com.example.mezereon.R;

import java.util.ArrayList;


import butterknife.Bind;
import butterknife.ButterKnife;

public class StationListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<String> mDatas;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            StationAdapter newAdapter = new StationAdapter(mDatas,StationListActivity.this);
            newAdapter.setBigOnePosition(6 + msg.what);
            mRecyclerView.setAdapter(newAdapter);
            mRecyclerView.stopScroll();
            mRecyclerView.scrollToPosition(msg.what+6>8?msg.what+2:1);
        }
    };
    //    @Bind(R.id.toolbar1)
//    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        getWindow().setStatusBarColor(Color.parseColor("#004d40"));
        setContentView(R.layout.activity_station_list);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        initData();
        //init();
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        final StationAdapter homeAdpter = new StationAdapter(mDatas,this);
        homeAdpter.setBigOnePosition(6);
        mRecyclerView.setAdapter(homeAdpter);

        new Thread(new Runnable() {
            @Override
            public void run() {
                int count = 1;
                while(count<15){
                    try {
                        Thread.sleep(3000);
                        handler.sendEmptyMessage(count);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count++;
                }
            }
        }).start();
    }

    protected void initData()
    {
        int count = 0;
        mDatas = new ArrayList<String>();
        while(count<10) {
            mDatas.add("十三号街");
            mDatas.add("中央大街");
            mDatas.add("222222");
            mDatas.add("四号街");
            mDatas.add("张士");
            count++;
        }


    }
    public class DrawView extends View{
        public DrawView(Context context) {
            super(context);
        }
        protected void onDraw(Canvas canvas) {
            Paint p = new Paint();
            p.setStrokeWidth(10);
            p.setColor(getResources().getColor(R.color.colormain));
            canvas.drawLine(60, 240, 60,475 , p);
            canvas.drawLine(60, 545, 60,770 , p);
            canvas.drawLine(60, 845, 60,1075 , p);
            canvas.drawLine(60, 1145, 60,1375 , p) ;
        }
    }
    private void init() {
        RelativeLayout layout=(RelativeLayout) findViewById(R.id.relative);
        final DrawView view=new DrawView(this);
        //通知view组件重绘
        view.invalidate();
        layout.addView(view);
    }
}
