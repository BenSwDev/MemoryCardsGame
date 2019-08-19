package com.example.memorycardsgame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class SettingsActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        FxManager.PlaySoundFx(FxManager.eSoundEffect.CLICK_FX, getApplicationContext());
        graphics_and_sounds_manager.setMusicState(graphics_and_sounds_manager.eMusicState.BETWEEN_ACTIVITIES);
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    protected void onStart() {
        super.onStart();
        graphics_and_sounds_manager.Play(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(graphics_and_sounds_manager.getMusicState() != graphics_and_sounds_manager.eMusicState.BETWEEN_ACTIVITIES){
            graphics_and_sounds_manager.Pause();
        }else {
            graphics_and_sounds_manager.setMusicState(graphics_and_sounds_manager.eMusicState.PLAYING);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new Settings()).commit();

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}