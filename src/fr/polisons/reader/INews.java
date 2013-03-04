package fr.polisons.reader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class INews extends Activity implements OnItemClickListener {
    
	private List<INewsData> m_data = null;	private News news;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.news);
        
        news = new News();
        initialize();
        
    }
    
    public void initialize() {
        try {
			news.open("http://www.poli-sons.fr/");
	        m_data = news.getNews("//table[@class='blabla']");
        } catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			AlertDialog alertDialog;
			alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("Internet");
			alertDialog.setMessage("Pas d'accés internet");
			alertDialog.show();
		} catch (IOException e) {
			AlertDialog alertDialog;
			alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("Internet");
			alertDialog.setMessage("Pas d'accés internet");
			alertDialog.show();
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
		String newsLink = ((INewsData)newsList.getAdapter().getItem(position)).link;
        StringBuilder urL = new StringBuilder();
        urL.append("http://www.poli-sons.fr/");
        urL.append(newsLink);
		this.startActivity(new Intent(this,INewsDetail.class).putExtra("link", urL.toString()).putExtra("name", getString(R.string.title_news)));
	}
	
	public void setNewsRows(List<INewsData> newsrows) {
		m_data = newsrows;
	}
}
