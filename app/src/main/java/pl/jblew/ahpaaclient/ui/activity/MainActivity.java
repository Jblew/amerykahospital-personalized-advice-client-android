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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.Arrays;

import dagger.android.support.DaggerAppCompatActivity;
import pl.jblew.ahpaaclient.R;
import pl.jblew.ahpaaclient.adapter.DynamicLinkAdapter;
import pl.jblew.ahpaaclient.data.AdviceToImportHolder;
import timber.log.Timber;

import javax.inject.Inject;

public class MainActivity extends DaggerAppCompatActivity {
  private static String ACTION_SIGN_OUT = MainActivity.class.getName() + ".ACTION_SIGN_OUT";
  private static final String TAG = "MainActivity";
  private static final int RC_SIGN_IN = 9410;

  private View rootLayout;
  private TextView msgText;
  private Button loginBtn;
  
  @Inject
  public DynamicLinkAdapter dynamicLinkAdapter;
  
  @Inject
  public MainActivity() {}
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setTheme(R.style.AppTheme);
    setContentView(R.layout.activity_main);
    findViewItems();
    createView();

    processDynamicDeepLinks();
    processIntentOrLogin();
  }

  private void createView() {
    loginBtn.setOnClickListener(
        evt -> {
          goToLoginActivity();
        });
  }

  private void findViewItems() {
    rootLayout = findViewById(R.id.ac_main_root_layout);
    msgText = findViewById(R.id.ac_main_msg_text);
    loginBtn = findViewById(R.id.ac_main_login_btn);
  }
  
  private void processDynamicDeepLinks() {
    dynamicLinkAdapter.processDynamicDeepLink(
        this,
        getIntent(),
        (res) -> {
          if (res.isSuccess()) handleDeepLink(res.data);
          else showDeepLinkError(res.message);
        });
  }
  
  public void handleDeepLink(DynamicLinkAdapter.DynamicLinkResult dynamicLinkResult) {
    if (dynamicLinkResult instanceof DynamicLinkAdapter.AdviceIdDynamicLink) {
      
      String adviceId = ((DynamicLinkAdapter.AdviceIdDynamicLink) dynamicLinkResult).adviceId;
      AdviceToImportHolder.setAdviceId(this, adviceId);
      
    } else showDeepLinkError("Unrecognized type of dynamic link");
  }
  
  private void showDeepLinkError(String message) {
    Timber.tag(TAG).e("Deep link error: " + message);
    Snackbar.make(rootLayout,
        "Could not handle deep link: " + message,
        Snackbar.LENGTH_LONG);
  }

  private void processIntentOrLogin() {
    Intent intent = getIntent();

    if (intent.getAction() == ACTION_SIGN_OUT) {
      performSignOut();
    } else {
      performLogin();
    }
  }

  private void performLogin() {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    // auth.addAuthStateListener();
    FirebaseUser user = auth.getCurrentUser();
    if (user != null) {
      showLoggedInUi(user);
      goToRootActivity(user);
    } else {
      showNotLoggedInUi();
      goToLoginActivity();
    }
  }

  private void showLoggedInUi(FirebaseUser user) {
    msgText.setText("Zalogowany jako " + user.getDisplayName());
    loginBtn.setVisibility(View.GONE);
  }

  private void showNotLoggedInUi() {
    msgText.setText(R.string.not_logged_in_msg);
    loginBtn.setVisibility(View.VISIBLE);
  }

  private void showMsg(int stringId) {
    msgText.setText(stringId);
    Snackbar.make(rootLayout, stringId, Snackbar.LENGTH_LONG).show();
  }
  
  private void goToLoginActivity() {
    startActivityForResult(
        // Get an instance of AuthUI based on the default app
        AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(
                Arrays.asList(
                    new AuthUI.IdpConfig.GoogleBuilder().build(),
                    new AuthUI.IdpConfig.EmailBuilder().build()))
            .build(),
        RC_SIGN_IN);
  }

  private void performSignOut() {
    AuthUI.getInstance()
        .signOut(this)
        .addOnCompleteListener(
            (Task<Void> task) -> {
              showNotLoggedInUi();
            });
  }

  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the
    // sign in flow.
    if (requestCode == RC_SIGN_IN) {
      IdpResponse response = IdpResponse.fromResultIntent(data);

      // Successfully signed in
      if (resultCode == RESULT_OK) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        showLoggedInUi(currentUser);
        goToRootActivity(currentUser);
        finish();
      } else {
        showNotLoggedInUi();

        // Sign in failed
        if (response == null) {
          // User pressed back button
          showMsg(R.string.sign_in_cancelled);
          return;
        }

        if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
          showMsg(R.string.no_internet_connection);
          return;
        }

        showMsg(R.string.unknown_error);
        Timber.tag(TAG).e(response.getError(), "Sign-in error: ");
      }
    }
  }

  private void goToRootActivity(FirebaseUser user) {
    startActivity(RootActivity.createSignedInIntent(this, user));
    finish();
  }

  public static Intent createSingOutIntent(RootActivity rootActivity) {
    Intent intent = new Intent(rootActivity, MainActivity.class);
    intent.setAction(ACTION_SIGN_OUT);
    return intent;
  }
}
