package com.chubby.chubbySleep;



import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

public class showHistory extends ListActivity{
	
	       //Retrieve all entries from the database
	        private chubbySleepDBHelper mDbHelper;
	        
	        /** Called when the activity is first created. */
	        @Override
	        public void onCreate(Bundle savedInstanceState) {
	            super.onCreate(savedInstanceState);
	         
	          setContentView(R.layout.history);
	            
	           mDbHelper = new chubbySleepDBHelper(this);
	           mDbHelper.open();
	           fillData(); 
	  }
	  
	   private void fillData() {
	        // Get all of the history entries from the database and create the item list
	        Cursor c = mDbHelper.fetchAllNotes();
	       
	        List<String> results = new ArrayList<String>();
	        startManagingCursor(c);
	        int createDatcol =c.getColumnIndex("createdate");
	        int birthDatCol =c.getColumnIndex("birthdate");
	        int percentileCol = c.getColumnIndex("percentile");
	        if (c!=null){
    				 
	         if(c.moveToFirst()) {
	        	 //Add headers for table to help display in the layout
        		 results.add("Birth Date, Create Date, Percentile"); 	        	 
	        	 do {
	        		 results.add(c.getString(createDatcol)+","+ c.getString(birthDatCol)+","+ 
	        				 c.getString(percentileCol));
	        	 }while(c.moveToNext());
	         }
	        }
	        setContentView(R.layout.history);
	        String[] from = new String[] { chubbySleepDBHelper.KEY_BIRTHDATE, chubbySleepDBHelper.KEY_CREATEDATE, chubbySleepDBHelper.KEY_PERCENTILE };
	      
	       
	        int[] to = new int[] {R.id.bdate, R.id.cdate, R.id.percent};
	       
	        
	        // Now create an array adapter and set it to display using our row
	        SimpleCursorAdapter notes =
	            new SimpleCursorAdapter(this, R.layout.notes_row, c, from, to);
	        setListAdapter(notes);
	        
	      //  this.setListAdapter(new ArrayAdapter<String>(this,
            //        R.layout.history, results));
	    }
	   protected void onResume() {
			super.onResume();
			setContentView(R.layout.history);
		    mDbHelper = new chubbySleepDBHelper(this);
	           mDbHelper.open();
	           fillData();
				
		}
}