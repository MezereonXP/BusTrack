package com.example.mezereon.Home.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mezereon.R;

import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Mezereon on 2017/1/15.
 */
public class SettingAdapter extends BaseAdapter {


    private List<Map<String, Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;

    public final class ArticleItem{
        public ImageView pic;
        public TextView name;
    }

    public SettingAdapter(Context context, List<Map<String, Object>> data){
        this.context=context;
        this.data=data;
        this.layoutInflater= LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ArticleItem item = new ArticleItem();
        if(convertView==null) {
            Log.e("convertView = ", " NULL");
            convertView = layoutInflater.inflate(R.layout.item_setting, null);
            item.pic = (ImageView) convertView.findViewById(R.id.pic);
            item.name = (TextView) convertView.findViewById(R.id.settingsName);
            convertView.setTag(item);
        }else{
            item=(ArticleItem) convertView.getTag();
        }
        item.pic.setImageResource((int) data.get(position).get("pic"));
        item.name.setText((String) data.get(position).get("name"));
        return convertView;
    }

}