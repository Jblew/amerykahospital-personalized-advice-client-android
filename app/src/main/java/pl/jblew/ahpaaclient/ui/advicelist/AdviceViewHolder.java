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

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import pl.jblew.ahpaaclient.R;
import pl.jblew.ahpaaclient.data.model.AdviceEntity;

public class AdviceViewHolder extends RecyclerView.ViewHolder {
  private final View mView;
  private final TextView doctorText;
  private final TextView dateText;
  private final TextView adviceText;

  public AdviceEntity advice;

  public AdviceViewHolder(View view) {
    super(view);
    mView = view;
    doctorText = (TextView) view.findViewById(R.id.doctor_text);
    dateText = (TextView) view.findViewById(R.id.date_text);
    adviceText = (TextView) view.findViewById(R.id.advice_text);
  }

  @Override
  public String toString() {
    return super.toString() + " '" + advice.advice + "'";
  }

  public void bindTo(AdviceEntity item) {
    doctorText.setText(item.medicalprofessionalName);
    dateText.setText(item.dateISO);
    adviceText.setText(item.advice);
  }
}
