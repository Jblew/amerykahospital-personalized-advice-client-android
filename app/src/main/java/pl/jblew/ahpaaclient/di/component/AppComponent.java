package pl.jblew.ahpaaclient.di.component;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import pl.jblew.ahpaaclient.AppController;
import pl.jblew.ahpaaclient.di.module.MainActivityModule;
import dagger.BindsInstance;
import android.app.Application;

@Component(modules = {
        MainActivityModule.class,
        AndroidSupportInjectionModule.class})
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