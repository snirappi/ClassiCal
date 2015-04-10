package com.team3.classical.activities;

import com.team3.classical.activities.ClassSync.OnClickClassListener;
import com.team3.classical.slidingtabs.AddEventActivityltl;
import com.team3.classical.slidingtabs.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class EventShowLtlActivity extends Activity {

	  private int colors[] = {
	            Color.rgb(0xff,0xff,0xff),
	            Color.rgb(0xf0,0x96,0x09),
	            Color.rgb(0x8c,0xbf,0x26),
	            Color.rgb(0x00,0xab,0xa9),
	            Color.rgb(0x99,0x6c,0x33),
	            Color.rgb(0x3b,0x92,0xbc),
	            Color.rgb(0xd5, 0x4d, 0x34),
	            Color.rgb(0xcc,0xcc,0xcc)
	    };
	private Intent intent;
	private String title;
	private String startTime;
	private String endtime;
	private int evetheight;
	private int intervervalHeight;
	private LinearLayout ll1;
	private LinearLayout ll2;
	private LinearLayout ll3;
	private LinearLayout ll4;
	private LinearLayout ll5;
	private LinearLayout ll6;
	private LinearLayout ll7;
	private String chooseWeekStr;

	 String[] mItems = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
	private LinearLayout fillLayout;
	private SharedPreferences mySharedPreferences;

	 @Override public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.event_calendar_ltl);
	        
	        mySharedPreferences= getSharedPreferences("events", 
					Activity.MODE_PRIVATE); 
	        intent = getIntent();
	        title = mySharedPreferences.getString("title","event");
	        startTime = mySharedPreferences.getString("startTime","6");
	        endtime = mySharedPreferences.getString("endTime","8");
	        
	        chooseWeekStr = mySharedPreferences.getString("chooseWeekStr","mondy");
	        
	        evetheight = mySharedPreferences.getInt("eventHeight", 1);
	        evetheight = evetheight+1;
	        intervervalHeight = mySharedPreferences.getInt("intervervalHeight", 1);
	        ll1 = (LinearLayout)findViewById(R.id.ll1);
	        ll2 = (LinearLayout)findViewById(R.id.ll2);
	        ll3 = (LinearLayout)findViewById(R.id.ll3);
	        ll4 = (LinearLayout)findViewById(R.id.ll4);
	        ll5 = (LinearLayout)findViewById(R.id.ll5);
	        ll6 = (LinearLayout)findViewById(R.id.ll6);
	        ll7 = (LinearLayout)findViewById(R.id.ll7);
	        
	        if (chooseWeekStr.equals(mItems[0])){
	        	fillLayout = ll1;
	        } else if (chooseWeekStr.equals(mItems[1])){
	        	fillLayout = ll2;
	        }else if (chooseWeekStr.equals(mItems[2])){
	        	fillLayout = ll3;
	        }else if (chooseWeekStr.equals(mItems[3])){
	        	fillLayout = ll4;
	        }else if (chooseWeekStr.equals(mItems[4])){
	        	fillLayout = ll5;
	        }else if (chooseWeekStr.equals(mItems[5])){
	        	fillLayout = ll6;
	        }else if (chooseWeekStr.equals(mItems[6])){
	        	fillLayout = ll7;
	        }
	        
	        setEvent(fillLayout,title,startTime,endtime,intervervalHeight,evetheight,3);
	    }
	 
	  
	    void setEvent(LinearLayout ll, String title,String startTime, String endTime,
	    		int evetheight,int intervalHeight,int color)
	    {
	        View view = LayoutInflater.from(this).inflate(R.layout.event_item_ltl, null);
	        view.setMinimumHeight(dip2px(this,evetheight*48));
	        view.setBackgroundColor(colors[color]);
	        ((TextView)view.findViewById(R.id.title_event)).setText(title);
	        ((TextView)view.findViewById(R.id.start_time_event)).setText(startTime);
	        ((TextView)view.findViewById(R.id.end_time_event)).setText(endTime);
	        view.setOnClickListener(new OnClickClassListener());
	        
	        TextView blank1 = new TextView(this);
	        TextView blank2 = new TextView(this);
	        blank1.setHeight(dip2px(this,intervalHeight*48));
	        blank2.setHeight(dip2px(this,48));
	        ll.addView(blank1);
	        ll.addView(view);
	        ll.addView(blank2);
	    }

	    void setNoEvent(LinearLayout ll,View v, int color)
	    {
	        ll.removeView(v);
	       
	    }

	    class OnClickClassListener implements View.OnClickListener {

	        public void onClick(View v) {
	        	setNoEvent(fillLayout,v,0);
	        	Intent intent = new Intent(EventShowLtlActivity.this,updateEventltlAcitivity.class);
	        	
	        	startActivity(intent);
	        }
	    }

	    public static int dip2px(Context context, float dpValue) {
	        final float scale = context.getResources().getDisplayMetrics().density;
	        return (int) (dpValue * scale + 0.5f);}
	    public static int px2dip(Context context, float pxValue) {
	        final float scale = context.getResources().getDisplayMetrics().density;
	        return (int) (pxValue / scale + 0.5f);}

}
