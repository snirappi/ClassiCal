package com.team3.classical.activities;

import java.util.Calendar;

import com.team3.classical.slidingtabs.AddEventActivityltl;
import com.team3.classical.slidingtabs.R;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.AdapterView.OnItemSelectedListener;

public class updateEventltlAcitivity extends Activity {

	private EditText updateTimeEditeStart = null;
    private EditText updateTimeEditeEnd = null;
    private EditText mDescription;
    private final static int DATE_DIALOG = 0;
    private final static int TIME_DIALOG = 1;
    private Calendar c = null;
    
    private Button updateEventBtn,delEventBtn,updateCancleEventBtn;
	protected int endHour=0;
	protected int startHour =0;
	private Button updateDelBtn;
	private Intent intent;
	protected int eventHeight;
	protected int intervervalHeight;
	
	private Spinner updateWeekChoose;
	protected String updateChooseWeekStr;
	private SharedPreferences mySharedPreferences;
	private Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.update_event_layout_ltl);
		intent = getIntent();
	
		mySharedPreferences= getSharedPreferences("events", 
				Activity.MODE_PRIVATE); 
		editor = mySharedPreferences.edit();
        

        intervervalHeight = mySharedPreferences.getInt("intervervalHeight", 1);
		updateWeekChoose = (Spinner)findViewById(R.id.update_week_choose);
		mDescription = (EditText)findViewById(R.id.update_description);
		mDescription.setText(mySharedPreferences.getString("title","event"));
		updateEventBtn =(Button)findViewById(R.id.event_update_ltl);
		updateDelBtn = (Button)findViewById(R.id.event_del_ltl);
		updateCancleEventBtn = (Button)findViewById(R.id.cancle_update_ltl);
		updateTimeEditeStart = (EditText) findViewById(R.id.update_timePickStart);
		updateTimeEditeStart.setText(mySharedPreferences.getString("startTime","6"));
		
        updateTimeEditeEnd = (EditText) findViewById(R.id.update_timePickEnd);
        updateTimeEditeEnd.setText(mySharedPreferences.getString("endTime","8"));
        
        // �������Դ
        String[] mItems = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
        // ����Adapter���Ұ����Դ
        ArrayAdapter<String> _Adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, mItems);
        //�� Adapter���ؼ�
        updateWeekChoose.setAdapter(_Adapter);
        updateWeekChoose.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				updateChooseWeekStr = arg0.getItemAtPosition(arg2).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
        
        updateTimeEditeStart.setOnTouchListener(new OnTouchListener(){
        	@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				showDialog(DATE_DIALOG);
				return true;
			}
        });
        updateTimeEditeEnd.setOnTouchListener(new OnTouchListener(){
        	@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				showDialog(TIME_DIALOG);
				return true;
			}
        });
        
        updateDelBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
        updateEventBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String startHourString = updateTimeEditeStart.getText().toString();
				String[] startHourArray = startHourString.split("ʱ");
				startHour = Integer.parseInt(startHourArray[0]);
				
				String endHourString = updateTimeEditeEnd.getText().toString();
				String[] endHourArray = endHourString.split("ʱ");
				endHour = Integer.parseInt(endHourArray[0]);
				
				eventHeight = startHour -6;
				intervervalHeight = endHour - startHour;
				editor.putString("title", mDescription.getText().toString());				
				editor.putString("startTime", updateTimeEditeStart.getText().toString());
				editor.putString("endTime",updateTimeEditeEnd.getText().toString());
				editor.putInt("eventHeight", eventHeight);
				editor.putInt("intervervalHeight", intervervalHeight);
				editor.putString("chooseWeekStr", updateChooseWeekStr);
				editor.commit();
				Intent intent = new Intent(updateEventltlAcitivity.this,EventShowLtlActivity.class);
				
				startActivity(intent);
				finish();
			}
		});
        
        updateCancleEventBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(updateEventltlAcitivity.this,EventShowLtlActivity.class);
				
				startActivity(intent);
				finish();
			}
		});
        
	}
	
	/**
     * �������ڼ�ʱ��ѡ��Ի���
     */
    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        switch (id) {
        case DATE_DIALOG:
        	  c=Calendar.getInstance();
              dialog=new TimePickerDialog(
                  this, 
                  new TimePickerDialog.OnTimeSetListener(){

					public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                      	updateTimeEditeStart.setText(hourOfDay+"h"+minute+"min");
                      	startHour = hourOfDay;
                      }
                  },
                  c.get(Calendar.HOUR_OF_DAY),
                  c.get(Calendar.MINUTE),
                  false
              );
            break;
        case TIME_DIALOG:
            c=Calendar.getInstance();
            dialog=new TimePickerDialog(
                this, 
                new TimePickerDialog.OnTimeSetListener(){
                   
					public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    	updateTimeEditeEnd.setText(hourOfDay+"h"+minute+"min");
                    	endHour = hourOfDay;
                    }
                },
                c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE),
                false
            );
            break;
        }
        return dialog;
    }
}
