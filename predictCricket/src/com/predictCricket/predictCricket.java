package com.predictCricket;

import java.io.IOException;
import java.net.MalformedURLException;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.Facebook.DialogListener;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class predictCricket extends Activity {
private static final String[] String = null;
	private String[] groupAPicks = new String[4]; 
	private String[] groupBPicks = new String[4]; 
	private int[] qtrPicksIndx = {-1,-1,-1,-1};  
	private int[] semiPicksIndx = {-1,-1};  
	private int[] groupAPicksIndx = {0,0,0,0}; 
	private int[] groupBPicksIndx =  {0,0,0,0}; 
	private int winner=-1;
	/*
	 * To set image resources dynamically in QTR, SEMI and FINAL
	 * screens, create array of image resource IDs.
	 */
	String allTeams[] ={"Australia", "Canada", "Kenya", "New Zealand", "Pakistan","Sri Lanka","Zimbabwe", 
			"Bangladesh","England","India","Ireland","Netherlands","South Africa","West Indies"};
	String allImagesTxt[] ={"AUS", "CAN", "KEN", "NZ", "PAK","SL","ZIM", "BGD","ENG","IND","IRL","NLD","SA","WI"};
	final int[] grpAImages = new int[] {R.drawable.aus, R.drawable.can, R.drawable.ken,
			R.drawable.nz, R.drawable.pak, R.drawable.sl, R.drawable.zim};
	final int[] grpBImages = new int[] {R.drawable.ban, R.drawable.eng, R.drawable.ind,
			R.drawable.irl, R.drawable.nld, R.drawable.sa, R.drawable.wi};	
	final int[] grpASelImages = new int[] {R.drawable.aus_sel, R.drawable.can_sel, R.drawable.ken_sel,
			R.drawable.nz_sel, R.drawable.pak_sel, R.drawable.sl_sel, R.drawable.zim_sel};
	final int[] grpBSelImages = new int[] {R.drawable.ban_sel, R.drawable.eng_sel, R.drawable.ind_sel,
			R.drawable.irl_sel, R.drawable.nld_sel, R.drawable.sa_sel, R.drawable.wi_sel};

	final int[] allImages = new int[] {R.drawable.aus, R.drawable.can, R.drawable.ken,
			R.drawable.nz, R.drawable.pak, R.drawable.sl, R.drawable.zim, R.drawable.ban, R.drawable.eng, R.drawable.ind,
			R.drawable.irl, R.drawable.nld, R.drawable.sa, R.drawable.wi};
	final int[] allSelImages = new int[] {R.drawable.aus_sel, R.drawable.can_sel, R.drawable.ken_sel,
			R.drawable.nz_sel, R.drawable.pak_sel, R.drawable.sl_sel, R.drawable.zim_sel, R.drawable.ban_sel, R.drawable.eng_sel, R.drawable.ind_sel,
			R.drawable.irl_sel, R.drawable.nld_sel, R.drawable.sa_sel, R.drawable.wi_sel};
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        groupa_pick();
       
    }
    
 private void groupa_pick(){
	 //Pick group A winners
     setContentView(R.layout.group_a);
   //Restore user selected values
     final Spinner a1Spinner = (Spinner) findViewById(R.id.Spinner01a);  
     a1Spinner.setSelection(groupAPicksIndx[0]);
     final Spinner a2Spinner = (Spinner) findViewById(R.id.Spinner02a);  
     a2Spinner.setSelection(groupAPicksIndx[1]);
     final Spinner a3Spinner = (Spinner) findViewById(R.id.Spinner03a);  
     a3Spinner.setSelection(groupAPicksIndx[2]);
     final Spinner a4Spinner = (Spinner) findViewById(R.id.Spinner04a);
     a4Spinner.setSelection(groupAPicksIndx[3]);
    //group A Next button
     Button nextAButton = (Button) findViewById(R.id.nextA);
     nextAButton.setOnClickListener(new View.OnClickListener() {
     public void onClick(View view) {
    	 //Get picks
    	final Spinner a1Spinner = (Spinner) findViewById(R.id.Spinner01a);  
     	String pickA1 = a1Spinner.getSelectedItem().toString(); 
     	groupAPicks[0] = pickA1 +"";
     	groupAPicksIndx[0] = a1Spinner.getSelectedItemPosition();
     	final Spinner a2Spinner = (Spinner) findViewById(R.id.Spinner02a);  
     	String pickA2 = a2Spinner.getSelectedItem().toString();
     	groupAPicks[1] = pickA2 +"";
    	groupAPicksIndx[1] = a2Spinner.getSelectedItemPosition();
     	final Spinner a3Spinner = (Spinner) findViewById(R.id.Spinner03a);  
     	String pickA3 = a3Spinner.getSelectedItem().toString();
     	groupAPicks[2] = pickA3 +"";
    	groupAPicksIndx[2] = a3Spinner.getSelectedItemPosition();
     	final Spinner a4Spinner = (Spinner) findViewById(R.id.Spinner04a);  
     	String pickA4 = a4Spinner.getSelectedItem().toString();
     	groupAPicks[3] = pickA3 +"";
    	groupAPicksIndx[3] = a4Spinner.getSelectedItemPosition();
    	
    	//Check if Picks are Unique
    	checkAPicks(pickA1,pickA2,pickA3,pickA4);	
     }
     });
 }
 
 private void groupb_pick(){
	//Pick group B winners
     setContentView(R.layout.group_b);
   //Restore user selected values
     final Spinner b1Spinner = (Spinner) findViewById(R.id.Spinner01b);  
     b1Spinner.setSelection(groupBPicksIndx[0]);
      final Spinner b2Spinner = (Spinner) findViewById(R.id.Spinner02b);  
      b2Spinner.setSelection(groupBPicksIndx[1]);
      final Spinner b3Spinner = (Spinner) findViewById(R.id.Spinner03b);  
      b3Spinner.setSelection(groupBPicksIndx[2]);
      final Spinner b4Spinner = (Spinner) findViewById(R.id.Spinner04b);
      b4Spinner.setSelection(groupBPicksIndx[3]);
    //group B Next button
     Button nextBButton = (Button) findViewById(R.id.nextB);
     nextBButton.setOnClickListener(new View.OnClickListener() {
     public void onClick(View view) {
    	 //Get picks
    	final Spinner b1Spinner = (Spinner) findViewById(R.id.Spinner01b);  
     	String pickB1 = b1Spinner.getSelectedItem().toString(); 
     	groupBPicks[0] = pickB1 +"";
     	groupBPicksIndx[0] = b1Spinner.getSelectedItemPosition();
     	final Spinner b2Spinner = (Spinner) findViewById(R.id.Spinner02b);  
     	String pickB2 = b2Spinner.getSelectedItem().toString();
     	groupBPicks[1] = pickB2 +"";
     	groupBPicksIndx[1] = b2Spinner.getSelectedItemPosition();
     	final Spinner b3Spinner = (Spinner) findViewById(R.id.Spinner03b);  
     	String pickB3 = b3Spinner.getSelectedItem().toString();
     	groupBPicks[2] = pickB3 +"";
     	groupBPicksIndx[2] = b3Spinner.getSelectedItemPosition();
     	final Spinner b4Spinner = (Spinner) findViewById(R.id.Spinner04b);  
     	String pickB4 = b4Spinner.getSelectedItem().toString();
     	groupBPicks[3] = pickB4 +"";
     	groupBPicksIndx[3] = b4Spinner.getSelectedItemPosition();
     	
    	 checkBPicks(pickB1,pickB2,pickB3,pickB4);	
     }
     });
   //group B Prev button
     Button prevBButton = (Button) findViewById(R.id.prevB);
     prevBButton.setOnClickListener(new View.OnClickListener() {
     public void onClick(View view) {
    	 //Get user's picks before moving to previous screen
    	final Spinner b1Spinner = (Spinner) findViewById(R.id.Spinner01b);  
      	String pickB1 = b1Spinner.getSelectedItem().toString(); 
   //   	groupBPicks[0] = pickB1 +"";
      	groupBPicksIndx[0] = b1Spinner.getSelectedItemPosition();
      	final Spinner b2Spinner = (Spinner) findViewById(R.id.Spinner02b);  
      	String pickB2 = b2Spinner.getSelectedItem().toString();
  //    	groupBPicks[1] = pickB2 +"";
      	groupBPicksIndx[1] = b2Spinner.getSelectedItemPosition();
      	final Spinner b3Spinner = (Spinner) findViewById(R.id.Spinner03b);  
      	String pickB3 = b3Spinner.getSelectedItem().toString();
    //  	groupBPicks[2] = pickB3 +"";
      	groupBPicksIndx[2] = b3Spinner.getSelectedItemPosition();
      	final Spinner b4Spinner = (Spinner) findViewById(R.id.Spinner04b);  
      	String pickB4 = b4Spinner.getSelectedItem().toString();
    //  	groupBPicks[3] = pickB4 +"";
      	groupBPicksIndx[3] = b4Spinner.getSelectedItemPosition();
    	//Restore user selected values
         b1Spinner.setSelection(groupBPicksIndx[0]);
         b2Spinner.setSelection(groupBPicksIndx[1]);  
         b3Spinner.setSelection(groupBPicksIndx[2]);
         b4Spinner.setSelection(groupBPicksIndx[3]);
    	 //Go to group A pick screen
          groupa_pick();	
     }
     }); 
 }
 
 private void checkAPicks(String pickA1,String pickA2,String pickA3,String pickA4){
	 
	 if((pickA1.compareTo(pickA2)==0)||(pickA1.compareTo(pickA3)==0)||(pickA1.compareTo(pickA4)==0)||(pickA2.compareTo(pickA3)==0)||(pickA2.compareTo(pickA4)==0)||(pickA3.compareTo(pickA4)==0)){
		 //Show error message
		 Toast toast=Toast.makeText(this, "Error: Teams must be unique. Please try again.", 2000);
		 toast.setGravity(Gravity.CENTER, 0, 0);
		 toast.show();
		 groupa_pick();
	 }
	 else {
		 groupb_pick();
	 }
 }
 
