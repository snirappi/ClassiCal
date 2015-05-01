package com.team3.classical.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.team3.classical.slidingtabs.R;

public class EventShowLtlActivity extends Activity {

    private int colors[] = {
            Color.rgb(0xff, 0xff, 0xff),
            Color.rgb(0xf0, 0x96, 0x09),
            Color.rgb(0x8c, 0xbf, 0x26),
            Color.rgb(0x00, 0xab, 0xa9),
            Color.rgb(0x99, 0x6c, 0x33),
            Color.rgb(0x3b, 0x92, 0xbc),
            Color.rgb(0xd5, 0x4d, 0x34),
            Color.rgb(0xcc, 0xcc, 0xcc)
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

    String[] mItems = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"};
    private LinearLayout fillLayout;
    private SharedPreferences mySharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_calendar_ltl);

        LinearLayout ll1 = (LinearLayout) findViewById(R.id.ll1);
        LinearLayout ll2 = (LinearLayout) findViewById(R.id.ll2);
        LinearLayout ll3 = (LinearLayout) findViewById(R.id.ll3);
        LinearLayout ll4 = (LinearLayout) findViewById(R.id.ll4);
        LinearLayout ll5 = (LinearLayout) findViewById(R.id.ll5);
        LinearLayout ll6 = (LinearLayout) findViewById(R.id.ll6);
        LinearLayout ll7 = (LinearLayout) findViewById(R.id.ll7);
        
        setNoClass(ll2, 2, 0);
        setClass(ll2, "EAPS 10400-001", "12738 Class", "10:30am-11:45am", "PHYS 203", 5, 1);
        setNoClass(ll2, 1, 0);
        setClass(ll2, "MA 35100-021", "22126 Class", "12:00pm-1:15pm", "UNIV 103", 5, 2);
        setNoClass(ll2, 1, 0);
        setClass(ll2, "CS 25200-LE1", "52938 Class", "1:30pm-2:45pm", "LILY G126", 5, 3);
        setNoClass(ll2, 1, 0);
        setClass(ll2, "CS 30700-LE1", "43855 Class", "3:00pm-4:15pm", "WTHR 172", 5, 4);
        setNoClass(ll2, 1, 0);
        setClass(ll2, "MA 41600-161", "43158 Class", "4:30pm-5:45pm", "REC 123", 5, 5);
        setNoClass(ll2, 1, 0);

        setNoClass(ll3, 14, 0);
        setClass(ll3, "CS 25200-L03", "69083 Class", "1:30pm-3:20pm", "LWSN B148", 7, 5);

        setNoClass(ll4, 2, 0);
        setClass(ll4, "EAPS 10400-001", "12738 Class", "10:30am-11:45am", "PHYS 203", 5, 1);
        setNoClass(ll4, 1, 0);
        setClass(ll4, "MA 35100-021", "22126 Class", "12:00pm-1:15pm", "UNIV 103", 5, 2);
        setNoClass(ll4, 1, 0);
        setClass(ll4, "CS 25200-LE1", "52938 Class", "1:30pm-2:45pm", "LILY G126", 5, 3);
        setNoClass(ll4, 1, 0);
        setClass(ll4, "CS 30700-LE1", "43855 Class", "3:00pm-4:15pm", "WTHR 172", 5, 4);
        setNoClass(ll4, 1, 0);
        setClass(ll4, "MA 41600-161", "43158 Class", "4:30pm-5:45pm", "REC 123", 5, 5);
        setNoClass(ll4, 1, 0);
    }


    void setClass(LinearLayout ll, String title, String place,
                  String last, String time, int classes, int color) {
        View view = LayoutInflater.from(this).inflate(R.layout.item2, null);
        view.setMinimumHeight(dip2px(this, classes * 25));
        view.setBackgroundColor(colors[color]);
        ((TextView) view.findViewById(R.id.title)).setText(title);
        ((TextView) view.findViewById(R.id.place)).setText(place);
        ((TextView) view.findViewById(R.id.last)).setText(last);
        ((TextView) view.findViewById(R.id.time)).setText(time);
        
        view.setOnClickListener(new OnClickClassListener());
       /* TextView blank1 = new TextView(this);
        TextView blank2 = new TextView(this);
        blank1.setHeight(dip2px(this, classes));
        blank2.setHeight(dip2px(this, classes));*/
        //ll.addView(blank1);
        ll.addView(view);
        //ll.addView(blank2);
    }


    void setNoClass(LinearLayout ll, int classes, int color) {
        TextView blank = new TextView(this);
        if (color == 0)
            blank.setMinHeight(dip2px(this, classes * 25));
        blank.setBackgroundColor(Color.parseColor("#F0FFFF"));
        ll.addView(blank);
    }

    class OnClickClassListener implements OnClickListener {

        public void onClick(View v) {
            
            String title;
            title = (String) ((TextView) v.findViewById(R.id.title)).getText();
            Toast.makeText(getApplicationContext(), "chick is:" + title,
                    Toast.LENGTH_SHORT).show();
        }
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}