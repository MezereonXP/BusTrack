package com.example.mezereon.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.mezereon.Home.HomeActivity;
import com.example.mezereon.R;
import com.jakewharton.rxbinding.view.RxView;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final Intent intent=new Intent();
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        ButterKnife.bind(this);

        RxView.clicks(btn_login).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                intent.setClass(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        RxView.clicks(btn_getConfirm).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {

            }
        });
    }
}