private void checkBPicks(String pickB1,String pickB2,String pickB3,String pickB4){
	 
	 if((pickB1.compareTo(pickB2)==0)||(pickB1.compareTo(pickB3)==0)||(pickB1.compareTo(pickB4)==0)||(pickB2.compareTo(pickB3)==0)||(pickB2.compareTo(pickB4)==0)||(pickB3.compareTo(pickB4)==0)){
		 //Show error message
		 Toast toast=Toast.makeText(this, "Error: Teams must be unique. Please try again.", 2000);
		 toast.setGravity(Gravity.CENTER, 0, 0);
		 toast.show();
		 groupb_pick();
	 }
	 else {
		 pickQtrFinalWin();
	 }
 }

private void pickQtrFinalWin(){
	for(int i=0; i<4; i++){
		qtrPicksIndx[i] = -1;
	}
	
	setContentView(R.layout.quarter_final);
	
	final ImageView qtrViewA1 = (ImageView) findViewById(R.id.qaiv1);
	qtrViewA1.setImageResource(grpAImages[groupAPicksIndx[0]]); 
	final ImageView qtrViewB1 = (ImageView) findViewById(R.id.qbiv1);
	qtrViewB1.setImageResource(grpBImages[groupBPicksIndx[3]]); 
	
	final ImageView qtrViewA2 = (ImageView) findViewById(R.id.qaiv2);
	qtrViewA2.setImageResource(grpAImages[groupAPicksIndx[1]]); 
	final ImageView qtrViewB2 = (ImageView) findViewById(R.id.qbiv2);
	qtrViewB2.setImageResource(grpBImages[groupBPicksIndx[2]]); 
	
	final ImageView qtrViewA3 = (ImageView) findViewById(R.id.qaiv3);
	qtrViewA3.setImageResource(grpAImages[groupAPicksIndx[2]]); 
	final ImageView qtrViewB3 = (ImageView) findViewById(R.id.qbiv3);
	qtrViewB3.setImageResource(grpBImages[groupBPicksIndx[1]]); 
	
	final ImageView qtrViewA4 = (ImageView) findViewById(R.id.qaiv4);
	qtrViewA4.setImageResource(grpAImages[groupAPicksIndx[3]]); 
	final ImageView qtrViewB4 = (ImageView) findViewById(R.id.qbiv4);
	qtrViewB4.setImageResource(grpBImages[groupBPicksIndx[0]]); 
	
	qtrViewA1.setOnTouchListener(new OnTouchListener(){
    	public boolean onTouch(View v, MotionEvent event) {
    		//Set A1 to display selected image
    		qtrViewA1.setImageResource(grpASelImages[groupAPicksIndx[0]]);
    		//Set B1 to display unselected image
    		qtrViewB1.setImageResource(grpBImages[groupBPicksIndx[3]]); 
    		//Add to Qtr picks
    		qtrPicksIndx[0]=groupAPicksIndx[0];
    		return false; 
    	}
	 });
	
	qtrViewA2.setOnTouchListener(new OnTouchListener(){
    	public boolean onTouch(View v, MotionEvent event) {
    		//Set A1 to display selected image
    		qtrViewA2.setImageResource(grpASelImages[groupAPicksIndx[1]]);
    		//Set B1 to display unselected image
    		qtrViewB2.setImageResource(grpBImages[groupBPicksIndx[2]]); 
    		//Add to Qtr picks
    		qtrPicksIndx[1]=groupAPicksIndx[1];
    		Log.i("QtrPick, A-", qtrPicksIndx[1]+","+groupAPicksIndx[1]);
    		return false; 
    	}
	 });
	
	qtrViewA3.setOnTouchListener(new OnTouchListener(){
    	public boolean onTouch(View v, MotionEvent event) {
    		//Set A1 to display selected image
    		qtrViewA3.setImageResource(grpASelImages[groupAPicksIndx[2]]);
    		//Set B1 to display unselected image
    		qtrViewB3.setImageResource(grpBImages[groupBPicksIndx[1]]); 
    		//Add to Qtr picks
    		qtrPicksIndx[2]=groupAPicksIndx[2];
    		return false; 
    	}
	 });
	


	qtrViewA4.setOnTouchListener(new OnTouchListener(){
	    	public boolean onTouch(View v, MotionEvent event) {
	    		//Set A1 to display selected image
	    		qtrViewA4.setImageResource(grpASelImages[groupAPicksIndx[3]]);
	    		//Set B1 to display unselected image
	    		qtrViewB4.setImageResource(grpBImages[groupBPicksIndx[0]]); 
	    		//Add to Qtr picks
	    		qtrPicksIndx[3]=groupAPicksIndx[3];
	    		return false; 
	    	}
		 });
	
	
	qtrViewB1.setOnTouchListener(new OnTouchListener(){
    	public boolean onTouch(View v, MotionEvent event) {
    		//Set A1 to display selected image
    		qtrViewB1.setImageResource(grpBSelImages[groupBPicksIndx[3]]);
    		//Set B1 to display unselected image
    		qtrViewA1.setImageResource(grpAImages[groupAPicksIndx[0]]); 
    		//Add to Qtr picks
    		qtrPicksIndx[0]=groupBPicksIndx[3] +7;
    		return false; 
    	}
	 });
	
	
	
	qtrViewB2.setOnTouchListener(new OnTouchListener(){
    	public boolean onTouch(View v, MotionEvent event) {
    		//Set A1 to display selected image
    		qtrViewB2.setImageResource(grpBSelImages[groupBPicksIndx[2]]);
    		//Set B1 to display unselected image
    		qtrViewA2.setImageResource(grpAImages[groupAPicksIndx[1]]); 
    		//Add to Qtr picks
    		qtrPicksIndx[1]=groupBPicksIndx[2] +7;
    		return false; 
    	}
	 });
	
	qtrViewB3.setOnTouchListener(new OnTouchListener(){
    	public boolean onTouch(View v, MotionEvent event) {
    		//Set A1 to display selected image
    		qtrViewB3.setImageResource(grpBSelImages[groupBPicksIndx[1]]);
    		//Set B1 to display unselected image
    		qtrViewA3.setImageResource(grpAImages[groupAPicksIndx[2]]); 
    		//Add to Qtr picks
    		qtrPicksIndx[2]=groupBPicksIndx[1] +7;
    		return false; 
    	}
	 });
			
	qtrViewB4.setOnTouchListener(new OnTouchListener(){
		    	public boolean onTouch(View v, MotionEvent event) {
		    		//Set A1 to display selected image
		    		qtrViewB4.setImageResource(grpBSelImages[groupBPicksIndx[0]]);
		    		//Set B1 to display unselected image
		    		qtrViewA4.setImageResource(grpAImages[groupAPicksIndx[3]]); 
		    		//Add to Qtr picks
		    		qtrPicksIndx[3]=groupBPicksIndx[0] +7;
		    		return false; 
		    	}
			 });
	
	//QtrFinal Next button
    Button nextQButton = (Button) findViewById(R.id.nextQ);
    nextQButton.setOnClickListener(new View.OnClickListener() {
    public void onClick(View view) {
    	//Check if all winners were selected
    	checkQtrPicks();
    }
    });
    
    //QTrFinal Prev Button
    Button prevQButton = (Button) findViewById(R.id.prevQ);
    prevQButton.setOnClickListener(new View.OnClickListener() {
    public void onClick(View view) {
    	groupb_pick();
    }
    });
}  

