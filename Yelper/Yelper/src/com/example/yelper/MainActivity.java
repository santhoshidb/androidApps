package com.example.yelper;

import java.io.IOException;
import java.io.OutputStreamWriter;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.*;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.os.Build;

public class MainActivity extends ActionBarActivity {

	  String consumerKey = "BDJkRMDSMntjV3965zE3PA";
      String consumerSecret = "BLyE0--sDYMvFe4P2rVIRneEPzY";
      String token = "fE5BY73VlahDWdnIx0t7NC6laPO-sM-L";
      String tokenSecret = "lY1Af3UE7XPw9Nqnz-k8evpreQI";
      String response = new String("null");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Update tokens here from Yelp developers site, Manage API access.
          
           // new callYelpAPI().execute(consumerKey, consumerSecret, token, tokenSecret);
           

           // System.out.println(response);
            
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) 
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            
            Yelp yelp = new Yelp(consumerKey, consumerSecret, token, tokenSecret);
      	 response = yelp.search("yoga", 30.361471, -87.164326);
          
         //  response = yelp.business("sanchos-taqueria-palo-alto");
        }
       
        TextView displayResponse = (TextView) findViewById(R.id.textView1);
        displayResponse.setText(response);
            Log.i("RESPONSE_LOG",response);
        
        String parsed = null;
		try {
			parsed = parseJSON(response);
		} catch (JSONException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        displayResponse.setText(parsed);
        
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.rLayout1, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
    
    private static String parseJSON(String responseJSON) throws org.json.simple.parser.ParseException, JSONException {
    	   
    	    JSONParser parser = new JSONParser();
    	    JSONObject response = null;
    	    try {
    	      response = (JSONObject) parser.parse(responseJSON);
    	    } catch (ParseException pe) {
    	      System.out.println("Error: could not parse JSON response:");
    	      System.out.println(responseJSON);
    	      System.exit(1);
    	    }
    	
    	    JSONArray reviewList = null;
			reviewList = (JSONArray) response.get("reviews");
    	    JSONObject firstReview = (JSONObject) reviewList.get(3);
    	    String firstReviewExcerpt = null;
			firstReviewExcerpt = firstReview.get("excerpt").toString();
    	    int totalReviews = reviewList.size();
    	    // Select the first business and display business details
			return totalReviews + "-" + firstReviewExcerpt;
    	    
    	  }
 
}
