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

import java.text.DateFormat;
import java.util.Date;

public class AdviceViewHolder extends RecyclerView.ViewHolder {
  private final View view;
  private final TextView doctorText;
  private final TextView datePatientText;
  private final TextView adviceText;
  
  private final DateFormat dateFormat;

  public AdviceEntity advice;

  public AdviceViewHolder(View view) {
    super(view);
    this.view = view;
    doctorText = view.findViewById(R.id.doctor_text);
    datePatientText = view.findViewById(R.id.datePatient_text);
    adviceText = view.findViewById(R.id.advice_text);
  
    dateFormat = android.text.format.DateFormat.getDateFormat(view.getContext());
  }

  @Override
  public String toString() {
    return super.toString() + " '" + advice.advice + "'";
  }

  public void bindTo(AdviceEntity item) {
    doctorText.setText(item.medicalprofessionalName);
    
    String datePatientStr = dateFormat.format(item.getDate()) + " • " + item.patientName;
    datePatientText.setText(datePatientStr);
    adviceText.setText(item.advice);
  }
}
