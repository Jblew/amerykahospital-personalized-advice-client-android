package pl.jblew.ahpaaclient.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import pl.jblew.ahpaaclient.adapter.ImportAdviceToUserFunctionAdapter;
import pl.jblew.ahpaaclient.data.Resource;

import javax.inject.Inject;

public class ImportAdviceViewModel extends ViewModel {
  @Inject
  ImportAdviceToUserFunctionAdapter importFunctionAdapter;
  
  @Inject
  public ImportAdviceViewModel() {}
  
  public MutableLiveData<Resource<Object>> importAdvice(String adviceId)  {
    MutableLiveData<Resource<Object>> importState = new MutableLiveData<>();
    importFunctionAdapter.importAdvice(adviceId, (res) -> importState.postValue((res)));
    return importState;
  }
}
