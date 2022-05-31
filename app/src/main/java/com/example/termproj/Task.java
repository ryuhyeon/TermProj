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
import java.util.Arrays;

public class Task extends AsyncTask<String, Void, String> {

    //String clientKey = "#########################";
    private String str, receiveMsg;
    //private final String ID = "########";

    @Override
    protected String doInBackground(String... params) {
        URL url = null;
        String APIkey="CPB5QJ3-9RN4SXB-G2MPDV9-MZ7MAST";
        try {
            url = new URL("http://haksik.us-west-2.elasticbeanstalk.com/get/haksik_data?apikey="+APIkey); // 서버 URL

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");


            conn.setRequestProperty("User-Agent", "Mozilla/9.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");

            conn.connect();
            //conn.setRequestProperty("x-waple-authorization", clientKey);
            int code=conn.getResponseCode();
            if (code==200) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();
                Log.i("receiveMsg : ", receiveMsg);

                reader.close();
            } else {
                Log.i("결과", conn.getResponseCode() + "Error");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return receiveMsg;
    }
    public static String StringReplace(String str){
        String match = "[\\[\\] \"]";
        str = str.replaceAll(match, "");
        return str;
    }
    public static ArrayList<String>[] convertData(String str){
        ArrayList<String>[] day_data=new ArrayList[5];

        String[] day=str.split("],");

        for(int i=0;i<day.length;i++){
            Log.e("DATA : ",day[i]);
            day[i]=StringReplace(day[i]);
            String[] sp=day[i].split(",");
            for(String z :sp){
                Log.i("DEBUG : ",z);
            }
            day_data[i]= new ArrayList<String>(Arrays.asList(sp));
        };
        for(int i=0;i<day.length;i++){
            for(int j=0;j<day_data[i].size();j++){
                Log.e("DAY"+i+" : ",day_data[i].get(j));
            }
        }
        Log.e("TEST : ",day_data[0].get(0));
        return day_data;
    }
    public static ArrayList<rateFood>[] newConvertData(ArrayList<String>[] list){
        ArrayList<rateFood>[] data=new ArrayList[5];
        for(int i=0;i<list.length;i++)
            data[i]=new ArrayList<rateFood>();




        for(int i=0;i<list.length;i++){
            for(int j=0;j<list[i].size();j++){
                rateFood obj=new rateFood();
                obj.setContent_name(list[i].get(j));
                obj.setTotal_star(0);
                data[i].add(obj);
            }
        }

        return data;
    }
}