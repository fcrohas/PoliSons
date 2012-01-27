
package fr.polisons.reader;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.Log;

public class News {
	
	private static final String ERROR = "ERROR";
	private HtmlCleaner pageParser;
	private TagNode rootNode;
	private INewsData datas;
	private List<INewsData> newsData;
	// Initialize Class
	public News() {
		// Create HtmlCleaner object to turn the page into
		// XML that we can analyze to get the songs from the page.
		pageParser = new HtmlCleaner();
		CleanerProperties props = pageParser.getProperties();
		props.setAllowHtmlInsideAttributes(true);
		props.setAllowMultiWordAttributes(true);
		props.setRecognizeUnicodeChars(true);
		props.setOmitComments(true);	
		props.setUseCdataForScriptAndStyle(true);
	}
	
	// Open web site URL
	public void open(String url) throws IOException {
		rootNode = pageParser.clean(new URL(url));
	}

	// extract an attribute from an element
	// Param attribute name
	//       TagNode to work on
	private String extractAttribute(String type, TagNode node) {
        TagNode[] tags = node.getElementsHavingAttribute(type, true);
        String value = "";
        for(TagNode t: tags){
             value = t.getAttributeByName(type);
        }
		return Html.fromHtml(value).toString();
	}

	// extract a text from an element
	// Param Element name
	//       TagNode to work on
	private String extractElement(String element, TagNode node) {
        TagNode[] tags = node.getElementsByName(element, true);
        String value = "";
        for(TagNode t: tags){
        	 if (t.getChildren().size() > 0) {
        		 value = t.getChildren().get(0).toString();
        	 }
        }
		return Html.fromHtml(value).toString();
	}

	private String extractText(TagNode node, boolean keepHtmlTags) {
        StringBuilder value = new StringBuilder();
        if (node.getChildren().size() > 0) {
        	for (int i=0; i< node.getChildren().size(); i++) {
        		if (node.getChildren().get(i).toString().equals("strong")) {
        			value.append("<b>"+((TagNode)node.getChildren().get(i)).getText().toString() +"</b>");
        		} else if (node.getChildren().get(i).toString().equals("img")) {
        			value.append("<br>");
        		} else if (node.getChildren().get(i).toString().equals("br")) {
        			value.append("<br>");
        		} else if (node.getChildren().get(i).toString().equals("a")) {
        			value.append(((TagNode)node.getChildren().get(i)).getText().toString());
        		} else if (node.getChildren().get(i).toString().equals("small")) {
        			value.append("<small>"+((TagNode)node.getChildren().get(i)).getText().toString() +"</small>");
        		} else if (node.getChildren().get(i).toString() != null) {
        			value.append(node.getChildren().get(i).toString() );
        		}
        	}
        }
        // Do you want to keep Html tags
        if (keepHtmlTags) {
        	return value.toString();
        	
        } else {
        	// To this to reformat encoded character and remove html tags like <br>
        	return Html.fromHtml(value.toString()).toString();
        }
	}
	
