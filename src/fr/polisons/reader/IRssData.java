package fr.polisons.reader;

import android.graphics.drawable.Drawable;

public class IRssData {
	public Drawable image;
	public String title;
	public String description;
	public String date;
	public String link;
	public String enclosure;
	public long length;
	public IRssData() {
		super();
	}

	public IRssData( Drawable image, String title, String description, String date, String link, String enclosure, int length) {
		super();
		this.image = image;
		this.title = title;
		this.description = description;
		this.date = date;
		this.link = link;
		this.enclosure = enclosure;
		this.length = length;
	}
	
}
