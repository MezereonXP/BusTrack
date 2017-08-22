package com.example.mezereon.Tool;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by 嚴 on 2017/8/21.
 */

public  class API {

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    static public String isRegisterForHttpGet(String mobile) throws ClientProtocolException, IOException {

        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String path="http://mezereon.gotoip2.com/bustrackapi/isRegister.php";
        String uri=path+"?phone="+mobile;

        String result="";
        HttpGet httpGet=new HttpGet(uri);
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse httpResponse = httpclient.execute(httpGet);
        Log.d("tag", "succes");
        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            result = EntityUtils.toString(httpResponse.getEntity());
        }else {
            result = "error";
        }
        return result;
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    static public String registerForHttpGet(String name, String phone, String number, String sex) throws ClientProtocolException, IOException {

        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String path="http://mezereon.gotoip2.com/bustrackapi/addUser.php";
        String uri=path+"?phone="+phone+"&name="+name+"&number="+number+"&sex="+sex;
        String result="";
        HttpGet httpGet=new HttpGet(uri);
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse httpResponse = httpclient.execute(httpGet);
        Log.d("tag", "succes");
        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            result = EntityUtils.toString(httpResponse.getEntity());
        }else {
            result = "error";
        }
        return result;
    }
}
