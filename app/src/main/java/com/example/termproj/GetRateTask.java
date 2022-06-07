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

public class GetRateTask extends AsyncTask<String, Void, String> {

    //String clientKey = "#########################";
    private String str, receiveMsg;
    //private final String ID = "########";
    ArrayList<String> data;
    ArrayList<FoodDTO> newData;
    @Override
    protected String doInBackground(String... params) {
        URL url = null;
        String APIkey="CPB5QJ3-9RN4SXB-G2MPDV9-MZ7MAST";
        String serverURL="termproject-haksik.herokuapp.com";
        String s="";
        for(int i=0;i<newData.size();i++){
            s+="\'";
            s+=newData.get(i).getContent_name().replace("&","and");
            if(i==newData.size()-1){
                s+="\'";
                break;
            }
            s+="\',";
        }

        Log.e("parsing",s);
        try {

            url = new URL("https://"+serverURL+"/rate?apikey="+APIkey+"&content="+s); // 서버 URL
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
    public void dataTransfer(ArrayList<FoodDTO> data){
        this.newData=data;
    }

    public ArrayList<FoodDTO> dataParse(String s){
        try{
            ArrayList<FoodDTO> list=new ArrayList<FoodDTO>();
            JSONObject obj;
            JSONArray arr=new JSONArray(s);
            String[] jsonName={"content_name","total_star"};
            //String[][] parseredData=new String[arr.length()][jsonName.length];
            for(int i=0;i<arr.length();i++){
                obj=arr.getJSONObject(i);
                Log.e("debug",obj.getString(jsonName[0]));
                if(obj!=null){
                    for(int j=0;j<newData.size();j++){
                        if(newData.get(j).getContent_name().equals(obj.getString(jsonName[0]))){
                            newData.get(j).setTotal_star(obj.getInt(jsonName[1]));
                        }
                        else if(newData.get(j).getContent_name().contains("&")){
                            if(newData.get(j).getContent_name().replace("&","").equals(obj.getString(jsonName[0]).replace("and",""))){
                                newData.get(j).setTotal_star(obj.getInt(jsonName[1]));
                            }
                        }
                    }
                }
            }
            return newData;
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}