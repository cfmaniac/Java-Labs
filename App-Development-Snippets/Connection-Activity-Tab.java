package com.HostConcepts.Tablet;

import java.io.*;

import android.app.*;
import android.content.*;
import android.net.*;
import android.os.Bundle;
import android.webkit.*;
import android.widget.*;

public class HCTabletActivity extends Activity {
	WebView mWebView;
	
    /** Called when the activity is first created.*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        File file = new File("/data/data/com.HostConcepts.Tablet/files/code.txt");
        String merch = null;
        
        if(file.exists()){
        	//Toast.makeText(this, "Hey! The file is present.  Lets try reading it...", Toast.LENGTH_LONG).show();
        	
        	try{
    			FileInputStream fIn = openFileInput("code.txt"); // Choose "code.txt" as input in the app resources folder
    			InputStreamReader isr = new InputStreamReader(fIn); // Input Stream Reader
    			char[] inBuff = new char[10]; // Input Buffer, max 4 char
    			
    			isr.read(inBuff);
    			
    			merch = new String(inBuff);
    			//Toast.makeText(this, "Success: Merchant code is '" + merch + "'", Toast.LENGTH_LONG).show();
    			Toast.makeText(this, "Merchant Code is " + merch, Toast.LENGTH_LONG).show();
    		}catch (IOException ioe){
    			ioe.printStackTrace();
    			//Toast.makeText(this, "Error: Merchant code was not set", Toast.LENGTH_LONG).show();
    		}
        }else{
        	//Toast.makeText(this, "The file is missing damnit! Make a new one!", Toast.LENGTH_LONG).show();
        	
        	getCode();
        }
        
        if(!chkConn()){
        	AlertDialog.Builder builder = new AlertDialog.Builder(this);
        	builder.setMessage("You Are Not Connected to WIFI Internet Connection. Please Try Again.")
        	       .setCancelable(false)
        	       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
        	           public void onClick(DialogInterface dialog, int id) {
        	        	   finish();
        	           }
        	       })
        	       .setNegativeButton("ChangeID", new DialogInterface.OnClickListener() {
        	           public void onClick(DialogInterface dialog, int id) {
        	        	   File file = new File("/data/data/com.HostConcepts.Tablet/files/code.txt");
        	        	   boolean deleted = file.delete();
        	        	   finish();
        	           }
        	       });
        	builder.show();
        }else{
        	mWebView = (WebView) findViewById(R.id.webview);
        	mWebView.setScrollBarStyle(mWebView.SCROLLBARS_OUTSIDE_OVERLAY);
            mWebView.getSettings().setJavaScriptEnabled(true);
            
            mWebView.clearHistory();
            mWebView.clearFormData();
            mWebView.clearCache(true);
            
           
            mWebView.loadUrl("http://www.hctablet.com/android/?force=" + merch);
            
            mWebView.setWebViewClient(new gtwViewClient());
        }
    }
    
    private class gtwViewClient extends WebViewClient{
    	@Override
    	public boolean shouldOverrideUrlLoading(WebView view, String url){
    		view.loadUrl(url);
    		return true;
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
    
    public void getCode(){
    	AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Code?");
        alert.setMessage("Enter merchant code:");

        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
        	public void onClick(DialogInterface dialog, int whichButton) {
        		try{
        			FileOutputStream fOut = openFileOutput("code.txt",
        				MODE_WORLD_READABLE);
        			OutputStreamWriter osw = new OutputStreamWriter(fOut); 

        			// Write the string to the file
        			osw.write(input.getText().toString());
        			osw.flush();
        			osw.close();
        		}catch (IOException ioe){
        			ioe.printStackTrace();
        			//Toast.makeText(this, "Error: Merchant code was not set", Toast.LENGTH_LONG).show();
        		}
        	}
        });

        /*alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int whichButton) {
        	  // Don't do anything
          }
        });*/

        alert.show();
    }
}