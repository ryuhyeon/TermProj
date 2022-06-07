package com.example.termproj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class WeekActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week);
        Button dayBtn=(Button)findViewById(R.id.todayBtn);
        dayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ArrayList<FoodDTO>[] data_list=((TodayActivity)TodayActivity.context_main).data_list;

        //Toast.makeText(this,data_list[1].get(0).getContent_name(),Toast.LENGTH_SHORT).show();
        Button[] btn=new Button[5];
        btn[0]=(Button) findViewById(R.id.Mon);
        btn[1]=(Button) findViewById(R.id.Tue);
        btn[2]=(Button) findViewById(R.id.Wen);
        btn[3]=(Button) findViewById(R.id.Thr);
        btn[4]=(Button) findViewById(R.id.Fri);
        for(int i=0;i<btn.length;i++){
            int finalI = i;
            btn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent popup = new Intent(WeekActivity.this, PopupActivity.class);
                    popup.putExtra("day",finalI);
                    startActivity(popup);
                }
            });
        }
        Button rankBtn=(Button)findViewById(R.id.rank);
        rankBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent popup = new Intent(WeekActivity.this, RankingActivity.class);
                startActivity(popup);
            }
        });
    }

}