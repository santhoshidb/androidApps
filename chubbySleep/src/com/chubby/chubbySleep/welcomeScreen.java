package com.chubby.chubbySleep;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/** Welcome splash screen**/
public class welcomeScreen extends Activity{
@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //setFullscreen();
    //setNoTitle();
    setContentView(R.layout.welcome);
    Thread splashThread = new Thread() {
        @Override
        public void run() {
           try {
              int waited = 0;
              while (waited < 5000) {
                 sleep(100);
                 waited += 100;
              }
           } catch (InterruptedException e) {
              // do nothing
           } finally {
              finish();
              Intent i = new Intent();
              i.setClassName("com.chubby.chubbySleep",
                             "com.chubby.chubbySleep.chubbySleep");
              startActivity(i);
           }
        }
    };
    splashThread.start();
 }
public void setFullscreen() {
	 
    requestWindowFeature(Window.FEATURE_NO_TITLE);

    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,

            WindowManager.LayoutParams.FLAG_FULLSCREEN);

}



public void setNoTitle() {

    requestWindowFeature(Window.FEATURE_NO_TITLE);

}
}
    
