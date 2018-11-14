package com.wallpaper.bing.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.wallpaper.bing.R;
import com.wallpaper.bing.rxbus.RxBus;

public class GeneralPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

    public final static String NIGHT_SWITCH = "night_switch";
    public final static String AUTO_CHANGE_WALLPAPER = "auto_change_wallpaper";

    private final int REQUEST_PERMISSION_NETWORK = 101;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);
        setHasOptionsMenu(false);

        Preference nightSwitch = findPreference(NIGHT_SWITCH);
        //Preference autoChangeSwitch = findPreference(AUTO_CHANGE_WALLPAPER);
        nightSwitch.setOnPreferenceChangeListener(this);
        //autoChangeSwitch.setOnPreferenceChangeListener(this);
    }


    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        switch (preference.getKey()) {
            case NIGHT_SWITCH:
                RxBus.getInstance().send(newValue);//发送切换夜间模式事件
                break;
            case AUTO_CHANGE_WALLPAPER:

                break;
        }
        return true;
    }

}
