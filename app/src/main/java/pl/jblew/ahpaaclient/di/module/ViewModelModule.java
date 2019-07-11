package pl.jblew.ahpaaclient.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import pl.jblew.ahpaaclient.di.ViewModelKey;
import pl.jblew.ahpaaclient.factory.ViewModelFactory;
import pl.jblew.ahpaaclient.viewmodel.AdviceListViewModel;

@Module
public abstract class ViewModelModule {

  @Binds
  abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

  /*
   * This method basically says
   * inject this object into a Map using the @IntoMap annotation,
   * with the  MovieListViewModel.class as key,
   * and a Provider that will build a MovieListViewModel
   * object.
   *
   * */

  @Binds
  @IntoMap
  @ViewModelKey(AdviceListViewModel.class)
  protected abstract ViewModel adviceListViewModel(AdviceListViewModel adviceListViewModel);
}
