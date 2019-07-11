package pl.jblew.ahpaaclient.ui.advicelist;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import pl.jblew.ahpaaclient.R;
import pl.jblew.ahpaaclient.data.model.AdviceEntity;

public class AdviceViewHolder extends RecyclerView.ViewHolder {
	public final View mView;
	public final TextView mIdView;
	public final TextView mContentView;
	public AdviceEntity mItem;

	public AdviceViewHolder(View view) {
		super(view);
		mView = view;
		mIdView = (TextView) view.findViewById(R.id.item_number);
		mContentView = (TextView) view.findViewById(R.id.content);
	}

	@Override
	public String toString() {
		return super.toString() + " '" + mContentView.getText() + "'";
	}

	public void bindTo(AdviceEntity item) {
		mIdView.setText(item.id);
		mContentView.setText(item.advice);
	}
}
