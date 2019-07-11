package pl.jblew.ahpaaclient;

import android.app.Activity;
import android.app.Application;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import javax.inject.Inject;
import pl.jblew.ahpaaclient.di.component.DaggerAppComponent;

public class AppController extends Application implements HasActivityInjector {

  @Inject DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

  @Override
  public DispatchingAndroidInjector<Activity> activityInjector() {
    return dispatchingAndroidInjector;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    DaggerAppComponent.builder().application(this).build().inject(this);
  }
}
