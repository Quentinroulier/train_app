package com.train_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class StatisticsActivity extends Activity{
	String id_station;
	String side;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("Statistics", "ici");
		setContentView(R.layout.statistics);
		Intent i = getParent().getIntent();
		// getting station id (sid) from intent
		id_station = i.getStringExtra("id_station");
		Log.d("Stations", ""+id_station);		
		
	}
}
