/*
* Copyright 2013 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.team3.classical.slidingtabs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.team3.classical.activities.ClassSync;
import com.team3.classical.activities.SampleActivityBase;
import com.team3.classical.activities.ListViewActivity;

/**
 * A simple launcher activity containing a summary sample description, sample log and a custom
 * {@link android.support.v4.app.Fragment} which can display a view.
 * <p>
 * For devices with displays with a width of 720dp or greater, the sample log is always visible,
 * on other devices it's visibility is controlled by an item on the Action Bar.
 */
public class MainActivity extends SampleActivityBase {

    public static final String TAG = "MainActivity";
    // Whether the Log Fragment is currently shown
    private boolean mLogShown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            SlidingTabsBasicFragment fragment = new SlidingTabsBasicFragment();
            fragment.setActivity(this);
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }
        setTitle("CS307"); //TODO: Make dynamic based on class
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	// TODO Auto-generated method stub
    	getMenuInflater().inflate(R.menu.menu_setting_ltl, menu);
    	return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	// TODO Auto-generated method stub
    	if(item != null && item.getItemId() == R.id.action_sync){
			startActivity(new Intent(MainActivity.this,ClassSync.class));

    	} else if (item != null && item.getItemId() == R.id.action_add_event){
    		startActivity(new Intent(MainActivity.this,AddEventActivityltl.class));
    	}
    	return super.onOptionsItemSelected(item);
    }
    public void onClick(View v) {
        ListViewActivity.restore();
    }
}
