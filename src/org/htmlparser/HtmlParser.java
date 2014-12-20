package org.htmlparser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.CompositeTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import android.os.Environment;
import android.util.Log;

/*
 * Get html page from web and parser the content. 
 */
public class HtmlParser {
	
	public static String LOG_PATH = Environment.getExternalStorageDirectory() + "/Wauee_html";
	File mParseLog = null;
	static BufferedWriter mParseLogWriter = null;
	
	/*
	 * 
	 */
	public HtmlParser() {
		File logDir = new File(LOG_PATH);
		if(!logDir.exists()) {
			logDir.mkdir();
		}
		File mParseLog = new File(LOG_PATH + "/parse_log.txt");
		mParseLog.deleteOnExit();
		try {
			mParseLog.createNewFile();
			mParseLogWriter = new BufferedWriter(new FileWriter(mParseLog));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	 * Start to parser the content from input stream.
	 */
	public static void parserFormStream(InputStream stream) {
		Log.i("Wauee", "---------------------------parserFormStream------------------------------");
		BufferedReader reader = null;
		String result = "";
		StringBuilder build = new StringBuilder();
		String line = null;
		try {
			reader = new BufferedReader(new InputStreamReader(stream, "utf-8"));
			while((line = reader.readLine()) !=null) {
				Log.i("Wauee", "-----line: " + line + " ---------");
				result += line + "\r\n";
			}
			parserFromString(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * parser the content from html string
	 */
	public static void parserFromString(String htmlString) {
		Parser parser = Parser.createParser(htmlString, "utf-8");
		try {
			
			parserHtmlNode(parser, "HTML");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * //extract html node from html page.
	 */
	public static void parserHtmlNode(Parser parser, String tag) throws Exception {
		NodeFilter filter = new TagNameFilter(tag);
		NodeList tagNodes = parser.parse(filter);
		for (int i = 0; i < tagNodes.size(); i++) {
			if ((tagNodes.elementAt(i) instanceof Tag) && !((Tag)tagNodes.elementAt(i)).isEndTag ()) {
				parserTagWithRecursive(tagNodes.elementAt(i));
			}
		}
	}
	/*
	 * Use recursion to parse tag.
	 */
	private static void parserTagWithRecursive(Node node) {
		List<HashMap<String, String>> attributeMaps = parserTag(node);;
		NodeList childNodes = node.getChildren();
		if (childNodes != null && childNodes.size() >= 1) {
			for (int i = 0; i < childNodes.size(); i++) {
				if ((childNodes.elementAt(i) instanceof Tag) && !((Tag)childNodes.elementAt(i)).isEndTag ()) {
					parserTagWithRecursive(childNodes.elementAt(i));
				}
			}
		} else {
//			parserTag(node);
			getTagText(node);
		}
		
	}

	/*
	 * Parser all nodes.
	 */
	public static void parserAllNodes(Parser parser) throws Exception {
		NodeList nodes = null;
		//extract all node from html page.
		nodes = parser.extractAllNodesThatMatch(new NodeFilter() {
			
			@Override
			public boolean accept(Node node) {
				return true;
			}
		});
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
				parserTag(node);
				nodeWriter.write(node.toHtml());
				nodeWriter.newLine();
				nodeWriter.write("-----------------------------------------------------------------------------");
				nodeWriter.newLine();
				if (((Tag) node).getTagName().equals("A") || ((Tag) node).getTagName().startsWith("LINK")) {
					Vector attributes = ((Tag) node).getAttributesEx();
//					for (int j = 0; j < attributes.size(); j++) {
						attrWriter.write(attributes.toString());
						attrWriter.newLine();
						attrWriter.write("-----------------------------------------------------------------------");
						attrWriter.newLine();
//					}
				}
			}
		}
	}
	/*
	 * Retrieve the content of tag
	 */
	public static List<HashMap<String, String>> parserTag(Node node) {
		List<HashMap<String, String>> attributeLists = new ArrayList<HashMap<String, String>>();
		String tagName = ((Tag)node).getTagName().toLowerCase();
		Vector attributes = ((Tag) node).getAttributesEx();
		Log.i("Wauee", "------------------tagName: " + tagName + " ------------------------------");
		if (tagName.equals("html")) {
			
		} else if (tagName.equals("head")) {
			
		} else if (tagName.equals("meta")) {
			
		} else if (tagName.equals("title")) {
			
		} else if (tagName.equals("body")) {
			
		} else if (tagName.equals("div")) {
			
		} else if (tagName.equals("a") || tagName.startsWith("li")) {
			List<HashMap<String, String>> list = parseAttribute("link", attributes);
			attributeLists.addAll(list);
//			getTagText(node);
		} else if (tagName.equals("img")) {
			List<HashMap<String, String>> list = parseAttribute("img", attributes);
			attributeLists.addAll(list);
//			getTagText(node);
		} else if (tagName.equals("h3")) {
			
		}

		return attributeLists;
	}

	/*
	 * Parse the attributes associate with the relative tag.
	 */
	private static List<HashMap<String, String>> parseAttribute(String tagName, Vector attributes) {
		List<HashMap<String, String>> attributeLists = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < attributes.size(); i++) {
			String name = tagName + ": " + ((Attribute)(attributes.get(i))).getName();
			String value = ((Attribute)(attributes.get(i))).getValue();
			try {
				mParseLogWriter.write(name + " value: " + value);
				mParseLogWriter.newLine();
				mParseLogWriter.write("-----------------------------------------------------------------------");
				mParseLogWriter.newLine();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			HashMap<String, String> attributeMap = new HashMap<String, String>();
			attributeMap.put(name, value);
			attributeLists.add(attributeMap);
		}
		return attributeLists;
	}

	/*
	 * Retrieve the text between the start tag and the end tag.
	 */
	private static String getTagText(Node node) {
		String text = null;
		String tagName = ((Tag)node).getTagName().toLowerCase();
		if (node instanceof CompositeTag) {
			text = ((CompositeTag)node).getStringText();
		} else {
			String tagHtml = node.toHtml();
			Log.i("Wauee", "-------------tagHtml: " + tagHtml + " -------------------------------");
			int tagTextStart = tagHtml.indexOf(">");
			if(tagTextStart < tagHtml.indexOf("/")) {
				int tagTextEnd = tagHtml.indexOf("<", tagTextStart);
			
//	        int start = node.getEndPosition ();
//	        int end = ((Tag)node).getEndTag().getStartPosition ();
//	        text = node.getPage ().getText (start, end);
				text = tagHtml.substring(tagTextStart, tagTextEnd);
			}
		}
		
		try {
			mParseLogWriter.write(tagName + "-->text: " + text);
			mParseLogWriter.newLine();
			mParseLogWriter.write("-----------------------------------------------------------------------");
			mParseLogWriter.newLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return text;
	}
}