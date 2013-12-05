package com.train_app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.library.DatabaseHandler;
import com.library.UserFunctions;
import com.train_app.AudioRecordUnknown.PlayButton;
 
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
 
public class DownloadFileDemo1 extends Activity {
	
    ProgressBar pb;
    Dialog dialog;
    int downloadedSize = 0;
    int totalSize = 0;
    TextView cur_val;
    String dwnload_file_path;
    
    private MediaPlayer   mPlayer = null;
    private PlayButton   mPlayButton = null;
    private static String mFileName = null;
    private static final String LOG_TAG = "AudioRecordTest";
    
 // JSON Response node names
    private static String KEY_SUCCESS = "success";
    private static String KEY_SONG = "song_name";
    
    String endUrl = null;
    UserFunctions userFunctions;
    
    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }
    private void startPlaying() {
        mPlayer = new MediaPlayer();
        
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }
    class PlayButton extends Button {
        boolean mStartPlaying = true;

        OnClickListener clicker = new OnClickListener() {
            public void onClick(View v) {
            	//showProgress(dwnload_file_path);
          
            	onPlay(mStartPlaying);
                if (mStartPlaying) {
                	
                    setText("Stop playing");
                } else {
                    
                    setText("Start playing");
                }
                mStartPlaying = !mStartPlaying;
            }
        };

        public PlayButton(Context ctx) {
            super(ctx);
            setText("Start playing");
            setOnClickListener(clicker);
        }
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_download);
        new Thread(new Runnable() {
            public void run() {
            	userFunctions = new UserFunctions();
                JSONObject json = userFunctions.downloadSongUser();
                try {
        			if (json.getString(KEY_SUCCESS) != null) {
        			    String res = json.getString(KEY_SUCCESS); 
        			    if(Integer.parseInt(res) == 1){
        			        // user successfully logged in
        			        // Store user details in SQLite Database
        			        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        			        JSONObject json_download = json.getJSONObject("download");
        			        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        			        mFileName += "/audiotestapp/"+json_download.getString(KEY_SONG);
        			        endUrl = json_download.getString(KEY_SONG);
        			        Log.d("path", mFileName);
        			    }
        			}
        		} catch (NumberFormatException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		} catch (JSONException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
                 downloadFile();
            }
        }).start();
        
        //LinearLayout ll = new LinearLayout(this);
        setContentView(R.layout.downloadfile);
        View btt =  findViewById(R.id.download); 
        mPlayButton = new PlayButton(this);
        ((LinearLayout)btt).addView(mPlayButton,
            new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                0));
       
        final EditText editText = (EditText) findViewById(R.id.answer_user);
        editText.setGravity(Gravity.BOTTOM);
        final Button sendButton = (Button) findViewById(R.id.send);
        sendButton.setOnClickListener(new Button.OnClickListener() {  
	        public void onClick(View v)
            {	
        		Log.d("edittext", editText.getText().toString()); 
        		
        		//userFunctions = new UserFunctions();
        		//userFunctions.uploadSongUser(editText.getText().toString(), mFileName, userFunctions.userEmail(getApplicationContext()));
        		Intent dashboard = new Intent(getApplicationContext(), DownloadFileDemo1.class);

                startActivity(dashboard);
                overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.slide_out_to_bottom);
                finish();
            }
         });
        
         
    }
    
         
    void downloadFile(){
         
        try {
        	userFunctions = new UserFunctions();
            URL url = new URL("http://domaine-berge-de-sainte-rose.com/train-app/uploads/"+endUrl);
            Log.d("path", url.toString());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
 
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);
 
            //connect
            urlConnection.connect();
 
            //set the path where we want to save the file           
            String SDCardRoot = Environment.getExternalStorageDirectory().toString()+"/audiotestapp/"; 
            //create a new file, to save the downloaded file 
            File file = new File(SDCardRoot,endUrl);
  
            FileOutputStream fileOutput = new FileOutputStream(file);
 
            //Stream used for reading the data from the internet
            InputStream inputStream = urlConnection.getInputStream();
 
            //this is the total size of the file which we are downloading
            totalSize = urlConnection.getContentLength();
 
            /*runOnUiThread(new Runnable() {
                public void run() {
                    pb.setMax(totalSize);
                }               
            });*/
             
            //create a buffer...
            byte[] buffer = new byte[1024];
            int bufferLength = 0;
 
            while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
                // update the progressbar //
                /*runOnUiThread(new Runnable() {
                    public void run() {
                        pb.setProgress(downloadedSize);
                        float per = ((float)downloadedSize/totalSize) * 100;
                        cur_val.setText("Downloaded " + downloadedSize + "KB / " + totalSize + "KB (" + (int)per + "%)" );
                    }
                });*/
            }
            //close the output stream when complete //
            fileOutput.close();
            runOnUiThread(new Runnable() {
                public void run() {
                	//dialog.dismiss(); // if you want close it..
                }
            });         
         
        } catch (final MalformedURLException e) {
            showError("Error : MalformedURLException " + e);        
            e.printStackTrace();
        } catch (final IOException e) {
            showError("Error : IOException " + e);          
            e.printStackTrace();
        }
        catch (final Exception e) {
            showError("Error : Please check your internet connection " + e);
        }       
    }
     
    void showError(final String err){
        runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(DownloadFileDemo1.this, err, Toast.LENGTH_LONG).show();
            }
        });
    }
     
    void showProgress(String file_path){
    	final Dialog dialog = new Dialog(DownloadFileDemo1.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.myprogressdialog);
        dialog.setTitle("Download Progress");
 
        TextView text = (TextView) dialog.findViewById(R.id.tv1);
        text.setText("Downloading file");
        cur_val = (TextView) dialog.findViewById(R.id.cur_pg_tv);
        cur_val.setText("Starting download...");
        dialog.show();
         
        pb = (ProgressBar)dialog.findViewById(R.id.progress_bar);
        pb.setProgress(0);
        pb.setProgressDrawable(getResources().getDrawable(R.drawable.green_progress));  
    }
    
}