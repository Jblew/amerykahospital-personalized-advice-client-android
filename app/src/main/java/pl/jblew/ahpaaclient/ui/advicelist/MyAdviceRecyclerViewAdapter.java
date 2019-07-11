package pl.jblew.ahpaaclient.ui.advicelist;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pl.jblew.ahpaaclient.data.model.AdviceEntity;
import pl.jblew.ahpaaclient.ui.advicelist.AdviceListFragment.OnListFragmentInteractionListener;
import pl.jblew.ahpaaclient.R;

import java.util.List;


public class MyAdviceRecyclerViewAdapter extends RecyclerView.Adapter<MyAdviceRecyclerViewAdapter.ViewHolder> {

	private final List<AdviceEntity> mValues;
	private final OnListFragmentInteractionListener mListener;

	public MyAdviceRecyclerViewAdapter(List<AdviceEntity> items,
									   OnListFragmentInteractionListener listener) {
		mValues = items;
		mListener = listener;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.fragment_advice, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(final ViewHolder holder, int position) {
		holder.mItem = mValues.get(position);
		holder.mIdView.setText(mValues.get(position).id);
		holder.mContentView.setText(mValues.get(position).advice);

		holder.mView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (null != mListener) {
					// Notify the active callbacks interface (the activity, if the
					// fragment is attached to one) that an item has been selected.
					mListener.onListFragmentInteraction(holder.mItem);
				}
			}
		});
	}

	@Override
	public int getItemCount() {
		return mValues.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {
		public final View mView;
		public final TextView mIdView;
		public final TextView mContentView;
		public AdviceEntity mItem;

		public ViewHolder(View view) {
			super(view);
			mView = view;
			mIdView = (TextView) view.findViewById(R.id.item_number);
			mContentView = (TextView) view.findViewById(R.id.content);
		}

		@Override
		public String toString() {
			return super.toString() + " '" + mContentView.getText() + "'";
		}
	}
}
