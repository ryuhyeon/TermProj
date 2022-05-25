package com.example.termproj;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.*;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.zip.Inflater;

public class TodayActivity extends Activity {
    ArrayList<rateFood> ratelist;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);
        //데이터 가져오기 시작
        String resultText = "[NULL]";
        try {
            resultText = new Task().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //Log.e("[DATA]",resultText);
        ArrayList<String>[] day_data=Task.convertData(resultText);
        //데이터 가져오기 완료
        //추천 데이터 가져오기
        String rateResult="[NULL]";
        rateTask rate=new rateTask();
        rate.dataTransfer(day_data[2]);
        try {
            rateResult = rate.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.e("rateDATA",rateResult);
        ratelist = rateTask.dataParse(rateResult);
        for(int i=0;i<ratelist.size();i++){
            Log.e("parsered","name:"+ratelist.get(i).getContent_name()+"   star : "+ratelist.get(i).getTotal_star());
        }
        //추천 데이터 종료
        //줄 추가
        for(int i=0;i<day_data[2].size();i++){
            addLine(day_data[2].get(i),i);
        }


    }
    private void addLine(String data,int i){
        LinearLayout container=(LinearLayout) findViewById(R.id.container);

        LinearLayout newRow=new LinearLayout(this);
        newRow.setOrientation(LinearLayout.HORIZONTAL);
        newRow.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        TextView tv1=new TextView(this);
        tv1.setText("◎");
        tv1.setGravity(Gravity.CENTER);
        tv1.setTextColor(Color.BLACK);
        LinearLayout.LayoutParams tv1params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        tv1params.width=50;
        TextView tv2=new TextView(this);
        tv2.setText(data);
        tv2.setGravity(Gravity.CENTER);
        tv2.setTextColor(Color.BLACK);
        LinearLayout.LayoutParams tv2params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        tv2params.width=480;
        LayoutInflater inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout likes_form=(LinearLayout) inflater.inflate(R.layout.likes_form,null);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        params.width=150;
        params.height=110;

        TextView num=new TextView(this);
        for(int j=0;j<ratelist.size();j++){
            if(tv2.getText().equals(ratelist.get(j).getContent_name())){
                num.setText(""+ratelist.get(j).getTotal_star());
            }else{
                num.setText("0");
            }
        }
        num.setTextSize(10);
        num.setWidth(70);
        num.setPadding(10,4,0,0);
        num.setGravity(View.TEXT_ALIGNMENT_TEXT_START);

        likes_form.addView(num);
        likes_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                likes_form.setClickable(false);
                Toast.makeText(TodayActivity.this,"TEST"+tv2.getText()+" OK", Toast.LENGTH_SHORT).show();
                num.setText(""+(Integer.parseInt((String) num.getText())+1));
            }
        });

        newRow.addView(tv1,tv1params);
        newRow.addView(tv2,tv2params);
        newRow.addView(likes_form,params);
        container.addView(newRow,new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));


    }
}
