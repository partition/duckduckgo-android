package com.duckduckgo.mobile.android.views;

import com.duckduckgo.mobile.android.R;
import com.duckduckgo.mobile.android.adapters.FavoriteFeedCursorAdapter;
import com.duckduckgo.mobile.android.bus.BusProvider;
import com.duckduckgo.mobile.android.download.AsyncImageView;
import com.duckduckgo.mobile.android.events.feedEvents.MainFeedItemLongClickEvent;
import com.duckduckgo.mobile.android.events.feedEvents.MainFeedItemSelectedEvent;
import com.duckduckgo.mobile.android.events.feedEvents.SavedFeedItemLongClickEvent;
import com.duckduckgo.mobile.android.objects.FeedObject;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.database.sqlite.SQLiteCursor;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainFeedListView extends ListView implements android.widget.AdapterView.OnItemClickListener, android.widget.AdapterView.OnItemLongClickListener {

  public MainFeedListView(Context context, AttributeSet attrs) {
    super(context, attrs);

    this.setOnItemClickListener(this);
    this.setOnItemLongClickListener(this);
  }

  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    Log.e("aaa", "main feed list view on click");
    Object item = getAdapter().getItem(position);
    FeedObject obj = null;
    if (item instanceof FeedObject) {
      obj = (FeedObject) item;
    } else if (item instanceof SQLiteCursor) {
      obj = new FeedObject(((SQLiteCursor) item));
    }

    if (obj != null) {
      BusProvider.getInstance().post(new MainFeedItemSelectedEvent(obj));
    }
  }

  public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
    Log.e("aaa", "main feed long click");
    Object item = getAdapter().getItem(position);
    FeedObject obj = null;
    if (item instanceof FeedObject) {
      obj = (FeedObject) item;
    } else if (item instanceof SQLiteCursor) {
      obj = new FeedObject(((SQLiteCursor) item));
    }

    if (obj != null) {
      if (getAdapter().getClass() == FavoriteFeedCursorAdapter.class) {
        Log.e("aaa", "saved feed item long click event");
        BusProvider.getInstance().post(new SavedFeedItemLongClickEvent(obj));
      } else {
        Log.e("aaa", "main feed item long click event");
        BusProvider.getInstance().post(new MainFeedItemLongClickEvent(obj));
      }
    }
    return true;
  }

  public void setSelectionById(String id) {
    int itemCount = getCount();
    for (int i = 0; i < itemCount; ++i) {
      if (((FeedObject) getItemAtPosition(i)).getId().equals(id)) {
        setSelection(i);
        break;
      }
    }
  }

  /**
   * Find the item given by its ID and return its position in the list
   *
   * @param id item ID
   * @return item position in the list
   */
  public int getSelectionPosById(String id) {
    int headerViewCount = getHeaderViewsCount();
    int itemCount = getCount() - headerViewCount;
    for (int i = headerViewCount; i < itemCount; ++i) {
      if (((FeedObject) getItemAtPosition(i)).getId().equals(id)) {
        return i;
      }
    }

    return -1;
  }

  public void cleanImageTasks() {
    int count = getCount();
    for (int i = 0; i < count; i++) {
      View v = getChildAt(i);
      if (v != null) {
        AsyncImageView iv = (AsyncImageView) v.findViewById(R.id.feedItemBackground);
        Picasso.with(getContext()).cancelRequest(iv);
      }
    }
  }
}
