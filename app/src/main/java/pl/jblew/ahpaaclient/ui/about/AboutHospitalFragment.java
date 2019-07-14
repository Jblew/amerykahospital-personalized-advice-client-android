package pl.jblew.ahpaaclient.ui.about;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.jblew.ahpaaclient.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutHospitalFragment extends Fragment {
  
  
  public AboutHospitalFragment() {
    // Required empty public constructor
  }
  
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_about_hospital, container, false);
  }
  
}
