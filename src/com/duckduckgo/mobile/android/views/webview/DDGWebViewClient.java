package com.duckduckgo.mobile.android.views.webview;

import com.duckduckgo.mobile.android.bus.BusProvider;
import com.duckduckgo.mobile.android.dialogs.SSLCertificateDialog;
import com.duckduckgo.mobile.android.events.searchBarEvents.SearchBarAddClearTextDrawable;
import com.duckduckgo.mobile.android.events.searchBarEvents.SearchBarClearEvent;
import com.duckduckgo.mobile.android.events.searchBarEvents.SearchBarSetTextEvent;
import com.duckduckgo.mobile.android.fragment.WebFragment;
import com.duckduckgo.mobile.android.util.DDGConstants;
import com.duckduckgo.mobile.android.util.DDGControlVar;
import com.duckduckgo.mobile.android.util.DDGUtils;
import com.duckduckgo.mobile.android.util.SESSIONTYPE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.MailTo;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

public class DDGWebViewClient extends WebViewClient {
  WebFragment fragment;
  private boolean mLoaded = false;

  public DDGWebViewClient(WebFragment fragment) {
    this.fragment = fragment;
  }

  private void clickedAnchorAction(DDGWebView view) {
    DDGControlVar.mDuckDuckGoContainer.sessionType = SESSIONTYPE.SESSION_BROWSE;
  }

  public boolean shouldOverrideUrlLoading(WebView view, String url) {
    // Log.i(TAG, "shouldOverrideUrl  " + url);

    if (!fragment.getSavedState() && mLoaded) {
      // handle mailto: and tel: links with native apps
      if (url.startsWith("mailto:")) {
        MailTo mt = MailTo.parse(url);
        Intent i = DDGUtils.newEmailIntent(mt.getTo(), mt.getSubject(), mt.getBody(), mt.getCc());
        fragment.startActivity(i);
        return true;
      } else if (url.startsWith("tel:")) {
        Intent i = DDGUtils.newTelIntent(url);
        fragment.startActivity(i);
        return true;
      } else if (url.startsWith("file:///android_asset/webkit/")) {
        return false;
      } else if (!(url.startsWith("http:") || url.startsWith("https:"))) {
        // custom handling, there can be a related app
        Intent customIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        DDGUtils.execIntentIfSafe(fragment.getActivity(), customIntent);
        return true;
      } else {
        clickedAnchorAction((DDGWebView) view);
        ((DDGWebView) view).setUserAgentString(url);
      }
    }
    return false;
  }

