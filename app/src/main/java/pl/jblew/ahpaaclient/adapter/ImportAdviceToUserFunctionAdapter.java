package pl.jblew.ahpaaclient.adapter;

import android.util.Log;
import com.google.firebase.functions.FirebaseFunctions;
import pl.jblew.ahpaaclient.data.Resource;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class ImportAdviceToUserFunctionAdapter {
  private static final String TAG = "ImportAdviceToUserAdapt";
  private static final String FUNCTION_NAME = "import_advice_to_user";
  private static final String DATA_KEY_ADVICEID = "adviceId";
  private static final String RESULT_KEY_ID = "id";
  
  @Inject
  public ImportAdviceToUserFunctionAdapter() {}
  
  
  public void importAdvice(String adviceId, Resource.Listener listener) {
    listener.resourceChanged(Resource.loading(null));
    
    FirebaseFunctions functions = FirebaseFunctions.getInstance();
  
    Map<String, Object> data = new HashMap<>();
    data.put(DATA_KEY_ADVICEID, adviceId);
    functions.getHttpsCallable(FUNCTION_NAME).call(data)
        .addOnCanceledListener(
            () -> {
              listener.resourceChanged(Resource.error("Advice import cancelled", null));
              Log.i(TAG, "Advice importing cancelled");
            })
        .addOnFailureListener(
            (Exception e) -> {
              listener.resourceChanged(
                  Resource.error("Could not import advice: " + e.getMessage(), null));
              Log.e(TAG, "Advice importing error: " + e, e);
            })
        .addOnSuccessListener(
            (qs) -> {
              Resource successRes =
                  Resource.success(null);
              listener.resourceChanged(successRes);
              Log.i(TAG, "Advice loading completed");
            });
    
  }
  
}
