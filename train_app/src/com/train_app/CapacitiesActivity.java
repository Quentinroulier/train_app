package com.train_app;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.train_app.ChooseSideActivity.GetSides;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class CapacitiesActivity extends Activity{

	String id_station;
	String side;
	String current_cart_capacity1;
	String current_cart_capacity2;
	String current_cart_capacity3;
	String current_cart_capacity4;
	String current_cart_capacity5;
	private ProgressDialog pDialog;
    // JSON parser class
    JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS = "success";
   	private static final String TAG_CAPACITIES = "capacities";
   	private static final String TAG_SIDE = "side";
   	private static final String TAG_NBCARTS = "number_carts";
   	private static final String TAG_CRRCARTCAP1 = "current_cart_capacity1";
   	private static final String TAG_CRRCARTCAP2 = "current_cart_capacity2";
   	private static final String TAG_CRRCARTCAP3 = "current_cart_capacity3";
   	private static final String TAG_CRRCARTCAP4 = "current_cart_capacity4";
   	private static final String TAG_CRRCARTCAP5 = "current_cart_capacity5";
   	
   	
   private static final String url_sides = "http://domaine-berge-de-sainte-rose.com/train-app/retrieve_capacities.php";
  
   JSONArray capacities = null;
   
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("capacities", "ici");
		setContentView(R.layout.capacities);
		Intent i = getParent().getIntent();
		id_station = i.getStringExtra("id_station");
		side = i.getStringExtra("side");
		Log.d("Stations", ""+id_station);		
		new GetCapacities().execute();
	}

	class GetCapacities extends AsyncTask<String, String, String> {
		 
		 
		@Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CapacitiesActivity.this);
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
                    	Log.d("sides", ""+side);
                    	Log.d("id_station", ""+id_station);
                    	List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("id_station", id_station));
                        params.add(new BasicNameValuePair("side",side));
 
                        // getting product details by making HTTP request
                        // Note that product details url will use GET request
                        JSONObject json = jsonParser.makeHttpRequest(
                        		url_sides, "GET", params);
                        
                        // check your log for json response
                        
 
                        // json success tag
                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                        	capacities = json.getJSONArray(TAG_CAPACITIES);
                        	
                            // looping through All Products
                            for (int i = 0; i < capacities.length(); i++) {
                                JSONObject c = capacities.getJSONObject(i);
                                Log.d("capacities:", json.toString());
                                // Storing each json item in variable
                                
                                int nb_carts = c.getInt(TAG_NBCARTS); 
                                
                                final TextView textView1 = (TextView)findViewById(R.id.textView1);
                                final TextView textView2 = (TextView)findViewById(R.id.textView2);
                                final TextView textView3 = (TextView)findViewById(R.id.textView3);
                                final TextView textView4 = (TextView)findViewById(R.id.textView4);
                                final TextView textView5 = (TextView)findViewById(R.id.textView5);   
                                
                                current_cart_capacity1 = c.getString(TAG_CRRCARTCAP1); 
                                textView1.setText(current_cart_capacity1);
                                current_cart_capacity2 = c.getString(TAG_CRRCARTCAP2); 
                                textView2.setText(current_cart_capacity2);
                                current_cart_capacity3 = c.getString(TAG_CRRCARTCAP3); 
                                textView3.setText(current_cart_capacity3);
                                current_cart_capacity4 = c.getString(TAG_CRRCARTCAP4); 
                                textView4.setText(current_cart_capacity4);
                                current_cart_capacity5 = c.getString(TAG_CRRCARTCAP5); 
                                textView5.setText(current_cart_capacity5);
                                
                                
                                
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