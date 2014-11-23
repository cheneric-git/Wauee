package com.wauee.app;


import android.support.v4.app.Fragment;
import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
		setContentView(R.layout.main_wauee);

		//鍒濆鍖栧弬鏁�
		final Animation in = AnimationUtils.loadAnimation(this, R.anim.in_alpha);

		final Animation out = AnimationUtils.loadAnimation(this, R.anim.out_alpha);

		
		viewFlipper = (ViewFlipper) ((RelativeLayout) findViewById(R.id.relative)).findViewById(R.id.viewFlipper);

		//寮�鍥剧墖鑷姩鎾斁锛岀涓�釜鏃堕棿涓洪棿闅旀椂闂达紝绗簩涓椂闂存湭鐭�
		new CountDownTimer(3000,100){

		@Override

		public void onFinish() {

		viewFlipper.setInAnimation(in);

		viewFlipper.setOutAnimation(out);

		int id = viewFlipper.getId();
		int content_id = R.id.one;
		switch(id) {
		case 1:
			content_id = R.id.one;
			break;
		case 2:
			content_id = R.id.two;
			break;
		case 3:
			content_id = R.id.three;
			break;
		case 4:
			content_id = R.id.four;
			break;
			
		}
		((ImageView)((LinearLayout) findViewById(R.id.imageview_id)).findViewById(content_id)).setBackgroundResource(R.drawable.icon_gallery_point_white);
		
		viewFlipper.showNext();

		start();

		}

		@Override

		public void onTick(long millisUntilFinished) {}

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
