package com.example.termproj;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.*;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.zip.Inflater;

public class TodayActivity extends Activity {
    public static Context context_main;
    private static final int PERMISSIONS_REQUEST_CODE = 22;
    ArrayList<rateFood> ratelist;
    ArrayList<rateFood>[] data_list;
    String uid="";
    Calendar c=Calendar.getInstance();
    int d=c.get(Calendar.DAY_OF_WEEK)-1;
    //int d=3;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);
        context_main=this;
        Log.e("요일",""+d);
        if(chkPermission()){
            // 휴대폰 정보는 TelephonyManager 를 이용
            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            // READ_PHONE_NUMBERS 또는 READ_PHONE_STATE 권한을 허가 받았는지 확인
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            uid= tm.getLine1Number();
        }
        if(d>4) {
            LinearLayout container = findViewById(R.id.container);
            RelativeLayout newRow = new RelativeLayout(this);
            newRow.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            TextView tv2 = new TextView(this);
            tv2.setText("주말엔 식당을 운영하지 않습니다.");
            tv2.setGravity(Gravity.CENTER);
            //tv2.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
            tv2.setTextColor(Color.BLACK);
            tv2.setPadding(0, 10, 0, 0);
            RelativeLayout.LayoutParams tv2params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            tv2params.width = LinearLayout.LayoutParams.WRAP_CONTENT;
            tv2params.addRule(RelativeLayout.CENTER_IN_PARENT);
            tv2params.leftMargin = 20;
            tv2params.rightMargin = 20;
            newRow.addView(tv2, tv2params);
            container.addView(newRow, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
        }else{
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
            data_list=Task.newConvertData(day_data);
            //데이터 가져오기 완료
            //추천 데이터 가져오기
            String rateResult="[NULL]";
            rateTask rate=new rateTask();
            Log.e("DT : ",data_list[d].toString());
            rate.dataTransfer(data_list[d]);
            try {
                rateResult = rate.execute().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            Log.e("DEBUG RES","\'"+rateResult+"\'");
            if(rateResult!=null){
                //Log.e("rateDATA",rateResult);
                data_list[d] = rate.dataParse(rateResult);
                for(int i=0;i<data_list[d].size();i++){
                    Log.e("parsered","name:"+data_list[d].get(i).getContent_name()+"   star : "+data_list[d].get(i).getTotal_star());
                }
            }

            //추천 데이터 종료
            //줄 추가
            for(int i=0;i<data_list[d].size();i++){
                addLine(data_list[d].get(i),i);
            }
        }
        Button weekBtn=(Button)findViewById(R.id.weekBtn);
        weekBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loading = new Intent(TodayActivity.this, WeekActivity.class);

                startActivity(loading);
            }
        });
        Button rankBtn=(Button)findViewById(R.id.rank);
        rankBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent popup = new Intent(TodayActivity.this, RankingActivity.class);
                startActivity(popup);
            }
        });
    }
    private void addLine(rateFood data,int i){
        LinearLayout container= findViewById(R.id.container);

        RelativeLayout newRow=new RelativeLayout(this);
        //newRow.setOrientation(LinearLayout.HORIZONTAL);
        newRow.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        TextView tv1=new TextView(this);
        tv1.setText("◎");
        tv1.setGravity(Gravity.CENTER);
        //tv1.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        tv1.setTextColor(Color.BLACK);
        tv1.setPadding(0,10,0,0);
        RelativeLayout.LayoutParams tv1params=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        tv1params.width= LinearLayout.LayoutParams.WRAP_CONTENT;
        tv1params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        TextView tv2=new TextView(this);
        tv2.setText(data.getContent_name());
        tv2.setGravity(Gravity.CENTER);
        //tv2.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        tv2.setTextColor(Color.BLACK);
        tv2.setPadding(0,10,0,0);
        RelativeLayout.LayoutParams tv2params=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        tv2params.width=LinearLayout.LayoutParams.WRAP_CONTENT;
        tv2params.addRule(RelativeLayout.CENTER_IN_PARENT);
        tv2params.leftMargin=20;
        tv2params.rightMargin=20;
        LayoutInflater inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout likes_form=(LinearLayout) inflater.inflate(R.layout.likes_form,null);
        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params.width=150;
        params.height=110;

        TextView num=new TextView(this);
        /*
        for(int j=0;j<data_list[d].size();j++){
            if(tv2.getText().equals(data_list[d].get(j).getContent_name())){
                num.setText(""+data_list[d].get(j).getTotal_star());
            }else{
                num.setText("0");
            }
        }
         */
        num.setText(""+data.getTotal_star());
        num.setTextSize(13);
        num.setWidth(70);
        num.setPadding(10,8,0,0);
        num.setGravity(View.TEXT_ALIGNMENT_TEXT_START);

        likes_form.addView(num);
        likes_form.setOnClickListener(view -> {
            if(likes_form.isFocusable()==false){
                Toast.makeText(this,"하루 한번만 추천 가능합니다.",Toast.LENGTH_SHORT).show();

            }else {
                final String[] rcode = {""};
                new Thread(() -> {
                    rcode[0] = new UpdateRate(tv2.getText().toString(), uid).doInBackground();
                    if (rcode[0].equals("200")) {
                        likes_form.setFocusable(false);
                        Handler mHandler = new Handler(Looper.getMainLooper());
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // 사용하고자 하는 코드
                                Toast.makeText(TodayActivity.this, "" + tv2.getText() + " 추천했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }, 0);


                        num.setText("" + (Integer.parseInt((String) num.getText()) + 1));
                    } else {
                        Handler mHandler = new Handler(Looper.getMainLooper());
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // 사용하고자 하는 코드
                                Toast.makeText(TodayActivity.this, "Error occured", Toast.LENGTH_SHORT).show();
                            }
                        }, 0);

                    }
                }).start();
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



    public boolean chkPermission() {
        // 위험 권한을 모두 승인했는지 여부
        boolean mPermissionsGranted = false;
        String[] mRequiredPermissions = new String[1];
        // 사용자의 안드로이드 버전에 따라 권한을 다르게 요청합니다
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // 11 이상인 경우
            mRequiredPermissions[0] = Manifest.permission.READ_PHONE_NUMBERS;

        }else{
            // 10 이하인 경우
            mRequiredPermissions[0] = Manifest.permission.READ_PRECISE_PHONE_STATE;
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 필수 권한을 가지고 있는지 확인한다.
            mPermissionsGranted = hasPermissions(mRequiredPermissions);

            // 필수 권한 중에 한 개라도 없는 경우
            if (!mPermissionsGranted) {
                // 권한을 요청한다.
                ActivityCompat.requestPermissions(this, mRequiredPermissions, PERMISSIONS_REQUEST_CODE);
            }
        } else {
            mPermissionsGranted = true;
        }

        return mPermissionsGranted;
    }
    public boolean hasPermissions(String[] permissions) {
        // 필수 권한을 가지고 있는지 확인한다.
        for (String permission : permissions) {
            if (checkCallingOrSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

}
