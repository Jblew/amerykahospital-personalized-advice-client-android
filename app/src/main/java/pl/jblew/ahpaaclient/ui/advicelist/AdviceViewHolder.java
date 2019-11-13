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
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import pl.jblew.ahpaaclient.R;
import pl.jblew.ahpaaclient.adapter.ThankFunctionAdapter;
import pl.jblew.ahpaaclient.data.model.AdviceEntity;

public class AdviceViewHolder extends RecyclerView.ViewHolder {
  private final ThankFunctionAdapter thankFunctionAdapter;
  private boolean showIntro = false;
  
  private final View view;
  private final TextView doctorText;
  private final TextView datePatientText;
  private final TextView adviceText;
  private final Button thankCount;
  private final Button thankButton;
  private final View thankIntro;
  
  private final DateFormat dateFormat;

  public AdviceEntity advice;

  public AdviceViewHolder(View view, ThankFunctionAdapter thankFunctionAdapter, boolean showIntro) {
    super(view);
    this.thankFunctionAdapter = thankFunctionAdapter;
    this.view = view;
    this.showIntro = showIntro;
    
    doctorText = view.findViewById(R.id.doctor_text);
    datePatientText = view.findViewById(R.id.datePatient_text);
    adviceText = view.findViewById(R.id.advice_text);
    thankCount = view.findViewById((R.id.thankCount));
    thankButton = view.findViewById((R.id.thankButton));
    thankIntro = view.findViewById((R.id.thankIntro));

    dateFormat = android.text.format.DateFormat.getDateFormat(view.getContext());
  }

  @Override
  public String toString() {
    return super.toString() + " '" + advice.advice + "'";
  }

  public void bindTo(AdviceEntity item) {
    this.advice = item;
    doctorText.setText(item.medicalprofessionalName);

    String datePatientStr = dateFormat.format(item.getDate()) + " • " + item.patientName;
    datePatientText.setText(datePatientStr);
    adviceText.setText(item.advice);
    
    int thanksCount = 0;
    thanksCount = item.thanksCount;
    thankCount.setText(ThanksCountTextGenerator.getThanksCountText(thanksCount));
  
    thankIntro.setVisibility(showIntro ? View.VISIBLE : View.GONE);
    
    thankButton.setOnClickListener(e -> {
      thankButton.setEnabled(false);
      thankFunctionAdapter.thank(advice.id, res -> {
        thankButton.setEnabled(true);
  
        if (res.isSuccess()) {
          thankCount.setText(ThanksCountTextGenerator.getThanksCountText(res.data));
          thankButton.setEnabled(true);
        }
        if (res.isError()) {
          Snackbar.make(view, "Błąd: " + res.message, Snackbar.LENGTH_LONG).show();
        }
        if (res.isLoading()) thankButton.setEnabled(false);
      });
    });
  }
}
