package com.train_app;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class AndroidTabLayoutActivity extends TabActivity{
	String id_station;
	String side;
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tab);
        
        Intent i = getIntent();
		id_station = i.getStringExtra("id_station");
		
        TabHost tabHost = getTabHost();
         
        // Tab for capacities
        TabSpec capacitiesspec = tabHost.newTabSpec("Capacities");
        // setting Title and Icon for the Tab
        capacitiesspec.setIndicator("Capacities");
        Intent capacitiesIntent = new Intent(this, CapacitiesActivity.class);
        capacitiesspec.setContent(capacitiesIntent);
         
        // Tab for statistics
        TabSpec statisticsspec = tabHost.newTabSpec("Statistics");
        statisticsspec.setIndicator("Statistics");
        Intent statisticsIntent = new Intent(this, StatisticsActivity.class);
        statisticsspec.setContent(statisticsIntent);
         
        // Tab for chooseCart
        TabSpec chooseCartspec = tabHost.newTabSpec("Choose Cart");
        chooseCartspec.setIndicator("Choose Cart");
        Intent chooseCartIntent = new Intent(this, ChooseCartActivity.class);
        chooseCartspec.setContent(chooseCartIntent);
         
        // Adding all TabSpec to TabHost
        tabHost.addTab(capacitiesspec); // Adding capacities tab
        tabHost.addTab(statisticsspec); // Adding statistics tab
        tabHost.addTab(chooseCartspec); // Adding chooseCart tab
    }
}