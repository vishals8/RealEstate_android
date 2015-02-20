package real.estate.zillowsearch;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ImageView img = (ImageView)findViewById(R.id.imageView1);
        img.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.zillow.com"));
                startActivity(intent);
            }
        });
        
        Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
            	String url="", adr,city,state;
            	adr=((TextView) findViewById(R.id.editText1)).getText().toString();
            	city=((TextView) findViewById(R.id.editText2)).getText().toString();
            	state=((Spinner)findViewById(R.id.spinner1)).getSelectedItem().toString();
            	
            	if(validateInputs( adr,city,state)){
            		
            		try {
						url="http://default-environment-phpjznzf3v.elasticbeanstalk.com/?address="+
								URLEncoder.encode(adr, "UTF-8")+
								"&city="+
								URLEncoder.encode(city, "UTF-8")+
								"&state="+
								URLEncoder.encode(state, "UTF-8");
						
						CallService service=new CallService();
						service.execute(url);
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            		
            	} 
//            	//. remove after debugging
//            	else {
//            		CallService service=new CallService();
//					service.execute(
//"http://default-environment-phpjznzf3v.elasticbeanstalk.com/?address=2366%20menlo%20ave&city=la&state=ca"
//							);
//            		
//            	}
            	
            	
            }
        });
        
    }

private boolean validateInputs(String adr,String city, String state)
{
	boolean valid=true;
	
	((TextView)findViewById(R.id.textView6)).setText(getResources().getString(R.string.blank));
	((TextView)findViewById(R.id.textView7)).setText(getResources().getString(R.string.blank));
	((TextView)findViewById(R.id.textView8)).setText(getResources().getString(R.string.blank));
	((TextView)findViewById(R.id.textView9)).setText(getResources().getString(R.string.blank));
	
	if(adr.equalsIgnoreCase("")){
		((TextView)findViewById(R.id.textView6)).setText(getResources().getString(R.string.error_msg));
		valid=false;
	}
	if(city.equalsIgnoreCase("")){
		((TextView)findViewById(R.id.textView7)).setText(getResources().getString(R.string.error_msg));
		valid=false;
	}
	
	if(state.equalsIgnoreCase("Choose State")){
		((TextView)findViewById(R.id.textView8)).setText(getResources().getString(R.string.error_msg));
		valid=false;
	}
	
	((TextView)findViewById(R.id.textView6)).setTextColor(Color.RED);
	((TextView)findViewById(R.id.textView7)).setTextColor(Color.RED);
	((TextView)findViewById(R.id.textView8)).setTextColor(Color.RED);
	
	return valid;
	
}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

 private JSONObject parseJSON(String res){
 
	 JSONObject data=null;String error="";
	 try {
		 data=new JSONObject(res);
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	try {
		error=data.getString("errormsg");
		if(!error.isEmpty()){
			((TextView)findViewById(R.id.textView9)).setText(error);
			data=null;
		}
		
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return data;
	 
 }
    private class CallService extends AsyncTask<String, Void, String>{
   
    	@Override
    	 protected void onPostExecute(String result) {
    		
     		JSONObject data = parseJSON(result);
    		
     		if(data != null){
    		Intent myIntent = new Intent(MainActivity.this, ResultActivity.class);
    		myIntent.putExtra("data", data.toString()); //Optional parameters
    		startActivity(myIntent);
     		}
         }

		@Override
		  protected String doInBackground(String... urls) {
		      String response = "";
		      for (String url : urls) {
		        DefaultHttpClient client = new DefaultHttpClient();
		        HttpGet httpGet = new HttpGet(url);
		        try {
		          HttpResponse execute = client.execute(httpGet);
		         response=EntityUtils.toString(execute.getEntity());

		        } catch (Exception e) {
		          e.printStackTrace();
		        }
		      }
		      return response;
		    }

    }

}


