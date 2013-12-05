package com.train_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * The Main Activity.
 * 
 * This activity starts up the RegisterActivity immediately, which communicates
 * with your App Engine backend using Cloud Endpoints. It also receives push
 * notifications from backend via Google Cloud Messaging (GCM).
 * 
 * Check out RegisterActivity.java for more details.
 */
public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	}
	public void choice_choose_line(View view){
		Intent intent = new Intent(this, AllLinesActivity.class);
		startActivity(intent);
	}
	public void send_notification(View view){
		Intent intent = new Intent(this, AudioRecordUnknown.class);
		startActivity(intent);
	}
	public void report_notification(View view){
		Intent intent = new Intent(this, ReportNotificationActivity.class);
		startActivity(intent);
	}
	public void report_bug(View view){
		Intent intent = new Intent(this, AudioRecordUnknown.class);
		startActivity(intent);
	}
	public void game(View view){
		
		Intent intent = new Intent(this, LoginActivity.class);
		
		startActivity(intent);
	}
	
}