private void checkQtrPicks(){ 
	
	if ((qtrPicksIndx[0]<0) || (qtrPicksIndx[1]<0) || (qtrPicksIndx[2]<0) || (qtrPicksIndx[3]<0)) 
	{
		Toast toastq=Toast.makeText(this, "Error: Winners for all matches must be selected. Please try again.", 2000);
		toastq.setGravity(Gravity.CENTER, 0, 0);
		toastq.show();
		
	}
	else pickSemiFinalWin();
}

private void pickSemiFinalWin(){
	setContentView(R.layout.semi_final);
	for(int i=0; i<2; i++){
		semiPicksIndx[i] = -1;
	}
		final ImageView semiView1 = (ImageView) findViewById(R.id.si1);
		semiView1.setImageResource(allImages[qtrPicksIndx[0]]); 
		final ImageView semiView2 = (ImageView) findViewById(R.id.si2);
		semiView2.setImageResource(allImages[qtrPicksIndx[1]]); 
		
		final ImageView semiView3 = (ImageView) findViewById(R.id.si3);
		semiView3.setImageResource(allImages[qtrPicksIndx[2]]); 
		final ImageView semiView4 = (ImageView) findViewById(R.id.si4);
		semiView4.setImageResource(allImages[qtrPicksIndx[3]]); 
		
		
		semiView1.setOnTouchListener(new OnTouchListener(){
	    	public boolean onTouch(View v, MotionEvent event) {
	    		//Set A1 to display selected image
	    		semiView1.setImageResource(allSelImages[qtrPicksIndx[0]]);
	    		//Set B1 to display unselected image
	    		semiView2.setImageResource(allImages[qtrPicksIndx[1]]); 
	    		//Add to Qtr picks
	    		semiPicksIndx[0]=qtrPicksIndx[0];
	    		return false; 
	    	}
		 });
		
		semiView2.setOnTouchListener(new OnTouchListener(){
	    	public boolean onTouch(View v, MotionEvent event) {
	    		//Set A1 to display selected image
	    		semiView2.setImageResource(allSelImages[qtrPicksIndx[1]]);
	    		//Set B1 to display unselected image
	    		semiView1.setImageResource(allImages[qtrPicksIndx[0]]); 
	    		//Add to Qtr picks
	    		semiPicksIndx[0]=qtrPicksIndx[1];
	    		return false; 
	    	}
		 });
		
		
		semiView3.setOnTouchListener(new OnTouchListener(){
	    	public boolean onTouch(View v, MotionEvent event) {
	    		//Set A1 to display selected image
	    		semiView3.setImageResource(allSelImages[qtrPicksIndx[2]]);
	    		//Set B1 to display unselected image
	    		semiView4.setImageResource(allImages[qtrPicksIndx[3]]); 
	    		//Add to Qtr picks
	    		semiPicksIndx[1]=qtrPicksIndx[2];
	    		return false; 
	    	}
		 });
		
		semiView4.setOnTouchListener(new OnTouchListener(){
	    	public boolean onTouch(View v, MotionEvent event) {
	    		//Set A1 to display selected image
	    		semiView4.setImageResource(allSelImages[qtrPicksIndx[3]]);
	    		//Set B1 to display unselected image
	    		semiView3.setImageResource(allImages[qtrPicksIndx[2]]); 
	    		//Add to Qtr picks
	    		semiPicksIndx[1]=qtrPicksIndx[3];
	    		return false; 
	    	}
		 });
		
		//Next button
	    Button nextQButton = (Button) findViewById(R.id.nextS);
	    nextQButton.setOnClickListener(new View.OnClickListener() {
	    public void onClick(View view) {
	    	checkSemiPicks();
	    }
	    });
	    
	    //Prev Button
	    Button prevQButton = (Button) findViewById(R.id.prevS);
	    prevQButton.setOnClickListener(new View.OnClickListener() {
	    public void onClick(View view) {
	    	pickQtrFinalWin();
	    }
	    });
}

