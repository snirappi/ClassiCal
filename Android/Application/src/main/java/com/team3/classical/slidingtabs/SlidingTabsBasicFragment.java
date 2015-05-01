/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.team3.classical.slidingtabs;

import com.team3.classical.activities.ChatSender;
import com.team3.classical.activities.ListViewActivity;
import com.team3.classical.activities.SampleActivityBase;
import com.team3.classical.slidingtabs.R;
import com.team3.classical.view.SlidingTabLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import java.util.List;

import static com.team3.classical.slidingtabs.R.layout.fragment_sample;

/**
 * A basic sample which shows how to use {@link com.team3.classical.view.SlidingTabLayout}
 * to display a custom {@link ViewPager} title strip which gives continuous feedback to the user
 * when scrolling.
 */
public class SlidingTabsBasicFragment extends Fragment {
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
    public static final String TAG = "SlidingTabsBasic";
    SampleActivityBase app;
    ChatSender cs = new ChatSender();
    ListViewActivity lva;// = new ListViewActivity();
    static final String LOG_TAG = "SlidingTabsBasicFragment";
    static final String[] titles = {"Calendar","Chat","Forum"};
    /**
     * A custom {@link ViewPager} title strip which looks much like Tabs present in Android v4.0 and
     * above, but is designed to give continuous feedback to the user when scrolling.
     */
    private SlidingTabLayout mSlidingTabLayout;

    /**
     * A {@link ViewPager} which will be used in conjunction with the {@link SlidingTabLayout} above.
     */
    private ViewPager mViewPager;

     public void setActivity(SampleActivityBase sab){
           app = sab;
     }
    /**
     * Inflates the {@link View} which will be displayed by this {@link Fragment}, from the app's
     * resources.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(fragment_sample, container, false);
    }

    // BEGIN_INCLUDE (fragment_onviewcreated)
    /**
     * This is called after the {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)} has finished.
     * Here we can pick out the {@link View}s we need to configure from the content view.
     *
     * We set the {@link ViewPager}'s adapter to be an instance of {@link SamplePagerAdapter}. The
     * {@link SlidingTabLayout} is then given the {@link ViewPager} so that it can populate itself.
     *
     * @param view View created in {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // BEGIN_INCLUDE (setup_viewpager)
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setAdapter(new SamplePagerAdapter());
        // END_INCLUDE (setup_viewpager)

        // BEGIN_INCLUDE (setup_slidingtablayout)
        // Give the SlidingTabLayout the ViewPager, this must be done AFTER the ViewPager has had
        // it's PagerAdapter set.
        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(mViewPager);
        // END_INCLUDE (setup_slidingtablayout)
    }
    // END_INCLUDE (fragment_onviewcreated)

    /**
     * The {@link android.support.v4.view.PagerAdapter} used to display pages in this sample.
     * The individual pages are simple and just display two lines of text. The important section of
     * this class is the {@link #getPageTitle(int)} method which controls what is displayed in the
     * {@link SlidingTabLayout}.
     */
    class SamplePagerAdapter extends PagerAdapter {

        /**
         * @return the number of pages to display
         */
        @Override
        public int getCount() {
            return 3;
        }

        /**
         * @return true if the value returned from {@link #instantiateItem(ViewGroup, int)} is the
         * same object as the {@link View} added to the {@link ViewPager}.
         */
        @Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }

        // BEGIN_INCLUDE (pageradapter_getpagetitle)
        /**
         * Return the title of the item at {@code position}. This is important as what this method
         * returns is what is displayed in the {@link SlidingTabLayout}.
         * <p>
         * Here we construct one using the position value, but for real application the title should
         * refer to the item's contents.
         */
        @Override
        public CharSequence getPageTitle(int position) {return titles[position];}
        // END_INCLUDE (pageradapter_getpagetitle)

        /**
         * Instantiate the {@link View} which should be displayed at {@code position}. Here we
         * inflate a layout from the apps resources and then change the text view to signify the position.
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // Inflate a new layout from our resources
            app.closeKeyboard();
            View view = getActivity().getLayoutInflater().inflate(R.layout.chat,
                    container, false);

            switch (position) {
                case 0:
                	view = getActivity().getLayoutInflater().inflate(R.layout.event_calendar_ltl,
                            container, false);
                	
                    LinearLayout ll1 = (LinearLayout) view.findViewById(R.id.ll1);
                    LinearLayout ll2 = (LinearLayout) view.findViewById(R.id.ll2);
                    LinearLayout ll3 = (LinearLayout) view.findViewById(R.id.ll3);
                    LinearLayout ll4 = (LinearLayout)view. findViewById(R.id.ll4);
                    LinearLayout ll5 = (LinearLayout) view.findViewById(R.id.ll5);
                    LinearLayout ll6 = (LinearLayout)view. findViewById(R.id.ll6);
                    LinearLayout ll7 = (LinearLayout)view. findViewById(R.id.ll7);
                    
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
                    break;
                case 1:
                    view = getActivity().getLayoutInflater().inflate(R.layout.chat,
                            container, false);
                    if(ChatSender.numInstances == 0) {
                        cs.startListener(view);
                        ChatSender.numInstances++;
                    }
                    break;
                case 2:
                    view = getActivity().getLayoutInflater().inflate(R.layout.forum,
                            container, false);

                    lva = new ListViewActivity(view);
                    break;
            }
            container.addView(view);

            // Return the View
            return view;
        }

        /**
         * Destroy the item from the {@link ViewPager}. In our case this is simply removing the
         * {@link View}.
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
    void setClass(LinearLayout ll, String title, String place,
        String last, String time, int classes, int color) {
    	View view = LayoutInflater.from(ll.getContext()).inflate(R.layout.item2, null);
    	view.setMinimumHeight(dip2px(ll.getContext(), classes * 25));
    	view.setBackgroundColor(colors[color]);
    	((TextView) view.findViewById(R.id.title)).setText(title);
    	((TextView) view.findViewById(R.id.place)).setText(place);
    	((TextView) view.findViewById(R.id.last)).setText(last);
    	((TextView) view.findViewById(R.id.time)).setText(time);
    	
    	view.setOnClickListener(new OnClickClassListener());
    	ll.addView(view);
    }

    void setNoClass(LinearLayout ll, int classes, int color) {
    	TextView blank = new TextView(ll.getContext());
    	if (color == 0)
    		blank.setMinHeight(dip2px(ll.getContext(), classes * 25));
    	blank.setBackgroundColor(Color.parseColor("#F0FFFF"));
    	ll.addView(blank);
    }

    class OnClickClassListener implements View.OnClickListener {

    	public void onClick(View v) {
    		String title;
    		title = (String) ((TextView) v.findViewById(R.id.title)).getText();
    		Toast.makeText(v.getContext(), "chick is:" + title,
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
