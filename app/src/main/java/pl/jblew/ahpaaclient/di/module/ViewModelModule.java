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

package pl.jblew.ahpaaclient.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import pl.jblew.ahpaaclient.di.ViewModelKey;
import pl.jblew.ahpaaclient.factory.ViewModelFactory;
import pl.jblew.ahpaaclient.viewmodel.AdviceListViewModel;
import pl.jblew.ahpaaclient.viewmodel.ImportAdviceViewModel;

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

  @Binds
  @IntoMap
  @ViewModelKey(ImportAdviceViewModel.class)
  protected abstract ViewModel importAdviceViewModel(ImportAdviceViewModel importAdviceViewModel);
}
