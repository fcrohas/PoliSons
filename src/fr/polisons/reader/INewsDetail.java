package fr.polisons.reader;

import java.io.IOException;
import java.net.MalformedURLException;
import fr.polisons.reader.R;
import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

public class INewsDetail extends Activity {

	private News news;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  
        // initialize form
        setContentView(R.layout.newsdetail);
        // Get link to display
        String link = getIntent().getExtras().get("link").toString();
        String name = getIntent().getExtras().get("name").toString();
        // Build URl website
        // create news grabber
        news = new News();
        try {
			news.open(link);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TextView title = (TextView)findViewById(R.id.title);
		title.setText(Html.fromHtml(news.getNewsDetail("//h1[@class='entry-title']","attribute","alt")) );
		TextView date =  (TextView)findViewById(R.id.date);
		date.setText(Html.fromHtml(news.getNewsDetail("//abbr[@class='published']","text","abbr")) );
		ImageView image = (ImageView)findViewById(R.id.image);
		image.setImageDrawable( news.getNewsImage("//img[@class='spip_logos']","attribute","src" ));
	    TextView description = (TextView)findViewById(R.id.description);
	    description.setText(Html.fromHtml(news.getNewsDetail("//div[@id='body']/p","text","*")));
	    TextView rubrique = (TextView)findViewById(R.id.rubrique);
	    rubrique.setText(name);
        
	}    
}
