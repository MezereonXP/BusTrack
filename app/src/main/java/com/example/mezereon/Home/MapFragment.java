package com.example.mezereon.Home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.example.mezereon.AlarmActivity;
import com.example.mezereon.R;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.baidu.location.LocationClientOption.LOC_SENSITIVITY_MIDDLE;


/**
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private boolean isFirst=true;
    //
    private boolean judgement = true;
    //判断是否无网络连接
    private boolean hasNetwork;
    private double latA=23.369027;
    private double lonA=116.716134;
    private double latB=23.3681810022;
    private double lonB=116.7165892029;
    private double speedOfBus=0.7;
    LatLng pt1 = new LatLng(latA, lonA);
    LatLng pt2 = new LatLng(latB, lonB);
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Spinner sp1,sp2;
    private TextureMapView mv;
    private BaiduMap baiduMap;

    private LatLng cenpt;

    private EMMessageListener msgListener;
    private EMMessage message1;

    private SharedPreferences hp;
    private SharedPreferences.Editor editor;


    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog alertDialog;
    private Spinner mLocat;
    private Spinner goal;
    private Button predict;
    private Button setAlarm;
    private ImageButton imageButton;
    double templat;
    double templon;
    private LatLng temp;
    private boolean isSet=false;
    //站点经纬度数组
    private ArrayList<LatLng> stationList = new ArrayList<LatLng>();
    //站点名字数组
    private ArrayList<String> stationNameList = new ArrayList<String>();
    private Handler handler=new Handler();
    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            //要做的事情
            mLocationClient.start();

        }
    };

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();

    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mv.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().groupManager().joinGroup("6044027781121");
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



        msgListener = new EMMessageListener() {

            @Override
            public void onMessageReceived(final List<EMMessage> messages) {
                //收到消息
                Log.d("message",messages.toString());
                message1=messages.get(0);

                Log.d("from",""+message1.getFrom().toString());
                Log.d("body",""+message1.getBody().toString());
                Log.d("txt",message1.getBody().toString().split("\"")[1]);
                templat=Double.parseDouble(message1.getBody().toString().split("\"")[1].split(",")[0]);
                templon=Double.parseDouble(message1.getBody().toString().split("\"")[1].split(",")[1]);

                baiduMap.clear();
                //构建Marker图标
                temp=new LatLng(templat,templon);
                BitmapDescriptor bitmap = BitmapDescriptorFactory
                        .fromResource(R.mipmap.ic_directions_bus_black_18dp);
                //构建MarkerOption，用于在地图上添加Marker
                OverlayOptions option = new MarkerOptions()
                        .position(temp)
                        .icon(bitmap);
                //在地图上添加Marker，并显示
               baiduMap.addOverlay(option);
                if(goal.getSelectedItemPosition()==1){
                    Log.d("tag","here1");
                    if(isSet&&DistanceUtil.getDistance(temp,pt1)<5){
                        Log.d("tag","here2");
                        isSet=!isSet;
                        Intent intent =new Intent();
                        intent.setClass(getActivity(), AlarmActivity.class);
                        startActivity(intent);
                    }
                }else{
                    Log.d("tag","here3");
                    if(isSet&&DistanceUtil.getDistance(temp,pt2)<5){
                        isSet=!isSet;
                        Intent intent =new Intent();
                        intent.setClass(getActivity(), AlarmActivity.class);
                        startActivity(intent);
                    }
                }

            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                //收到透传消息

            }

            @Override
            public void onMessageRead(List<EMMessage> list) {

            }

            @Override
            public void onMessageDelivered(List<EMMessage> list) {

            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {
                //消息状态变动
            }
        };
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_map, container, false);
        initialStationList();
        initialStationNameList();
        alertDialogBuilder=new AlertDialog.Builder(getActivity());
        alertDialog = alertDialogBuilder.create();

        mLocat= (Spinner) v.findViewById(R.id.spinner);
        goal= (Spinner) v.findViewById(R.id.spinner2);
        imageButton = (ImageButton) v.findViewById(R.id.imageButton);
        mLocat.setEnabled(false);

        predict= (Button) v.findViewById(R.id.appCompatButton3);
        setAlarm= (Button) v.findViewById(R.id.appCompatButton4);

        sp1= (Spinner) v.findViewById(R.id.spinner);
        sp2= (Spinner) v.findViewById(R.id.spinner2);
        mv= (TextureMapView) v.findViewById(R.id.bmapView);
        baiduMap=mv.getMap();
        mLocationClient = new LocationClient(getActivity().getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener( myListener );
        initLocation();
        mLocationClient.start();
        hp = this.getActivity().getSharedPreferences("USERINFO", MODE_PRIVATE);
        editor = hp.edit();
        baiduMap.clear();
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.ic_station);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option;
        for(LatLng latlng:stationList){
            option = new MarkerOptions()
                    .position(latlng)
                    .icon(bitmap);
            //在地图上添加Marker，并显示
            baiduMap.addOverlay(option);
        }
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cenpt!=null) {
                    MapStatus mMapStatus = new MapStatus.Builder()
                            .target(cenpt)
                            .zoom(18)
                            .build();
                    //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
                    //Toast.makeText(getActivity(),location.getLocationDescribe(),Toast.LENGTH_SHORT).show();

                    MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                    //改变地图状态
                    baiduMap.animateMapStatus(mMapStatusUpdate);
                }
            }
        });
        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(goal.getSelectedItemPosition()==0){
                    int time= (int) (DistanceUtil.getDistance(temp,pt1)/speedOfBus);
                    alertDialog.setMessage("预计还有"+time/3600+"小时"+(time-(time/3600)*3600)/60+"分钟"+(time-(time/3600)*3600)%60+"秒到达目的地");
                }else{
                    int time= (int) (DistanceUtil.getDistance(temp,pt2)/speedOfBus);
                    alertDialog.setMessage("预计还有"+time/3600+"小时"+(time-(time/3600)*3600)/60+"分钟"+(time-(time/3600)*3600)%60+"秒到达目的地");
                }
                alertDialog.setCancelable(true);
                alertDialog.show();//将dialog显示出来
            }
        });

        setAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSet==false){
                    setAlarm.setText("已设置，点击取消");
                }else{
                    setAlarm.setText("设置提醒");
                }

                isSet=!isSet;
            }
        });

        return v;
    }

    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=5000;
        //option.setOpenAutoNotifyMode(5000,100, LOC_SENSITIVITY_MIDDLE);
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
}



    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
                hasNetwork = true;
                judgement = true;
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                hasNetwork = false;
                //Toast.makeText(getContext(), "定位需要wlan功能,请打开wlan", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
                if(judgement) {
                    Toast.makeText(getContext(), "暂时无法获取当前位置，请联网后重试", Toast.LENGTH_LONG).show();
                    judgement = false;
                }
                hasNetwork = false;
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息
            List<Poi> list = location.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            Log.i("BaiduLocationApiDem", sb.toString());
                double lat=location.getLatitude();
            double lon=location.getLongitude();
            cenpt = new LatLng(lat,lon);
            //定义地图状态
            if(hasNetwork) {
                if (isFirst == true) {
                    MapStatus mMapStatus = new MapStatus.Builder()
                            .target(cenpt)
                            .zoom(18)
                            .build();
                    //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
                    //Toast.makeText(getActivity(),location.getLocationDescribe(),Toast.LENGTH_SHORT).show();

                    MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                    //改变地图状态
                    baiduMap.setMapStatus(mMapStatusUpdate);
                    isFirst = false;
                }

                // 开启定位图层
                baiduMap.setMyLocationEnabled(true);
                // 构造定位数据
                MyLocationData locData = new MyLocationData.Builder()
                        .latitude(location.getLatitude())
                        .longitude(location.getLongitude()).build();
                // 设置定位数据
                baiduMap.setMyLocationData(locData);
                // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
                BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
                        .fromResource(R.mipmap.ic_navigation_black_18dp);
                MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, false, mCurrentMarker);
                baiduMap.setMyLocationConfigeration(config);
            }
            setMiniDistanceStation();
            //mLocationClient.stop();
            //handler.postDelayed(runnable, 2000);
            if(hp.getString("PHONE","NONE").equals("13032494890")){
                EMMessage message = EMMessage.createTxtSendMessage(lat+","+lon,"6044027781121");
                message.setChatType(EMMessage.ChatType.GroupChat);
                EMClient.getInstance().chatManager().sendMessage(message);
            }

        }
    }

    private void setMiniDistanceStation() {
        int min = 0;
        for(int i = 0;i < stationList.size();i++){
            if(DistanceUtil.getDistance(stationList.get(i),cenpt)<=DistanceUtil.getDistance(stationList.get(min),cenpt)){
                Log.i("text", stationNameList.get(i)+"  "+ DistanceUtil.getDistance(stationList.get(i),cenpt));
                min = i;
            }
        }
        mLocat.setSelection(min);
    }


    @Override
    public void onResume() {
        mv.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        mv.onDestroy();
        if(mLocationClient!=null){
            mLocationClient.unRegisterLocationListener(myListener);
            mLocationClient.stop();
        }
        super.onDestroy();
    }

    @Override
    public void onPause() {
        mv.onPause();
        super.onPause();
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {        //核心方法，避免因Fragment跳转导致地图崩溃
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser == true) {
            // if this view is visible to user, start to request user location
            isFirst=true;
            if(mLocationClient!=null){
                mLocationClient.registerLocationListener(myListener);
                mLocationClient.start();
                mLocationClient.requestLocation();
                MapStatusUpdate mapStatus = MapStatusUpdateFactory.newLatLngZoom(cenpt, 18);
                baiduMap.setMapStatus(mapStatus);
            }else{
                Log.d("tag","client is null");
            }

        } else if (isVisibleToUser == false) {
            // if this view is not visible to user, stop to request user
            // location
            if(mLocationClient!=null){
                mLocationClient.unRegisterLocationListener(myListener);
                mLocationClient.stop();
            }

        }
    }
    //初始化站点经纬度
    public void initialStationList(){
        stationList.add(new LatLng(41.771751,123.236737));
        stationList.add(new LatLng(41.771775,123.248797));
        stationList.add(new LatLng(41.771492,123.266045));
        stationList.add(new LatLng(41.771815,123.283741));
        stationList.add(new LatLng(41.777292,123.300459));
    }
    //初始化站点名字
    public void initialStationNameList(){
        stationNameList.add("十三号街");
        stationNameList.add("中央大街");
        stationNameList.add("七号街");
        stationNameList.add("四号街");
        stationNameList.add("张士");
    }

}
