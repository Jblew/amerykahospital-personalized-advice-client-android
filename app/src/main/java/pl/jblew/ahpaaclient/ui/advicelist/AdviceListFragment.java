package pl.jblew.ahpaaclient.ui.advicelist;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import pl.jblew.ahpaaclient.R;
import pl.jblew.ahpaaclient.data.model.AdviceEntity;
import pl.jblew.ahpaaclient.factory.ViewModelFactory;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class AdviceListFragment extends Fragment {
	private OnListFragmentInteractionListener mListener;

	private AdviceListViewModel adviceListViewModel;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the fragment (e.g. upon
	 * screen orientation changes).
	 */
	public AdviceListFragment() {
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidSupportInjection.inject(this);
		adviceListViewModel = ViewModelProviders.of(this).get(AdviceListViewModel.class);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_advice_list, container, false);

		// Set the adapter
		if (view instanceof RecyclerView) {
			Context context = view.getContext();
			RecyclerView recyclerView = (RecyclerView) view;
			recyclerView.setLayoutManager(new LinearLayoutManager(context));

			AdviceListAdapter adapter = new AdviceListAdapter();
			adviceListViewModel.getAdvices().observe(this, listRes -> adapter.submitList(listRes.data));
			recyclerView.setAdapter(adapter);
		}
		return view;
	}


	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof OnListFragmentInteractionListener) {
			mListener = (OnListFragmentInteractionListener) context;
		} else {
			throw new RuntimeException(
					context.toString() + " must implement OnListFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	/**
	 * This interface must be implemented by activities that contain this fragment to allow an
	 * interaction in this fragment to be communicated to the activity and potentially other
	 * fragments contained in that activity.
	 * <p/>
	 * See the Android Training lesson <a href= "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnListFragmentInteractionListener {
		// TODO: Update argument type and name
		void onListFragmentInteraction(AdviceEntity item);
	}
}
