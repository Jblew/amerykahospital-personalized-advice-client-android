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

package pl.jblew.ahpaaclient.di.util;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import android.content.Context;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;
import com.google.errorprone.annotations.ForOverride;
import dagger.android.*;
import javax.inject.Inject;

/**
 * Ported from
 * https://raw.githubusercontent.com/google/dagger/dagger-2.22/java/dagger/android/DaggerApplication.java
 * An {@link Application} that injects its members and can be used to inject {@link Activity}s,
 * {@link Fragment}s, {@link Service}s, {@link BroadcastReceiver}s and {@link ContentProvider}s
 * attached to it. Injection is performed in {@link #onCreate()} or the first call to {@link
 * AndroidInjection#inject(ContentProvider)}, whichever happens first.
 */
public abstract class DaggerMultiDexApplication extends MultiDexApplication
    implements HasActivityInjector,
        HasFragmentInjector,
        HasServiceInjector,
        HasBroadcastReceiverInjector,
        HasContentProviderInjector {

  @Inject DispatchingAndroidInjector<Activity> activityInjector;
  @Inject DispatchingAndroidInjector<BroadcastReceiver> broadcastReceiverInjector;
  @Inject DispatchingAndroidInjector<Fragment> fragmentInjector;
  @Inject DispatchingAndroidInjector<Service> serviceInjector;
  @Inject DispatchingAndroidInjector<ContentProvider> contentProviderInjector;
  private volatile boolean needToInject = true;

  @Override
  protected void attachBaseContext(Context context) {
    super.attachBaseContext(context);
    MultiDex.install(this);
  }

  @Override
  public void onCreate() {
    super.onCreate();
    injectIfNecessary();
  }

  /**
   * Implementations should return an {@link AndroidInjector} for the concrete {@link
   * DaggerApplication}. Typically, that injector is a {@link dagger.Component}.
   */
  @ForOverride
  protected abstract AndroidInjector<? extends DaggerMultiDexApplication> applicationInjector();

  /**
   * Lazily injects the {@link DaggerApplication}'s members. Injection cannot be performed in {@link
   * Application#onCreate()} since {@link android.content.ContentProvider}s' {@link
   * android.content.ContentProvider#onCreate() onCreate()} method will be called first and might
   * need injected members on the application. Injection is not performed in the constructor, as
   * that may result in members-injection methods being called before the constructor has completed,
   * allowing for a partially-constructed instance to escape.
   */
  private void injectIfNecessary() {
    if (needToInject) {
      synchronized (this) {
        if (needToInject) {
          @SuppressWarnings("unchecked")
          AndroidInjector<DaggerMultiDexApplication> applicationInjector =
              (AndroidInjector<DaggerMultiDexApplication>) applicationInjector();
          applicationInjector.inject(this);
          if (needToInject) {
            throw new IllegalStateException(
                "The AndroidInjector returned from applicationInjector() did not inject the "
                    + "DaggerApplication");
          }
        }
      }
    }
  }

  @Inject
  void setInjected() {
    needToInject = false;
  }

  @Override
  public DispatchingAndroidInjector<Activity> activityInjector() {
    return activityInjector;
  }

  @Override
  public DispatchingAndroidInjector<Fragment> fragmentInjector() {
    return fragmentInjector;
  }

  @Override
  public DispatchingAndroidInjector<BroadcastReceiver> broadcastReceiverInjector() {
    return broadcastReceiverInjector;
  }

  @Override
  public DispatchingAndroidInjector<Service> serviceInjector() {
    return serviceInjector;
  }

  // injectIfNecessary is called here but not on the other *Injector() methods because it is the
  // only one that should be called (in AndroidInjection.inject(ContentProvider)) before
  // Application.onCreate()
  @Override
  public AndroidInjector<ContentProvider> contentProviderInjector() {
    injectIfNecessary();
    return contentProviderInjector;
  }
}
