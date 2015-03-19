package com.duckduckgo.mobile.android.util;

import com.duckduckgo.mobile.android.container.DuckDuckGoContainer;
import com.duckduckgo.mobile.android.objects.FeedObject;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;

public class AppStateManager {
  public static void saveAppState(SharedPreferences prefs, DuckDuckGoContainer duckDuckGoContainer,
      FeedObject currentFeedObject) {
    Editor editor = prefs.edit();
    editor.putBoolean("homeScreenShowing", DDGControlVar.homeScreenShowing);
    editor.putBoolean("webviewShowing", duckDuckGoContainer.webviewShowing);
    editor.putInt("currentScreen", duckDuckGoContainer.currentScreen.ordinal());
    editor.putInt("prevScreen", duckDuckGoContainer.prevScreen.ordinal());
    editor.putInt("sessionType", duckDuckGoContainer.sessionType.ordinal());
    editor.putString("currentFragmentTag", duckDuckGoContainer.currentFragmentTag);
    editor.putString("currentUrl", duckDuckGoContainer.currentUrl);
    if (currentFeedObject != null) {
      editor.putString("currentFeedObjectId", currentFeedObject.getId());
    }
    editor.commit();
  }

  public static void saveAppState(Bundle bundle, DuckDuckGoContainer duckDuckGoContainer,
      FeedObject currentFeedObject) {
    bundle.putBoolean("homeScreenShowing", DDGControlVar.homeScreenShowing);
    bundle.putBoolean("webviewShowing", duckDuckGoContainer.webviewShowing);
    bundle.putInt("currentScreen", duckDuckGoContainer.currentScreen.ordinal());
    bundle.putInt("prevScreen", duckDuckGoContainer.prevScreen.ordinal());
    bundle.putInt("sessionType", duckDuckGoContainer.sessionType.ordinal());
    bundle.putString("currentFragmentTag", duckDuckGoContainer.currentFragmentTag);
    bundle.putString("currentUrl", duckDuckGoContainer.currentUrl);
    if (currentFeedObject != null) {
      bundle.putString("currentFeedObjectId", currentFeedObject.getId());
    }
  }

  public static void recoverAppState(Object state, DuckDuckGoContainer duckDuckGoContainer,
      FeedObject currentFeedObject) {
    Bundle bundle = null;
    SharedPreferences prefs = null;

    if (state instanceof Bundle) {
      bundle = (Bundle) state;

      DDGControlVar.homeScreenShowing = bundle.getBoolean("homeScreenShowing");
      duckDuckGoContainer.webviewShowing = bundle.getBoolean("webviewShowing");
      duckDuckGoContainer.currentScreen = SCREEN.getByCode(bundle.getInt("currentScreen"));
      duckDuckGoContainer.prevScreen = SCREEN.getByCode(bundle.getInt("prevScreen"));
      duckDuckGoContainer.sessionType = SESSIONTYPE.getByCode(bundle.getInt("sessionType"));
      duckDuckGoContainer.currentFragmentTag = bundle.getString("currentFragmentTag");
      duckDuckGoContainer.currentUrl = bundle.getString("currentUrl");
    }
    // do we ever get here?
    else if (state instanceof SharedPreferences) {
      prefs = (SharedPreferences) state;

      DDGControlVar.homeScreenShowing = prefs.getBoolean("homeScreenShowing", false);
      duckDuckGoContainer.webviewShowing = prefs.getBoolean("webviewShowing", false);
      duckDuckGoContainer.currentScreen = SCREEN.getByCode(prefs.getInt("currentScreen", SCREEN.SCR_STORIES.getCode()));
      duckDuckGoContainer.prevScreen = SCREEN.getByCode(prefs.getInt("prevScreen", SCREEN.SCR_STORIES.getCode()));
      duckDuckGoContainer.sessionType = SESSIONTYPE.getByCode(prefs.getInt("sessionType", SESSIONTYPE.SESSION_BROWSE.getCode()));
      duckDuckGoContainer.currentFragmentTag = prefs.getString("currentFragmentTag", "");
      duckDuckGoContainer.currentUrl = prefs.getString("currentUrl", "");
    }
  }

  public static String getCurrentFeedObjectId(Object state) {
    if (state instanceof Bundle) {
      return ((Bundle) state).getString("currentFeedObjectId");
    } else if (state instanceof SharedPreferences) {
      return ((SharedPreferences) state).getString("currentFeedObjectId", null);
    }
    return "";
  }
}
