package com.chubby.chubbySleep;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

public class calculatePercentile extends Activity{
	SimpleDateFormat dateformatYYMMDD = new SimpleDateFormat("yyyy-MM-dd");
	Integer resultPercent = -1;
	String percentile = "";
	String improveScore="";
	String reasont="";
	String reasonl="";
	private TextView totalsR;
	private TextView longsR;
	  public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.data_entry_view);
	        
	        //Create Data Entry Form 
	        createEntryFormHelper();
		
	  }
	  
public void createEntryFormHelper()
{
	 /** Begin implementing dropdown for Total Sleep Time **/
    Spinner spinner3 = (Spinner) findViewById(R.id.total_sleep_spinner);
    ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(
            this, R.array.totalSleep_array, android.R.layout.simple_spinner_item);
    adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinner3.setAdapter(adapter3);
    
    /** End implementing dropdown for Total Sleep Time **/
    
    /** Begin implementing dropdown for Sleep Time **/
    Spinner spinner1 = (Spinner) findViewById(R.id.longest_sleep_spinner);
    ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
            this, R.array.sleepTime_array, android.R.layout.simple_spinner_item);
    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinner1.setAdapter(adapter1);
    
    /** End implementing dropdown for Sleep Time **/
    
    Button confirmButton = (Button) findViewById(R.id.confirm);
	confirmButton.setOnClickListener(new View.OnClickListener() {
    public void onClick(View view) {	    
    	
    	final DatePicker dp = (DatePicker) findViewById(R.id.age_pick);
    	int bmonth = dp.getMonth() +1;
    	String birthday = dp.getYear()+"-"+bmonth+"-"+dp.getDayOfMonth();
    	Date bday = null;
    	try{ bday =dateformatYYMMDD.parse(birthday);}
    	catch(Exception e){};
    	//Log.i("Month","=" +dp.getMonth()+","+bmonth);
    	//Log.i("Birthdate","=" +birthday);
 
    	final Spinner totalSleepSpinner = (Spinner) findViewById(R.id.total_sleep_spinner);  
    	String totalSleepEntry = totalSleepSpinner.getSelectedItem().toString();  
    	final Spinner longestSleepSpinner = (Spinner) findViewById(R.id.longest_sleep_spinner);  
    	String longestSleepEntry = longestSleepSpinner.getSelectedItem().toString();  
    	//Get current Date
    	Date dateNow = new Date ();
    	StringBuilder nowYYMMDD = new StringBuilder( dateformatYYMMDD.format(dateNow) );
    	String now = ""+nowYYMMDD;
    	//Check if birthdate is in the past
    	
    	Log.i("Date check", "now"+dateNow+"bday"+bday+" "+dateNow.after(bday));
    	if (dateNow.after(bday) == true) {
    		resultPercent = getPercentile(birthday, now, totalSleepEntry, longestSleepEntry);
    		percentile = resultPercent.toString()+"%";
    		addDataToHistory(now, birthday, percentile);
    	} else percentile = "ERROR: Invalid birthday!";
    	
    	//Convert percentile value to range and display corresponding layout
    	if (percentile.equalsIgnoreCase("25%")) {
    		
    		setContentView(R.layout.report25);
    	} else if (percentile.equalsIgnoreCase("50%")) {
    		
    		setContentView(R.layout.report50);
    	} else if (percentile.equalsIgnoreCase("75%")) {
    		
    		setContentView(R.layout.report75);
    	} else if (percentile.equalsIgnoreCase("100%")) {
    		
    		setContentView(R.layout.report100);
    	} else {
    		setContentView(R.layout.result);
    	};
    	
    	if (!(percentile.equalsIgnoreCase("100%"))) {
    	totalsR = (TextView) findViewById(R.id.totalsreason);
    	longsR = (TextView) findViewById(R.id.longsreason);
    	totalsR.setText(reasont);
    	longsR.setText(reasonl);
    	}
    	//Log data to server
    	sendData(birthday, now, totalSleepEntry, longestSleepEntry);
            }
        }); 
}
	 

public void addDataToHistory(String nowYYMMDD, String bdayYYMMDD, String percentileVal)
{        
	chubbySleepDBHelper mDBHelper;
	mDBHelper = new chubbySleepDBHelper(this);
	mDBHelper.open();

	mDBHelper.createNote(nowYYMMDD, percentileVal, bdayYYMMDD);
  mDBHelper.close();
}

