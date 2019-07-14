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

package pl.jblew.ahpaaclient.viewmodel;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import pl.jblew.ahpaaclient.BackendConfig;
import pl.jblew.ahpaaclient.data.Resource;
import pl.jblew.ahpaaclient.data.model.AdviceEntity;
import pl.jblew.ahpaaclient.data.repository.AdviceRepository;

public class AdviceListViewModel extends ViewModel {
  private static String TAG = "AdviceListViewModel";
  @Inject public AdviceRepository adviceRepository;

  private MutableLiveData<Resource<List<AdviceEntity>>> advices;

  @Inject
  public AdviceListViewModel() {}

  public LiveData<Resource<List<AdviceEntity>>> getAdvices() {
    if (advices == null) {
      advices = new MutableLiveData<>();
      reloadAdvices();
    }
    return advices;
  }

  public void reloadAdvices() {
    advices.postValue(Resource.loading(this.getPresentListOrEmptyList()));

    Log.i(TAG, "Advice reload started");
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    db.collection(BackendConfig.FIRESTORE_COLLECTION_ADVICES)
        .get()
        .addOnCanceledListener(
            () -> {
              advices.postValue(Resource.success(this.getPresentListOrEmptyList()));
              Log.i(TAG, "Advice loading calcelled");
            })
        .addOnFailureListener(
            (Exception e) -> {
              advices.postValue(
                  Resource.error(
                      "Could not fetch data: " + e.getMessage(), this.getPresentListOrEmptyList()));
              Log.e(TAG, "Advice loading error: " + e, e);
            })
        .addOnSuccessListener(
            (qs) -> {
              Resource successRes =
                  Resource.success(
                      this.mapQuerySnapshotToAdviceList(qs),
                      qs.getMetadata().isFromCache()
                          ? "This data was loaded from " + "offline cache"
                          : null);
              advices.postValue(successRes);
              Log.i(TAG, "Advice loading completed");
            });
  }

  private List<AdviceEntity> mapQuerySnapshotToAdviceList(QuerySnapshot qs) {
    return qs.toObjects(AdviceEntity.class);
  }

  private List<AdviceEntity> getPresentListOrEmptyList() {
    return advices.getValue() != null ? advices.getValue().data : Collections.EMPTY_LIST;
  }
}
