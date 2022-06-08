package com.example.termproj;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RankingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
       //getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.popup_layout);


        String resultStr="[NULL]";
        ArrayList<FoodDTO> ranking=null;
        try {
            resultStr=new RankingTask().execute().get();
            ranking=new RankingTask().dataParse(resultStr);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        setTitle("메뉴 추천 랭킹");
        Log.e("size",ranking.size()+"");
        for(int i=0;i<ranking.size();i++){
            Log.e("dvdvd",ranking.get(i).getContent_name()+ranking.get(i).getTotal_star());
            addLine(ranking.get(i),i);
        }
    }
    private void addLine(FoodDTO data, int i){
        LinearLayout container= findViewById(R.id.container);

        RelativeLayout newRow=new RelativeLayout(this);
        //newRow.setOrientation(LinearLayout.HORIZONTAL);
        newRow.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        TextView tv1=new TextView(this);
        tv1.setText((i+1)+"위 :");
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