public int getPercentile(String birthDate, String currDate, String totalSleep, String longestSleep)
{
	DateTime start = new DateTime(birthDate, DateTimeZone.UTC);
	DateTime end = new DateTime(currDate, DateTimeZone.UTC);
	Period period = new Period(start, end, PeriodType.yearMonthDayTime());
	Integer ageInMonths = Math.abs(period.getYears()*12+period.getMonths());
	int pFactor = 0;
	//Log.i("getP","age"+ageInMonths);
	//get percentile based on total sleep in 24 hrs
	//*****0 to 25%
	if ((ageInMonths<36) & ((totalSleep.equalsIgnoreCase("10"))|(totalSleep.equalsIgnoreCase("less than 10")))) pFactor = 1;
	else if ((ageInMonths>=36) & (totalSleep.equalsIgnoreCase("less than 10"))) pFactor = 1;	
	//*****25 to 50%
	if ((ageInMonths<13) & ((totalSleep.equalsIgnoreCase("11"))|(totalSleep.equalsIgnoreCase("12")))) pFactor = 2;
	else if ((ageInMonths>12)&(ageInMonths<37) & ((totalSleep.equalsIgnoreCase("10"))|(totalSleep.equalsIgnoreCase("11"))|(totalSleep.equalsIgnoreCase("12")))) pFactor = 2;
	else if ((ageInMonths>36) & (totalSleep.equalsIgnoreCase("10"))) pFactor = 2;
	//*****50 to 75%
	if ((ageInMonths<13) & ((totalSleep.equalsIgnoreCase("13"))|(totalSleep.equalsIgnoreCase("14")))) pFactor = 3;
	else if ((ageInMonths>12)&(ageInMonths<37) & (totalSleep.equalsIgnoreCase("13"))) pFactor = 3;
	else if ((ageInMonths>36) & (totalSleep.equalsIgnoreCase("11"))) pFactor = 3;
	//*****75 to 100%
	if ((ageInMonths<13) & (totalSleep.equalsIgnoreCase("15 or more"))) pFactor = 4;
	else if ((ageInMonths>12)&(ageInMonths<37) & ((totalSleep.equalsIgnoreCase("14"))|(totalSleep.equalsIgnoreCase("15 or more")))) pFactor = 4;
	else if ((ageInMonths>36) & ((totalSleep.equalsIgnoreCase("12"))|(totalSleep.equalsIgnoreCase("13"))|(totalSleep.equalsIgnoreCase("14"))|(totalSleep.equalsIgnoreCase("15 or more")))) pFactor = 4;
	//**************
	int lFactor =0;
	//get percentile based on night time sleep s
	//*****0 to 25%
	if ((ageInMonths<7) & (longestSleep.equalsIgnoreCase("less than 6"))) lFactor = 1;
	else if (((ageInMonths>6)&(ageInMonths<13)) & ((longestSleep.equalsIgnoreCase("less than 6")) | (longestSleep.equalsIgnoreCase("7")) |(longestSleep.equalsIgnoreCase("8")))) lFactor = 1;	
	else if (((ageInMonths>12)&(ageInMonths<19)) & ((longestSleep.equalsIgnoreCase("less than 6")) | (longestSleep.equalsIgnoreCase("7")) |(longestSleep.equalsIgnoreCase("8"))| (longestSleep.equalsIgnoreCase("9")))) lFactor = 1;	
	else if ((ageInMonths>18) & ((longestSleep.equalsIgnoreCase("less than 6")) | (longestSleep.equalsIgnoreCase("7")))) lFactor = 1;	
	
	//*****25 to 50%
	if ((ageInMonths<7) & (longestSleep.equalsIgnoreCase("7"))) lFactor = 2;
	else if (((ageInMonths>6)&(ageInMonths<13)) & (longestSleep.equalsIgnoreCase("9"))) lFactor = 2;	
	else if (((ageInMonths>12)&(ageInMonths<19)) & (longestSleep.equalsIgnoreCase("10"))) lFactor = 2;	
	else if ((ageInMonths>18) & (longestSleep.equalsIgnoreCase("8"))) lFactor = 2;	
	//*****50 to 75%
	if ((ageInMonths<7) & (longestSleep.equalsIgnoreCase("8"))| (longestSleep.equalsIgnoreCase("9"))) lFactor = 3;
	else if (((ageInMonths>6)&(ageInMonths<13)) & (longestSleep.equalsIgnoreCase("10"))) lFactor = 3;	
	else if (((ageInMonths>12)&(ageInMonths<19)) & (longestSleep.equalsIgnoreCase("11"))) lFactor = 3;	
	else if ((ageInMonths>18) & (longestSleep.equalsIgnoreCase("9"))) lFactor = 3;	
	//*****75 to 100%
	if ((ageInMonths<7) & (longestSleep.equalsIgnoreCase("10"))| (longestSleep.equalsIgnoreCase("11"))| (longestSleep.equalsIgnoreCase("12 or more"))) lFactor = 4;
	else if (((ageInMonths>6)&(ageInMonths<13)) & (longestSleep.equalsIgnoreCase("11")) | (longestSleep.equalsIgnoreCase("12 or more"))) lFactor = 4;	
	else if (((ageInMonths>12)&(ageInMonths<19)) & (longestSleep.equalsIgnoreCase("12 or more"))) lFactor = 4;	
	else if ((ageInMonths>18) & (longestSleep.equalsIgnoreCase("10"))| (longestSleep.equalsIgnoreCase("11"))| (longestSleep.equalsIgnoreCase("12 or more"))) lFactor = 4;
	
	//**************
	Integer percentile = Math.abs(((pFactor+lFactor)/2)*25);
	//Log.i("Percentile", "pFactor =" + pFactor + ",lFactor="+lFactor+",percentile =" + percentile);
	improvePercentile(ageInMonths, lFactor,pFactor);
	return percentile;
  }

