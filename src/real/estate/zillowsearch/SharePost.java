package real.estate.zillowsearch;

import java.util.Arrays;
import java.util.List;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.Session.OpenRequest;
import com.facebook.SessionLoginBehavior;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.UserSettingsFragment;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class SharePost extends Activity {
	private UiLifecycleHelper uiHelper;
	 UserSettingsFragment userSettingsFragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share_post);
			
		
		 uiHelper = new UiLifecycleHelper(this, null);
		    uiHelper.onCreate(savedInstanceState);
		    
		   
		    if (FacebookDialog.canPresentShareDialog(this,
					FacebookDialog.ShareDialogFeature.SHARE_DIALOG)) {
			FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(this)
	        .setLink(getIntent().getStringExtra("add_url"))
	        .setPicture(getIntent().getStringExtra("img_url"))
	        .setName(getIntent().getStringExtra("add_val"))
	        .setCaption(getIntent().getStringExtra("cap"))
	        .setDescription(getIntent().getStringExtra("desc"))
	        .build();
			
			
			 uiHelper.trackPendingDialogCall(shareDialog.present());}
		  
			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.share_post, menu);
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
	
	String ss="";
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	   
	    Log.i("RequestCode",requestCode+"");
        Log.i("RequestCode",resultCode+"");
	    uiHelper.onActivityResult(requestCode, resultCode, data, new FacebookDialog.Callback() {
	    	
	    	@Override
	        public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
	            Log.e("Activity", String.format("Error: %s", error.toString()));
	            Toast.makeText(getApplicationContext(),"Post Cancelled", Toast.LENGTH_SHORT).show();
	        }

	        @Override
	        public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
	            Log.i("Activity", data.toString());
	            boolean dd=FacebookDialog.getNativeDialogDidComplete(data);
	            ss=FacebookDialog.getNativeDialogCompletionGesture(data);
	            ss=data.getString("com.facebook.platform.extra.COMPLETION_GESTURE");
	            String postId = FacebookDialog.getNativeDialogPostId(data);
	           // Toast.makeText(getApplicationContext(),"Post Sucessfully", Toast.LENGTH_SHORT).show();
	            
	            finish();
	        }	
	    });
	}
	
	@Override
	protected void onResume() {
	    super.onResume();
	    uiHelper.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    uiHelper.onSaveInstanceState(outState);
	}

	@Override
	public void onPause() {
	    super.onPause();
	    uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
	    super.onDestroy();
	    uiHelper.onDestroy();
	}
}
