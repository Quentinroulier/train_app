package com.train_app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ChooseStationActivity extends ListActivity{
	String id_line;
	
    // Progress Dialog
    private ProgressDialog pDialog;
    // JSON parser class
    JSONParser jsonParser = new JSONParser();
    
    ArrayList<HashMap<String, String>> stationsList;
    
    private static final String TAG_ID = "id";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_STATIONS = "stations";
    private static final String TAG_SID = "sid";
    private static final String TAG_NAME = "name";
    
    private static final String url_stations = "http://domaine-berge-de-sainte-rose.com/train-app/retrieve_stations.php";
    
    JSONArray stations = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stations);
		stationsList = new ArrayList<HashMap<String, String>>();
		Intent i = getIntent();
		// getting product id (pid) from intent
		id_line = i.getStringExtra(TAG_ID);
		//Log.e(id_line, id_line);
		new GetStation().execute();
		
		ListView lv = getListView();
		 
        // on seleting single product
        // launching Edit Product Screen
        lv.setOnItemClickListener(new OnItemClickListener() {
 
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                // getting values from selected ListItem
                String id_station = ((TextView) view.findViewById(R.id.id)).getText()
                        .toString();
                
                // Starting new intent
                Intent intent = new Intent(getApplicationContext(), ChooseSideActivity.class);
                // sending id to next activity
                intent.putExtra("id_station", id_station);
 
                // starting new activity and expecting some response back
                startActivity(intent);
            }
        });
		
	}
	class GetStation extends AsyncTask<String, String, String> {
		 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ChooseStationActivity.this);
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
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("id_line", id_line));
 
                        // getting product details by making HTTP request
                        // Note that product details url will use GET request
                        JSONObject json = jsonParser.makeHttpRequest(
                        		url_stations, "GET", params);
 
                        // check your log for json response
                        Log.d("Stations", json.toString());
 
                        // json success tag
                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                        	stations = json.getJSONArray(TAG_STATIONS);
                        	 
                            // looping through All Products
                            for (int i = 0; i < stations.length(); i++) {
                                JSONObject c = stations.getJSONObject(i);
                                
                                // Storing each json item in variable
                                String id = c.getString(TAG_SID);
                                String name = c.getString(TAG_NAME);
   
                                // creating new HashMap
                                HashMap<String, String> map = new HashMap<String, String>();
         
                                // adding each child node to HashMap key => value
                                map.put(TAG_SID, id);
                                map.put(TAG_NAME, name);
         
                                // adding HashList to ArrayList
                                stationsList.add(map);
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
 
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once got all details
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                            ChooseStationActivity.this, stationsList,
                            R.layout.list_item, new String[] { TAG_SID,
                                    TAG_NAME},
                            new int[] { R.id.id, R.id.name });
                    // updating listview
                    setListAdapter(adapter);
                }
            });
        }
    }
	
}
