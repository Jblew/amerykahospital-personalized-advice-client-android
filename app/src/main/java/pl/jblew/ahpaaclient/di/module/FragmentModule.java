package pl.jblew.ahpaaclient.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import pl.jblew.ahpaaclient.ui.advicelist.AdviceListFragment;

@Module
public abstract class FragmentModule {
	@ContributesAndroidInjector
	abstract AdviceListFragment contributeAdviceListFragment();
}
