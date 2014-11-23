package com.wauee.app;


import android.support.v4.app.Fragment;
import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;

public class WaueeActivity extends Activity {

	private ViewFlipper viewFlipper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_wauee);
		
		final Animation in = AnimationUtils.loadAnimation(this, R.anim.in_alpha);

		final Animation out = AnimationUtils.loadAnimation(this, R.anim.out_alpha);

		
		viewFlipper = (ViewFlipper) ((RelativeLayout) findViewById(R.id.relative)).findViewById(R.id.viewFlipper);

		
		new CountDownTimer(3000,100){

		int id = 1;
		@Override

		public void onFinish() {
			Log.i("Wauee", "--------------id: " + id + " ---------------------1");
		viewFlipper.setInAnimation(in);

		viewFlipper.setOutAnimation(out);

		int content_id = R.id.one;
		switch(id + 1) {
		case 1:
			content_id = R.id.one;
			break;
		case 2:
			((ImageView)((LinearLayout) findViewById(R.id.imageview_id)).findViewById(R.id.one)).setBackgroundResource(R.drawable.icon_gallery_point_grey);
			content_id = R.id.two;
			break;
		case 3:
			((ImageView)((LinearLayout) findViewById(R.id.imageview_id)).findViewById(R.id.two)).setBackgroundResource(R.drawable.icon_gallery_point_grey);
			content_id = R.id.three;
			break;
		case 4:
			((ImageView)((LinearLayout) findViewById(R.id.imageview_id)).findViewById(R.id.three)).setBackgroundResource(R.drawable.icon_gallery_point_grey);
			content_id = R.id.four;
			break;
			
		}
		
		((ImageView)((LinearLayout) findViewById(R.id.imageview_id)).findViewById(content_id)).setBackgroundResource(R.drawable.icon_gallery_point_white);
		
		id++;
		if (id > 4) {
			((ImageView)((LinearLayout) findViewById(R.id.imageview_id)).findViewById(R.id.four)).setBackgroundResource(R.drawable.icon_gallery_point_grey);
			id = 1;
			((ImageView)((LinearLayout) findViewById(R.id.imageview_id)).findViewById(R.id.one)).setBackgroundResource(R.drawable.icon_gallery_point_white);
		}
		
		viewFlipper.showNext();
		Log.i("Wauee", "--------------id: " + id + " ---------------------");
		start();

		}

		@Override
		public void onTick(long millisUntilFinished) {

		}

		}.start();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wauee, menu);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_wauee,
					container, false);
			return rootView;
		}
	}

}
