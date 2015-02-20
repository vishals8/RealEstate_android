package real.estate.zillowsearch;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v13.app.FragmentPagerAdapter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ImageSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;
import com.facebook.UiLifecycleHelper;
@SuppressWarnings("deprecation")
public class ResultActivity extends Activity implements ActionBar.TabListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v13.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		JSONObject obj=null;
		ResultActivity o=this;
		try {
			 obj = new JSONObject(getIntent().getStringExtra("data"));
			obj=obj.getJSONObject("result");
			  
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setContentView(R.layout.activity_result);

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager(),obj,o);

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result, menu);
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

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {
		JSONObject objPart;
		ResultActivity o;
		public SectionsPagerAdapter(FragmentManager fm,JSONObject obj,ResultActivity oo) {
			super(fm);
			objPart=obj;
			o=oo;
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class
			// below).
			
			switch(position){
			case 0:
				return TableFragment.newInstance(position + 1,objPart,o);
				
			case 1:
				return ImageFragment.newInstance(position + 1,objPart,o);
				
			}
			return null;
		}

		@Override
		public int getCount() {
			// Show 2 total pages.
			
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			
			}
			return null;
		}
	}

	
	public static class TableFragment extends Fragment {
	static JSONObject objPart;
	static ResultActivity ros;
	private UiLifecycleHelper uiHelper;
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static TableFragment newInstance(int sectionNumber,JSONObject obj,ResultActivity o) {
			TableFragment fragment = new TableFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			objPart=obj;
			ros=o;
			return fragment;
		}

		public TableFragment() {
		}
		@Override
		public void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);

		    uiHelper = new UiLifecycleHelper(getActivity(), null);
		    uiHelper.onCreate(savedInstanceState);
		}
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_result,
					container, false);
			LinearLayout scr = (LinearLayout)rootView.findViewById(R.id.linearContent);
			
			try {
				scr.addView(createGrid(objPart));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			  TextView img = (TextView)rootView.findViewById(R.id.textView1);
		        img.setOnClickListener(new View.OnClickListener(){
		            public void onClick(View v){
		                Intent intent = new Intent();
		                intent.setAction(Intent.ACTION_VIEW);
		                intent.addCategory(Intent.CATEGORY_BROWSABLE);
		                intent.setData(Uri.parse("http://www.zillow.com/zestimate/"));
		                startActivity(intent);
		            }
		        });
		        img = (TextView)rootView.findViewById(R.id.textView2);
		        img.setOnClickListener(new View.OnClickListener(){
		            public void onClick(View v){
		                Intent intent = new Intent();
		                intent.setAction(Intent.ACTION_VIEW);
		                intent.addCategory(Intent.CATEGORY_BROWSABLE);
		                intent.setData(Uri.parse("http://www.zillow.com/corp/Terms.htm"));
		                startActivity(intent);
		            }
		        });
			
			return rootView;
		}
		
		
	   

		private String parseValue(String val,int type){
			
			String returnValue="";
			NumberFormat formatter;
			if(val.isEmpty()){
				return "N/A";
			}
			if(type==4 && Float.parseFloat(val)==0.0){
				return "N/A";
			}
			
			 // write switch to parse data;
		    switch (type) {
		    case 1: //"Indicator":
		        if (Float.parseFloat(val)==0.0) {
		            returnValue = "";
		            break;
		        }
		        returnValue =Float.parseFloat(val)>0.0 ? "1" : "0";
		        break;
		    case 2: //"Date":
		            if(val.equalsIgnoreCase("01-Jan-1970") || val.equalsIgnoreCase("31-Dec-1969"))
		            {returnValue = "N/A"; }
		            else{
		                returnValue =val;
		            }

		        break;
		    case 3: //"Area":
		    	formatter=NumberFormat.getNumberInstance(Locale.US);
		        returnValue = formatter.format(Float.parseFloat(val))+ " sq. ft.";

		        break;
		    case 4: //"Money":
		    	formatter= NumberFormat.getCurrencyInstance();
		        returnValue = formatter.format(Float.parseFloat(val));

		        break;
		    default:
		        returnValue = val;
		    }
			
			
			
			return returnValue;
		}
		private  String img_url="",add_url="",add_val="",desc="",cap="Property information from Zillow.com";
				
