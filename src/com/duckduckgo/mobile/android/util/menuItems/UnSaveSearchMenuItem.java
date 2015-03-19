package com.duckduckgo.mobile.android.util.menuItems;

import com.duckduckgo.mobile.android.R;
import com.duckduckgo.mobile.android.events.saveEvents.UnSaveSearchEvent;
import com.duckduckgo.mobile.android.util.Item;

import android.content.Context;

public class UnSaveSearchMenuItem extends Item {

  public UnSaveSearchMenuItem(Context context, String query) {
    super(context.getResources().getString(R.string.action_remove_favorite), android.R.drawable.ic_menu_delete, ItemType.UNSAVE);
    this.EventToFire = new UnSaveSearchEvent(query);
  }
}
