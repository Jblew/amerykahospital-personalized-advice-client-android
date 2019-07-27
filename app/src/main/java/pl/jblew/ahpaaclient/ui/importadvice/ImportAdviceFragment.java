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

package pl.jblew.ahpaaclient.ui.importadvice;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.support.DaggerFragment;
import pl.jblew.ahpaaclient.R;
import pl.jblew.ahpaaclient.data.Resource;
import pl.jblew.ahpaaclient.factory.ViewModelFactory;
import pl.jblew.ahpaaclient.ui.activity.MainActivity;
import pl.jblew.ahpaaclient.viewmodel.AdviceListViewModel;
import pl.jblew.ahpaaclient.viewmodel.ImportAdviceViewModel;

import javax.inject.Inject;

public class ImportAdviceFragment extends DaggerFragment {
  private static final String TAG = "ImportAdviceFragment";
  
  private String importAdviceOnFragmentStartup_adviceId = null;
  
  @Inject
  public ViewModelFactory vmFactory;
  
  private ImportAdviceViewModel importAdviceViewModel;
  private EditText adviceCodeInput;
  private Button importButton;
  private ProgressBar loadingSpinnder;
  private TextView statusText;
  
  private ImportAdviceFragment(String importAdviceOnFragmentStartup_adviceId) {
     this.importAdviceOnFragmentStartup_adviceId = importAdviceOnFragmentStartup_adviceId;
  }
  
  public ImportAdviceFragment() {
    // Required empty public constructor
  }
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    importAdviceViewModel = ViewModelProviders.of(this, vmFactory).get(ImportAdviceViewModel.class);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view =  inflater.inflate(R.layout.fragment_import_advice, container, false);
    adviceCodeInput = view.findViewById(R.id.importAdvice_adviceCodeInput);
    importButton = view.findViewById(R.id.importAdvice_importBtn);
    loadingSpinnder = view.findViewById(R.id.importAdvice_loadingSpinner);
    statusText = view.findViewById(R.id.importAdvice_statusText);
    
    importButton.setOnClickListener((e) -> fireAdviceImport());
    
    
    return view;
  }
  
  @Override
  public void onStart() {
    super.onStart();
    
    handleImportAdviceOnStartup();
  }
  
  private void handleImportAdviceOnStartup() {
    if (importAdviceOnFragmentStartup_adviceId != null) {
      importAdvice(importAdviceOnFragmentStartup_adviceId);
      importAdviceOnFragmentStartup_adviceId = null;
    }
  }
  
  private void importAdvice(String adviceId) {
    adviceCodeInput.setText(adviceId);
    fireAdviceImport();
  }
  
  private void fireAdviceImport() {
    importButton.setEnabled(false);
    adviceCodeInput.setEnabled(false);
  
    loadingSpinnder.setVisibility(View.VISIBLE);
    statusText.setVisibility(View.VISIBLE);
    statusText.setText(R.string.loading);
    statusText.setTextColor(Color.BLACK);
    
    String adviceId = adviceCodeInput.getText().toString();
    doImportAdvice(adviceId);
  }
  
  private void doImportAdvice(String adviceId) {
    MutableLiveData<Resource<Object>> importState = this.importAdviceViewModel.importAdvice(adviceId);
    importState.observe(this, res -> {
      if (res.isError()) {
        adviceImportError(res.message);
      }
      else if (res.isSuccess()) {
        adviceImportFinished();
      }
    });
  }
  
  private void adviceImportFinished() {
    importButton.setEnabled(true);
    adviceCodeInput.setEnabled(true);
    adviceCodeInput.setText("");
  
    loadingSpinnder.setVisibility(View.GONE);
    statusText.setVisibility(View.GONE);
    statusText.setText("");
    statusText.setTextColor(Color.BLACK);
  
  
    ((MainActivity) getActivity()).openAdviceList();
  }
  
  private void adviceImportError(String error) {
    importButton.setEnabled(true);
    adviceCodeInput.setEnabled(true);
    adviceCodeInput.setText("");
  
    loadingSpinnder.setVisibility(View.GONE);
    statusText.setVisibility(View.VISIBLE);
    statusText.setText(error);
    statusText.setTextColor(Color.RED);
  }
  
  public static ImportAdviceFragment importAdviceOnStartup(String adviceId) {
    return new ImportAdviceFragment(adviceId);
  }
}
