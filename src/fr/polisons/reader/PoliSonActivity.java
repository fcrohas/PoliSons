package fr.polisons.reader;


import fr.polisons.reader.INews;
import fr.polisons.reader.IRssMenu;
import fr.polisons.reader.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewSwitcher;

public class PoliSonActivity extends Activity implements OnItemClickListener, ViewSwitcher.ViewFactory {
	private static final int BGCOLOR = 0xFF000000;

	private ViewSwitcher vSwitcher;

    String[] menuItems = {
    		"Nouvelles",
    		"PodCast"
    };
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        vSwitcher = (ViewSwitcher) findViewById(R.id.itemSwitcher);
        Animation in = AnimationUtils.loadAnimation(this, 
                android.R.anim.slide_in_left); 
        Animation out = AnimationUtils.loadAnimation(this, 
                android.R.anim.slide_out_right); 
        vSwitcher.setInAnimation(in); 
        vSwitcher.setOutAnimation(out);
    	ListView menu = (ListView)this.findViewById(R.id.list);
        menu.setAdapter(new ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1, menuItems));
        menu.setOnItemClickListener( this);
        
    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
		if (position == 0) {
			this.startActivity(new Intent(this, INews.class));
		}
		if (position == 1) {
			this.startActivity(new Intent(this, IRssMenu.class));
		}
		
	}

	@Override
	public View makeView() {
	    ImageView i = new ImageView(this);
	    i.setBackgroundColor(BGCOLOR);
	    i.setScaleType(ImageView.ScaleType.FIT_CENTER);
	    i.setLayoutParams(new ImageSwitcher.LayoutParams(
	        LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		return i;
	}
    
}