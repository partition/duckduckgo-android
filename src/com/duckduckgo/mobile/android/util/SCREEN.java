package com.duckduckgo.mobile.android.util;

public enum SCREEN {
  SCR_STORIES(0), SCR_RECENTS(1), SCR_FAVORITE(2), SCR_SEARCH_HOME_PAGE(3), SCR_WEBVIEW(4), SCR_ABOUT(5), SCR_HELP(6), SCR_SETTINGS(7), SCR_SEARCH(8), SCR_SOURCES(9);

  private int code;

  private SCREEN(int c) {
    code = c;
  }

  public static SCREEN getByCode(int code) {
    switch (code) {
    case 0:
      return SCR_STORIES;
    case 1:
      return SCR_RECENTS;
    case 2:
      return SCR_FAVORITE;
    case 3:
      return SCR_SEARCH_HOME_PAGE;
    case 4:
      return SCR_WEBVIEW;
    case 5:
      return SCR_ABOUT;
    case 6:
      return SCR_HELP;
    case 7:
      return SCR_SETTINGS;
    case 8:
      return SCR_SEARCH;
    case 9:
      return SCR_SOURCES;
    default:
      return SCR_STORIES;
    }
  }

  public int getCode() {
    return code;
  }
/*
    public SCREEN getByTag(String tag) {
        if(tag.equals(FeedFragment.TAG)) {
            return SCR_STORIES;
        } else if(false) {
            return SCR_RECENTS;
        } else if(tag.equals(WebFragment.TAG)) {
            return SCR_WEBVIEW;
        }
    }
*/
  /**
   * method to get associated ViewFlipper order in main.xml
   * @return
   *//*aaa
  public int getFlipOrder() {
		switch(this) {
			case SCR_WEBVIEW:
				return 0;
			case SCR_STORIES:
				return 1;
			case SCR_RECENT_SEARCH:
				return 2;
			case SCR_DUCKMODE:
				return 3;
			case SCR_SAVED_FEED:
				return 4;
			default:
				return 1;
		}
	}*/
}