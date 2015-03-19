package com.duckduckgo.mobile.android.util.menuItems;

import com.duckduckgo.mobile.android.R;
import com.duckduckgo.mobile.android.events.externalEvents.SendToExternalBrowserEvent;
import com.duckduckgo.mobile.android.util.Item;

import android.content.Context;

public class SendToExternalBrowserMenuItem extends Item {

  public SendToExternalBrowserMenuItem(Context context, String url) {
    super(context.getResources().getString(R.string.action_view_external), android.R.drawable.ic_menu_rotate, ItemType.EXTERNAL);
    this.EventToFire = new SendToExternalBrowserEvent(context, url);
  }
}
