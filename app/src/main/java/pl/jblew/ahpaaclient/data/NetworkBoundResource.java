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

package pl.jblew.ahpaaclient.data;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public abstract class NetworkBoundResource<ResultType, RequestType> {

  private Observable<Resource<ResultType>> result;

  @MainThread
  protected NetworkBoundResource() {
    Observable<Resource<ResultType>> source;
    if (shouldFetch()) {
      source =
          createCall()
              .subscribeOn(Schedulers.io())
              .doOnNext(apiResponse -> saveCallResult(processResponse(apiResponse)))
              .flatMap(apiResponse -> loadFromDb().toObservable().map(Resource::success))
              .doOnError(t -> onFetchFailed())
              .onErrorResumeNext(
                  t -> {
                    return loadFromDb()
                        .toObservable()
                        .map(data -> Resource.error(t.getMessage(), data));
                  })
              .observeOn(AndroidSchedulers.mainThread());
    } else {
      source = loadFromDb().toObservable().map(Resource::success);
    }

    result = Observable.concat(loadFromDb().toObservable().map(Resource::loading).take(1), source);
  }

  public Observable<Resource<ResultType>> getAsObservable() {
    return result;
  }

  protected void onFetchFailed() {}

  @WorkerThread
  protected RequestType processResponse(Resource<RequestType> response) {
    return response.data;
  }

  @WorkerThread
  protected abstract void saveCallResult(@NonNull RequestType item);

  @MainThread
  protected abstract boolean shouldFetch();

  @NonNull
  @MainThread
  protected abstract Flowable<ResultType> loadFromDb();

  @NonNull
  @MainThread
  protected abstract Observable<Resource<RequestType>> createCall();
}
