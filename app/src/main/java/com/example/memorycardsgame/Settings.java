package com.example.memorycardsgame;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;

public class Settings extends PreferenceFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        String pointsCount = Integer.toString(MainActivity.mTotalScore);
        String numberOfSurvival = Integer.toString(MainActivity.mTotalTimesFinishedSurvival);

        SwitchPreference musicSwitch = (SwitchPreference) getPreferenceManager()
                .findPreference("pref_key_music");

        SwitchPreference hintSwitch = (SwitchPreference) getPreferenceManager()
                .findPreference("pref_key_game_hint");

        Preference regularStat = (Preference) getPreferenceManager()
                .findPreference("pref_rmr_stat");

        Preference survivalStat = (Preference) getPreferenceManager()
                .findPreference("pref_smr_stat");

        if(hintSwitch != null){
            hintSwitch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {

                    if(!((boolean) newValue)){
                        MainActivity.mWithHint = false;
                    }else{
                        MainActivity.mWithHint = true;
                    }
                    return true;
                }
            });
        }

        if (musicSwitch != null) {
            musicSwitch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {

                    if(!((boolean) newValue)){
                        graphics_and_sounds_manager.Pause();
                    }else{
                        if(graphics_and_sounds_manager.getMusicState().equals(graphics_and_sounds_manager.eMusicState.PAUSED)) {
                            graphics_and_sounds_manager.Resume();
                        }else{
                            graphics_and_sounds_manager.InitMediaPlayer(getActivity(), graphics_and_sounds_manager.eSongTypes.MAIN_SONG);
                        }
                    }

                    return true;
                }
            });
        }

        regularStat.setSummary(pointsCount);
        survivalStat.setSummary(numberOfSurvival);
    }
}
