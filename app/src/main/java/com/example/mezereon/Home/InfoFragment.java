package com.example.mezereon.Home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Switch;

import com.example.mezereon.Home.Adapter.SettingAdapter;
import com.example.mezereon.Home.Adapter.UserAdapter;
import com.example.mezereon.R;
import com.hyphenate.chat.EMClient;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link InfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FrameLayout frameLayout;
    private ListView userList,settingList;
    private UserAdapter userAdapter;
    private SettingAdapter settingAdapter;
    private SharedPreferences hp;
    private SharedPreferences.Editor editor;

    public InfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InfoFragment newInstance(String param1, String param2) {
        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_info, container, false);
        userList= (ListView) v.findViewById(R.id.user);
        settingList= (ListView) v.findViewById(R.id.setting);
        OverScrollDecoratorHelper.setUpOverScroll(settingList);

        try {
            userList.setAdapter(new UserAdapter(getActivity(),getData()));
            settingList.setAdapter(new SettingAdapter(getActivity(),getSettingData()));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        settingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        new AlertDialog.Builder(getActivity()).setTitle("系统提示")//设置对话框标题

                                .setMessage("确认退出登陆？")//设置显示的内5容

                                .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮



                                    @Override

                                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件

                                        EMClient.getInstance().logout(true);
                                        hp.edit().clear().commit();
                                        getActivity().finish();

                                    }

                                }).setNegativeButton("取消",new DialogInterface.OnClickListener() {//添加返回按钮



                            @Override

                            public void onClick(DialogInterface dialog, int which) {//响应事件



                            }

                        }).show();//在按键响应事件中显示此对话框

                        break;
                    default:
                        break;
                }
            }
        });
        return v;
    }

    private List<Map<String,Object>> getData() throws JSONException, IOException {
        hp = this.getActivity().getSharedPreferences("USERINFO", MODE_PRIVATE);
        editor = hp.edit();

        List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
        for (int i = 0; i < 1; i++) {
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("userPic",R.mipmap.pic);
            map.put("userName", hp.getString("PHONE","NONE"));
            list.add(map);
        }
        return list;
    }
    private List<Map<String,Object>> getSettingData() throws JSONException, IOException {
        List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
        for (int i = 0; i < 3; i++) {
            Map<String, Object> map=new HashMap<String, Object>();
            switch (i){
                case 0:
                    map.put("pic",R.mipmap.ic_brightness_high_grey600_36dp);
                    map.put("name", "系统设置");
                    break;
                case 1:
                    map.put("pic",R.mipmap.ic_storage_grey600_36dp);
                    map.put("name", "修改信息");
                    break;
                case 2:
                    map.put("pic",R.mipmap.ic_exit_to_app_grey600_36dp);
                    map.put("name", "退出登陆");
                    break;
            }

            list.add(map);
        }
        return list;
    }

}
