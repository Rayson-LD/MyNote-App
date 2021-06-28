package com.project.mynoteapp;
import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

public class DisplayAdapter extends BaseAdapter implements Filterable {
	private ArrayList<String> filteredlist = null;
	private ArrayList<String> originallist = null; // Original Values
	private LayoutInflater inflater;
	private ItemFilter Filter = new ItemFilter();
	public DisplayAdapter(Context c, ArrayList<String> nama) {
		this.filteredlist = nama;
		this.originallist = nama;
		inflater = LayoutInflater.from(c);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return filteredlist.size();
	}
	public Object getItem(int position) {

		return filteredlist.get(position);
	}
	public long getItemId(int position) {

		return position;
	}
	public View getView(int pos, View child, ViewGroup parent) {
		Holder mHolder;
		if (child == null) {
			child = inflater.inflate(R.layout.listcell, null);
			mHolder = new Holder();
			mHolder.txtnama = (TextView) child.findViewById(R.id.txtnama);
			child.setTag(mHolder);
		} else {
			mHolder = (Holder) child.getTag();
		}
	
		mHolder.txtnama.setText(filteredlist.get(pos));
		return child;
	}

	public class Holder {

		TextView txtnama;
	}
	@Override
	public Filter getFilter()
	{
		return Filter;
	}
		private class ItemFilter extends Filter
		{
			@Override
			protected FilterResults performFiltering(CharSequence c)
			{
				String filterString = c.toString().toLowerCase();
				FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
				final ArrayList<String> FilteredArrList = originallist;
				int count = FilteredArrList.size();
				final ArrayList<String> nlist = new ArrayList<>(count);
				String filterableString ;
					for (int i = 0; i < count; i++)
					{
						filterableString = FilteredArrList.get(i);
						if (filterableString.toLowerCase().contains(filterString))
							nlist.add(filterableString);
					}
					results.count = nlist.size();
					results.values = nlist;
				return results;
			}
			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(CharSequence c,FilterResults results)
			{

				filteredlist = (ArrayList<String>) results.values;
				notifyDataSetChanged();
			}
		}

}

