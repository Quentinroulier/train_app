package com.train_app;

import com.library.UserFunctions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Singsomething extends Activity{
	UserFunctions userFunctions;
    Button btnLogout;
    
	 public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		userFunctions = new UserFunctions();
        if(userFunctions.isUserLoggedIn(getApplicationContext())){
        	//userFunctions.userEmail(getApplicationContext());
        	//Log.d("email", userFunctions.userEmail(getApplicationContext()));	
        	// user already logged in show databoard
            btnLogout = (Button) findViewById(R.id.btnLogout);
            
             btnLogout.setOnClickListener(new View.OnClickListener() {
                 
                public void onClick(View arg0) {
                    userFunctions.logoutUser(getApplicationContext());
                    Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                    login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(login);
                    // Closing dashboard screen
                    finish();
                }
            });
             
        }else{
            // user is not logged in show login screen
            Intent login = new Intent(getApplicationContext(), LoginActivity.class);
            login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(login);
            // Closing dashboard screen
            finish();
        }        
	
	 }
	public void audioRecord(View view){
		Intent intent = new Intent(this, ModeGameActivity.class);
		startActivity(intent);
	}
	public void downloadFile(View view){
		Intent intent = new Intent(this, DownloadFileDemo1.class);
		startActivity(intent);
	}
	public void userProfile (View view){
		Intent intent = new Intent(this, UserProfileActivity.class);
		startActivity(intent);
	}
}
