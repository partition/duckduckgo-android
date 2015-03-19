package com.duckduckgo.mobile.android.adapters.menuAdapters;

import com.duckduckgo.mobile.android.adapters.PageMenuContextAdapter;
import com.duckduckgo.mobile.android.objects.FeedObject;
import com.duckduckgo.mobile.android.objects.history.HistoryObject;
import com.duckduckgo.mobile.android.util.menuItems.SaveStoryMenuItem;
import com.duckduckgo.mobile.android.util.menuItems.SendToExternalBrowserMenuItem;
import com.duckduckgo.mobile.android.util.menuItems.ShareFeedMenuItem;
import com.duckduckgo.mobile.android.util.menuItems.UnSaveStoryMenuItem;

import android.content.Context;

public class HistoryStoryMenuAdapter extends PageMenuContextAdapter {
  private Context context;
  private HistoryObject historyObject;

  public HistoryStoryMenuAdapter(Context context, int resource,
      int textViewResourceId) {
    super(context, resource, textViewResourceId);
    this.context = context;
  }

  public HistoryStoryMenuAdapter(Context context, int resource,
      int textViewResourceId, HistoryObject historyObject) {
    this(context, resource, textViewResourceId);
    this.historyObject = historyObject;
    addItems();
  }

  public void addItems() {
    FeedObject feedObject = new FeedObject(historyObject.getFeedId());
    if (feedObject.isSaved()) {
      add(new UnSaveStoryMenuItem(context, historyObject.getFeedId()));
    } else {
      add(new SaveStoryMenuItem(context, feedObject));
    }
    add(new ShareFeedMenuItem(context, historyObject.getData(), historyObject.getUrl()));
    //add(new DeleteStoryInHistoryMenuItem(context, historyObject.getFeedId()));
    add(new SendToExternalBrowserMenuItem(context, historyObject.getUrl()));
  }
}