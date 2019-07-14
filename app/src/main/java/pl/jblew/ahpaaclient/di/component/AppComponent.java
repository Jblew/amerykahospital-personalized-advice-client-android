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
