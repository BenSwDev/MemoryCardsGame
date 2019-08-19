package com.example.memorycardsgame;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeaderBoardActivity extends AppCompatActivity {

    static ArrayList<User> Champions;
    ListView mLeaderBoardLV;
    Button mExit;

    @Override
    public void onBackPressed() {
        graphics_and_sounds_manager.InitMediaPlayer(getApplication(), graphics_and_sounds_manager.eSongTypes.MAIN_SONG);
        graphics_and_sounds_manager.setMusicState(graphics_and_sounds_manager.eMusicState.BETWEEN_ACTIVITIES);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        Intent i=new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
        
        mLeaderBoardLV = findViewById(R.id.leader_board_list_view);
        mExit = findViewById(R.id.exit_lb_btn);
        retrieveArrayFromSharedPref();
        insertArrayToListView();
    }

    private void retrieveArrayFromSharedPref() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPrefs.getString("champs", "");
        Type type = new TypeToken<List<User>>() {}.getType();
        LeaderBoardActivity.Champions = gson.fromJson(json, type);
    }


    public static void insertNewUser(User user){
        if(Champions == null){
            Champions = new ArrayList<>();
            Champions.add(user);
        }
        else{
            Champions.add(user);
            Collections.sort(Champions, new SortByScore());
        }
    }

    public void insertArrayToListView(){
        if(Champions != null){
            ListViewAdapter listViewAdapter = new ListViewAdapter(this);
            mLeaderBoardLV.setAdapter(listViewAdapter);
        }
    }


    public void closeGame(View view) {
        if (MainActivity.isMusicOn) {
            FxManager.PlaySoundFx(FxManager.eSoundEffect.CLICK_FX, getApplicationContext());
        }
        graphics_and_sounds_manager.InitMediaPlayer(getApplication(), graphics_and_sounds_manager.eSongTypes.MAIN_SONG);
        graphics_and_sounds_manager.setMusicState(graphics_and_sounds_manager.eMusicState.BETWEEN_ACTIVITIES);
        Intent i=new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }


    static class SortByScore implements Comparator<User>
    {
        public int compare(User a, User b)
        {
            return b.getmScore()- a.getmScore();
        }
    }
}
