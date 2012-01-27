package fr.polisons.reader;

import android.graphics.drawable.Drawable;

public class INewsData {
	public Drawable image;
	public String title;
	public String description;
	public String date;
	public String link;
	public INewsData() {
		super();
	}
	
	public INewsData( Drawable image, String title, String description, String date, String link) {
		super();
		this.image = image;
		this.title = title;
		this.description = description;
		this.date = date;
		this.link = link;
	}

}
