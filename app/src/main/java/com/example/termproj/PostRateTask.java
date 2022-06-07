package com.example.termproj;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class PostRateTask extends AsyncTask<String, Void, String> {


    private String str;
    private String uid;
    String APIkey="CPB5QJ3-9RN4SXB-G2MPDV9-MZ7MAST";
    String serverURL="termproject-haksik.herokuapp.com";
    @Override
    protected String doInBackground(String... params) {
        URL url = null;
        String rcode="";
        str=str.replaceAll("&","and");

        try {

            url = new URL("http://"+serverURL+"/rate?apikey="+APIkey+"&menu='"+str+"'&uid="+uid); // 서버 URL
            Log.e("URL:",url.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");


            conn.setRequestProperty("User-Agent", "Mozilla/9.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");

            conn.connect();
            //conn.setRequestProperty("x-waple-authorization", clientKey);
            int code=conn.getResponseCode();
            rcode=""+code;
            if (code==200) {
                Log.i("POST결과","StatusCode:200");
            } else {
                Log.i("결과", conn.getResponseCode() + "Error");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rcode;
    }
    PostRateTask(String s, String uid){
        str=s;
        this.uid=uid;
    }

}