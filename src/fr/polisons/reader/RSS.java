package fr.polisons.reader;

import java.util.ArrayList;
import java.util.List;

import org.mcsoxford.rss.RSSFeed;
import org.mcsoxford.rss.RSSItem;
import org.mcsoxford.rss.RSSReader;
import org.mcsoxford.rss.RSSReaderException;

import android.text.Html;

public class RSS {
	
	private RSSReader readerRSS;
	private RSSFeed   feedRSS;
	private ArrayList<IRssData> rssData;
	private IRssData datas;
	
    public void initialize() {
    	readerRSS = new RSSReader();
    }

    public void open(String url) throws RSSReaderException {
    	// "http://www.poli-sons.fr/spip.php?page=podcast_rubriques&id_rubrique=24";
    	feedRSS = readerRSS.load(url);  	
    }
    
    public ArrayList<IRssData> getFeeds() {
		
    	rssData = new ArrayList<IRssData>();
    	// Loop on each item of rss feed
    	List<RSSItem> items = feedRSS.getItems();
		
		for (RSSItem item : items) {
			datas = new IRssData();
			datas.date = item.getPubDate().toLocaleString();
			datas.description = Html.fromHtml(item.getDescription()).toString();
			datas.title = item.getTitle();
			datas.link = item.getLink().toString();
			//datas.image = item.getThumbnails();
			rssData.add(datas);
		}
    	return rssData;
    	 
    }
    
	
}
