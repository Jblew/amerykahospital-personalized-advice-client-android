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

package pl.jblew.ahpaaclient.ui.supplementaryfragments;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import pl.jblew.ahpaaclient.R;

public class DrawerHeaderFragment extends Fragment {
  private static final String TAG = "DrawerHeaderFragment";

  public DrawerHeaderFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_drawer_header, container, false);

    populateUserSections(view);

    return view;
  }

  private void populateUserSections(View view) {
    ImageView profilePhotoImage = view.findViewById(R.id.userProfilePhotoImage);
    TextView userDisplayNameText = view.findViewById(R.id.userDisplayNameText);
    TextView userEmailText = view.findViewById(R.id.userEmailText);

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    if (user != null) {
      userDisplayNameText.setText(user.getDisplayName());
      userEmailText.setText(user.getEmail());

      Uri profilePhotoUri = user.getPhotoUrl();
      if (profilePhotoUri != null) {
        Picasso.get()
            .load(profilePhotoUri)
            .placeholder(R.drawable.ic_account_circle_white_36dp)
            .into(profilePhotoImage);
      }
    }
  }
}
