package com.duckduckgo.mobile.android.events.WebViewEvents;

import com.duckduckgo.mobile.android.objects.FeedObject;

import android.view.MenuItem;
import android.view.View;

public class WebViewItemMenuClickEvent {

  public MenuItem item;
  public View itemView = null;
  public FeedObject feed = null;

  public WebViewItemMenuClickEvent(MenuItem item) {
    this.item = item;
  }

  public WebViewItemMenuClickEvent(MenuItem item, View itemView) {
    this.item = item;
    this.itemView = itemView;
  }

  public WebViewItemMenuClickEvent(MenuItem item, FeedObject feed) {
    this.item = item;
    this.feed = feed;
  }
}
