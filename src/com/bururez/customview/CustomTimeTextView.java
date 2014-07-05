package com.bururez.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bururez.customtimeview.R;

public class CustomTimeTextView extends LinearLayout{
	private static final int MAX_SECONDS = 60;
	private static final int MAX_MINUTES = 60;
	private static final int MAX_HOURS = 24;	
	
	TextView txtHH;
	TextView txtMM;
	TextView txtSS;
	
	int height;
	
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
		int width = -1;
		height = -1;
		for (int i = 0; i < N; ++i){
			int attr = a.getIndex(i);
			switch (attr) {
			case R.styleable.CustomTimeTextView_txt_width:
				width = a.getDimensionPixelSize(attr, 100);
				width = width / 3;
				LayoutParams lpw;
				if (height == -1)
					lpw = new LayoutParams(width, LayoutParams.WRAP_CONTENT);
				else
					lpw = new LayoutParams(width, height);
				txtHH.setLayoutParams(lpw);
				txtMM.setLayoutParams(lpw);
				txtSS.setLayoutParams(lpw);
				break;
			case R.styleable.CustomTimeTextView_txt_color:
				int color = a.getColor(attr, 0);
				txtHH.setTextColor(color);
				txtMM.setTextColor(color);
				txtSS.setTextColor(color);
				break;
			case R.styleable.CustomTimeTextView_txt_height:
				LayoutParams lph;
				height = a.getDimensionPixelSize(attr, LayoutParams.WRAP_CONTENT);
				if (width == -1)
					lph = new LayoutParams(LayoutParams.WRAP_CONTENT, height);
				else
					lph = new LayoutParams(width, height);
				txtHH.setLayoutParams(lph);
				txtMM.setLayoutParams(lph);
				txtSS.setLayoutParams(lph);
				break;
			case R.styleable.CustomTimeTextView_txt_text_size:
				int size = a.getDimensionPixelSize(attr, 15);
				txtHH.setTextSize(TypedValue.COMPLEX_UNIT_SP, (float)size);
				txtMM.setTextSize(TypedValue.COMPLEX_UNIT_SP, (float)size);
				txtSS.setTextSize(TypedValue.COMPLEX_UNIT_SP, (float)size);
				break;
			default:
				break;
			}
		}
		a.recycle();
		/*
		 * Only if the width isn't set and only if the view is loaded
		 * adjust the width of the view
		 */
		if (width == -1){
			post(new Runnable() {
				
				@Override
				public void run() {
					adjustWidthWhenXMLWidthIs0dp();
				}
			});
		}
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
	
	/**
	 * When the width = -1 it would be that isn't set into xml declaration.
	 * get the value on view and divided into thre parts
	 */
	private void adjustWidthWhenXMLWidthIs0dp(){
		int width = getWidth() - 10;
		width = width / 3;
		LayoutParams lpw;
		if (height == -1)
			lpw = new LayoutParams(width, LayoutParams.WRAP_CONTENT);
		else
			lpw = new LayoutParams(width, height);
		txtHH.setLayoutParams(lpw);
		txtMM.setLayoutParams(lpw);
		txtSS.setLayoutParams(lpw);
	}
}
