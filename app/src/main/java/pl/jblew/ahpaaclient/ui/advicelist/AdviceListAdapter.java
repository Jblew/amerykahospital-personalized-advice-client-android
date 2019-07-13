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
import pl.jblew.ahpaaclient.R;
import pl.jblew.ahpaaclient.data.model.AdviceEntity;

public class AdviceListAdapter extends ListAdapter<AdviceEntity, AdviceViewHolder> {
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

  public AdviceListAdapter() {
    super(DIFF_CALLBACK);
  }

  @Override
  public void onBindViewHolder(AdviceViewHolder holder, int position) {
    holder.bindTo(getItem(position));
  }

  @Override
  public AdviceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_advice, parent, false);
    return new AdviceViewHolder(view);
  }
}
