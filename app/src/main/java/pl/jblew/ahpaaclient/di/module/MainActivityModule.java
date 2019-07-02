package pl.jblew.ahpaaclient.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

import pl.jblew.ahpaaclient.MainActivity;


@Module
public abstract class MainActivityModule {

    @ContributesAndroidInjector()
    abstract MainActivity contributeMainActivity();
}