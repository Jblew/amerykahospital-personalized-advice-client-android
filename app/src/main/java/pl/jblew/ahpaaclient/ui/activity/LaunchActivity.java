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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.Arrays;
import pl.jblew.ahpaaclient.R;

public class LaunchActivity extends AppCompatActivity {
  private static String ACTION_SIGN_OUT = LaunchActivity.class.getName() + ".ACTION_SIGN_OUT";
  private static final String TAG = "LaunchActivity";
  private static final int RC_SIGN_IN = 9410;

  private View rootLayout;
  private TextView msgText;
  private Button loginBtn;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setTheme(R.style.AppTheme);
    setContentView(R.layout.activity_launch);
    findViewItems();
    createView();

    processIntentOrLogin();
  }

  private void createView() {
    loginBtn.setOnClickListener(
        evt -> {
          goToLoginActivity();
        });
  }

  private void findViewItems() {
    rootLayout = findViewById(R.id.ac_launch_root_layout);
    msgText = findViewById(R.id.ac_launch_msg_text);
    loginBtn = findViewById(R.id.ac_launch_login_btn);
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

  private void processIntentOrLogin() {
    Intent intent = getIntent();
    Log.i(TAG, "Processing intent. Action = " + intent.getAction());

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
      goToMainActivity(user);
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

  private void performSignOut() {
    Log.i(TAG, "Performing sign out");

    AuthUI.getInstance()
        .signOut(this)
        .addOnCompleteListener(
            (Task<Void> task) -> {
              showNotLoggedInUi();
            });
  }

  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    Log.i(TAG, "onActivityResult ");
    super.onActivityResult(requestCode, resultCode, data);
    // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the
    // sign in flow.
    if (requestCode == RC_SIGN_IN) {
      IdpResponse response = IdpResponse.fromResultIntent(data);

      // Successfully signed in
      if (resultCode == RESULT_OK) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        showLoggedInUi(currentUser);
        goToMainActivity(currentUser);
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
        Log.e(TAG, "Sign-in error: ", response.getError());
      }
    }
  }

  private void goToMainActivity(FirebaseUser user) {
    startActivity(MainActivity.createSignedInIntent(this, user));
    finish();
  }

  public static Intent createSingOutIntent(MainActivity mainActivity) {
    Intent intent = new Intent(mainActivity, LaunchActivity.class);
    intent.setAction(ACTION_SIGN_OUT);
    return intent;
  }
}
