package com.bururez.customview;

import com.bururez.customtimeview.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomTimeTextView extends LinearLayout{
	private static final int MAX_SECONDS = 60;
	private static final int MAX_MINUTES = 60;
	private static final int MAX_HOURS = 24;	
	
	TextView txtHH;
	TextView txtMM;
	TextView txtSS;
	
	/*
	 * Only for special case use thi Context variable
	 */
	Context context; 
	
	public CustomTimeTextView(Context context){
		super(context);
		
		initViews(context);
	}
	
	public CustomTimeTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		initViews(context);
		defAttribute(context, attrs);
	}
	
	public CustomTimeTextView(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
		
		initViews(context);
		defAttribute(context, attrs);
	}
	
	private void defAttribute(Context context, AttributeSet attrs){
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomTimeTextView);
		final int N = a.getIndexCount();
		for (int i = 0; i < N; ++i){
			int attr = a.getIndex(i);
			switch (attr) {
			case R.styleable.CustomTimeTextView_txt_width:
				int width = a.getDimensionPixelSize(attr, 100);
				width = width / 3;
				txtHH.setLayoutParams(new LayoutParams(width, LayoutParams.WRAP_CONTENT));
				txtMM.setLayoutParams(new LayoutParams(width, LayoutParams.WRAP_CONTENT));
				txtSS.setLayoutParams(new LayoutParams(width, LayoutParams.WRAP_CONTENT));
				break;
			case R.styleable.CustomTimeTextView_txt_color:
				int color = a.getColor(attr, 0);
				txtHH.setTextColor(color);
				txtMM.setTextColor(color);
				txtSS.setTextColor(color);
				break;
			default:
				break;
			}
		}
		a.recycle();
	}
	
	/**
	 * Return the total seconds the user insert in three editboxes.
	 * The developer must check if the return is different from -1.
	 * if differente from -1 then the result is correct else the user must control
	 * the values inserted.
	 * @return
	 */
	public Long getTimeInSeconds(){
		int hh = getHoursFromEditView();
		int mm = getMinutesFromEditView();
		int ss = getSecondsFromEditView();
		
		long result;
		
		if (ss <= MAX_SECONDS && mm <= MAX_MINUTES && hh <= MAX_HOURS){
			result = ss + mm * 60 + hh * 60 * 60;
		}else
			result = -1;
		
		return result;
	}
	
	/**
	 * Return the total minutes the user insert in three editboxes.
	 * The developer must check if the return is different from -1.
	 * if differente from -1 then the result is correct else the user must control
	 * the values inserted.
	 * @return
	 */
	public Double getTimeInMinutes(){
		int hh = getHoursFromEditView();
		int mm = getMinutesFromEditView();
		int ss = getSecondsFromEditView();
		
		double result;
		
		if (ss <= MAX_SECONDS && mm <= MAX_MINUTES && hh <= MAX_HOURS){
			result = ((double)ss / 60) + mm + hh * 60;
		}else
			result = -1;
		
		return result;
	}
	
	/**
	 * Set the total time in seconds (time must be seconds. long type)
	 * @param time
	 */
	public void setTimeInSeconds(double time){
		int seconds = (int)(time % MAX_SECONDS);
		int minutes = (int)(Math.floor(time / MAX_SECONDS) % MAX_MINUTES);
		int hour = (int)(Math.floor(time / MAX_MINUTES / MAX_SECONDS));
		txtHH.setText(Integer.toString(hour));
		txtMM.setText(Integer.toString(minutes));
		txtSS.setText(Integer.toString(seconds));
	}
	
	/**
	 * Set the total time in minutes (time must be minutes. double type)
	 */
	public void setTimeInMinutes(double time){
		int timeInSeconds = (int) (time * MAX_SECONDS);
		setTimeInSeconds(timeInSeconds);/*
		int seconds = timeInSeconds % MAX_SECONDS;
		int minutes = (int)(Math.floor(timeInSeconds / MAX_SECONDS) % MAX_MINUTES);
		int hour = (int)(Math.floor(timeInSeconds / MAX_MINUTES / MAX_SECONDS));
		txtHH.setText(Integer.toString(hour));
		txtMM.setText(Integer.toString(minutes));
		txtSS.setText(Integer.toString(seconds));*/
	}

	/**
	 * Return the value of seconds in editText, if is empty return 0.
	 * @return
	 */
	private int getSecondsFromEditView(){
		int ss;
		try{
			ss = Integer.parseInt(txtHH.getText().toString());
		}catch (Exception e){
			ss = 0;
		}
		
		return ss;
	}
	
	/**
	 * Return the value of minutes in editText, if is empty return 0.
	 * @return
	 */
	private int getMinutesFromEditView(){
		int mm;
		try{
			mm = Integer.parseInt(txtMM.getText().toString());
		}catch (Exception e){
			mm = 0;
		}
		return mm;
	}
	
	/**
	 * Return the value of hours in editText, if is empty return 0.
	 * @return
	 */
	private int getHoursFromEditView(){
		int hh;
		try{
			hh = Integer.parseInt(txtHH.getText().toString());
		}catch (Exception e){
			hh = 0;
		}
		return hh;
	}
	
	private void initViews(Context context){

		LayoutInflater layoutInflater = (LayoutInflater)context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutInflater.inflate(R.layout.layout_custom_time_text_view, this);

		txtHH = (TextView)findViewById(R.id.txt_hh);
		txtMM = (TextView)findViewById(R.id.txt_mm);
		txtSS = (TextView)findViewById(R.id.txt_ss);
	}
}