public void improvePercentile(Integer ageInMonths, int lFactor, int pFactor){
	String requiredls="";
	String requiredts="";
	
	switch(lFactor){
	case 1:if (ageInMonths<7) requiredts="atleast 8 hours";
	   	   else if ((ageInMonths>6)&(ageInMonths<13))  requiredls="atleast 10 hours";
	   	   else if ((ageInMonths>13)&(ageInMonths<19))  requiredls="atleast 11 hours";
	   	   else if (ageInMonths>18) requiredls="atleast 9 hours"; break;
	case 2:if (ageInMonths<7) requiredts="atleast 8 hours";
	   	   else if ((ageInMonths>6)&(ageInMonths<13))  requiredls="atleast 10 hours";
	   	   else if ((ageInMonths>13)&(ageInMonths<19))  requiredls="atleast 11 hours";
	   	   else if (ageInMonths>18) requiredls="atleast 9 hours"; break;
	case 3:if (ageInMonths<7) requiredts="atleast 8 hours";
	   else if ((ageInMonths>6)&(ageInMonths<13))  requiredls="atleast 10 hours";
   	   else if ((ageInMonths>13)&(ageInMonths<19))  requiredls="atleast 11 hours";
   	   else if (ageInMonths>18) requiredls="atleast 9 hours"; break;
	default:break;
	}
	switch(pFactor){
	case 1:if (ageInMonths<13) requiredts="13-14hrs";
		   else if ((ageInMonths>12)&(ageInMonths<37))  requiredts="13hrs";
		   else if (ageInMonths>36) requiredts="11hrs"; break;
	case 2:if (ageInMonths<13) requiredts="13-14hrs";
	   	   else if ((ageInMonths>12)&(ageInMonths<37))  requiredts="13hrs";
	   else if (ageInMonths>36) requiredts="11hrs"; break;
	case 3:if (ageInMonths<13) requiredts="15 or more hrs";
	   	   else if ((ageInMonths>12)&(ageInMonths<37))  requiredts="14 - 15hrs";
	   else if (ageInMonths>36) requiredts="12 to 15hrs"; break;
	default:break;
	}
	
	if (requiredts.equalsIgnoreCase("") != true) reasont ="A child of this age sleeps an average of "+requiredts+" in a 24 hour period.";
	//Add text for high percentiles
	if (requiredls.equalsIgnoreCase("") != true) reasonl ="A child of this age is able to sleep continuously for a stretch of "+requiredls+" .";
	if (reasont.equalsIgnoreCase("")|reasonl.equalsIgnoreCase("")) improveScore ="Why did you get this score?";
}

public void sendData(String birthDate, String currDate, String totalSleep, String longestSleep) {
	// Create JSON object
	 String feedbackj=currDate + ",b= " +birthDate+",ts= " +totalSleep+",ls= "+longestSleep;
		Log.i("HTTP resp data", feedbackj);
	 //Send data as feedback via HTTP Post 
		String url = "http://www.chubbysleep.appspot.com/feedback";
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
     @SuppressWarnings("unused")
	HttpResponse response;
     try {
			response = httpclient.execute(request);
		//Log.i("HTTP resp data", response.toString());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		}

}
protected void onResume() {
	super.onResume();
	setContentView(R.layout.data_entry_view);
	 //Create Data Entry Form 
    createEntryFormHelper();
		
}
}