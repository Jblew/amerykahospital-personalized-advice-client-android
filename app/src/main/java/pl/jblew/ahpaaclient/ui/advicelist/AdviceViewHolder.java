package pl.jblew.ahpaaclient.ui.advicelist;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;

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
