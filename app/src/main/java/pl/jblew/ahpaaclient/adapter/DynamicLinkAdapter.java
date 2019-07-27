package pl.jblew.ahpaaclient.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import pl.jblew.ahpaaclient.data.Resource;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import timber.log.Timber;

@Singleton
public class DynamicLinkAdapter {
  private static final String TAG = "DynamicLinkAdapter";
  private static final String MATCH_HOST = "aplikacja.ameryka.com.pl";
  private static final String MATCH_FIRST_SEGMENT = "advice";
  
  @Inject
  public DynamicLinkAdapter() {
  }
  
  public void processDynamicDeepLink(Activity activity, Intent intent, Resource.Listener<DynamicLinkResult> resListener) {
    FirebaseDynamicLinks.getInstance()
        .getDynamicLink(intent)
        .addOnSuccessListener(activity, pendingDynamicLinkData -> {
          Uri deepLink = null;
          if (pendingDynamicLinkData != null) {
            deepLink = pendingDynamicLinkData.getLink();
          }
          handleUri(deepLink, resListener);
        })
        .addOnFailureListener(activity, e -> resListener.resourceChanged(Resource.error(e.getMessage(), null)));
  }
  
  private void handleUri(Uri uri, Resource.Listener<DynamicLinkResult> resListener) {
    try {
      if (uri == null) throw new DeepLinkAdapterException("Nul Uri");
      DynamicLinkResult processResult = processUri(uri);
      resListener.resourceChanged(Resource.success(processResult));
    } catch (Exception e) {
      Timber.tag(TAG).e(e,  "Error while handling deep link uri (" + uri + "): " + e.getMessage());
      resListener.resourceChanged(Resource.error(e.getMessage(), null));
    }
  }
  
  private DynamicLinkResult processUri(Uri uri) {
    if (!uri.getHost().equals(MATCH_HOST))
      throw new DeepLinkAdapterException("Unrecognized deep link host " + uri.getHost());
    List<String> segments = uri.getPathSegments();
    if (segments.size() != 2) throw new DeepLinkAdapterException("Wrong number of segments in deep link " + uri);
    if (!segments.get(0).equals(MATCH_FIRST_SEGMENT))
      throw new DeepLinkAdapterException("Unrecognized deep link first segment " + segments.get(0));
    
    String adviceId = segments.get(1);
    return new AdviceIdDynamicLink(adviceId);
  }
  
  public interface DynamicLinkResult {
  }
  
  public class AdviceIdDynamicLink implements DynamicLinkResult {
    public final String adviceId;
    
    AdviceIdDynamicLink(String adviceId) {
      this.adviceId = adviceId;
    }
  }
  
  public class DeepLinkAdapterException extends RuntimeException {
    public DeepLinkAdapterException(String msg) {
      super(msg);
    }
  }
}
