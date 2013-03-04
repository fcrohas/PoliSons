package fr.polisons.reader;

import java.util.List;

import org.mcsoxford.rss.RSSReaderException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class IRss extends Activity implements OnItemClickListener {
	
	private RSS rss;
	private List<IRssData> m_data;
	
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.rss);

        rss = new RSS();
        rss.initialize();
        try {
			initialize();
		} catch (RSSReaderException e) {
			// TODO Auto-generated catch block
			AlertDialog alertDialog;
			alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("Internet");
			alertDialog.setMessage("Pas d'accés internet");
			alertDialog.show();
			//e.printStackTrace();
		}

    }
    
    public void initialize() throws RSSReaderException {
        String link = getIntent().getExtras().get("link").toString();
    	rss.open(link);
        m_data = rss.getFeeds();
    	IRssAdapter newsAdapter = new IRssAdapter(this, R.layout.rssrow, m_data);
        ListView rssList = (ListView)findViewById(R.id.list);
        rssList.setAdapter( newsAdapter);
        rssList.setOnItemClickListener(this);
    	
    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
		// TODO Auto-generated method stub
		ListView newsList = (ListView)findViewById(R.id.list);
		String rssLink = ((IRssData)newsList.getAdapter().getItem(position)).link;
		String enclosure = ((IRssData)newsList.getAdapter().getItem(position)).enclosure;
		long length = ((IRssData)newsList.getAdapter().getItem(position)).length;
		this.startActivity(new Intent(this,IRssDetail.class).putExtra("link", rssLink).putExtra("name", getString(R.string.title_podcasts)).putExtra("length",length).putExtra("enclosure", enclosure));
		
	}

}