  @SuppressLint("NewApi")
  public void onPageStarted(WebView view, String url, Bitmap favicon) {
    super.onPageStarted(view, url, favicon);
    Log.e("aaa", "on page started: " + url);
    if (url.equals(DDGWebView.ABOUT_BLANK)) {
      //activity.clearSearchBar();//to delete
      BusProvider.getInstance().post(new SearchBarClearEvent());
      return;
    }
    mLoaded = false;
    //DDGControlVar.newPageLoading = true;
    view.getSettings().setDomStorageEnabled(true);
    view.getSettings().setPluginState(PluginState.ON_DEMAND);

    DDGWebView wv = ((DDGWebView) view);
    if (wv.loadingReadableBack) {
      wv.stopLoading();
      return;
    }

    if (url.equals(DDGControlVar.mDuckDuckGoContainer.lastFeedUrl)) {
      DDGControlVar.mDuckDuckGoContainer.sessionType = SESSIONTYPE.SESSION_FEED;
    }

    // Omnibar like behavior.
    if (url.contains("duckduckgo.com")) {

      view.getSettings().setSupportZoom(true);
      view.getSettings().setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
      view.getSettings().setBuiltInZoomControls(false);
      view.getSettings().setUseWideViewPort(false);
      view.getSettings().setLoadWithOverviewMode(false);

      view.getSettings().setPluginState(WebSettings.PluginState.ON);

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        view.getSettings().setEnableSmoothTransition(false);
        view.getSettings().setDisplayZoomControls(false);
      }

      URL fullURL = null;
      try {
        fullURL = new URL(url);
      } catch (MalformedURLException e) {
        e.printStackTrace();
      }

      if (fullURL != null) {
        //Okay, it's a valid url, which we already knew...

        // disambiguations appear directly as path string
        String path = fullURL.getPath();

        String query = fullURL.getQuery();
        if (query != null) {
          //Get the actual query string now...
          int index = query.indexOf("q=");
          if (index != -1) {
            String text = query.substring(query.indexOf("q=") + 2);
            if (text.contains("&")) {
              text = text.substring(0, text.indexOf("&"));
            }
            String realText = URLDecoder.decode(text);
            setSearchBarText(realText);
          } else if (path != null && !path.equals("/")) {
            String text = path.substring(path.lastIndexOf("/") + 1).replace("_", " ");
            setSearchBarText(text);
          } else {
            setSearchBarText(url);
          }
        } else {
          setSearchBarText(url);
        }
      } else {
        //Just in case...
        setSearchBarText(url);
      }
    } else {
      //This isn't duckduck go...
      //view.getSettings().setUserAgentString(activity.mWebViewDefaultUA);//not needed anymore
      // This is a bit strange, but necessary to load Twitter in the app
      //TODO: Figure out a better way, it has something to do with JS with errors
      if (url.contains("twitter.com") && Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
        view.getSettings().setUserAgentString(DDGConstants.USER_AGENT);
      }

      view.getSettings().setSupportZoom(true);
      view.getSettings().setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
      view.getSettings().setBuiltInZoomControls(true);
      view.getSettings().setUseWideViewPort(true);
      view.getSettings().setLoadWithOverviewMode(true);
      view.getSettings().setPluginState(WebSettings.PluginState.ON);

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        view.getSettings().setEnableSmoothTransition(true);
        view.getSettings().setDisplayZoomControls(false);
      }
      setSearchBarText(url);
    }
  }

  public void onPageFinished(WebView view, String url) {
    super.onPageFinished(view, url);
    Log.e("aaa", "on page finished: " + url);
    mLoaded = true;

    DDGControlVar.mCleanSearchBar = false;

    if (view.getVisibility() != View.VISIBLE) {
      return;
    }

    //((DuckDuckGo)activity).getSearchField().setBackgroundDrawable(DDGControlVar.mDuckDuckGoContainer.searchFieldDrawable);//aaa
    BusProvider.getInstance().post(new SearchBarAddClearTextDrawable());

    //		// This makes a little (X) to clear the search bar.
    //		mDuckDuckGoContainer.reloadDrawable.setBounds(0, 0, (int)Math.floor(mDuckDuckGoContainer.reloadDrawable.getIntrinsicWidth()/1.5), (int)Math.floor(mDuckDuckGoContainer.reloadDrawable.getIntrinsicHeight()/1.5));
    //        searchField.setCompoundDrawables(null, null, mDuckDuckGoContainer.reloadDrawable, null);

    DDGWebView wv = ((DDGWebView) view);

    if (wv.readableBackState) {
      wv.readableBackState = false;
      if (wv.canGoBack()) {
        wv.loadingReadableBack = true;
        wv.goBack();
      } else {
        wv.shouldClearHistory = true;
        wv.readableAction(DDGControlVar.currentFeedObject);
      }
    } else if (wv.loadingReadableBack) {
      wv.loadingReadableBack = false;
      wv.readableAction(DDGControlVar.currentFeedObject);
    }

    if (wv.shouldClearHistory) {
      wv.clearHistory();
      wv.shouldClearHistory = false;
    }
    wv.requestFocus();
  }

  @Override
  public void doUpdateVisitedHistory(WebView view, String url,
      boolean isReload) {
    DDGWebView wv = ((DDGWebView) view);
    if (wv.shouldClearHistory) {
      wv.clearHistory();
    }
    super.doUpdateVisitedHistory(view, url, isReload);
  }

  @Override
  public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
    if (Build.VERSION.SDK_INT == Build.VERSION_CODES.FROYO) {
      new SSLCertificateDialog(view.getContext(), handler, error).show();
      return;
    }
    super.onReceivedSslError(view, handler, error);
  }

  private void setSearchBarText(String text) {
    BusProvider.getInstance().post(new SearchBarSetTextEvent(text));
  }
}
