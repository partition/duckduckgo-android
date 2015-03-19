package com.duckduckgo.mobile.android.container;

import com.duckduckgo.mobile.android.adapters.AutoCompleteResultsAdapter;
import com.duckduckgo.mobile.android.adapters.MultiHistoryAdapter;
import com.duckduckgo.mobile.android.adapters.RecentResultCursorAdapter;
import com.duckduckgo.mobile.android.adapters.TempAutoCompleteResultsAdapter;
import com.duckduckgo.mobile.android.util.SCREEN;
import com.duckduckgo.mobile.android.util.SESSIONTYPE;

import android.graphics.drawable.Drawable;

public class DuckDuckGoContainer {

  public boolean webviewShowing = false;

  public SESSIONTYPE sessionType = SESSIONTYPE.SESSION_BROWSE;
  public String lastFeedUrl = "";
  public String currentFragmentTag = "";
  public String currentUrl = "";

  public SCREEN currentScreen = SCREEN.SCR_STORIES;
  public SCREEN prevScreen = SCREEN.SCR_STORIES;

  public Drawable progressDrawable, searchFieldDrawable;
  public Drawable stopDrawable;

  public MultiHistoryAdapter historyAdapter = null;

  //public MainFeedAdapter feedAdapter = null;
  //public MainFeedTask mainFeedTask = null;

  public AutoCompleteResultsAdapter acAdapter = null;
  public TempAutoCompleteResultsAdapter tempAdapter = null;
  public RecentResultCursorAdapter recentResultCursorAdapter = null;
}