private void checkSemiPicks(){

	if ((semiPicksIndx[0]<0) || (semiPicksIndx[1]<0)) 
	{
		Toast toastq=Toast.makeText(this, "Error: Winners for all matches must be selected. Please try again.", 2000);
		toastq.setGravity(Gravity.CENTER, 0, 0);
		toastq.show();
	}
	else
	{
		pickFinalWin();
	}
		
}
private void pickFinalWin(){
	setContentView(R.layout.final_pick);
	winner = -1;
	final ImageView finalView1 = (ImageView) findViewById(R.id.fi1);
	finalView1.setImageResource(allImages[semiPicksIndx[0]]); 
	final ImageView finalView2 = (ImageView) findViewById(R.id.fi2);
	finalView2.setImageResource(allImages[semiPicksIndx[1]]); 
	
	//Winner announcement
	final TextView awin = (TextView) findViewById(R.id.announce);
	
	finalView1.setOnTouchListener(new OnTouchListener(){
    	public boolean onTouch(View v, MotionEvent event) {
    		//Set A1 to display selected image
    		finalView1.setImageResource(allSelImages[semiPicksIndx[0]]);
    		//Set B1 to display unselected image
    		finalView2.setImageResource(allImages[semiPicksIndx[1]]); 
    		//Add to Semi picks
    		winner = semiPicksIndx[0];
    		
    		awin.setText(allTeams[winner] + " Wins!");
    		
    		Log.i("Winnera", "-" + winner); 
    		return false; 
    	}
	 });
	
	finalView2.setOnTouchListener(new OnTouchListener(){
    	public boolean onTouch(View v, MotionEvent event) {
    		//Set A1 to display selected image
    		finalView2.setImageResource(allSelImages[semiPicksIndx[1]]);
    		//Set B1 to display unselected image
    		finalView1.setImageResource(allImages[semiPicksIndx[0]]); 
    		//Add to Semi picks
    		winner = semiPicksIndx[1];
    		
    		awin.setText(allTeams[winner] + " Wins!");
    		
    		Log.i("Winnerb", "-" + winner);
    		return false; 
    	}
	 });
	
	//Next button
    Button nextFButton = (Button) findViewById(R.id.nextF);
    nextFButton.setOnClickListener(new View.OnClickListener() {
    public void onClick(View view) {
    	checkFinalPicks();
    }
    });
    
    //Prev Button
    Button prevFButton = (Button) findViewById(R.id.prevF);
    prevFButton.setOnClickListener(new View.OnClickListener() {
    public void onClick(View view) {
    	pickSemiFinalWin();
    }
    }); 

}

