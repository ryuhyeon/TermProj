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

public class RankingTask extends AsyncTask<String, Void, String> {

    //String clientKey = "#########################";
    private String str, receiveMsg;
    //private final String ID = "########";
    ArrayList<String> data;
    ArrayList<rateFood> newData;
    @Override
    protected String doInBackground(String... params) {
        URL url = null;
        String APIkey="CPB5QJ3-9RN4SXB-G2MPDV9-MZ7MAST";
        String serverURL="termproject-haksik.herokuapp.com";


        try {

            url = new URL("https://"+serverURL+"/rateranking?apikey="+APIkey); // 서버 URL
            Log.e("DEBUG SEND",url.toString());
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

    public ArrayList<rateFood> dataParse(String s){
        try{
            ArrayList<rateFood> list=new ArrayList<rateFood>();
            JSONObject obj;
            JSONArray arr=new JSONArray(s);
            String[] jsonName={"content_name","total_star"};
            for(int i=0;i<arr.length();i++){
                obj=arr.getJSONObject(i);
                Log.e("debug",obj.getString(jsonName[0]));
                if(obj!=null){
                    rateFood food=new rateFood();
                    if(obj.getString(jsonName[0]).contains("and")){
                        food.setContent_name(obj.getString(jsonName[0]).replace("and","&"));
                    }else {
                        food.setContent_name(obj.getString(jsonName[0]));
                    }
                    food.setTotal_star(obj.getInt(jsonName[1]));
                    list.add(food);
                }
            }
            return list;
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}