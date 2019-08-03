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

package pl.jblew.ahpaaclient.data.repository;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import pl.jblew.ahpaaclient.BackendConfig;
import pl.jblew.ahpaaclient.data.Resource;
import pl.jblew.ahpaaclient.data.model.AdviceEntity;
import timber.log.Timber;

@Singleton
public class AdviceRepository {
  private static final String TAG = "AdviceRepository";

  @Inject
  public AdviceRepository() {}

  public void loadAdvicesForUser(FirebaseUser user, Resource.Listener listener) {
    Timber.tag(TAG).i("Advice reload started");
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    listener.resourceChanged(Resource.loading(null));
    db.collection(BackendConfig.FIRESTORE_COLLECTION_ADVICES)
        .whereEqualTo("uid", user.getUid())
        .orderBy("timestamp", Query.Direction.DESCENDING)
        .get()
        .addOnCanceledListener(
            () -> {
              listener.resourceChanged(Resource.success(null));
              Timber.tag(TAG).i("Advice loading calcelled");
            })
        .addOnFailureListener(
            (Exception e) -> {
              listener.resourceChanged(
                  Resource.error("Could not fetch data: " + e.getMessage(), null));
              Timber.tag(TAG).e(e, "Advice loading error: " + e);
            })
        .addOnSuccessListener(
            (qs) -> {
              Resource successRes =
                  Resource.success(
                      this.mapQuerySnapshotToAdviceList(qs),
                      qs.getMetadata().isFromCache()
                          ? "This data was loaded from " + "offline cache"
                          : null);
              listener.resourceChanged(successRes);
              Timber.tag(TAG).i("Advice loading completed");
            });
  }

  private List<AdviceEntity> mapQuerySnapshotToAdviceList(QuerySnapshot qs) {
    return qs.toObjects(AdviceEntity.class);
  }
}
