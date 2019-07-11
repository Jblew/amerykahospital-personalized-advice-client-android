package pl.jblew.ahpaaclient.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

import pl.jblew.ahpaaclient.ui.activity.MainActivity;


@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector(modules = FragmentModule.class)
    abstract MainActivity contributeMainActivity();
}
