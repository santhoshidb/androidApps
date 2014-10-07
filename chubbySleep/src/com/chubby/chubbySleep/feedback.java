package com.chubby.chubbySleep;


import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class feedback extends Activity {
	     
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
     
      setContentView(R.layout.feedback);
     // Get text value from feedback screen
    
    Button submitfButton = (Button) findViewById(R.id.submitfeedback);
    submitfButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View view) {
    	  
    	 // Log.i("Feedback", "in click");
    	  EditText feedbackTxt = (EditText) findViewById(R.id.fbtext);
    	  EditText feedbackEmail = (EditText) findViewById(R.id.fbemail);
    	 String feedbackVal = " ";
    	feedbackVal = "Email:" + feedbackEmail.getText().toString()+",Feedback:"+feedbackTxt.getText().toString();
    	//Log.i("Feedback", feedbackVal);
    //Create JSON object
    	  JSONObject feedbackj = new JSONObject();
    	  try {
			feedbackj.put("feedback",feedbackVal);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    //Send feedback via HTTP Post 
			String url = "http://chubbysleep.appspot.com/feedback";
			HttpClient httpclient = new DefaultHttpClient();
	        HttpPost request = new HttpPost(url);
	        HttpEntity entity;
	        StringEntity s = null;
			try {
				s = new StringEntity(feedbackj.toString());
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
	        entity = s;
	        request.setEntity(entity); 
	        HttpResponse response;
	        try {
				response = httpclient.execute(request);
			//Log.i("HTTP resp", response.toString());
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace(); 
			}
			setContentView(R.layout.feedback_submitted);
      }

    });     
    }
    
}
