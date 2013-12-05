package com.train_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ModeGameActivity extends Activity{
	
	 public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choosemodegame);
		
	}
	public void unknownmusic(View view){
		Intent intent = new Intent(this, AudioRecordUnknown.class);
		startActivity(intent);
	}
	public void knownmusic(View view){
		Intent intent = new Intent(this, AudioRecordKnown.class);
		startActivity(intent);
	}
	
	public void answer_last (View view){
		Intent intent = new Intent(this, AnswerLastSongActivity.class);
		startActivity(intent);
	}
}
