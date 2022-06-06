package com.example.termproj;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PopupActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
       //getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.popup_layout);
        ArrayList<rateFood>[] data_list=((TodayActivity)TodayActivity.context_main).data_list;
        Intent intent=getIntent();
        int d=intent.getIntExtra("day",-1);
        String t="";
        switch(d){
            case 0:
                t="월요일";
                break;
            case 1:
                t="화요일";
                break;
            case 2:
                t="수요일";
                break;
            case 3:
                t="목요일";
                break;
            case 4:
                t="금요일";
                break;
        }
        setTitle(t);
        for(int i=0;i<data_list[d].size();i++){
            addLine(data_list[d].get(i),i);
        }

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


        newRow.addView(tv1,tv1params);

        newRow.addView(tv2,tv2params);
        newRow.addView(likes_form,params);

        container.addView(newRow,new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));


    }

}
