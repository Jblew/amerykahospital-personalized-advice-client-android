package pl.jblew.ahpaaclient.data;

import android.content.Context;
import android.content.SharedPreferences;
import timber.log.Timber;

import pl.jblew.ahpaaclient.R;

public class AdviceToImportHolder {
  private static final String TAG = "AdviceToImportHolder";
  private static final String KEY_ADVICEID = "ADVICE_ID";
  
  
  private AdviceToImportHolder() {}
  
  public static void setAdviceId(Context context, String adviceId) {
    SharedPreferences pref = getPref(context);
    pref.edit().putString(KEY_ADVICEID, adviceId).apply();
    Timber.tag(TAG).i("Written " + adviceId + " to " + KEY_ADVICEID);
  }
  
  public static void clearAdviceId(Context context) {
    SharedPreferences pref = getPref(context);
    pref.edit().remove(KEY_ADVICEID).apply();
    Timber.tag(TAG).i("Removed from " + KEY_ADVICEID);
  }
  
  public static String getAdviceIdOrNull(Context context) {
    SharedPreferences pref = getPref(context);
    if(!pref.contains(KEY_ADVICEID)) return null;
    else {
      String gotAdviceId = pref.getString(KEY_ADVICEID, null);
      Timber.tag(TAG).i("Read adviceId " + gotAdviceId);
      return gotAdviceId;
    }
  }
  
  private static SharedPreferences getPref(Context context) {
    return context.getSharedPreferences(context.getString(R.string.sharedstorage_key_advice_to_be_imported), Context.MODE_PRIVATE);
  }
}
