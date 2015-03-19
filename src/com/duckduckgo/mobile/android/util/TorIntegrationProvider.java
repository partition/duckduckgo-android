package com.duckduckgo.mobile.android.util;

import android.app.Activity;

public class TorIntegrationProvider {

  public static TorIntegration torIntegration = null;

  private TorIntegrationProvider() {

  }

  public static TorIntegration getInstance(Activity activity) {
    if (torIntegration == null) {
      torIntegration = new TorIntegration(activity);
    }
    return torIntegration;
  }
}
