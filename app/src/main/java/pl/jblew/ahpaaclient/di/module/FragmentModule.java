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

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import pl.jblew.ahpaaclient.ui.advicelist.AdviceListFragment;
import pl.jblew.ahpaaclient.ui.importadvice.ImportAdviceFragment;

@Module
public abstract class FragmentModule {
  @ContributesAndroidInjector
  abstract AdviceListFragment contributeAdviceListFragment();

  @ContributesAndroidInjector
  abstract ImportAdviceFragment contributeImportAdviceFragment();
}
