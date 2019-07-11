package pl.jblew.ahpaaclient.di.component;

import android.app.Application;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import javax.inject.Singleton;
import pl.jblew.ahpaaclient.AppController;
import pl.jblew.ahpaaclient.di.module.ActivityModule;
import pl.jblew.ahpaaclient.di.module.FragmentModule;
import pl.jblew.ahpaaclient.di.module.ViewModelModule;

@Component(
    modules = {
      ActivityModule.class,
      AndroidSupportInjectionModule.class,
      ViewModelModule.class,
      FragmentModule.class
    })
@Singleton
public interface AppComponent {

  /* We will call this builder interface from our custom Application class.
   * This will set our application object to the AppComponent.
   * So inside the AppComponent the application instance is available.
   * So this application instance can be accessed by our modules
   * such as ApiModule when needed
   * */
  @Component.Builder
  interface Builder {

    @BindsInstance
    Builder application(Application application);

    AppComponent build();
  }

  /*
   * This is our custom Application class
   * */
  void inject(AppController appController);
}
