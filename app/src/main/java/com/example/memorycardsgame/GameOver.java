package com.example.memorycardsgame;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

public class GameOver extends AppCompatActivity {

    int mNumOfMistakes,mLevelNum;
    boolean mIsWin;
    Button mSaveScore,mNextLevelOrTryAgain, mExit;
    TextView mWinOrLose, mScoreTextView;
    Animation zoomAnimation;
    nl.dionsegijn.konfetti.KonfettiView mKonfettiView;
    Dialog saveDlg;
    int mScore;
    static Gson gson = new Gson();
    static String champions;
    @Override
    public void onBackPressed() {
        exitToMainMenu();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        Bundle extras = getIntent().getExtras();
        mNumOfMistakes = extras.getInt("livesLeft");
        mLevelNum = extras.getInt("levelNum");
        mIsWin = extras.getBoolean("win");
        mScore = extras.getInt("score");
        setResources();
        setValuesToButtons();

    }

    private void setValuesToButtons() {
        if(MainActivity.mModeGame == 1) {
            setValuesForRegularGame();
        }
        else if(MainActivity.mModeGame == 2){
            setValuesForOneLevelGame();
        }
        else if(MainActivity.mModeGame == 3){
            setValuesForSurviverGame();
        }

    }

    private void setValuesForRegularGame(){
        if (!mIsWin) {
            if (MainActivity.isMusicOn) {
                FxManager.PlaySoundFx(FxManager.eSoundEffect.LOSE_FX, getApplicationContext());
            }
            mSaveScore.setVisibility(View.INVISIBLE);
            mWinOrLose.setBackground(getResources().getDrawable(R.drawable.you_lost));
            mNextLevelOrTryAgain.setBackground(getResources().getDrawable(R.drawable.try_again));
            String score = Integer.toString(mScore);
            mScoreTextView.setText(getString(R.string.your_score_is) + " " + score);
        } else {
            showKonfetti();
            checkIfNoMoreLevelRemaind();
            String score = Integer.toString(mScore);
            mScoreTextView.setText(getString(R.string.your_score_is) + " " + score);
            if (MainActivity.isMusicOn) {
                FxManager.PlaySoundFx(FxManager.eSoundEffect.WIN_FX, getApplicationContext());
            }
        }
    }

    private void setValuesForOneLevelGame(){
        if (!mIsWin) {
            if (MainActivity.isMusicOn) {
                FxManager.PlaySoundFx(FxManager.eSoundEffect.LOSE_FX, getApplicationContext());
            }
            mSaveScore.setVisibility(View.INVISIBLE);
            mWinOrLose.setBackground(getResources().getDrawable(R.drawable.you_lost));
            mNextLevelOrTryAgain.setBackground(getResources().getDrawable(R.drawable.try_again));
            String score = Integer.toString(mScore);
            mScoreTextView.setText(getString(R.string.your_score_is) + " " + score);
        } else {
            showKonfetti();
            String score = Integer.toString(mScore);
            mScoreTextView.setText(getString(R.string.your_score_is) + " " + score);
            mNextLevelOrTryAgain.setVisibility(View.INVISIBLE);
            if (MainActivity.isMusicOn) {
                FxManager.PlaySoundFx(FxManager.eSoundEffect.WIN_FX, getApplicationContext());
            }
        }
    }

    private void setValuesForSurviverGame(){
        if (!mIsWin) {
            if (MainActivity.isMusicOn) {
                FxManager.PlaySoundFx(FxManager.eSoundEffect.LOSE_FX, getApplicationContext());
            }
            mSaveScore.setVisibility(View.INVISIBLE);
            mWinOrLose.setBackground(getResources().getDrawable(R.drawable.you_lost));
            mNextLevelOrTryAgain.setBackground(getResources().getDrawable(R.drawable.try_again));
            mScoreTextView.setVisibility(View.INVISIBLE);
        } else {
            showKonfetti();
            checkIfNoMoreLevelRemaind();
            mScoreTextView.setVisibility(View.INVISIBLE);
            mSaveScore.setVisibility(View.INVISIBLE);
            if (MainActivity.isMusicOn) {
                FxManager.PlaySoundFx(FxManager.eSoundEffect.WIN_FX, getApplicationContext());
            }
        }
    }

