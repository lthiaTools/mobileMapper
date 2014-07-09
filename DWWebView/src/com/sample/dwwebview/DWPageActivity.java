package com.sample.dwwebview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.driftwatch.dwwebview.R;

/**
 * @author Eric
 *
 * Lots of code borrowed from 
 * http://stackoverflow.com/questions/2585055/using-webview-sethttpauthusernamepassword
 * from the dparnas answer post
 *
 */
@SuppressLint("SetJavaScriptEnabled")
public class DWPageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dwpage);
		
		WebView myWebView = (WebView) findViewById(R.id.webview);
		//Make a webview variable for reference
		//R.id.webview is an auto-generated java variable
		// that is created because the "activity_dwpage.xml"
		// file specifies "android:id" for the WebView object
		// -- the plus sign in the id specified there
		// means to create a new id to use, and so this id
		// is generated and usable for retrieving the WebView
		// object in our Java code.
		
		myWebView.loadUrl("http://beelist.agriculture.purdue.edu/mobile/index.html");
		//Load the driftwatch mobile URL in the webview object
		
		WebSettings webSettings = myWebView.getSettings();
		//Make a WebSettings variable for use/reference
		
		webSettings.setJavaScriptEnabled(true);
		//Enable JavaScript in these settings (meaning that the
		// WebView will still be using these settings that now
		// have JavaScript enabled)
		
		myWebView.setWebViewClient( new BeelistWebViewClient() );
		//Give the WebView variable a "BeelistWebViewClient" object,
		// which is the object it will ask when it
		// has an authentication request.
		// See "BeelistWebViewClient" class below.
	}
	
	private class BeelistWebViewClient extends WebViewClient {
		@Override
		public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm)
		{
			//Overrides the WebViewClient method for
			// how an authentication request is handled,
			// by always authenticating using the following
			// user login info:
			handler.proceed("beelist", "SixStates2010");
		}
		public void onReceivedSslError (WebView view, SslErrorHandler handler, SslError error)
		{	
			handler.proceed();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dwpage, menu);
		return true;
	}
	
	/**
	 * This method is called by clicking the
	 * reset button, as per the "res/menu/dwpage.xml" file
	 * ( which defines the program's menu )
	 * 
	 * @param item - The item that was clicked (required by system)
	 */
	public void resetClick(MenuItem item)
	{
		WebView myWebView = (WebView) findViewById(R.id.webview);
		myWebView.loadUrl("http://beelist.agriculture.purdue.edu/mobile/index.html");
		//Same code as when the Activity loads; resetting just
		// sends the user back to the original driftwatch mobile
		// page (this button was later renamed "Home" in the 
		// "res/values/strings.xml" file.)
	}
	
	/**
	 * This is a method for the logout button; it calls a default
	 * method from the Activity class that "finishes" it
	 * by closing it.
	 * 
	 * @param item
	 */
	public void logoutClick(MenuItem item)
	{
		finish();
	}

	/**
	 * Calls the "goBack" method on the WebView object,
	 * as long as the WebView object's ".canGoBack()" method
	 * returns true.
	 * 
	 * @param item
	 */
	public void backClick(MenuItem item)
	{
		WebView myWebView = (WebView) findViewById(R.id.webview);
		if( myWebView.canGoBack() ) 
		{
			myWebView.goBack();
		}
	}

	/**
	 * Calls the "goForward" method on the WebView object,
	 * as long as the WebView object's ".canGoForward()" method
	 * returns true.
	 * 
	 * @param item
	 */
	public void forwardClick(MenuItem item)
	{
		WebView myWebView = (WebView) findViewById(R.id.webview);
		if( myWebView.canGoForward() ) 
		{
			myWebView.goForward();
		}
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onBackPressed()
	 * 
	 * This is an override of what happens when you push the
	 * default back button while this Activity is open.
	 * If the WebView object's "canGoBack" method returns true,
	 * then it calls "goBack()" on the WebView. Otherwise,
	 * it calls the "onBackPressed()" method from the parent class (Activity)
	 * 
	 */
	@Override
	public void onBackPressed()
	{
		WebView myWebView = (WebView) findViewById(R.id.webview);
		if(myWebView.canGoBack())
		{
			myWebView.goBack();
		}
		else
		{
			super.onBackPressed();
		}
	}
}
