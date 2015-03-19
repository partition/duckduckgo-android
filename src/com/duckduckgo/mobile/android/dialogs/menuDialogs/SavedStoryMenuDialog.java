package com.duckduckgo.mobile.android.dialogs.menuDialogs;

import com.duckduckgo.mobile.android.R;
import com.duckduckgo.mobile.android.adapters.PageMenuContextAdapter;
import com.duckduckgo.mobile.android.adapters.menuAdapters.SavedStoryMenuAdapter;
import com.duckduckgo.mobile.android.listener.ExecuteActionOnClickListener;
import com.duckduckgo.mobile.android.objects.FeedObject;

import android.app.AlertDialog;
import android.content.Context;

/*
Shows a dialog to alert the user the feedrequest failed, asking him to try again.
 */
public final class SavedStoryMenuDialog extends AlertDialog.Builder {
  public SavedStoryMenuDialog(final Context context, FeedObject feedObject) {
    super(context);

    final PageMenuContextAdapter contextAdapter = new SavedStoryMenuAdapter(context, R.layout.temp_dialog_item/*android.R.layout.select_dialog_item*/, android.R.id.text1, feedObject);
    //setTitle(R.string.StoryOptionsTitle);
    setAdapter(contextAdapter, new ExecuteActionOnClickListener(contextAdapter));
  }
}
