package com.duckduckgo.mobile.android.events;

import com.duckduckgo.mobile.android.objects.FeedObject;

import android.view.View;

public class SourceFilterEvent extends Event {

  public View itemView;
  public String sourceType;
  public FeedObject feedObject;

  public SourceFilterEvent(View itemView, String sourceType, FeedObject feedObject) {
    this.itemView = itemView;
    this.sourceType = sourceType;
    this.feedObject = feedObject;
  }
}
