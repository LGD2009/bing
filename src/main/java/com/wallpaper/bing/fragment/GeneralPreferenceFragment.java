package com.wallpaper.bing.fragment;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;

import com.wallpaper.bing.R;
import com.wallpaper.bing.rxbus.RxBus;

public class GeneralPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

    public final static String NIGHT_SWITCH = "night_switch";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);
        setHasOptionsMenu(false);

        Preference nightSwitch = findPreference(NIGHT_SWITCH);
        nightSwitch.setOnPreferenceChangeListener(this);
    }


    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference instanceof SwitchPreference) {
            RxBus.getInstance().send(newValue);//发送切换夜间模式事件
        }
        return true;
    }

}
