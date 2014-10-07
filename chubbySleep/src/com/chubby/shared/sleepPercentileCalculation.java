package com.chubby.shared;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.util.Log;

import com.chubby.chubbySleep.R;


public class sleepPercentileCalculation extends Activity{
	public sleepPercentileCalculation(){
		//Hashtable percentileTab = new Hashtable();
		//Read hashtable from file in /res/raw/sleepmap
		//Key consists of ageInMonths_totalSleep_longestSleep
		//Value is the percentile
		InputStream fos = null;
		try {
	    fos = getResources().openRawResource(R.raw.sleepmap);
		byte[] reader = new byte[fos.available()];
		while (fos.read(reader) != -1) {}
		String fileContent = new String(reader);
		Log.i("FILEOUTPUT", fileContent);
	} catch(IOException e) {	
		Log.i("FILEOUTPUT", "error opening file");
	} 
	}
}


