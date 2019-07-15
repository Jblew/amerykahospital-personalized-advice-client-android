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

package pl.jblew.ahpaaclient.utils;

import android.os.Build;
import android.text.Html;
import android.widget.TextView;

public class HtmlIntoTextView {
  private HtmlIntoTextView() {}

  public static void insertHtmlIntoTextView(TextView textView, String htmlString) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      textView.setText(Html.fromHtml(htmlString, Html.FROM_HTML_MODE_COMPACT));
    } else {
      textView.setText(Html.fromHtml(htmlString));
    }
  }
}
