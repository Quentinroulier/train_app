package com.train_app;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class ChooseSideActivity extends Activity{
	
	String id_station;
	String first_station_id;
	String second_station_id;
	String third_station_id;
	String first_station_name;
	String second_station_name;
	String third_station_name;
    // Progress Dialog
    private ProgressDialog pDialog;
    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    

    private static final String TAG_SUCCESS = "success";
	private static final String TAG_SIDES = "sides";
    private static final String TAG_FIRST = "first_station_id";
    private static final String TAG_SECOND = "second_station_id";
    private static final String TAG_THIRD = "third_station_id";
    private static final String TAG_FIRST_NAME= "first_station_name";
    private static final String TAG_SECOND_NAME = "second_station_name";
    private static final String TAG_THIRD_NAME = "third_station_name";
    
    private static final String url_sides = "http://domaine-berge-de-sainte-rose.com/train-app/retrieve_sides.php";
   
    JSONArray sides = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("Stations", "ici");
		setContentView(R.layout.sides);
		Intent i = getIntent();
		// getting station id (sid) from intent
		id_station = i.getStringExtra("id_station");
		Log.d("Stations", ""+id_station);
		new GetSides().execute();
		
		
		
	}
	
	class GetSides extends AsyncTask<String, String, String> {
		 
 
		@Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ChooseSideActivity.this);
            pDialog.setMessage("Loading stations. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        protected String doInBackground(String... params) {
        	 
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    // Check for success tag
                    int success;
                    try {
                        // Building Parameters
                    	Log.d("sides", ""+id_station);
                    	List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("id_station", id_station));
 
                        // getting product details by making HTTP request
                        // Note that product details url will use GET request
                        JSONObject json = jsonParser.makeHttpRequest(
                        		url_sides, "GET", params);
 
                        // check your log for json response
                        
 
                        // json success tag
                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                        	sides = json.getJSONArray(TAG_SIDES);
                        	
                            // looping through All Products
                            for (int i = 0; i < sides.length(); i++) {
                                JSONObject c = sides.getJSONObject(i);
                                Log.d("Sides", json.toString());
                                // Storing each json item in variable

                                final Button next1 = (Button)findViewById(R.id.next1);
                                final Button next2 = (Button)findViewById(R.id.next2);
                                final Button next3 = (Button)findViewById(R.id.next3);
                                
                                if(!c.isNull("first_station_id")){
                                	first_station_id = c.getString(TAG_FIRST);
                                	first_station_name = c.getString(TAG_FIRST_NAME); 
                                    next1.setText(first_station_name);
                                    next1.setOnClickListener(new OnClickListener() {

                                        @Override
                                        public void onClick(View v) {
                                        	// Starting new intent
                                            Intent intent = new Intent(getApplicationContext(), AndroidTabLayoutActivity.class);
                                            // sending id to next activity
                                            intent.putExtra("side", first_station_id);
                                            intent.putExtra("id_station", id_station);
                             
                                            // starting new activity and expecting some response back
                                            startActivity(intent);
                                        }
                                    });
                                }else{
                                	next1.setVisibility(View.GONE);
                                }
                                if(!c.isNull("second_station_id")){
                                	second_station_id = c.getString(TAG_SECOND);
                                	second_station_name = c.getString(TAG_SECOND_NAME); 
                                    next2.setText(second_station_name);
                                    next2.setOnClickListener(new OnClickListener() {

                                        @Override
                                        public void onClick(View v) {
                                        	// Starting new intent
                                            Intent intent = new Intent(getApplicationContext(), AndroidTabLayoutActivity.class);
                                            // sending id to next activity
                                            intent.putExtra("side", second_station_id);
                                            intent.putExtra("id_station", id_station);
                             
                                            // starting new activity and expecting some response back
                                            startActivity(intent);
                                        }
                                    });
                                }else{
                                	next2.setVisibility(View.GONE);
                                }
                                if(!c.isNull("third_station_id")){
                                	third_station_id = c.getString(TAG_THIRD);
                                	third_station_name = c.getString(TAG_THIRD_NAME);
                                    next3.setText(third_station_name);
                                    next3.setOnClickListener(new OnClickListener() {

                                        @Override
                                        public void onClick(View v) {
                                        	// Starting new intent
                                            Intent intent = new Intent(getApplicationContext(), AndroidTabLayoutActivity.class);
                                            // sending id to next activity
                                            intent.putExtra("side", third_station_id);
                                            intent.putExtra("id_station", id_station);
                             
                                            // starting new activity and expecting some response back
                                            startActivity(intent);
                                        }
                                    });
                                }else{
                                	next3.setVisibility(View.GONE);
                                }
                                
                            }
                            	
 
                        }else{
                            // product with sid not found
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
 
            return null;
        }
 

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once got all details
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {

                }
            });
        }
    }
	
}