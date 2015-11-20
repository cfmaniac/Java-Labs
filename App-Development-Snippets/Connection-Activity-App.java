package com.hostconcepts.hcguest;

import android.app.*;
import android.content.*;
import android.net.*;
import android.os.Bundle;
import android.webkit.*;
import android.widget.*;
import com.phonegap.*;

public class HCGuestActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.main);
    	
    	if(!chkConn()){
        	AlertDialog.Builder builder = new AlertDialog.Builder(this);
        	builder.setMessage("You Are Not Connected to WIFI Internet Connection. Please Try Again.");
        	builder.setCancelable(false);
        	builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
        	           public void onClick(DialogInterface dialog, int id) {
        	        	   finish();
        	           }
        	       	});
        	builder.show();
    	} else {
    		WebView mWebView = new WebView(this);
    		mWebView.setScrollBarStyle(mWebView.SCROLLBARS_OUTSIDE_OVERLAY);
    		mWebView.getSettings().setJavaScriptEnabled(true);
    		mWebView.loadUrl("http://www.google.com");
    		setContentView(mWebView);
    	}
    	
    }
    
    public final boolean chkConn(){
    	final ConnectivityManager connMgr = (ConnectivityManager)
    	this.getSystemService(Context.CONNECTIVITY_SERVICE);

    	final android.net.NetworkInfo wifi =
    		connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

    	final android.net.NetworkInfo mobile =
    		connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

    	if(wifi.isAvailable()){ return true; }
    	/*else if(mobile.isAvailable()){ Toast.makeText(this, "Mobile 3G " , Toast.LENGTH_LONG).show(); }*/
    	else{ return false; }
    }
}