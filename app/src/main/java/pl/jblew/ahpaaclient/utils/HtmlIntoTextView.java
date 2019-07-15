package pl.jblew.ahpaaclient.utils;

import android.os.Build;
import android.text.Html;
import android.widget.TextView;
import pl.jblew.ahpaaclient.R;

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
