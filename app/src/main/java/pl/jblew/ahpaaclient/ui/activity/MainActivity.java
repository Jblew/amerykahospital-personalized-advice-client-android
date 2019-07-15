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

package pl.jblew.ahpaaclient.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseUser;
import dagger.android.support.DaggerAppCompatActivity;
import dagger.android.support.HasSupportFragmentInjector;
import pl.jblew.ahpaaclient.R;
import pl.jblew.ahpaaclient.data.model.AdviceEntity;
import pl.jblew.ahpaaclient.ui.about.AboutAppFragment;
import pl.jblew.ahpaaclient.ui.about.AboutHospitalFragment;
import pl.jblew.ahpaaclient.ui.advicelist.AdviceListFragment;
import pl.jblew.ahpaaclient.ui.importadvice.ImportAdviceFragment;

public class MainActivity extends DaggerAppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener,
        HasSupportFragmentInjector,
        AdviceListFragment.OnListFragmentInteractionListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    drawView();
    changeFragment(new AdviceListFragment());
  }

  private void drawView() {
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    NavigationView navigationView = findViewById(R.id.nav_view);
    ActionBarDrawerToggle toggle =
        new ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close) {};

    drawer.addDrawerListener(toggle);
    toggle.syncState();
    navigationView.setNavigationItemSelectedListener(this);
  }

  private void changeFragment(Fragment nextFragment) {
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    ft.replace(R.id.ac_main_content_frame, nextFragment);
    ft.commit();
  }

  @Override
  public void onBackPressed() {
    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();

    if (id == R.id.nav_logout) {
      startActivity(LaunchActivity.createSingOutIntent(this));
      finish();
    } else if (id == R.id.nav_advices) {
      changeFragment(new AdviceListFragment());
    } else if (id == R.id.nav_import_advice) {
      changeFragment(new ImportAdviceFragment());
    } else if (id == R.id.nav_about_hospital) {
      changeFragment(new AboutHospitalFragment());
    } else if (id == R.id.nav_about_app) {
      changeFragment(new AboutAppFragment());
    }

    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }

  @Override
  public void onListFragmentInteraction(AdviceEntity item) {}

  public static Intent createSignedInIntent(LaunchActivity launchActivity, FirebaseUser user) {
    Intent intent = new Intent(launchActivity, MainActivity.class);
    return intent;
  }
}