//		PopupWindow popupMessage,fadePopup;
//		LinearLayout layoutOfPopup,innerL,cover;
		
		private TableLayout createGrid(JSONObject obj) throws JSONException{
			add_val=obj.getString("street")+ ", " + obj.getString("city") + ", " + obj.getString("state")+ "-" + obj.getString("zipcode");
			add_url=obj.getString("homedetails");
			String[][] dataArray={
					{add_val,add_url},
					{"Property Type:", parseValue(obj.getString("useCode"),0)},
					  {"Year Built:", parseValue(obj.getString("yearBuilt"),0)},
				        {"Lot Size:", parseValue(obj.getString("lotSizeSqFt"), 3)},
				        {"Finished Area:", parseValue(obj.getString("finishedSqFt"),3)},
				        {"Bathrooms:", parseValue(obj.getString("bathrooms"),0)},
				         {"Bedrooms:", parseValue(obj.getString("bedrooms"),0)},
				            {"Tax Assesment Year:", parseValue(obj.getString("taxAssessmentYear"),0)},
				    {"Tax Assesment:", parseValue(obj.getString("taxAssessment"), 4)},
				    {"Last Sold Price:", parseValue(obj.getString("lastSoldPrice"), 4)},
				    {"Last Sold Date:", parseValue(obj.getString("lastSoldDate"), 2)},
				    {"Zestimate \u00AE Property Estimate as of " + parseValue(obj.getString("estimateLastUpdate"), 2) + ":", parseValue(obj.getString("estimateAmount"), 4)},
				    {"30 Days Overall Change " + ":", obj.getString("estimateValueChange")},
				    {"All Time Property Range:", parseValue(obj.getString("estimateValuationRangeLow"),4) + " - " + parseValue(obj.getString("estimateValuationRangeHigh"), 4)},
				    {"Rent Zestimate \u00AE Valuation as of " + parseValue(obj.getString("restimateLastUpdate"),2) + ":", parseValue(obj.getString("restimateAmount"),4)},
				    {"30 Days Rent Change " + ":", obj.getString("restimateValueChange")},
				    {"All Time Rent Change:", parseValue(obj.getString("restimateValuationRangeLow"),4) + " - " + parseValue(obj.getString("restimateValuationRangeHigh"), 4)}
				    
			};
			if((dataArray[12][1]).isEmpty()){
				desc=dataArray[9][0]+" "+dataArray[9][1]+", "+dataArray[12][0]+" ";

			} else{
			desc=dataArray[9][0]+" "+dataArray[9][1]+", "+dataArray[12][0]+" "+parseValue(Math.abs(Float.parseFloat(dataArray[12][1]))+"",4);
			
			}
			
//			layoutOfPopup=new LinearLayout(ros);
//			innerL=new LinearLayout(ros);
//			cover=new LinearLayout(ros);
//			Button btnOK=new Button(ros), btnCancel=new Button(ros);
//			TextView pTxt=new TextView(ros);
//			btnOK.setText("OK");
//			btnCancel.setText("Cancel");
//			pTxt.setText("Share on Facebook");
//			pTxt.setPadding(20, 20, 20, 20);
//			btnCancel.setPadding(20, 20, 20, 20);
//			btnOK.setPadding(20, 20, 20, 20);
//			layoutOfPopup.setOrientation(1);
//			btnCancel.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
//			btnOK.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
//			pTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
//			innerL.setGravity(Gravity.CENTER);
//			innerL.addView(btnOK);
//			innerL.addView(btnCancel);
//			
//			layoutOfPopup.setBackgroundColor(Color.WHITE);
//			
//			layoutOfPopup.addView(pTxt);
//			layoutOfPopup.addView(innerL);
//			
//			cover.setBackgroundColor(Color.argb(200, 0, 0, 0));
//			
//			btnCancel.setOnClickListener(
//					new View.OnClickListener() {
//			            public void onClick(View v) {
//			            	popupMessage.dismiss();
//			            	fadePopup.dismiss();
//			            	Toast.makeText(ros, "Post Cancelled", Toast.LENGTH_SHORT).show();
//			            }
//			            }
//					);
//			
//			btnOK.setOnClickListener(
//					new View.OnClickListener() {
//			            public void onClick(View v) {
//			            	
//			            	
//			            	popupMessage.dismiss();
//			            	fadePopup.dismiss();
//			            	Intent myIntent = new Intent(ros, SharePost.class);
//			        	myIntent.putExtra("add_url", add_url); 
//			        	myIntent.putExtra("img_url", img_url); 
//			        	myIntent.putExtra("add_val", add_val); 
//			        	myIntent.putExtra("cap", cap); 
//			        	myIntent.putExtra("desc", desc); 
//			        	
//			        		startActivity(myIntent);
//			            	
//			    		
//			            }
//			            }
//					);
			
			TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
		     TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
		    
		     rowParams.weight = 1;
		     
		     TableLayout tableLayout = new TableLayout(ros);

		     tableLayout.setLayoutParams(tableParams);

		     TableRow tableRow=null;
		     tableRow = new TableRow(ros);
	    	 tableRow.setLayoutParams(tableParams);
	    	 
	    	 TextView fText = new TextView(ros);
	    	 fText.setLayoutParams(rowParams);
	    	 fText.setText("See more details on Zillow:");
	    	 
	    	 img_url=((obj.getJSONObject("chart")).getJSONObject("1year")).getString("url");
	    	 Button fBtn = new Button(ros);
	    	 fBtn.setLayoutParams(rowParams);
	    	 fBtn.setText("f Share");
	    	 fBtn.setBackgroundResource(R.drawable.com_facebook_button_blue_normal);
	    	 fBtn.setTextColor(Color.WHITE);
	    	 
	    	 fBtn.setOnClickListener(new View.OnClickListener() {
		            public void onClick(View v) {
		        		
		            	AlertDialog.Builder alert = new AlertDialog.Builder(ros);

		            	alert.setMessage("Post to Facebook");

		            	alert.setPositiveButton("OK",
		            	        new DialogInterface.OnClickListener() {
		            	            public void onClick(DialogInterface dialog, int which) {
		            	            	Intent myIntent = new Intent(ros, SharePost.class);
		        			        	myIntent.putExtra("add_url", add_url); 
		        			        	myIntent.putExtra("img_url", img_url); 
		        			        	myIntent.putExtra("add_val", add_val); 
		        			        	myIntent.putExtra("cap", cap); 
		        			        	myIntent.putExtra("desc", desc); 
		        			        	
		        			        		startActivity(myIntent);
		        			        		 dialog.cancel();
		            	            }
		            	        });
		            	
		            	alert.setNegativeButton("NO",
		            	        new DialogInterface.OnClickListener() {
		            	            public void onClick(DialogInterface dialog, int which) {
		            	                // Write your code here to execute after dialog
		            	            	Toast.makeText(ros, "Post Cancelled", Toast.LENGTH_SHORT).show();
		            	                dialog.cancel();
		            	            }
		            	        });

		            	alert.show();
		            	
		            }
		        });
	    	
	    	 
	    	 tableRow.addView(fText);
	    	 tableRow.addView(fBtn);
	    	 tableLayout.addView(tableRow);
		     
		     for(int i=0;i<dataArray.length;i++){
		    	 tableRow = new TableRow(ros);
		    	 tableRow.setLayoutParams(tableParams);

		    	 TextView textView = new TextView(ros);

	        	 TextView textViewR = new TextView(ros);
		    	
		             textView.setLayoutParams(rowParams);

		             
		             
		             if(i==0){
		            	 String str_links = "<a href='"+dataArray[i][1]+"'>"+dataArray[i][0]+"</a>";
		            	 textView.setLinksClickable(true);
		            	 textView.setMovementMethod(LinkMovementMethod.getInstance());
		            	 textView.setText( Html.fromHtml( str_links ) );
		            	 
		             }
		             else{
		            	 textView.setText(dataArray[i][0]);
			             if(i==12 || i== 15){
			            	 if((dataArray[i][1]).isEmpty()){
			            		 
			            		 textViewR.setText("N/A");
			            	 }else{
			            	 
			            	 ImageSpan imageSpan=null;
			            	 if(parseValue(dataArray[i][1], 1).equalsIgnoreCase("1")){
			            	  imageSpan = new ImageSpan(ros, R.drawable.up_g,ImageSpan.ALIGN_BASELINE);
			            	 }
			            	 else{
			            		 imageSpan = new ImageSpan(ros, R.drawable.down_r,ImageSpan.ALIGN_BASELINE);
			            	 }
			            	 textViewR.setText(" "+parseValue(Math.abs(Float.parseFloat(dataArray[i][1]))+"",4));
			            	 SpannableString spannableString = new SpannableString(textViewR.getText());
			            	 spannableString.setSpan(imageSpan, 0, 1, 0);
			            	 textViewR.setText(spannableString);}
			             } 
			             else{
			            	
				             textViewR.setText(dataArray[i][1]);
			             }
			             
			             textViewR.setLayoutParams(rowParams);
			             textViewR.setGravity(Gravity.RIGHT);

			            
		             }
		             
			             tableRow.addView(textView);
			             textView.setPadding(20,20,20,20);
			             if(i>0){
			             tableRow.addView(textViewR);
			             textViewR.setPadding(20,20,20,20);
			             
			             if(i%2==0){
			            	 tableRow.setBackgroundColor(Color.WHITE);
			            	 
			             }
			             else{
			            	 tableRow.setBackgroundColor(Color.argb(150, 232, 242, 254));
			            	 
			             }
			             
			             }

			             tableLayout.addView(tableRow);
		     }
		    
		     
		     
		     return tableLayout;	
		}

	
	}

	
	public static class ImageFragment extends Fragment {
		static JSONObject objPart;
		static ResultActivity ros;
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static ImageFragment newInstance(int sectionNumber,JSONObject obj,ResultActivity o) {
			ImageFragment fragment = new ImageFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			objPart=obj;
			ros=o;
			return fragment;
		}

		public ImageFragment() {
		}

		public class ImageDownload extends AsyncTask<String, Void, Drawable[]> {
				private Drawable vals[]=new Drawable[3];
			@Override
			protected Drawable[] doInBackground(String... params) {
			
				for(int i=0;i<params.length;i++){
					URL url=null;
					InputStream content=null;
					try {
						url = new URL(params[i]);
						content = (InputStream)url.getContent();
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					vals[i]= Drawable.createFromStream(content , "src");
				}
				
				IMAGES=vals;
				return vals;
			}
			
			protected void onPostExecute(Drawable[] val){
				
				

				mTextSwitcher.setText(TEXTS[mPosition]);
				mImageSwitcher.setImageDrawable(IMAGES[mPosition]);
					
				
					 
				        button.setOnClickListener(new View.OnClickListener() {
				            public void onClick(View v) {
				            	mPosition = (mPosition + 1) >= IMAGES.length?0:(mPosition + 1);
				            	mTextSwitcher.setText(TEXTS[mPosition]);
				    			mImageSwitcher.setImageDrawable(IMAGES[mPosition]);
				    			
				            }
				        });
				        
				        button_d.setOnClickListener(new View.OnClickListener() {
				            public void onClick(View v) {
				            	mPosition = (mPosition - 1)<0?IMAGES.length-1:(mPosition - 1);
				            	
				            	mTextSwitcher.setText(TEXTS[mPosition]);
				    			mImageSwitcher.setImageDrawable(IMAGES[mPosition]);
				    			
				            }
				        });
				
			}
			
			
		}
	
		
				
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.image_fragment,
					container, false);
			
			// push images in Image array from URl
			JSONObject ob=null;
			try {
				TextView vwtxt = (TextView)rootView.findViewById(R.id.textView5);
				vwtxt.setText(objPart.getString("street")+ ", " + objPart.getString("city") + ", " + objPart.getString("state")+ "-" + objPart.getString("zipcode"));
				
				ob = objPart.getJSONObject("chart");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				ImageDownload img=new ImageDownload();
				img.execute((ob.getJSONObject("1year")).getString("url"),
						(ob.getJSONObject("5year")).getString("url"),
						(ob.getJSONObject("10year")).getString("url"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			

			
			  TextView img = (TextView)rootView.findViewById(R.id.textView1);
		        img.setOnClickListener(new View.OnClickListener(){
		            public void onClick(View v){
		                Intent intent = new Intent();
		                intent.setAction(Intent.ACTION_VIEW);
		                intent.addCategory(Intent.CATEGORY_BROWSABLE);
		                intent.setData(Uri.parse("http://www.zillow.com/zestimate/"));
		                startActivity(intent);
		            }
		        });
		        img = (TextView)rootView.findViewById(R.id.textView2);
		        img.setOnClickListener(new View.OnClickListener(){
		            public void onClick(View v){
		                Intent intent = new Intent();
		                intent.setAction(Intent.ACTION_VIEW);
		                intent.addCategory(Intent.CATEGORY_BROWSABLE);
		                intent.setData(Uri.parse("http://www.zillow.com/corp/Terms.htm"));
		                startActivity(intent);
		            }
		        });
// text switcher code
				
				mTextSwitcher = (TextSwitcher)rootView.findViewById(R.id.textSwitcher);
				mTextSwitcher.setFactory(new ViewFactory() {
					@Override
					public View makeView() {
						TextView textView = new TextView(ros);
						textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
						textView.setGravity(Gravity.CENTER);
						return textView;
					}
				});

				mTextSwitcher.setInAnimation(ros, android.R.anim.fade_in);
				mTextSwitcher.setOutAnimation(ros, android.R.anim.fade_out);

				
				// image switcher code
				
				mImageSwitcher = (ImageSwitcher)rootView.findViewById(R.id.imageSwitcher);
				mImageSwitcher.setFactory(new ViewFactory() {
					@Override
					public View makeView() {
						ImageView imageView = new ImageView(ros);
						imageView.setScaleType(ImageView.ScaleType.FIT_XY);
						imageView.setLayoutParams(new
		                        ImageSwitcher.LayoutParams(
		                                    LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
						return imageView;
					}
				});
				mImageSwitcher.setInAnimation(ros, android.R.anim.fade_in);
				mImageSwitcher.setOutAnimation(ros,android.R.anim.fade_out);
				  button = (Button)rootView.findViewById(R.id.n_btn);
				   button_d = (Button)rootView.findViewById(R.id.p_btn);
			
			return rootView;
		}
	
		private static final String[] TEXTS = { "Historical Zestimate for the past 1 year", "Historical Zestimate for the past 5 year", "Historical Zestimate for the past 10 year" };
		private static Drawable[] IMAGES =new Drawable[3];
		private int mPosition = 0;
		private TextSwitcher mTextSwitcher;
		private ImageSwitcher mImageSwitcher;
		private   Button button,button_d;

	}

}
