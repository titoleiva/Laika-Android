package cl.laikachile.laika.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cl.laikachile.laika.R;
import cl.laikachile.laika.models.AlarmReminder;

public class RemindersAdapter extends ArrayAdapter<AlarmReminder> {
	
	private int mIdLayout = R.layout.lk_my_dog_reminders_adapter;
	private Context context;
	private List<AlarmReminder> reminders;

	public RemindersAdapter(Context context, int resource, List<AlarmReminder> objects) {
		super(context, resource, objects);
		
		this.context = context;
		this.reminders = objects;
	}
	
	@Override
	  public View getView(int position, View view, ViewGroup parent) {
		  
	    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(mIdLayout, parent, false);

	    
	    return rowView;
	    
	  } 
}
