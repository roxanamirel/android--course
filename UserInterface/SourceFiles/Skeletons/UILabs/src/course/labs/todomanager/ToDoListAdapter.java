package course.labs.todomanager;

import java.util.ArrayList;
import java.util.List;

import course.labs.todomanager.ToDoItem.Status;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ToDoListAdapter extends BaseAdapter {

	private final List<ToDoItem> mItems = new ArrayList<ToDoItem>();
	private final Context mContext;

	private static final String TAG = "Lab-UserInterface";

	public ToDoListAdapter(Context context) {

		mContext = context;

	}

	// Add a ToDoItem to the adapter
	// Notify observers that the data set has changed

	public void add(ToDoItem item) {

		mItems.add(item);
		notifyDataSetChanged();

	}

	// Clears the list adapter of all items.

	public void clear() {

		mItems.clear();
		notifyDataSetChanged();

	}

	// Returns the number of ToDoItems

	@Override
	public int getCount() {

		return mItems.size();

	}

	// Retrieve the number of ToDoItems

	@Override
	public Object getItem(int pos) {

		return mItems.get(pos);

	}

	// Get the ID for the ToDoItem
	// In this case it's just the position

	@Override
	public long getItemId(int pos) {

		return pos;

	}

	// Create a View for the ToDoItem at specified position
	// Remember to check whether convertView holds an already allocated View
	// before created a new View.
	// Consider using the ViewHolder pattern to make scrolling more efficient
	// See: http://developer.android.com/training/improving-layouts/smooth-scrolling.html
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// Get the current ToDoItem
		final ToDoItem toDoItem = (ToDoItem) getItem(position);
		ViewHolder holder;
		if(convertView ==null){
			 LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			 convertView = inflater.inflate(R.layout.todo_item, parent, false); 
			holder= new ViewHolder();
			holder.titleView = (TextView)convertView.findViewById(R.id.titleView);
			holder.statusView = (CheckBox)convertView.findViewById(R.id.statusCheckBox);
			holder.priorityView = (TextView)convertView.findViewById(R.id.PriorityLabel);
			holder.dateView = (TextView)convertView.findViewById(R.id.dateView);
			convertView.setTag(holder);  
		}
		else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.titleView.setText(toDoItem.getTitle());
		holder.statusView.setChecked(toDoItem.getStatus() == Status.DONE);
		holder.statusView.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				Log.i(TAG, "Entered onCheckedChanged()");
				// set up an OnCheckedChangeListener, which
				// is called when the user toggles the status checkbox
				Status currentStatus = isChecked == true?Status.DONE:Status.NOTDONE;
				toDoItem.setStatus(currentStatus);

			}
		});
		holder.priorityView.setText(toDoItem.getPriority().toString());
		holder.dateView.setText(ToDoItem.FORMAT.format(toDoItem.getDate()));
		// Return the View you just created
		return convertView;

	}
	static class ViewHolder {
		  TextView titleView;
		  CheckBox statusView;
		  TextView priorityView;
		  TextView dateView;
		}
}

