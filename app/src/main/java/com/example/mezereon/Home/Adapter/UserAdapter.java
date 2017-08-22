package com.example.mezereon.Home.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mezereon.R;

import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Mezereon on 2017/1/15.
 */
public class UserAdapter extends BaseAdapter {
    private List<Map<String, Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;

    public final class ArticleItem{
        public CircleImageView userPic;
        public TextView userName;
    }

    public UserAdapter(Context context, List<Map<String, Object>> data){
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

            convertView = layoutInflater.inflate(R.layout.item_user, null);
            item.userPic = (CircleImageView) convertView.findViewById(R.id.circleImageView);
            item.userName = (TextView) convertView.findViewById(R.id.username);
            convertView.setTag(item);
        }else{
            item=(ArticleItem) convertView.getTag();
        }
        item.userPic.setImageResource((int) data.get(position).get("userPic"));
        item.userName.setText((String) data.get(position).get("userName"));
        return convertView;
    }

}