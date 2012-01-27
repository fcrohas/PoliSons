package fr.polisons.reader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class IRssMenu extends Activity implements OnItemClickListener {
	
	private static final int linkSubstr = 3;
	private List<INewsData> m_data = null;
	private News news;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.news);
        
        news = new News();
        initialize();
        
    }
    
    public void initialize() {
        try {
			news.open("http://www.poli-sons.fr/-Les-Podcasts-.html");
	        m_data = news.getPodcast("//div[@class='box']");
        } catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	INewsAdapter newsAdapter = new INewsAdapter(this, R.layout.newsrow, m_data);
        ListView newsList = (ListView)findViewById(R.id.list);
        newsList.setAdapter( newsAdapter);
        newsList.setOnItemClickListener(this);
    	
    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
		// TODO Auto-generated method stub
		ListView newsList = (ListView)findViewById(R.id.list);
		String rssLink = ((INewsData)newsList.getAdapter().getItem(position)).link;
		// bad hack , need to replace later
		rssLink = rssLink.substring(linkSubstr);
		StringBuilder link = new StringBuilder();
		link.append("http://www.poli-sons.fr/");
		link.append(rssLink);
		this.startActivity(new Intent(this,IRss.class).putExtra("link", link.toString()));
	}

}
