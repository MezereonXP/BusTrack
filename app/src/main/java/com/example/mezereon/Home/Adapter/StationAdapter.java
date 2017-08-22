package com.example.mezereon.Home.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mezereon.Home.Model.Myline;
import com.example.mezereon.Home.StationListActivity;
import com.example.mezereon.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017-08-18.
 */

public class StationAdapter extends RecyclerView.Adapter<StationAdapter.MyViewHolder>{
    private ArrayList<String> mDatas;
    private Context context;
    public StationAdapter(ArrayList<String> mDatas,Context context) {
        this.mDatas = mDatas;
        this.context = context;
    }

    public int getBigOnePosition() {
        return bigOnePosition;
    }

    public void setBigOnePosition(int bigOnePosition) {
        this.bigOnePosition = bigOnePosition;
    }

    private int bigOnePosition = -1;//用于控制变亮的点的位置
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(
                context).inflate(R.layout.item_station, parent,
                false);

        MyViewHolder holder = new MyViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        holder.tv.setText(mDatas.get(position));
        holder.img.setImageResource (R.drawable.circle);
        if(position == (mDatas.size()-1)){
            holder.myline.setShowLine(false);
        }else {
            holder.myline.setShowLine(true);
        }

        if(position == bigOnePosition){
            holder.myline.setShowBigOne(true);
            holder.introduction.setVisibility(View.VISIBLE);
        }else {
            holder.myline.setShowBigOne(false);
            holder.introduction.setVisibility(View.GONE);
        }

    }
    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView img;
        TextView tv, introduction;
        Myline myline;

        public MyViewHolder(View view)
        {
            super(view);
            tv = (TextView) view.findViewById(R.id.stationName);
            img = (ImageView) view.findViewById(R.id.imageView);
            myline = (Myline) view.findViewById(R.id.mylineview);
            introduction = (TextView) view.findViewById(R.id.stationIntroduction);
        }
    }
}