package com.team3.classical.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.team3.classical.slidingtabs.R;

/**
 * Created by clevo on 2015/2/26.
 */
public class ClassSync  extends Activity {
    private static final String TAG = ClassSync.class.getSimpleName();
    static int instance = 0;
    LinearLayout ll1;
    LinearLayout ll2;
    LinearLayout ll6;
    LinearLayout ll7;
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_calendar_ltl);
        ll1 = (LinearLayout)findViewById(R.id.ll1);
        ll2 = (LinearLayout)findViewById(R.id.ll2);
        LinearLayout ll3 = (LinearLayout)findViewById(R.id.ll3);
        LinearLayout ll4 = (LinearLayout)findViewById(R.id.ll4);
        LinearLayout ll5 = (LinearLayout)findViewById(R.id.ll5);
        ll6 = (LinearLayout)findViewById(R.id.ll6);
        ll7 = (LinearLayout)findViewById(R.id.ll7);
        //setClass(ll1, "", "", "", "", 2, 0);

    }

    public void startListener(View v) {
      /**  Button sync = (Button) v.findViewById(R.id.button);
        sync.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                syncCourses(courseNames, courseTime);
            }
        });**/
    }

    private int colors[] = {
            Color.rgb(0xee,0xff,0xff),
            Color.rgb(0xf0,0x96,0x09),
            Color.rgb(0x8c,0xbf,0x26),
            Color.rgb(0x00,0xab,0xa9),
            Color.rgb(0x99,0x6c,0x33),
            Color.rgb(0x3b,0x92,0xbc),
            Color.rgb(0xd5, 0x4d, 0x34),
            Color.rgb(0xcc,0xcc,0xcc)
    };

    //public void syncCourses(char[][] names, int[] time)
    /*public void syncCourses() {
        LinearLayout ll1 = (LinearLayout)findViewById(R.id.ll1);
        LinearLayout ll2 = (LinearLayout)findViewById(R.id.ll2);
        LinearLayout ll3 = (LinearLayout)findViewById(R.id.ll3);
        LinearLayout ll4 = (LinearLayout)findViewById(R.id.ll4);
        LinearLayout ll5 = (LinearLayout)findViewById(R.id.ll5);
        LinearLayout ll6 = (LinearLayout)findViewById(R.id.ll6);
        LinearLayout ll7 = (LinearLayout)findViewById(R.id.ll7);
        //TODO adding classes and blanks
        setClass(ll1, "", "", "", "", 2, 0);
        setClass(ll1, "CS180", "LAWN B158", "Week 1-15 Three times per week", "9:50-11:25", 2, 1);
        setNoClass(ll1, 3, 0);
        setClass(ll1, "STAT350", "BEERING 2260", "Week 1-15, Twice per week", "14:55-17:25", 3, 2);
        setNoClass(ll1, 1, 0);
        setClass(ll1, "CHEM 115", "EE 107", "Week 1-15 Three times per week", "19:00-20:30", 2, 4);
        setNoClass(ll1, 1, 0);

        setClass(ll2, "ENGL 106", "BEERING 1060", "Week 1-15 Three times per week", "8:00-9:35", 2, 3);
        setClass(ll2, "MA 351", "MATH 2301", "Week 1-15 Three times per week", "9:50-12:15", 3, 5);
        setNoClass(ll2, 3, 0);
        setClass(ll2, "COM 217", "HILLTOP 32-18", "Week 1-15, Twice per week", "15:45-17:25", 2, 6);
        setNoClass(ll2, 1, 0);
        setClass(ll2, "SOC 100", "LILY 1105", "Week 1-15, Twice per week", "19:00-21:25", 3, 1);

        setNoClass(ll6, 14, 0);
        setNoClass(ll7, 14, 0);
    }*/

    void setClass(LinearLayout ll, String title, String place,
                  String last, String time, int classes, int color)
    {
        View view = LayoutInflater.from(this).inflate(R.layout.item2, null);
        view.setMinimumHeight(dip2px(this,classes * 48));
        view.setBackgroundColor(colors[color]);
        ((TextView)view.findViewById(R.id.title)).setText(title);
        ((TextView)view.findViewById(R.id.place)).setText(place);
        ((TextView)view.findViewById(R.id.last)).setText(last);
        ((TextView)view.findViewById(R.id.time)).setText(time);
        view.setOnClickListener(new OnClickClassListener());
        TextView blank1 = new TextView(this);
        TextView blank2 = new TextView(this);
        blank1.setHeight(dip2px(this,classes));
        blank2.setHeight(dip2px(this,classes));
        ll.addView(blank1);
        ll.addView(view);
        ll.addView(blank2);


    }

    void setNoClass(LinearLayout ll,int classes, int color)
    {
        TextView blank = new TextView(this);
        if(color == 0)
            blank.setMinHeight(dip2px(this,classes * 50));
        blank.setBackgroundColor(colors[color]);
        ll.addView(blank);
    }

    class OnClickClassListener implements View.OnClickListener {

        public void onClick(View v) {
            String title;
            title = (String) ((TextView)v.findViewById(R.id.title)).getText();
            Toast.makeText(getApplicationContext(), "You clicked on " + title,
                    Toast.LENGTH_SHORT).show();
        }
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);}
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);}

}