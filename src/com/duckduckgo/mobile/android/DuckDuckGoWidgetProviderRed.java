package com.duckduckgo.mobile.android;

import com.duckduckgo.mobile.android.activity.DuckDuckGo;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class DuckDuckGoWidgetProviderRed extends AppWidgetProvider {

  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    final int N = appWidgetIds.length;

    // Perform this loop procedure for each App Widget that belongs to this provider
    for (int i = 0; i < N; i++) {
      int appWidgetId = appWidgetIds[i];

      Intent intent = new Intent(context, DuckDuckGo.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
      intent.putExtra("widget", true);

      PendingIntent pendingIntent = PendingIntent.getActivity(context, appWidgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

      // Get the layout for the App Widget and attach an on-click listener to the button
      RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_red);
      views.setOnClickPendingIntent(R.id.search_widget, pendingIntent);
      views.setOnClickPendingIntent(R.id.dax_icon, pendingIntent);
      views.setOnClickPendingIntent(R.id.search_icon, pendingIntent);
      //views.setOnClickPendingIntent(R.id.widget_voice_btn, pendingIntent);

      // Tell the AppWidgetManager to perform an update on the current App Widget
      appWidgetManager.updateAppWidget(appWidgetId, views);
    }
  }
}
