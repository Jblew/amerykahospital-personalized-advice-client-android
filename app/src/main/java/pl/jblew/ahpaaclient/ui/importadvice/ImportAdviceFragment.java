package pl.jblew.ahpaaclient.ui.importadvice;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.jblew.ahpaaclient.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImportAdviceFragment extends Fragment {
  
  
  public ImportAdviceFragment() {
    // Required empty public constructor
  }
  
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_import_advice, container, false);
  }
  
}
