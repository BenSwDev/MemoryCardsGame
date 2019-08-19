package com.example.memorycardsgame;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;
import android.app.Fragment;


public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    public static int mTotalScore,mTotalTimesFinishedSurvival;
    public static SharedPreferences volumePref;
    RelativeLayout mRelativeLayout;
    SharedPreferences diffPref;
    Button startBtn, exitBtn, difficultyBtn, mSettings, chooseLevelBtn, leaderBoardBtn,startWithLevel;
    Dialog levelsDialog;
    ImageView levelLoadingView;
    public static MediaPlayer Song;
    Animation zoomAnimation;
    public static int mNumberOfLevels;
    public static SoundPool soundFx = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
    public static int mBtnClickFx, mCardFlipFx, mLoseFx, mWinFx, mSuccessFx;
    public static boolean isMusicOn, mWithHint=true;
    int difficultyCounter;
    static int mModeGame, lives;
    EditText mFirstUserName,mSecondUserName;
    public static String player1name, player2name;

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
        setContentView(R.layout.activity_main);
        setResources();
        setDifficulty();
        graphics_and_sounds_manager.InitMediaPlayer(this, graphics_and_sounds_manager.eSongTypes.MAIN_SONG);
        FxManager.InitManager(this);
        mNumberOfLevels = getResources().getInteger(R.integer.num_of_themes) - 1;
        retrieveStats();
        changeBackgroundAnimation();
    }

    private void retrieveStats() {
        SharedPreferences sp = getSharedPreferences("stats", Activity.MODE_PRIVATE);
        mTotalTimesFinishedSurvival = sp.getInt("mTotalTimesFinishedSurvival", 0);
        mTotalScore = sp.getInt("mTotalScore", 0);
    }

    void changeBackgroundAnimation() {
        Resources res = getResources();
        Drawable b = res.getDrawable(R.drawable.background_list);

        int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            mRelativeLayout.setBackgroundDrawable(b);
        } else {
            mRelativeLayout.setBackground(b);
        }

        AnimationDrawable animationDrawable = (AnimationDrawable)b;
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();
    }

    private void setResources() {

        mRelativeLayout = findViewById(R.id.main_activity_relative_layout);
        volumePref = getSharedPreferences("volume", MODE_PRIVATE);
        isMusicOn = volumePref.getBoolean("volumeState", true);

        diffPref = getSharedPreferences("difficulty", MODE_PRIVATE);
        difficultyCounter = diffPref.getInt("diffLevel", 1);

        zoomAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom);

        Song = MediaPlayer.create(MainActivity.this, R.raw.uefa_champions_league_anthem);

        mBtnClickFx = soundFx.load(this, R.raw.button_click, 1);
        mCardFlipFx = soundFx.load(this, R.raw.flip_card, 1);
        mLoseFx = soundFx.load(this, R.raw.lose, 1);
        mWinFx = soundFx.load(this, R.raw.win, 1);
        mSuccessFx = soundFx.load(this, R.raw.success, 1);

        startBtn = (Button) findViewById(R.id.startGame);
        exitBtn = (Button) findViewById(R.id.exit);
        difficultyBtn = (Button) findViewById(R.id.easy);
        mSettings = (Button) findViewById(R.id.settings_main_btn);
        chooseLevelBtn = (Button) findViewById(R.id.choose_level);
        leaderBoardBtn = (Button) findViewById(R.id.leader_board);
        levelLoadingView = findViewById(R.id.levelLoader);
    }

    public void openSettings(View view) {
        FxManager.PlaySoundFx(FxManager.eSoundEffect.CLICK_FX, getApplicationContext());
        graphics_and_sounds_manager.setMusicState(graphics_and_sounds_manager.eMusicState.BETWEEN_ACTIVITIES);

        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void setDifficulty(){
        if(((difficultyCounter % 3) == 0)) {
            difficultyBtn.setBackgroundResource(R.drawable.hard);
            lives = 7;
        }
        else if ((difficultyCounter % 3) == 2) {
            difficultyBtn.setBackgroundResource(R.drawable.medium);
            lives = 12;
        }
        else {
            difficultyBtn.setBackgroundResource(R.drawable.easy);
            lives = 20;
        }
    }

    public void chooseDiffculty(View view) {
        difficultyBtn.startAnimation(zoomAnimation);
        if(isMusicOn)  {
            FxManager.PlaySoundFx(FxManager.eSoundEffect.CLICK_FX, getApplicationContext());
        }
        difficultyCounter++;
        setDifficulty();
    }

    public void startGame(int level) {
        Intent intent = new Intent(MainActivity.this, singel_player.class);
        intent.putExtra("livesLeft", lives);
        intent.putExtra("levelNum",level);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void selectLevelDialogOpen(View view) {
        if(isMusicOn)  {
            FxManager.PlaySoundFx(FxManager.eSoundEffect.CLICK_FX, getApplicationContext());
        }
        chooseLevelBtn.startAnimation(zoomAnimation);
        final View dialogView = getLayoutInflater().inflate(R.layout.recycler_view_for_levels, null);
        dialogView.findViewById(R.id.levels_dialog_layour).setVisibility(View.VISIBLE);

        Drawable levelDialogBackground = getResources().getDrawable(R.drawable.level_dialog_background);
        levelDialogBackground.setAlpha(200);
        dialogView.setBackground(levelDialogBackground);

        levelsDialog = createLevelsDialog(dialogView);
        loadLevels(dialogView);
        levelsDialog.show();
        startWithLevel = (Button) levelsDialog.findViewById(R.id.levels_dialog_start);
        startWithLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isMusicOn)  {
                    FxManager.PlaySoundFx(FxManager.eSoundEffect.CLICK_FX, getApplicationContext());
                }
                startWithLevel.startAnimation(zoomAnimation);
                mModeGame = 2;
                startGame(RecyclerViewAdapter.level);
            }
        });
    }

    private void loadLevels(View dialogView) {
        TypedArray LevelList = getResources().obtainTypedArray(R.array.cards_array);
        ArrayList<String> levelNames = new ArrayList<>();
        levelNames.add(getString(R.string.level_1_israel));
        levelNames.add(getString(R.string.level_2_england));
        levelNames.add(getString(R.string.level_3_spain));
        levelNames.add(getString(R.string.level_4_italy));
        levelNames.add(getString(R.string.level_5_germany));
        levelNames.add(getString(R.string.level_6_france));
        levelNames.add(getString(R.string.level_7_netherlands));
        levelNames.add(getString(R.string.level_8_greece));
        levelNames.add(getString(R.string.level_9_belgium));
        levelNames.add(getString(R.string.level_10_agrentina));
        levelNames.add(getString(R.string.level_11_international));

        Log.d(TAG, "initRecyclerView: init recyclerview");

        RecyclerView levelRecyclerView;
        levelRecyclerView = dialogView.findViewById(R.id.levels_list);
        levelRecyclerView.setHasFixedSize(true);
        levelRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, levelNames , LevelList);
        levelRecyclerView.setAdapter(adapter);
    }

    private Dialog createLevelsDialog(View dialogView) {
        final Dialog dialog = getCustomDialog(dialogView);

        dialogView.findViewById(R.id.levels_dialog_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if(isMusicOn)  {
                    FxManager.PlaySoundFx(FxManager.eSoundEffect.CLICK_FX, getApplicationContext());
                }
            }
        });

        dialogView.findViewById(R.id.levels_dialog_close_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isMusicOn)  {
                    FxManager.PlaySoundFx(FxManager.eSoundEffect.CLICK_FX, getApplicationContext());
                }
                dialog.dismiss();
            }
        });


        return dialog;

    }

    private Dialog getCustomDialog(View dialogView) {
        final Dialog dialog = new Dialog(this);

        if(dialog.getWindow() != null) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setContentView(dialogView);
            dialog.getWindow().setLayout((6 * getResources().getDisplayMetrics().widthPixels) / 7, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        }

        return dialog;
    }

    public void startFromBegining(View view){
        mModeGame = 1;
        if(isMusicOn)  {
            FxManager.PlaySoundFx(FxManager.eSoundEffect.CLICK_FX, getApplicationContext());
        }
        startBtn.startAnimation(zoomAnimation);
        startGame(0);
    }

    public void exitGame(View view) {
        if(MainActivity.isMusicOn) {
            FxManager.PlaySoundFx(FxManager.eSoundEffect.CLICK_FX, getApplicationContext());
        }
        exitBtn.startAnimation(zoomAnimation);
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        supportFinishAfterTransition();
    }

    public void moveToChampionsTableActivity(View view) {
        if(MainActivity.isMusicOn) {
            FxManager.PlaySoundFx(FxManager.eSoundEffect.CLICK_FX, getApplicationContext());
        }
        graphics_and_sounds_manager.setMusicState(graphics_and_sounds_manager.eMusicState.BETWEEN_ACTIVITIES);
        Intent intent = new Intent(MainActivity.this, LeaderBoardActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void survivalMode(View view) {
        mModeGame = 3;
        startGame(0);
    }


    public void oneOnOne(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View customLayout = getLayoutInflater().inflate(R.layout.users_one_on_one, null);
        builder.setView(customLayout);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText mFirstUserName = customLayout.findViewById(R.id.first_user_name);
                EditText mSecondUserName = customLayout.findViewById(R.id.second_user_name);

                sendDialogDataToActivity(mFirstUserName.getText().toString(),mSecondUserName.getText().toString());
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void sendDialogDataToActivity(String iFirstUserName, String iSecondUserName) {
        Intent intent = new Intent(MainActivity.this, MultiplayerActivity.class);
        intent.putExtra("player1", 0);
        intent.putExtra("player2",0);
        player1name = iFirstUserName;
        player2name = iSecondUserName;
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}

