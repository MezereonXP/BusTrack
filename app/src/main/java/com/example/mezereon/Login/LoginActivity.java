package com.example.mezereon.Login;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.Explode;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mezereon.Home.HomeActivity;
import com.example.mezereon.MyApp;
import com.example.mezereon.R;
import com.example.mezereon.Tool.API;
import com.gospelware.liquidbutton.LiquidButton;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.exceptions.HyphenateException;
import com.jakewharton.rxbinding.view.RxView;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;


public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.textInputEditText)
    EditText et_id;
    @Bind(R.id.textInputEditText2)
    EditText et_confirm;
    @Bind(R.id.appCompatButton)
    Button btn_getConfirm;
    @Bind(R.id.appCompatButton2)
    Button btn_login;
    @Bind(R.id.textInputLayout)
    TextInputLayout til;
    @Bind(R.id.textInputLayout2)
    TextInputLayout til2;


    final Intent intent=new Intent();
    private SharedPreferences hp;
    private SharedPreferences.Editor editor;
    private ProgressDialog progressDialog = null;
    private AlertDialog alertDialog = null;
    private static final int MESSAGETYPE_01 = 0x0001;
    private static final int MESSAGETYPE_02 = 0x0002;
    private Handler handler = new Handler() {

        public void handleMessage(Message message) {
            switch (message.what) {
                case MESSAGETYPE_01:
                        EMClient.getInstance().login(hp.getString("PHONE","none"),hp.getString("PHONE","none"),new EMCallBack() {//回调
                                @Override
                                public void onSuccess() {
                                    EMClient.getInstance().groupManager().loadAllGroups();
                                    EMClient.getInstance().chatManager().loadAllConversations();
                                    progressDialog.dismiss(); //关闭进度条
                                    getWindow().setExitTransition(new Explode());
                                    intent.setClass(LoginActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                    finish();

                                }

                                @Override
                                public void onProgress(int progress, String status) {
                                    Log.d("progress",progress+"");
                                }

                                @Override
                                public void onError(int code, String message) {
                                   //Toast.makeText(LoginActivity.this,"登陆服务器失败",Toast.LENGTH_SHORT).show();
                                }
                            });
                    //刷新UI，显示数据，并关闭进度条
                    break;
                case MESSAGETYPE_02:
                    showTheWayToRegist();
                    break;

            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_login);
        progressDialog=new ProgressDialog(LoginActivity.this);

        hp = this.getSharedPreferences("USERINFO", MODE_PRIVATE);
        editor = hp.edit();
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        ButterKnife.bind(this);

        EMOptions options = new EMOptions();
        //初始化
        EMClient.getInstance().init(new MyApp(), options);


        if(!hp.getString("PHONE","none").equals("none")){
            intent.setClass(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
        RxView.clicks(btn_login).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Log.d("tag1",et_id.getText().equals("")+"");
                Log.d("tag2",isMobileNO(et_id.getText().toString())+"");
                if ((!et_id.getText().equals(""))&&isMobileNO(et_id.getText().toString())) {
                    editor.putString("PHONE",et_id.getText().toString());
                    editor.commit();
                    progressDialog.setMessage("登陆中");
                    progressDialog.show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("tag","here!");
                            isRegisted(et_id.getText().toString());
                        }

                    }).start();
                } else{
                    til.setErrorEnabled(true);
                    til.setError("请输入正确的手机号码");
                }
            }
        });


    }

    private void showTheWayToRegist() {
        progressDialog.dismiss();
        alertDialog=new AlertDialog.Builder(LoginActivity.this)
                .setTitle("前往注册界面")
                .setMessage("确定吗？")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getWindow().setExitTransition(new Explode());
                        intent.setClass(LoginActivity.this, SignActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                })
                .show();
    }

    private void isRegisted(String s) {
        String result="";
        try {
            result = API.isRegisterForHttpGet(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(result.equals("1")){
            handler.sendEmptyMessage(MESSAGETYPE_01);
        }else{
            handler.sendEmptyMessage((MESSAGETYPE_02));
        }
    }

    /**
     * 验证手机格式
     */
    public  boolean isMobileNO(String mobiles) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }


}
