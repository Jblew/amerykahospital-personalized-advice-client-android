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
