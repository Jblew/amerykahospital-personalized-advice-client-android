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

package pl.jblew.ahpaaclient.adapter;

import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.functions.FirebaseFunctions;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import pl.jblew.ahpaaclient.data.Resource;
import pl.jblew.ahpaaclient.data.model.AdviceEntity;
import timber.log.Timber;

@Singleton
public class ThankFunctionAdapter {
  private static final String TAG = "ThankFunctionAdapter";
  private static final String FUNCTION_NAME = "thank";
  private static final String DATA_KEY_ADVICEID = "adviceId";
  private static final String RESULT_KEY_ID = "newThanksCount";
  
  @Inject
  public ThankFunctionAdapter() {}
  
  public void thank(String adviceId, Resource.Listener<Integer> listener) {
    listener.resourceChanged(Resource.loading(null));
    
    FirebaseFunctions functions = FirebaseFunctions.getInstance();
    
    Map<String, Object> data = new HashMap<>();
    data.put(DATA_KEY_ADVICEID, adviceId);
    functions
        .getHttpsCallable(FUNCTION_NAME)
        .call(data)
        .addOnCanceledListener(
            () -> {
              listener.resourceChanged(Resource.error("Advice import cancelled", null));
              Timber.tag(TAG).w("Advice importing cancelled");
            })
        .addOnFailureListener(
            (Exception e) -> {
              listener.resourceChanged(
                  Resource.error("Could not import advice: " + e.getMessage(), null));
              Timber.tag(TAG).e(e, "Advice importing error: " + e);
            })
        .addOnSuccessListener(
            (ts) -> {
              Map<String, String> kvs = (Map<String, String>) ts.getData();
              Resource successRes = Resource.success(kvs.get(RESULT_KEY_ID))8;
              listener.resourceChanged(successRes);
            });
  }
}
