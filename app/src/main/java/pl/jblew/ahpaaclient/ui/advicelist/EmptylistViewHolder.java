package pl.jblew.ahpaaclient.ui.advicelist;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

public class EmptylistViewHolder extends RecyclerView.ViewHolder {
  public EmptylistViewHolder(View itemView) {
    super(itemView);
    itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // Do whatever you want on clicking the item
      }
    });
  }
}
