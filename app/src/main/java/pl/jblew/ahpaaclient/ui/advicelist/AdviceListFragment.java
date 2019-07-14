/***
 *     Ameryka Hospital Personalized advice system — the Android client app. This system allows
 *     doctors to securely and reliably send medical advices to patient's smartphones.
 *
 *     Copyright (C) 2019 by Jędrzej Lewandowski <jedrzejblew@gmail.com>
 *         (https://jedrzej.lewandowski.doctor)
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
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
  private RecyclerView recyclerView;
  private SwipeRefreshLayout swipeLayout;
  private LinearLayoutManager layoutManager;

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
  
    recyclerView = view.findViewById(R.id.advicesrecyclerview);
    layoutManager = new LinearLayoutManager(context) {
      @Override
      public boolean supportsPredictiveItemAnimations() {
        return false;
      }
    };
    recyclerView.setLayoutManager(layoutManager);

    AdviceListAdapter adapter = new AdviceListAdapter();
    adviceListViewModel
        .getAdvices()
        .observe(this, listRes -> onAdviceListChanged(view, adapter, listRes));
    adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
      @Override
      public void onItemRangeInserted(int positionStart, int itemCount) {
        layoutManager.smoothScrollToPosition(recyclerView, null, 0);
      }
    });
    recyclerView.setAdapter(adapter);

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
