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
import java.util.concurrent.ExecutionException;
import java.util.zip.Inflater;

public class TodayActivity extends Activity {

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
        likes_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TodayActivity.this,"TEST"+tv2.getText()+" OK", Toast.LENGTH_SHORT).show();
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
