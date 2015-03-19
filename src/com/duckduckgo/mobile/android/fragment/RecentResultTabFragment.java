package com.duckduckgo.mobile.android.fragment;

import com.duckduckgo.mobile.android.DDGApplication;
import com.duckduckgo.mobile.android.R;
import com.duckduckgo.mobile.android.actionbar.DDGActionBarManager;
import com.duckduckgo.mobile.android.adapters.RecentResultCursorAdapter;
import com.duckduckgo.mobile.android.bus.BusProvider;
import com.duckduckgo.mobile.android.events.SyncAdaptersEvent;
import com.duckduckgo.mobile.android.util.PreferencesManager;
import com.duckduckgo.mobile.android.views.RecentSearchListView;
import com.squareup.otto.Subscribe;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

public class RecentResultTabFragment extends ListFragment {

  public static final String TAG = "recent_result_tab_fragment";

  //private HistoryListView historyListView;
  private RecentSearchListView recentSearchListView;
  private RecentResultCursorAdapter recentResultAdapter;

  //private boolean recordHistory = true;

  private int lastFirstVisibleItem;

  private View fragmentView = null;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    BusProvider.getInstance().register(this);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    BusProvider.getInstance().unregister(this);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    //recordHistory = PreferencesManager.getRecordHistory();
    //Log.e("aaa", "record history: "+recordHistory);
    fragmentView = inflater.inflate(R.layout.fragment_tab_recentsresult, container, false);
    return fragmentView;
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    if (PreferencesManager.getRecordHistory()) {
      //historyListView = (HistoryListView) getListView();
      recentSearchListView = (RecentSearchListView) getListView();
      //recentSearchListView = (RecentSearchListView) fragmentView.findViewById(R.id.listview);
      recentResultAdapter = new RecentResultCursorAdapter(getActivity(), DDGApplication.getDB().getCursorSearchHistory(), true);
      //historyListView.setAdapter(recentResultAdapter);
      recentSearchListView.setAdapter(recentResultAdapter);

      recentSearchListView.setOnScrollListener(new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
          final int currentFirstVisibleItem = recentSearchListView.getFirstVisiblePosition();
          ;
          if (currentFirstVisibleItem > lastFirstVisibleItem) {
            DDGActionBarManager.getInstance().tryToHideTab();
          } else if (currentFirstVisibleItem < lastFirstVisibleItem) {
            DDGActionBarManager.getInstance().tryToShowTab();
          }
          lastFirstVisibleItem = currentFirstVisibleItem;
        }
      });
    } else {
      getListView().setVisibility(View.GONE);

      TextView title = (TextView) fragmentView.findViewById(R.id.empty_title);
      title.setText(getResources().getString(R.string.disabled_recents_title));

      TextView text = (TextView) fragmentView.findViewById(R.id.empty_text);
      text.setText(getResources().getString(R.string.disabled_recents_text));
      //fragmentView.findViewById(R.id.contenitore).setVisibility(View.GONE);
      //fragmentView.findViewById(R.id.text).setVisibility(View.VISIBLE);
    }
  }

  @Override
  public void setUserVisibleHint(boolean isVisibleToUser) {
    super.setUserVisibleHint(isVisibleToUser);
    Log.e("aaa", "is visible to user");
    DDGActionBarManager.getInstance().tryToShowTab();
  }

  @Override
  public void onListItemClick(ListView l, View v, int position, long id) {
    recentSearchListView.onItemClick(l, v, position, id);

/*
        DDGDialogMenu dialog = new DDGDialogMenu();
        Menu menu = new MenuBuilder(getActivity());
        getActivity().getMenuInflater().inflate(R.menu.feed, menu);
        //if(DDGApplication.getDB().isSaved())

        dialog.setMenu(menu);
        //dialog.setFeed();
        dialog.show(getChildFragmentManager(), "dialog");
        //dialog.show(getChildFragmentManager(), DDGDialogMenu.TAG);
*/


        /*
        Log.e("aaa", "recent result tab fragment onitem click");
        super.onListItemClick(l, v, position, id);

        Object adapter = getListView().getAdapter();
        Cursor c = null;

        if(adapter instanceof RecentResultCursorAdapter) {
            c = (Cursor) ((RecentResultCursorAdapter) adapter).getItem(position);
            String data = c.getString(c.getColumnIndex("data"));
            if(data!=null) {
                Log.e("aaa", "--------------------------------data: "+data);
                //BusProvider.getInstance().post(new HistoryItemSelectedEvent());
                //aaa todo
            }
        }*/
  }

  @Subscribe
  public void onSyncAdaptersEvent(SyncAdaptersEvent event) {
    recentResultAdapter.changeCursor(DDGApplication.getDB().getCursorSearchHistory());
    recentResultAdapter.notifyDataSetChanged();
  }
}
