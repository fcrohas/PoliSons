package fr.polisons.reader;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class INewsAdapter extends ArrayAdapter<INewsData>{

	private Context context;
	private int layoutResourceId;
	private List<INewsData> data = null;
	
	public INewsAdapter(Context context, int textViewResourceId,
			List<INewsData> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.layoutResourceId = textViewResourceId;
		this.data = objects;
		
	}

	public void add(INewsData values) {
		data.add( values);
	}

    static class INewsHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
        TextView txtDescription;
        TextView txtDate;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        INewsHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new INewsHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.image);
            holder.txtTitle = (TextView)row.findViewById(R.id.title);
            holder.txtDescription = (TextView)row.findViewById(R.id.description);
            holder.txtDate = (TextView)row.findViewById(R.id.date);
            row.setTag(holder);
        }
        else
        {
            holder = (INewsHolder)row.getTag();
        }
        
        INewsData application = data.get(position);
        holder.txtDescription.setText(application.description);
        holder.txtTitle.setText(application.title);
        if (application.image != null) {
        	holder.imgIcon.setImageDrawable(application.image);
        }
        holder.txtDate.setText(application.date);
        return row;
    }
    
	
}
