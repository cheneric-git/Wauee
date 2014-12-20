package com.wauee.app;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;

import org.htmlparser.HtmlParser;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.Tag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.visitors.HtmlPage;
//import org.w3c.tidy.Tidy;

import android.support.v4.app.Fragment;
import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputBinding;
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
//			Log.i("Wauee", "--------------id: " + id + " ---------------------1");
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
//		Log.i("Wauee", "--------------id: " + id + " ---------------------");
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

	@Override
	public void onResume() {
		super.onResume();
		HtmlParser htmlParser = new HtmlParser();
		new Thread( new Runnable() {
			
			@Override
			public void run() {
				InputStream input = streampost("http://www.wauee.com");
				try {
					
//					Tidy tidy = new Tidy();
					File xml = new File("/sdcard/xml");
					if (!xml.exists()) {
						xml.mkdir();
					}
					File errorfile = new File("/sdcard/xml/error.txt");
					if (!errorfile.exists()) {
						errorfile.createNewFile();
					}
					
					File htmlFile = new File("/sdcard/xml/html.xml");
					if (!htmlFile.exists()) {
						htmlFile.createNewFile();
					}
					HtmlParser.parserFormStream(input);
					postString(input);
//					tidy.setErrout(new PrintWriter(new FileWriter("/sdcard/xml/error.txt"), true));
//					tidy.parse(input, new FileOutputStream("/sdcard/xml/html.xml"));
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}
				).start();
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

	public InputStream streampost(String remote_addr){
	    URL infoUrl =null;
	    InputStream inStream =null;
	    try{
	        infoUrl =new URL(remote_addr);
	        URLConnection connection = infoUrl.openConnection();
	        HttpURLConnection httpConnection = (HttpURLConnection)connection;
	        int responseCode = httpConnection.getResponseCode();
	        if(responseCode == HttpURLConnection.HTTP_OK){
	            inStream = httpConnection.getInputStream();
	        }
	    }catch (MalformedURLException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    return inStream;
	}
	
	public String postString(InputStream input) throws Exception {
		String result = "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(input, "utf-8"));
		StringBuilder build = new StringBuilder();
		String line = null;
		File htmlxml = new File("/sdcard/xml/wauee_home.xml");
		if(htmlxml.exists()) {
			htmlxml.delete();
			htmlxml.createNewFile();
		} else {
			htmlxml.createNewFile();
		}

		BufferedWriter writer = new BufferedWriter(new FileWriter(htmlxml));
		while((line = reader.readLine()) != null) {
//			Log.i("Wauee", "-----line: " + line + " ---------");
			writer.write(line);
			writer.newLine();
			result += line + "\r\n";
			
			build.append(line + "\n");
		}
		writer.close();
		reader.close();
		Parser parser = Parser.createParser(result, "utf-8");
		NodeList nodes = parser.extractAllNodesThatMatch(new NodeFilter() {
			
			@Override
			public boolean accept(Node node) {
				// TODO Auto-generated method stub
				return true;
			}
		});
		
		HtmlPage htmlPage = new HtmlPage(parser);
		parser.visitAllNodesWith(htmlPage);
		//Retrive all nodes.
		NodeList nodeList = htmlPage.getBody();
		NodeFilter filter = new TagNameFilter("A");
		String linkString = "";
		File linkFile = new File("/sdcard/xml/link_file.txt");
		if(linkFile.exists()) {
			linkFile.delete();
			linkFile.createNewFile();
		} else {
			linkFile.createNewFile();
		}

		File tagName = new File(Environment.getExternalStorageDirectory() + "/xml/tag_name.txt");
		File nodeFile = new File(Environment.getExternalStorageDirectory() + "/xml/tag_node.txt");
		File attrFile = new File(Environment.getExternalStorageDirectory() + "/xml/att_node.txt");
		attrFile.deleteOnExit();
		attrFile.createNewFile();
		if(tagName.exists()) {
			tagName.delete();
			nodeFile.delete();
			tagName.createNewFile();
			nodeFile.createNewFile();
		} else {
			tagName.createNewFile();
			nodeFile.createNewFile();
		}
		Log.i("Wauee", "-----------Path: " + tagName.getPath() + " ---------------");
		BufferedWriter tagWriter = new BufferedWriter(new FileWriter(tagName));
		BufferedWriter nodeWriter = new BufferedWriter(new FileWriter(nodeFile));
		BufferedWriter attrWriter = new BufferedWriter(new FileWriter(attrFile));
//		tagWriter.write(nodeList.toHtml());
//		tagWriter.newLine();
		Log.i("Wauee", "-----nodes.size(): " + nodes.size() + " -------------------------");
		for (int i = 0; i < nodes.size(); i++) {
			String nodename = null;
			Node node = (Node) nodes.elementAt(i);
			
			if ((node instanceof Tag) && !((Tag)node).isEndTag ()) {
				nodename = "tagname: " + ((Tag)node).getTagName();
				nodeWriter.write(node.toHtml());
				nodeWriter.newLine();
				nodeWriter.write("-----------------------------------------------------------------------------");
				nodeWriter.newLine();
				if (((Tag) node).getTagName().equals("A") || ((Tag) node).getTagName().startsWith("LI")) {
					Vector attributes = ((Tag) node).getAttributesEx();
//					for (int j = 0; j < attributes.size(); j++) {
						attrWriter.write(attributes.toString());
						attrWriter.newLine();
						attrWriter.write("-----------------------------------------------------------------------");
						attrWriter.newLine();
//					}
				} else if (((Tag) node).getTagName().equals("HEAD")) {
					NodeList list = node.getChildren();
					for (int k = 0; k < list.size(); k++) {
						Node childNode = list.elementAt(k);
						if ((childNode instanceof Tag) && !((Tag)childNode).isEndTag ()) {
							Vector attributesC = ((Tag) childNode).getAttributesEx();
							Log.i("Wauee", "-----------attributesC: " + attributesC.toString() + " --------------");
						}
						attrWriter.write("-----childNode: " + childNode.toString());
						attrWriter.newLine();
						attrWriter.write("-----------------------------------------------------------------------");
						attrWriter.newLine();
					}
				}
			} else {
				nodename = "Text: " + node.getText() + "\r\n";
				nodename += "Html: " + node.toHtml() + "\r\n";
				nodename += "Tostring: " + node.toString();
			}
			tagWriter.write(nodename);
			tagWriter.newLine();
//			Log.i("Wauee", "-----nodename: " + nodename);
		}
		nodeWriter.close();
		attrWriter.close();
		tagWriter.close();
		BufferedWriter linkWriter = new BufferedWriter(new FileWriter(linkFile));
		nodeList = nodeList.extractAllNodesThatMatch(filter, true);
		for (int i = 0; i < nodeList.size(); i++) {
			LinkTag tag = (LinkTag) nodeList.elementAt(i);
//			Log.i("Wauee", "-----Name: " + tag.getStringText() + " ---------");
//			Log.i("Wauee", "-----address: " + tag.getAttribute("href") + " ---------");
			linkString = "name: " + tag.getStringText() + "  address: " + tag.getAttribute("href");
			linkWriter.write(linkString);
			linkWriter.newLine();
		}

		linkWriter.close();
//		Parser htmlParser = new Parser(resource, feedback)
//		input.close();
		result = build.toString();
//		Log.i("Wauee", "-----result: " + result + " ---------");
		return result;
	}
}
