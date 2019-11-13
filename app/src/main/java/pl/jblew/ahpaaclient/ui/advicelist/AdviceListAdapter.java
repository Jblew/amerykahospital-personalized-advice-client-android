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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import pl.jblew.ahpaaclient.R;
import pl.jblew.ahpaaclient.adapter.ThankFunctionAdapter;
import pl.jblew.ahpaaclient.data.model.AdviceEntity;
import timber.log.Timber;

public class AdviceListAdapter extends ListAdapter<AdviceEntity, RecyclerView.ViewHolder> {
  private static final String TAG = "AdviceListAdapter";
  private final ThankFunctionAdapter thankFunctionAdapter;

  public static final DiffUtil.ItemCallback<AdviceEntity> DIFF_CALLBACK =
      new DiffUtil.ItemCallback<AdviceEntity>() {

        @Override
        public boolean areItemsTheSame(@NonNull AdviceEntity a, @NonNull AdviceEntity b) {
          return a.id != null && a.id.equals(b.id);
        }

        @Override
        public boolean areContentsTheSame(@NonNull AdviceEntity a, @NonNull AdviceEntity b) {
          return a.equals(b);
        }
      };

  public AdviceListAdapter(ThankFunctionAdapter thankFunctionAdapter) {
    super(DIFF_CALLBACK);
    this.thankFunctionAdapter = thankFunctionAdapter;
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (holder instanceof AdviceViewHolder) {
      ((AdviceViewHolder) holder).bindTo(getItem(position));
    } else if (holder instanceof FooterViewHolder) {
      // it does not require binding
    } else if (holder instanceof EmptylistViewHolder) {
      // it does not require binding
    } else {
      Timber.tag(TAG).e("Unknown view holder: " + holder);
    }
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    if (viewType == ViewType.FOOTER.ordinal()) {
      View v =
          LayoutInflater.from(parent.getContext())
              .inflate(R.layout.advice_list_footer, parent, false);
      FooterViewHolder vh = new FooterViewHolder(v);
      return vh;
    }

    if (viewType == ViewType.EMPTYLIST.ordinal()) {
      View v =
          LayoutInflater.from(parent.getContext())
              .inflate(R.layout.advice_list_empty, parent, false);
      EmptylistViewHolder vh = new EmptylistViewHolder(v);
      return vh;
    }

    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_advice, parent, false);

    if (viewType == ViewType.ADVICE_FIRST.ordinal()) {
      return new AdviceViewHolder(view, thankFunctionAdapter, true);
    }
    
    return new AdviceViewHolder(view, thankFunctionAdapter, false);
  }

  @Override
  public int getItemCount() {
    int listLength = super.getItemCount();
    return listLength == 0 ? 2 : super.getItemCount() + 1;
  }

  @Override
  public int getItemViewType(int position) {
    int listLength = super.getItemCount();
    if (listLength == 0 && position == 0) {
      return ViewType.EMPTYLIST.ordinal();
    }

    if (position == this.getItemCount() - 1) {
      return ViewType.FOOTER.ordinal();
    }
    
    if (position == 0) {
      return ViewType.ADVICE_FIRST.ordinal();
    }

    return ViewType.ADVICE.ordinal();
  }

  private enum ViewType {
    FOOTER,
    EMPTYLIST,
    ADVICE_FIRST,
    ADVICE,
  }
}
