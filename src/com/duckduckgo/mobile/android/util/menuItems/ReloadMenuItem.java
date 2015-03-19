package com.duckduckgo.mobile.android.util.menuItems;

import com.duckduckgo.mobile.android.R;
import com.duckduckgo.mobile.android.events.ReloadEvent;
import com.duckduckgo.mobile.android.util.Item;

import android.content.Context;

public class ReloadMenuItem extends Item {

  public ReloadMenuItem(Context context) {
    //super(context.getResources().getString(R.string.Refresh), R.drawable.icon_reload, ItemType.REFRESH);
    super(context.getResources().getString(R.string.Refresh), R.drawable.icon_reload, ItemType.SAVE);
    this.EventToFire = new ReloadEvent();
  }
}