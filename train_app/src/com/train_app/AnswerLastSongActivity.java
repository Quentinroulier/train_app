package com.train_app;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;

import com.library.Model;
import com.library.Group;

public class AnswerLastSongActivity extends ListActivity {

	SparseArray<Group> groups = new SparseArray<Group>();

/** Called when the activity is first created. */

 protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.answerlastsong);
    createData();
    ExpandableListView listView = (ExpandableListView) findViewById(R.id.listView);
    MyExpandableListAdapter adapter = new MyExpandableListAdapter(this, groups);
    listView.setAdapter(adapter);
    
    
  }
  public void createData() {
	    for (int j = 0; j < 5; j++) {
	      Group group = new Group("Test " + j);
	      for (int i = 0; i < 5; i++) {
	        group.children.add("Sub Item" + i);
	        
	      }
	      groups.append(j, group);
	    }
	  }

} 