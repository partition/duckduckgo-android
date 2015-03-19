package com.duckduckgo.mobile.android.events.WebViewEvents;

import com.duckduckgo.mobile.android.events.Event;

import android.view.View;

public class WebViewOpenMenuEvent extends Event {

  public View anchorView;

  public WebViewOpenMenuEvent(View anchorView) {
    this.anchorView = anchorView;
  }

  ;
}
