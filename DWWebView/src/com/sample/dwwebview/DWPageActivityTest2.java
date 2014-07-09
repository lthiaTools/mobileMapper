package com.sample.dwwebview;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.Menu;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.driftwatch.dwwebview.R;

public class DWPageActivityTest2 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dwpage);

		//CODE COPIED FROM
		//http://stackoverflow.com/questions/4540972/set-credentials-on-an-android-webview-using-secured-https-connection
		// AND THEN MODIFIED.
		
		  // Getting info from Intent extras
		  // Get it if it s different from null
		getIntent().putExtra("username", "beelist");
		getIntent().putExtra("passcode", "SixStates2010");
		  Bundle extras = getIntent().getExtras();            
		  final String mUsrName = extras != null ? extras.getString("username") : null;
		  final String mPassC = extras != null ? extras.getString("passcode") : null;

		  WebView mWebView = (WebView) findViewById(R.id.webview);
		  mWebView.getSettings().setJavaScriptEnabled(true);
		  // mWebView.setHttpAuthUsernamePassword("myhost.com",
		  //                                   "myrealm",
		  //                                   mUsrName,
		  //                                   mPassC);

		  mWebView.setWebViewClient(new WebViewClient() {
		      @Override 
		      public void onReceivedHttpAuthRequest(WebView view,
		                                            HttpAuthHandler handler,
		                                            String host,
		                                            String realm){ 
		        handler.proceed(mUsrName, mPassC);
		      } 

		      public void onReceivedSslError(WebView view,
		                                     SslErrorHandler handler,
		                                     SslError error) {
		        handler.proceed() ;
		      }
		    });

		  String up = mUsrName +":" +mPassC;
		  String authEncoded = new String(up.getBytes());//Base64.encodeBase64(up.getBytes()));
		  String authHeader = "Basic " +authEncoded;
		  Map<String, String> headers = new HashMap<String, String>();
		  headers.put("Authorization", authHeader);
		  mWebView.loadUrl("http://beelist.agriculture.purdue.edu/mobile/index.html", headers);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dwpage, menu);
		return true;
	}

}
