package com.example.termproj;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {
    static Calendar c=Calendar.getInstance();
    static int d=c.get(Calendar.DAY_OF_WEEK)-1;
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        RemoteViews widget=new RemoteViews(context.getPackageName(),R.layout.new_app_widget);
        String s="";
        ArrayList<FoodDTO>[] data_list=((TodayActivity)TodayActivity.context_main).data_list;
        if(data_list!=null)
        {
            for(int i=0;i<data_list[d].size();i++) {
                s+=data_list[d].get(i).getContent_name();
                s+="\n";
            }
        }
        widget.setTextViewText(R.id.text,s);

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
            int appWidgetId) {

        RemoteViews widget=new RemoteViews(context.getPackageName(),R.layout.new_app_widget);
        String s="";
        ArrayList<FoodDTO>[] data_list=((TodayActivity)TodayActivity.context_main).data_list;
        if(data_list!=null)
        {
            for(int i=0;i<data_list[d].size();i++) {
                s+=data_list[d].get(i).getContent_name();
                s+="\n";
            }
        }
        widget.setTextViewText(R.id.text,s);
        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        //views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);


    }
/*
    private void addLine(rateFood data,int i,Context c,RemoteViews w){


       

        
        RemoteViews tv2=new TextView(c);

        tv2.setText(data.getContent_name());
        tv2.setGravity(Gravity.CENTER);
        //tv2.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);

        tv2.setPadding(0,10,0,0);
        RelativeLayout.LayoutParams tv2params=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        tv2params.width=LinearLayout.LayoutParams.WRAP_CONTENT;
        tv2params.addRule(RelativeLayout.CENTER_IN_PARENT);
        tv2params.leftMargin=20;
        tv2params.rightMargin=20;








        w.addView(R.id.wgcontainer,(RemoteViews) tv2);





    }
    */
}