package pl.jblew.ahpaaclient.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

import pl.jblew.ahpaaclient.R;

public class LaunchActivity extends AppCompatActivity {
	private static final int RC_SIGN_IN = 9410;

	private TextView msgText;
	private Button loginBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launch);
		findViewItems();
		createView();

		processAuth();
	}

	private void createView() {
		loginBtn.setOnClickListener(evt -> {
			goToLoginActivity();
		});
	}

	private void findViewItems() {
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



	private void processAuth() {
		FirebaseAuth auth = FirebaseAuth.getInstance();
		// auth.addAuthStateListener();
		FirebaseUser user = auth.getCurrentUser();
		if (user != null) {
			msgText.setText("Zalogowany jako " + user.getDisplayName());
			loginBtn.setVisibility(View.GONE);
		}
		else {
			msgText.setText(R.string.not_logged_in_msg);
			loginBtn.setVisibility(View.VISIBLE);
			goToLoginActivity();
		}
	}
}
