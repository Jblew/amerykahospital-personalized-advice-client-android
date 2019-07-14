package pl.jblew.ahpaaclient.ui.advicelist;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

public class FooterViewHolder extends RecyclerView.ViewHolder {
  public FooterViewHolder(View itemView) {
    super(itemView);
    itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // Do whatever you want on clicking the item
      }
    });
  }
}
