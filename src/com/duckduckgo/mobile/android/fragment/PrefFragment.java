package com.duckduckgo.mobile.android.fragment;

import com.duckduckgo.mobile.android.R;
import com.duckduckgo.mobile.android.activity.DuckDuckGo;
import com.duckduckgo.mobile.android.bus.BusProvider;
import com.duckduckgo.mobile.android.events.ConfirmDialogOkEvent;
import com.duckduckgo.mobile.android.events.DisplayScreenEvent;
import com.duckduckgo.mobile.android.util.DDGConstants;
import com.duckduckgo.mobile.android.util.DDGUtils;
import com.duckduckgo.mobile.android.util.PreferencesManager;
import com.duckduckgo.mobile.android.util.SCREEN;
import com.duckduckgo.mobile.android.util.TorIntegrationProvider;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.support.v4.app.DialogFragment;
import android.support.v4.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PrefFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {

  public static final String TAG = "preferences_fragment";

  //private final TorIntegration torIntegration;

  //private Preference
  private ListPreference startScreenPref;
  private ListPreference regionPref;
  private Preference sourcesPref;
  private Preference clearHistoryPref;
  private Preference clearCookiesPref;
  private Preference clearWebCachePref;
  private Preference aboutPref;
  private Preference sendFeedbackPref;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    addPreferencesFromResource(R.xml.temp_preferences);
    getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

    startScreenPref = (ListPreference) findPreference("startScreenPref");
    startScreenPref.setSummary(startScreenPref.getEntry());
    startScreenPref.setOnPreferenceChangeListener(this);

    regionPref = (ListPreference) findPreference("regionPref");
    regionPref.setSummary(regionPref.getEntry());
    regionPref.setOnPreferenceChangeListener(this);

    sourcesPref = findPreference("sourcesPref");
    sourcesPref.setOnPreferenceClickListener(this);

    clearHistoryPref = findPreference("clearHistoryPref");
    clearHistoryPref.setOnPreferenceClickListener(this);

    clearCookiesPref = findPreference("clearCookiesPref");
    clearCookiesPref.setOnPreferenceClickListener(this);

    clearWebCachePref = findPreference("clearWebCachePref");
    clearWebCachePref.setOnPreferenceClickListener(this);

    aboutPref = findPreference("aboutPref");
    aboutPref.setOnPreferenceClickListener(this);

    sendFeedbackPref = findPreference("sendFeedbackPref");
    sendFeedbackPref.setOnPreferenceClickListener(this);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View rootView = super.onCreateView(inflater, container, savedInstanceState);
    rootView.setBackgroundColor(getResources().getColor(R.color.background));
    return rootView;
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    //torIntegration = new TorIntegration(getActivity());

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      getListView().setPadding(0, 0, 0, getListView().getPaddingBottom());
    }

    whenChangingTorChecksForOrbot();
    whenCheckingOrbotStatusStartsOrbotAndSetsProxy();
  }

  @Override
  public boolean onPreferenceClick(Preference preference) {
    if (preference == sourcesPref) {
      //Intent intent = new Intent(getActivity(), SourcePreferences.class);
      //startActivity(intent);
      BusProvider.getInstance().post(new DisplayScreenEvent(SCREEN.SCR_SOURCES, false));
      return true;
    } else if (preference == clearHistoryPref) {
      ConfirmDialog dialog = ConfirmDialog.newInstance(getResources().getString(R.string.Confirm), getResources().getString(R.string.ConfirmClearHistory), DDGConstants.CONFIRM_CLEAR_HISTORY);
      dialog.show(getFragmentManager(), ConfirmDialog.TAG);
      return true;
    } else if (preference == clearCookiesPref) {
      ConfirmDialog dialog = ConfirmDialog.newInstance(getResources().getString(R.string.Confirm), getResources().getString(R.string.ConfirmClearCookies), DDGConstants.CONFIRM_CLEAR_COOKIES);
      dialog.show(getFragmentManager(), ConfirmDialog.TAG);
      return true;
    } else if (preference == clearWebCachePref) {
      ConfirmDialog dialog = ConfirmDialog.newInstance(getResources().getString(R.string.Confirm), getResources().getString(R.string.ConfirmClearCacheAndCookies), DDGConstants.CONFIRM_CLEAR_WEB_CACHE);
      dialog.show(getFragmentManager(), ConfirmDialog.TAG);
    } else if (preference == aboutPref) {
      BusProvider.getInstance().post(new DisplayScreenEvent(SCREEN.SCR_ABOUT, false));
      return true;
    } else if (preference == sendFeedbackPref) {
      Intent intent = DDGUtils.newEmailIntent(getActivity().getResources().getString(R.string.FeedbackTo),
          getActivity().getResources().getString(R.string.FeedbackSubject), DDGUtils.getBuildInfo(getActivity()), "");
      startActivity(Intent.createChooser(intent, getResources().getString(R.string.select_application)));
      return true;
    }
    return false;
  }

  @Override
  public boolean onPreferenceChange(Preference preference, Object newValue) {
    if (preference instanceof ListPreference && newValue instanceof String) {
      ListPreference listPref = (ListPreference) preference;
      for (int i = 0; i < listPref.getEntries().length; i++) {
        if (newValue.equals(listPref.getEntryValues()[i])) {
          listPref.setSummary(listPref.getEntries()[i]);
        }
      }
    }
    return true;
  }

  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    PreferencesManager.onSharedPreferenceChanged(sharedPreferences, key);
  }

  private void whenCheckingOrbotStatusStartsOrbotAndSetsProxy() {
    Preference checkOrbotPreference = findPreference("checkOrbotStatus");
    checkOrbotPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
      @Override
      public boolean onPreferenceClick(Preference preference) {
        if (!TorIntegrationProvider.getInstance(getActivity()).isOrbotRunningAccordingToSettings()) {
          TorIntegrationProvider.getInstance(getActivity()).prepareTorSettings();
        } else {
          //result_startOrbotCheck = true;
          //finish();
          ((DuckDuckGo) getActivity()).searchOrGoToUrl(getString(R.string.OrbotCheckSite));
        }
        return true;
      }
    });
  }

  private void whenChangingTorChecksForOrbot() {
    Preference enableTorPreference = findPreference("enableTor");
    if (!TorIntegrationProvider.getInstance(getActivity()).isTorSupported()) {
      setTorNotSupportedInfo(enableTorPreference);
    } else {
      enableTorPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
        public boolean onPreferenceChange(Preference preference, Object newValue) {
          return TorIntegrationProvider.getInstance(getActivity()).prepareTorSettings((Boolean) newValue);
        }
      });
    }
  }

  private void setTorNotSupportedInfo(Preference enableTorPreference) {
    enableTorPreference.setEnabled(false);
    enableTorPreference.setSummary("Tor is currently not supported in Android 4.4 due to changes in the WebView implementation.");
  }

  public static class ConfirmDialog extends DialogFragment {

    public static final String TAG = "confirm_dialog";

    private int action;

    public ConfirmDialog() {
    }

    public static ConfirmDialog newInstance(String title, String message, int action) {
      ConfirmDialog dialog = new ConfirmDialog();
      Bundle args = new Bundle();
      args.putString("title", title);
      args.putString("message", message);
      args.putInt("action", action);
      dialog.setArguments(args);
      return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

      Bundle args = getArguments();

      action = args.getInt("action");

      AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
          .setTitle(args.getString("title"))
          .setMessage(args.getString("message"))
          .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              BusProvider.getInstance().post(new ConfirmDialogOkEvent(action));
            }
          })
          .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              dismiss();
            }
          });

      return builder.create();
    }
  }
}