    private void checkIfNoMoreLevelRemaind() {
        if (mLevelNum == MainActivity.mNumberOfLevels) {
            mNextLevelOrTryAgain.setVisibility(View.INVISIBLE);
            if (MainActivity.isMusicOn) {
                FxManager.PlaySoundFx(FxManager.eSoundEffect.CLICK_FX, getApplicationContext());
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage(R.string.congrats_string)
                    .setPositiveButton(R.string.positive_dialog_btn, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (MainActivity.isMusicOn) {
                                FxManager.PlaySoundFx(FxManager.eSoundEffect.CLICK_FX, getApplicationContext());
                            }
                            if(MainActivity.mModeGame == 3){
                                MainActivity.mTotalTimesFinishedSurvival += 1;
                                saveStats("mTotalTimesFinishedSurvival", MainActivity.mTotalTimesFinishedSurvival);
                            }
                            else if(MainActivity.mModeGame == 1){
                                MainActivity.mTotalScore += mScore;
                                saveStats("mTotalScore", MainActivity.mTotalScore);

                            }
                            exitToMainMenu();
                        }
                    });
            builder.show();
        }
    }

    private void saveStats(String iKey, int iValue){
        SharedPreferences sp = getSharedPreferences("stats", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(iKey, iValue);
        editor.commit();
    }

    private void setResources() {
        mScoreTextView = findViewById(R.id.score_show);
        mSaveScore = findViewById(R.id.save_score_btn);
        mNextLevelOrTryAgain = findViewById(R.id.next_level_or_try_again_btn);
        mExit = findViewById(R.id.exit_to_main_manu);
        mWinOrLose = findViewById(R.id.game_end_state);
        zoomAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom);
        mKonfettiView = findViewById(R.id.viewKonfetti);
    }

    public void nextLevelOrTryAgainOnClick(View view) {
        if(MainActivity.isMusicOn)  {
            FxManager.PlaySoundFx(FxManager.eSoundEffect.CLICK_FX, getApplicationContext());
        }
        mNextLevelOrTryAgain.startAnimation(zoomAnimation);
        if(!mIsWin){
            tryAgain();
        }
        else{
            nextLevel();
        }
    }

    private void nextLevel() {
        Intent intent = new Intent(GameOver.this, singel_player.class);
        intent.putExtra("livesLeft", mNumOfMistakes);
        intent.putExtra("levelNum",mLevelNum+1);
        intent.putExtra("score",mScore);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void tryAgain() {
        if(MainActivity.mModeGame == 3){
            mLevelNum = 0;
        }
        Intent intent = new Intent(GameOver.this, singel_player.class);
        intent.putExtra("livesLeft", MainActivity.lives);
        intent.putExtra("levelNum",mLevelNum);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void showKonfetti() {
        mKonfettiView.build()
                .addColors(android.graphics.Color.rgb(255,215,0))
                .setDirection(0.0, 150.0)
                .setSpeed(1f, 5f)
                .setFadeOutEnabled(true)
                .setTimeToLive(3000L)
                .addShapes(Shape.CIRCLE)
                .addSizes(new Size(12, 5f))
                .setPosition(0, mKonfettiView.getWidth() + 50f, -50f, -50f)
                .streamFor(300, 7000L);
    }

    public void exitGameToMainManu(View view) {
        if (MainActivity.isMusicOn) {
            FxManager.PlaySoundFx(FxManager.eSoundEffect.CLICK_FX, getApplicationContext());
        }
        mExit.startAnimation(zoomAnimation);
        startActivity(new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void saveScoreOnClick(View view) {
        saveDlg = new Dialog(GameOver.this);
        saveDlg.setContentView(R.layout.save_score);
        final EditText nameEd = (EditText) saveDlg.findViewById(R.id.nameEd);
        Button save = (Button) saveDlg.findViewById(R.id.save_score_btn);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nameEd.getText().toString().equals("")){
                    Toast toast = Toast.makeText(GameOver.this, getString(R.string.please_enter_un),Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }
                String name = nameEd.getText().toString();
                User user = new User(name, mScore);
                LeaderBoardActivity.insertNewUser(user);
                saveScoreTableData();

                saveDlg.dismiss();
            }
        });
        saveDlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        saveDlg.show();
    }

    private void saveScoreTableData(){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        gson = new Gson();

        String json = gson.toJson(LeaderBoardActivity.Champions);

        editor.putString("champs", json);
        editor.commit();
    }

    private void exitToMainMenu() {
        graphics_and_sounds_manager.InitMediaPlayer(getApplication(), graphics_and_sounds_manager.eSongTypes.MAIN_SONG);
        graphics_and_sounds_manager.setMusicState(graphics_and_sounds_manager.eMusicState.BETWEEN_ACTIVITIES);
        mExit.startAnimation(zoomAnimation);
        Intent i=new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

}