    private Bitmap downloadImage(String urL)
    {        
        Bitmap bitmap = null;
        InputStream in = null;        
        try {
            in = openHttpConnection("http://www.poli-sons.fr/"+urL);
            bitmap = BitmapFactory.decodeStream(in);
            in.close();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return bitmap;                
    }
    
    private InputStream openHttpConnection(String urlString) 
    throws IOException
    {
        InputStream in = null;
        int response = -1;
               
        URL url = new URL(urlString); 
        URLConnection conn = url.openConnection();
                 
        if (!(conn instanceof HttpURLConnection)) {                     
            throw new IOException("Not an HTTP connection");
        }
        
        try{
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect(); 

            response = httpConn.getResponseCode();                 
            if (response == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();                                 
            }                     
        }
        catch (Exception ex)
        {
            throw new IOException("Error connecting");            
        }
        return in;     
    }
    
	public List<INewsData> getNews(String xPathExpression) {
		newsData = new ArrayList<INewsData>(); 
	     try {
	          // Stupid API returns Object[]...  Why not TagNodes?  We'll cast it later
	          Object[] downloadNodes = rootNode.evaluateXPath(xPathExpression);
	          // Create data structure
	          // Iterate through the nodes selected by the XPath statement...
	          for(Object linkNode : downloadNodes){   
	               // Recursively find all nodes which have "href" (link) attributes.  Then, store
	               // the link values in an ArrayList.  Create a new ArchiveSongObj with these links
	               // and the title of the track, which is the inner HTML of the first child node.
		           datas = new INewsData();
	        	   datas.title = extractAttribute("title", (TagNode)linkNode);//pageParser.getInnerHtml(((TagNode)((TagNode)linkNode).getChildren().get(0))).trim();
	               datas.link  = extractAttribute("href", (TagNode)linkNode);
	               datas.description = extractElement("p", (TagNode)linkNode);
	               datas.date = extractElement("abbr", (TagNode)linkNode);
	               datas.image = new BitmapDrawable(downloadImage(extractAttribute("src", (TagNode)linkNode)));
	               Log.d("PoliSons", "Title is " + datas.title);
	               Log.d("PoliSons", "Link is "  + datas.link);
	               Log.d("PoliSons", "Description is "  + datas.description);
	               Log.d("PoliSons", "Date is " + datas.date);
	               newsData.add( datas);
	          }   
	     	} catch (XPatherException e) {
		          Log.e(ERROR, e.getMessage());
		    }
		return newsData;
	}

	public List<INewsData> getPodcast(String xPathExpression) {
		newsData = new ArrayList<INewsData>(); 
	     try {
	          // Stupid API returns Object[]...  Why not TagNodes?  We'll cast it later
	          Object[] downloadNodes = rootNode.evaluateXPath(xPathExpression);
	          // Create data structure
	          // Iterate through the nodes selected by the XPath statement...
	          for(Object linkNode : downloadNodes){   
	               // Recursively find all nodes which have "href" (link) attributes.  Then, store
	               // the link values in an ArrayList.  Create a new ArchiveSongObj with these links
	               // and the title of the track, which is the inner HTML of the first child node.
		           datas = new INewsData();
		           datas.title = extractText((TagNode)linkNode , false);
		           datas.title = datas.title.substring( datas.title.lastIndexOf("\n")+1 );
	        	   //datas.title = extractAttribute("title", (TagNode)linkNode);//pageParser.getInnerHtml(((TagNode)((TagNode)linkNode).getChildren().get(0))).trim();
	               datas.link  = extractAttribute("href", (TagNode)linkNode);
	               //datas.description = extractElement("p", (TagNode)linkNode);
	               //datas.date = extractElement("abbr", (TagNode)linkNode);
	               Bitmap img = downloadImage(extractAttribute("src", (TagNode)linkNode));
	               if (img != null) {
	            	   datas.image = new BitmapDrawable(img);
	               }
	               Log.d("PoliSons", "Title is " + datas.title);
	               Log.d("PoliSons", "Link is "  + datas.link);
	               Log.d("PoliSons", "Description is "  + datas.description);
	               Log.d("PoliSons", "Date is " + datas.date);
	               newsData.add( datas);
	          }   
	     	} catch (XPatherException e) {
		          Log.e(ERROR, e.getMessage());
		    }
		return newsData;
	}
	
	public String getNewsDetail(String xPathExpression, String type, String value) {
		String data = "";
	     try {
	           // Stupid API returns Object[]...  Why not TagNodes?  We'll cast it later
	           Object[] downloadNodes = rootNode.evaluateXPath(xPathExpression);
	           for(Object linkNode : downloadNodes){   
		           // Create data structure
	        	   if (type.equals("attribute")) {
	        		   data = extractAttribute(value, (TagNode)linkNode);
	        	   }
	        	   if (type.equals("element")) {
	        		   data += extractElement(value, (TagNode)linkNode);
	        	   }
	        	   if (type.equals("text")) {
	        		   data += extractText((TagNode)linkNode, true)+"<br>";
	        	   }
		           Log.d("PoliSons", "Data extracted is " + data);
	           }
	     	} catch (XPatherException e) {
		          Log.e(ERROR, e.getMessage());
		    }
		return data;
	}

	public Drawable getNewsImage(String xPathExpression, String type, String value) {
        Drawable img = null;
	     try {
	          // Stupid API returns Object[]...  Why not TagNodes?  We'll cast it later
	          Object[] downloadNodes = rootNode.evaluateXPath(xPathExpression);
	          // Create data structure
	          // Iterate through the nodes selected by the XPath statement...
	          for(Object linkNode : downloadNodes){   
	               // Recursively find all nodes which have "href" (link) attributes.  Then, store
	               // the link values in an ArrayList.  Create a new ArchiveSongObj with these links
	               // and the title of the track, which is the inner HTML of the first child node.
	               String imglink = ((TagNode)linkNode).getAttributeByName(value);
	               img = new BitmapDrawable(downloadImage(imglink));
	               return img;
	          }   
	     	} catch (XPatherException e) {
		          Log.e(ERROR, e.getMessage());
		    }
		return img;
		
	}
}
