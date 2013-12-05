package com.train_app;

import org.json.JSONException;
import org.json.JSONObject;

import com.library.DatabaseHandler;
import com.library.UserFunctions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class UserProfileActivity extends Activity{
	
	UserFunctions userFunctions;
	TextView loginErrorMsg;
	
	private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR = "error";
    private static String KEY_ERROR_MSG = "error_msg";
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userprofile);
		
		loginErrorMsg = (TextView) findViewById(R.id.login_error);
		
		userFunctions = new UserFunctions();
		JSONObject json = userFunctions.userInformation(userFunctions.userEmail(getApplicationContext()));
		 try {
             if (json.getString(KEY_SUCCESS) != null) {
                 loginErrorMsg.setText("");
                 String res = json.getString(KEY_SUCCESS); 
                 if(Integer.parseInt(res) == 1){
                	 
                     DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                     JSONObject json_user = json.getJSONObject("user");
                     json_user.get("name").toString();
                     
                     final TextView name_user = (TextView) findViewById(R.id.name_user);
                     final TextView score_user = (TextView) findViewById(R.id.score_user);
                     final TextView ranking_user = (TextView) findViewById(R.id.ranking_user);
                     final TextView songposted_user = (TextView) findViewById(R.id.songposted_user);
                     
                     name_user.setText(json_user.get("name").toString());
                     score_user.setText(json_user.get("score").toString());
                     ranking_user.setText(json_user.get("ranking").toString());
                     songposted_user.setText(json_user.get("song_posted").toString());

                              
                 }else{
                     // Error in login
                     loginErrorMsg.setText("Couldn't fetch the data");
                 }
             }
         } catch (JSONException e) {
             e.printStackTrace();
         }
	}

}
