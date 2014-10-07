package com.chubby.chubbySleep;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.GetChars;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;

@SuppressWarnings("unused")
public class chubbySleep extends TabActivity {
    protected static final String KEY_BABYAGE = null;
	/** Called when the activity is first created. */
	private EditText mBabyAge;
	private EditText mBabyAge1; 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      //Create Tabs ******************
        setContentView(R.layout.main);

        Resources res = getResources(); // Resource object to get Drawables
        TabHost tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;  // Reusable TabSpec for each tab
        Intent intent;  // Reusable Intent for each tab

        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, calculatePercentile.class);

        // Add input form tab
        spec = tabHost.newTabSpec("inputForm").setIndicator("Calculate",
                          res.getDrawable(R.drawable.create_percentile_tab))
                      .setContent(intent);
        tabHost.addTab(spec);

        // Add history tab
        intent = new Intent().setClass(this, showHistory.class);
        spec = tabHost.newTabSpec("history").setIndicator("History",
                          res.getDrawable(R.drawable.history_tab))
                      .setContent(intent);
        tabHost.addTab(spec);
        
        // Add feedback tab
        intent = new Intent().setClass(this, feedback.class);
        spec = tabHost.newTabSpec("feedback").setIndicator("Feedback",
                          res.getDrawable(R.drawable.feedback_tab))
                      .setContent(intent);
        tabHost.addTab(spec);
        
    }
    //End tabs *********
 
 
    
  }
