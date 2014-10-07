package com.predictCricket;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.Facebook.DialogListener;

public class AuthorizeListener implements DialogListener {
	Facebook mfacebook;
	private String wallpost;
	
    public AuthorizeListener(Facebook mfacebook, String wallpost) {
        this.mfacebook = mfacebook;
        this.wallpost = wallpost;
    }
	@Override
	public void onCancel() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onComplete(Bundle values) {
		
		try {
			String response = mfacebook.request("me");

	        values.putString("access_token", values.getString("access_token"));
	        values.putString("message", wallpost);
	        values.putString("description", "Cricket Champ");
	       values.putString("method", "stream.publish");
	        Log.i("values",values.toString());
	       response=mfacebook.request(values);
	       // response = mfacebook.request("me/feed", values,  "POST");
	        Log.i("Tests", "got response: " + response);
	        Log.i("adone","adone");
	        if (response == null || response.equals("") || 
	                response.equals("false")) {
	           Log.i("Error", "Blank response");
	        }
	 } catch(Exception e) {
	     e.printStackTrace();
	 }

	}

	@Override
	public void onError(DialogError e) {
		
	}

	@Override
	public void onFacebookError(FacebookError e) {
		// TODO Auto-generated method stub

	}

}
