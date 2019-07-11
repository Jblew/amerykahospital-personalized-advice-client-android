package pl.jblew.ahpaaclient.ui.advicelist;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.android.material.snackbar.Snackbar;
import dagger.android.support.AndroidSupportInjection;
import java.util.List;
import pl.jblew.ahpaaclient.R;
import pl.jblew.ahpaaclient.data.Resource;
import pl.jblew.ahpaaclient.data.model.AdviceEntity;
import pl.jblew.ahpaaclient.viewmodel.AdviceListViewModel;

public class AdviceListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
  private static String TAG = "AdviceListFragment";

  private OnListFragmentInteractionListener mListener;
  private AdviceListViewModel adviceListViewModel;

  private SwipeRefreshLayout swipeLayout;

  public AdviceListFragment() {}

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    AndroidSupportInjection.inject(this);
    adviceListViewModel = ViewModelProviders.of(this).get(AdviceListViewModel.class);
  }

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_advice_list, container, false);
    Context context = view.getContext();
    swipeLayout = view.findViewById(R.id.advicelistswiperefresh);

    RecyclerView rv = view.findViewById(R.id.advicesrecyclerview);
    rv.setLayoutManager(new LinearLayoutManager(context));

    AdviceListAdapter adapter = new AdviceListAdapter();
    adviceListViewModel
        .getAdvices()
        .observe(this, listRes -> onAdviceListChanged(view, adapter, listRes));
    rv.setAdapter(adapter);

    swipeLayout.setOnRefreshListener(() -> onRefresh());

    return view;
  }

  private void onAdviceListChanged(
      View view, AdviceListAdapter adapter, Resource<List<AdviceEntity>> listRes) {
    adapter.submitList(listRes.data);
    if (listRes.message != null && listRes.message.length() > 0) {
      Snackbar.make(view, listRes.message, Snackbar.LENGTH_LONG).show();
    }
    setRefreshingIndicatorVisibility(view, listRes.isLoading());
    Log.i(TAG, "onAdviceListChanged: status=" + listRes.status.name());
  }

  private void setRefreshingIndicatorVisibility(View view, boolean loading) {
    swipeLayout.setRefreshing(loading);
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

  @Override
  public void onRefresh() {
    adviceListViewModel.reloadAdvices();
  }

  /**
   * This interface must be implemented by activities that contain this fragment to allow an
   * interaction in this fragment to be communicated to the activity and potentially other fragments
   * contained in that activity.
   *
   * <p>See the Android Training lesson <a href=
   * "http://developer.android.com/training/basics/fragments/communicating.html" >Communicating with
   * Other Fragments</a> for more information.
   */
  public interface OnListFragmentInteractionListener {
    // TODO: Update argument type and name
    void onListFragmentInteraction(AdviceEntity item);
  }
}
