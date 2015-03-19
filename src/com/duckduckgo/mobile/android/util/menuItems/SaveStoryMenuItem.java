package com.duckduckgo.mobile.android.util.menuItems;

import com.duckduckgo.mobile.android.R;
import com.duckduckgo.mobile.android.events.saveEvents.SaveStoryEvent;
import com.duckduckgo.mobile.android.objects.FeedObject;
import com.duckduckgo.mobile.android.util.Item;

import android.content.Context;

public class SaveStoryMenuItem extends Item {

  public SaveStoryMenuItem(Context context, FeedObject feedObject) {
    super(context.getResources().getString(R.string.action_add_favorite), android.R.drawable.ic_menu_save, ItemType.SAVE);
    EventToFire = new SaveStoryEvent(feedObject);
  }
}