private void checkFinalPicks(){

	if (winner<0)
	{
		Toast toastq=Toast.makeText(this, "Error: Please select a winner.", 2000);
		toastq.setGravity(Gravity.CENTER, 0, 0);
		toastq.show();
	}
	else
	{
		shareOnFB();
	}
	} 
	
private void shareOnFB(){	
	setContentView(R.layout.share_fb);
	Button fbButton = (Button) findViewById(R.id.postFBWall);
	fbButton.setOnClickListener(new View.OnClickListener() {
		public void onClick(View view) {
			shareFB();
		}
	});
	
	Button redoButton = (Button) findViewById(R.id.redo);
	redoButton.setOnClickListener(new View.OnClickListener() {
		public void onClick(View view) {
			groupa_pick();
		}
	});
	
}

public void shareFB(){
	String wp = generateWallPost();
	Facebook facebookClient = new Facebook("186458578043671");
	facebookClient.authorize(this, new String[] {"publish_stream"}, new AuthorizeListener(facebookClient, wp));
	Log.i("done","done");
	try {
		facebookClient.logout(this);
	} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

private String generateWallPost()
{
	String head1 ="My cricket world cup 2011 prediction:"
	+"\n Group A - "+"#1 "+allImagesTxt[groupAPicksIndx[0]]+", "+"#2 "+allImagesTxt[groupAPicksIndx[1]]+", "+"#3 "+allImagesTxt[groupAPicksIndx[2]]+", "+"#4 "+allImagesTxt[groupAPicksIndx[3]]
	+"\n Group B - "+"#1 "+allImagesTxt[groupBPicksIndx[0]+7]+", "+"#2 "+allImagesTxt[groupBPicksIndx[1]+7]+", "+"#3 "+allImagesTxt[groupBPicksIndx[2]+7]+", "+"#4 "+allImagesTxt[groupBPicksIndx[3]+7]
	+"\n"+"QF 1 - "+ allImagesTxt[groupAPicksIndx[0]] +" vs "+allImagesTxt[groupBPicksIndx[3]+7]+" - "+allImagesTxt[qtrPicksIndx[0]]+" wins."
	+"\n"+"QF 2 - "+ allImagesTxt[groupAPicksIndx[1]] +" vs "+allImagesTxt[groupBPicksIndx[2]+7]+" - "+allImagesTxt[qtrPicksIndx[1]]+" wins."
	+"\n"+"QF 3 - "+ allImagesTxt[groupAPicksIndx[2]] +" vs "+allImagesTxt[groupBPicksIndx[1]+7]+" - "+allImagesTxt[qtrPicksIndx[2]]+" wins."
	+"\n"+"QF 4 - "+ allImagesTxt[groupAPicksIndx[3]] +" vs "+allImagesTxt[groupBPicksIndx[0]+7]+" - "+allImagesTxt[qtrPicksIndx[3]]+" wins."
	+"\n"+"SF 1 - "+ allImagesTxt[qtrPicksIndx[0]] +" vs "+allImagesTxt[qtrPicksIndx[1]]+" - "+allImagesTxt[semiPicksIndx[0]]+" wins."+"\n"+"SF 2 - "+ allImagesTxt[qtrPicksIndx[2]] +" vs "+allImagesTxt[qtrPicksIndx[3]]+" - "+allImagesTxt[semiPicksIndx[1]]+" wins."
	+"\n"+"Finals - "+ allImagesTxt[semiPicksIndx[0]] +" vs "+allImagesTxt[semiPicksIndx[1]]+" - "+allImagesTxt[winner]+" wins."
	+"\n"+allTeams[winner]+" wins the Cricket World Cup 2011!"
	+"\n"+ "Make your prediction now with Cricket Champ on Android";

	String wpost = head1;
	return wpost;
}
}