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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Resource<T> {
  @NonNull public final Status status;
  @Nullable public final T data;
  @Nullable public final String message;

  private Resource(@NonNull Status status, @Nullable T data, @Nullable String message) {
    this.status = status;
    this.data = data;
    this.message = message;
  }

  public static <T> Resource<T> success(@NonNull T data) {
    return new Resource<>(Status.SUCCESS, data, null);
  }

  public static <T> Resource<T> success(@NonNull T data, String message) {
    return new Resource<>(Status.SUCCESS, data, message);
  }

  public static <T> Resource<T> error(String msg, @Nullable T data) {
    return new Resource<>(Status.ERROR, data, msg);
  }

  public static <T> Resource<T> loading(@Nullable T data) {
    return new Resource<>(Status.LOADING, data, null);
  }

  public boolean isSuccess() {
    return status == Status.SUCCESS && data != null;
  }

  public boolean isLoading() {
    return status == Status.LOADING;
  }

  public boolean isLoaded() {
    return status != Status.LOADING;
  }

  public boolean isError() {
    return status == Status.ERROR;
  }

  public enum Status {
    LOADING,
    SUCCESS,
    ERROR
  }
}
