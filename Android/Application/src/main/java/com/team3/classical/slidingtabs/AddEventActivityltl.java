package com.team3.classical.slidingtabs;

import java.util.Calendar;

import com.team3.classical.activities.ClassSync;
import com.team3.classical.activities.EventShowLtlActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

public class AddEventActivityltl extends Activity {

	private EditText timeEditeStart = null;
    private EditText timeEditeEnd = null;
    private EditText mDescription;
    
    private Spinner weekChoose;
    private final static int DATE_DIALOG = 0;
    private final static int TIME_DIALOG = 1;
    private Calendar c = null;
    
    private Button addEventBtn,cancleEventBtn;
	protected int endHour;
	protected int startHour;
	protected String chooseWeekStr;
	private SharedPreferences mySharedPreferences;
	private Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		mySharedPreferences= getSharedPreferences("events", 
				Activity.MODE_PRIVATE); 
		editor = mySharedPreferences.edit(); 
		setContentView(R.layout.add_event_layout_ltl);
		
		weekChoose = (Spinner)findViewById(R.id.week_choose);
		mDescription = (EditText)findViewById(R.id.description);
		addEventBtn =(Button)findViewById(R.id.event_add_ltl);
		cancleEventBtn = (Button)findViewById(R.id.cancle_event_ltl);
		timeEditeStart = (EditText) findViewById(R.id.timePickStart);
        timeEditeEnd = (EditText) findViewById(R.id.timePickEnd);
        
       // �������Դ
        String[] mItems = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
        // ����Adapter���Ұ����Դ
        ArrayAdapter<String> _Adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, mItems);
        //�� Adapter���ؼ�
        weekChoose.setAdapter(_Adapter);
        weekChoose.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				chooseWeekStr = arg0.getItemAtPosition(arg2).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
        timeEditeStart.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				showDialog(DATE_DIALOG);
				return false;
			}
		});
        timeEditeEnd.setOnTouchListener(new OnTouchListener(){
        	@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				showDialog(TIME_DIALOG);
				return false;
			}
        });
        
        addEventBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int eventHeight = startHour -6;
				int intervervalHeight = endHour - startHour;
				editor.putString("title", mDescription.getText().toString());				
				editor.putString("startTime", timeEditeStart.getText().toString());
				editor.putString("endTime",timeEditeEnd.getText().toString());
				editor.putInt("eventHeight", eventHeight);
				editor.putInt("intervervalHeight", intervervalHeight);
				editor.putString("chooseWeekStr", chooseWeekStr);
				editor.commit();
				Intent intent = new Intent(AddEventActivityltl.this,EventShowLtlActivity.class);

				startActivity(intent);
				finish();
			}
		});
        
        cancleEventBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
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
                      	timeEditeStart.setText(hourOfDay+"h"+minute+"min");
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
                    	timeEditeEnd.setText(hourOfDay+"h"+minute+"min");
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
