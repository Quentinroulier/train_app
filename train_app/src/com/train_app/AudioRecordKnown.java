package com.train_app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import com.library.UserFunctions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class AudioRecordKnown extends Activity
{
    private static final String LOG_TAG = "AudioRecordTest";
    private static String mFileName = null;

    private RecordButton mRecordButton = null;
    private MediaRecorder mRecorder = null;

    private PlayButton   mPlayButton = null;
    private MediaPlayer   mPlayer = null;
    
    UserFunctions userFunctions;

    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

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

    private void startRecording() {
    	
    	final Random r = new Random();
    	final int rand = r.hashCode() ;
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/audiotestapp/audiorecord"+rand+".3gp";
        Log.d("file", mFileName);
        
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }

    class RecordButton extends Button {
        boolean mStartRecording = true;

        /*OnClickListener clicker = new OnClickListener() {
            public void onClick(View v) {
                onRecord(mStartRecording);
                if (mStartRecording) {
                    setText("Stop recording");
                } else {
                    setText("Start recording");
                    
                }
                mStartRecording = !mStartRecording;
            }
        };*/
        OnTouchListener toucher = new View.OnTouchListener() {
	        public boolean onTouch(View v, MotionEvent event) {
	            // TODO Auto-generated method stub
	            switch(event.getAction()){
	             case MotionEvent.ACTION_DOWN:
	            	 onRecord(mStartRecording);
	            	 setText("Stop recording");
	                 break;
	             case MotionEvent.ACTION_UP:
	            	 onRecord(!mStartRecording);
	            	 setText("Start recording");
	                 break;
	            }
	            return false;
	        }
        };

        public RecordButton(Context ctx) {
            super(ctx);
            setText("Start recording");
            setOnTouchListener(toucher);
        }
    }

    class PlayButton extends Button {
        boolean mStartPlaying = true;

        OnClickListener clicker = new OnClickListener() {
            public void onClick(View v) {
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

    public AudioRecordKnown() {
    		
    	File folder = new File(Environment.getExternalStorageDirectory().toString()+"/audiotestapp/");
    	folder.mkdirs();
    	
    	
    }
    
    public void uploadFile(){

    	try {
    		FileInputStream fstrm = new FileInputStream(mFileName);
	    	HttpFileUploader htfu = new HttpFileUploader("http://domaine-berge-de-sainte-rose.com/train-app/upload_sound.php","noparam", mFileName);
	    	htfu.doStart(fstrm);
    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    	}
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);

        //LinearLayout ll = (LinearLayout) findViewById(R.layout.audiorecordknow);
    	setContentView(R.layout.audiorecordknow);
    	View ll =  findViewById(R.id.info);  	
        mRecordButton = new RecordButton(this);
        ((LinearLayout)ll).addView(mRecordButton,
            new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                0));
        mPlayButton = new PlayButton(this);
        ((LinearLayout)ll).addView(mPlayButton,
            new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                0));
        setContentView(ll);
        final EditText editText = (EditText) findViewById(R.id.answer_player);
        editText.setGravity(Gravity.BOTTOM);
        final Button sendButton = (Button) findViewById(R.id.send);
        sendButton.setOnClickListener(new Button.OnClickListener() {  
	        public void onClick(View v)
            {	
        		Log.d("edittext", editText.getText().toString()); 
        		uploadFile();
        		
        		userFunctions = new UserFunctions();
        		userFunctions.uploadSongUser(editText.getText().toString(), mFileName, userFunctions.userEmail(getApplicationContext()));
        		Intent dashboard = new Intent(getApplicationContext(), Singsomething.class);

                startActivity(dashboard);
            }
         });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }
}