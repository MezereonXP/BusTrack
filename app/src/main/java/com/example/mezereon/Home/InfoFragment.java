package com.example.mezereon.Home;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.mezereon.Home.Adapter.SettingAdapter;
import com.example.mezereon.Home.Adapter.UserAdapter;
import com.example.mezereon.R;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private ListView userList,settingList;
    private UserAdapter userAdapter;
    private SettingAdapter settingAdapter;

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

        try {
            userList.setAdapter(new UserAdapter(getActivity(),getData()));
            settingList.setAdapter(new SettingAdapter(getActivity(),getSettingData()));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return v;
    }

    private List<Map<String,Object>> getData() throws JSONException, IOException {
        List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
        for (int i = 0; i < 1; i++) {
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("userPic",R.mipmap.pic);
            map.put("userName", "13032494890");
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
                    map.put("pic",R.mipmap.ic_brightness_high_black_36dp);
                    map.put("name", "系统设置");
                    break;
                case 1:
                    map.put("pic",R.mipmap.ic_storage_black_36dp);
                    map.put("name", "修改信息");
                    break;
                case 2:
                    map.put("pic",R.mipmap.ic_exit_to_app_black_36dp);
                    map.put("name", "退出登陆");
                    break;
            }

            list.add(map);
        }
        return list;
    }

}
