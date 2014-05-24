package com.example.mushserver;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

public class MainActivity extends Activity implements OnClickListener {

	private RequestQueue mRequestQueue;
	private String mMessage;
	private EditText mEdit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(this);
		
		mEdit = (EditText) findViewById(R.id.editText1);
		

		mRequestQueue = Volley.newRequestQueue(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		mMessage = mEdit.getText().toString();
		new Task().execute();
	}
	
	private class Content {
		private List<String> registration_ids;
		private Map<String,String> data;
		
		public Content(){
			registration_ids = new LinkedList<String>();
			data = new HashMap<String,String>();
			
			data.put("title", mMessage);
			data.put("message", "TESTMessage");
			
			registration_ids.add("APA91bEA1WFPepMEiSXLF_ihSp5myIo-aye22vImVlhh0XLjOizVRNX4XqynTEvYSiXZl52s7n5bGiZMmiYeH3CbhsLx37uSKbnj81iZfC_E5RRKrUxSrno0b0CqIvyfaCNj4m9LjyutQjhsjgiSIiVcnX9-dU7dVA");
		}
	}

	private class Task extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			String urlString = "https://android.googleapis.com/gcm/send";
			final String apiKey = "AIzaSyA20uHVpAz3j5e0IhYdPKsRaO3N5uxGjAE";
			Content content = new Content();
			Gson gson = new Gson();
			String json = gson.toJson(content);
			try {
				JsonObjectRequest request = new JsonObjectRequest(
						Request.Method.POST, urlString, new JSONObject(json),
						new Response.Listener<JSONObject>() {

							@Override
							public void onResponse(JSONObject arg0) {
								Log.i("REPONSE", "RESPONSE+Positive");

							}
						}, new Response.ErrorListener() {

							@Override
							public void onErrorResponse(VolleyError arg0) {
								Log.i("REPONSE", "RESPONSE+Negative");

							}
						}) {
					@Override
					public Map<String, String> getHeaders() throws AuthFailureError {
						HashMap<String, String> headers = new HashMap<String, String>();
						headers.put("Content-Type", "application/json");
						headers.put("Authorization", "key="+apiKey);
						return headers;
					}
				};
				mRequestQueue.add(request);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

	}

}
