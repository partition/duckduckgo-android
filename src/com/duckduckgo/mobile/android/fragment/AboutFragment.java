package com.duckduckgo.mobile.android.fragment;

import com.duckduckgo.mobile.android.R;
import com.duckduckgo.mobile.android.util.DDGUtils;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AboutFragment extends Fragment {

  public static final String TAG = "about_fragment";

  private View fragmentView = null;
  private TextView versionTextView;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    setHasOptionsMenu(true);
    fragmentView = inflater.inflate(R.layout.fragment_about, container, false);
    return fragmentView;
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    init();
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    Log.e("aaa", "new config");
    ViewGroup container = (ViewGroup) getView();
    container.removeAllViewsInLayout();
    fragmentView = onCreateView(getActivity().getLayoutInflater(), container, null);
    container.addView(fragmentView);
    init();
  }

  private void init() {
    versionTextView = (TextView) fragmentView.findViewById(R.id.textview_version);
    versionTextView.setText(versionTextView.getText() + getVersion());
  }

  private String getVersion() {
    return " " + DDGUtils.getVersionName(getActivity()) + " (" + DDGUtils.getVersionCode(getActivity()) + ")";
  }
}
