package com.example.mezereon.Home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
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
import com.example.mezereon.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StationListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<String> mDatas;
//    @Bind(R.id.toolbar1)
//    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
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
        init();
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new HomeAdpter());
    }
    class HomeAdpter extends RecyclerView.Adapter<HomeAdpter.MyViewHolder>{
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    StationListActivity.this).inflate(R.layout.item_station, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position)
        {
            holder.tv.setText(mDatas.get(position));
            holder.img.setImageResource (R.drawable.circle);
        }
        @Override
        public int getItemCount()
        {
            return mDatas.size();
        }
        class MyViewHolder extends RecyclerView.ViewHolder
        {
            ImageView img;
            TextView tv;
            public MyViewHolder(View view)
            {
                super(view);
                tv = (TextView) view.findViewById(R.id.stationName);
                img = (ImageView) view.findViewById(R.id.imageView);
            }
        }
    }
    protected void initData()
    {
        mDatas = new ArrayList<String>();
        mDatas.add("十三号街");
        mDatas.add("中央大街");
        mDatas.add("七号街");
        mDatas.add("四号街");
        mDatas.add("张士");
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
            canvas.drawLine(60, 1145, 60,1375 , p);
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
