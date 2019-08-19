package com.example.memorycardsgame;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Arrays;
import java.util.Collections;

import static android.view.View.INVISIBLE;


public class singel_player extends AppCompatActivity {

    LevelBuilder mCurrentLevel;

    android.support.constraint.ConstraintLayout mConstraintLayout;

    ImageView mIconImageView;

    int mNumOfMistakes, mLevelNum;
    int image1,image2,image3,image4,image5,image6,image7,image8,image9,image10,image11,image12,image13,image14,image15,image16,image17,image18,image19,image20;
    int mFirstCard,mSecondCard;
    int mClickedFirst,mClickedSecond;
    int mCardNumber = 1;
    static int numOfMistakesWithoutCounterDown;
    int mScore=0;
    Boolean mIsWin = false;
    Animation zoomAnimation;
    ImageView[] imageViewsArray = new ImageView[20];
    Button mExit;
    TextView mLevel_textview, mLivesTextView, mScoreTextView;
    ImageView iv_1,iv_2,iv_3,iv_4,iv_5,iv_6,iv_7,iv_8,iv_9,iv_10,iv_11,iv_12,iv_13,iv_14,iv_15,iv_16,iv_17,iv_18,iv_19,iv_20;
    Integer[] cardArray  = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};

    @Override
    public void onBackPressed() {
        if(MainActivity.isMusicOn) {
            FxManager.PlaySoundFx(FxManager.eSoundEffect.CLICK_FX, getApplicationContext());
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(R.string.leave_game_dialog_message)
                .setPositiveButton(R.string.positive_dialog_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(MainActivity.isMusicOn) {
                            FxManager.PlaySoundFx(FxManager.eSoundEffect.CLICK_FX, getApplicationContext());
                        }
                        exitToMainMenu();
                    }
                })
                .setNegativeButton(R.string.negative_dialog_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(MainActivity.isMusicOn) {
                            FxManager.PlaySoundFx(FxManager.eSoundEffect.CLICK_FX, getApplicationContext());
                        }
                        dialogInterface.dismiss();
                    }
                }).setCancelable(false).create().show();    }

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


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singel_player);
        Bundle extras = getIntent().getExtras();
        mNumOfMistakes = extras.getInt("livesLeft");
        mLevelNum = extras.getInt("levelNum");
        mScore = extras.getInt("score");
        mCurrentLevel = new LevelBuilder(mLevelNum,this);
        setResources();
        String mLevelN = Integer.toString(mLevelNum);
        String mNumOfM = Integer.toString(mNumOfMistakes);
        mLivesTextView.setText(mNumOfM);
        numOfMistakesWithoutCounterDown = mNumOfMistakes;

        setBackground();
        setIcon();
        setLevel();
        setBackOfCard();
        if(MainActivity.mWithHint){
            hint(mNumOfM);
        }
        onClickCards();
        startSong();
        setStartScore();
        updateScore();
    }

    private void startSong() {
        graphics_and_sounds_manager.InitMediaPlayer(this,
                graphics_and_sounds_manager.eSongTypes.values()[mLevelNum+1]);
    }

    private void updateScore() {
        if(MainActivity.mModeGame != 3){
            String score = Integer.toString(mScore);
            mScoreTextView.setText(getString(R.string.your_score)+score);
        }
        else{
            mScoreTextView.setVisibility(INVISIBLE);
        }
    }

    private void setStartScore() {
        if(numOfMistakesWithoutCounterDown == 20){
            mScore += 0;
        }
        else if(numOfMistakesWithoutCounterDown == 12){
            mScore += 5;
        }
        else if(numOfMistakesWithoutCounterDown == 7){
            mScore += 10;
        }
    }


    private void hint(final String iNumOfM) {
        mLivesTextView.setVisibility(View.VISIBLE);

        new CountDownTimer(3300, 1000) {
            int t = 3;

            @Override
            public void onTick(long l) {
                mLivesTextView.setText(String.valueOf(t--));
            }

            @Override
            public void onFinish() {
                mLivesTextView.setText(iNumOfM);
                mLivesTextView.setVisibility(View.VISIBLE);
            }
        }.start();

        for(int i = 0 ; i <= 19 ; i++) {
            switch (cardArray[i]){
                case 1 : imageViewsArray[i].setImageResource(image1); break;
                case 2 : imageViewsArray[i].setImageResource(image2); break;
                case 3 : imageViewsArray[i].setImageResource(image3); break;
                case 4 : imageViewsArray[i].setImageResource(image4); break;
                case 5 : imageViewsArray[i].setImageResource(image5); break;
                case 6 : imageViewsArray[i].setImageResource(image6); break;
                case 7 : imageViewsArray[i].setImageResource(image7); break;
                case 8 : imageViewsArray[i].setImageResource(image8); break;
                case 9 : imageViewsArray[i].setImageResource(image9); break;
                case 10 : imageViewsArray[i].setImageResource(image10); break;
                case 11 : imageViewsArray[i].setImageResource(image11); break;
                case 12 : imageViewsArray[i].setImageResource(image12); break;
                case 13 : imageViewsArray[i].setImageResource(image13); break;
                case 14 : imageViewsArray[i].setImageResource(image14); break;
                case 15 : imageViewsArray[i].setImageResource(image15); break;
                case 16 : imageViewsArray[i].setImageResource(image16); break;
                case 17 : imageViewsArray[i].setImageResource(image17); break;
                case 18 : imageViewsArray[i].setImageResource(image18); break;
                case 19 : imageViewsArray[i].setImageResource(image19); break;
                case 20 : imageViewsArray[i].setImageResource(image20); break;
            }
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (ImageView iv: imageViewsArray) {
                    iv.setImageResource(mCurrentLevel.mIcon);
                }
                onClickCards();
            }
        }, 3000);
    }
    //----------------------------------Game Logic------------------------------------------------------

    //-----part of onclick -------------//
    private void onClickCards() {
        for(int i = 0 ; i <= 19 ; i++) {
            final int cardIndex = i;
            imageViewsArray[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(MainActivity.isMusicOn)  {
                        FxManager.PlaySoundFx(FxManager.eSoundEffect.CARD_FLIP_FX, getApplicationContext());
                    }
                    int card = Integer.parseInt((String) view.getTag());
                    doStuff(imageViewsArray[cardIndex],card);
                }
            });
        }
    }
    //-----end part of onclick --------//

    private void doStuff(ImageView imageView, int card) {

        setImageToImageView(imageView,card);
        checkCardNumberAndSaveItToTempVariable(imageView, card);

    }

    private void setImageToImageView(ImageView imageView, int card) {
        if(cardArray[card]==1){
            imageView.setImageResource(image1);
        }
        else if(cardArray[card]==2){
            imageView.setImageResource(image2);
        }
        else if(cardArray[card]==3){
            imageView.setImageResource(image3);
        }
        else if(cardArray[card]==4){
            imageView.setImageResource(image4);
        }
        else if(cardArray[card]==5){
            imageView.setImageResource(image5);
        }
        else if(cardArray[card]==6){
            imageView.setImageResource(image6);
        }
        else if(cardArray[card]==7){
            imageView.setImageResource(image7);
        }
        else if(cardArray[card]==8){
            imageView.setImageResource(image8);
        }
        else if(cardArray[card]==9){
            imageView.setImageResource(image9);
        }
        else if(cardArray[card]==10){
            imageView.setImageResource(image10);
        }
        else if(cardArray[card]==11){
            imageView.setImageResource(image11);
        }
        else if(cardArray[card]==12){
            imageView.setImageResource(image12);
        }
        else if(cardArray[card]==13){
            imageView.setImageResource(image13);
        }
        else if(cardArray[card]==14){
            imageView.setImageResource(image14);
        }
        else if(cardArray[card]==15){
            imageView.setImageResource(image15);
        }
        else if(cardArray[card]==16){
            imageView.setImageResource(image16);
        }
        else if(cardArray[card]==17){
            imageView.setImageResource(image17);
        }
        else if(cardArray[card]==18){
            imageView.setImageResource(image18);
        }
        else if(cardArray[card]==19){
            imageView.setImageResource(image19);
        }
        else if(cardArray[card]==20){
            imageView.setImageResource(image20);
        }
    }

    private void checkCardNumberAndSaveItToTempVariable(ImageView imageView, int card) {
        if(mCardNumber == 1){
            mFirstCard = cardArray[card];
            if(mFirstCard>10){
                mFirstCard = mFirstCard-10;
            }
            mCardNumber = 2;
            mClickedFirst = card;
            imageView.setEnabled(false);
        } else if(mCardNumber ==2){
            mSecondCard = cardArray[card];
            if(mSecondCard>10){
                mSecondCard = mSecondCard-10;
            }
            mCardNumber = 1;
            mClickedSecond = card;

            setImageViewEnable();
            openHandlerAndStartRulesChecker();
        }
    }

    private void setImageViewEnable() {
        iv_1.setEnabled(false);
        iv_2.setEnabled(false);
        iv_3.setEnabled(false);
        iv_4.setEnabled(false);
        iv_5.setEnabled(false);
        iv_6.setEnabled(false);
        iv_7.setEnabled(false);
        iv_8.setEnabled(false);
        iv_9.setEnabled(false);
        iv_10.setEnabled(false);
        iv_11.setEnabled(false);
        iv_12.setEnabled(false);
        iv_13.setEnabled(false);
        iv_14.setEnabled(false);
        iv_15.setEnabled(false);
        iv_16.setEnabled(false);
        iv_17.setEnabled(false);
        iv_18.setEnabled(false);
        iv_19.setEnabled(false);
        iv_20.setEnabled(false);
    }

    private void openHandlerAndStartRulesChecker() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // לבדוק אם התמונות הנבחרות זהות
                calculate();
            }
        }, 1000);
    }

    private void calculate() {
        //אם תמונות זהות, תמחק אותם
        if(mFirstCard == mSecondCard){
            ifTheresAMatch();
            if(MainActivity.isMusicOn)  {
                FxManager.PlaySoundFx(FxManager.eSoundEffect.MATCH_FX, getApplicationContext());
            }
            setNewScoreByDifficulty();
        } else {
            mScore--;
            setBackOfCard();
            mNumOfMistakes = mNumOfMistakes-1;
            String mNumOfM = Integer.toString(mNumOfMistakes);
            mLivesTextView.setText(mNumOfM);
        }

        updateScore();
        enableAllPhotos();
        checkIfGameEnds();
    }

    private void setNewScoreByDifficulty() {
        if(MainActivity.lives == 20){
            mScore += 2;
        }
        else if(MainActivity.lives == 12){
            mScore += 5;
        }
        else if(MainActivity.lives == 7){
            mScore += 8;
        }
    }

    private void ifTheresAMatch() {
        if(mClickedFirst==0){
            fadeOutAndHideImage(iv_1);
            iv_1.setVisibility(INVISIBLE);
        }
        else if(mClickedFirst == 1){
            fadeOutAndHideImage(iv_2);
            iv_2.setVisibility(INVISIBLE);
        }
        else if(mClickedFirst == 2){
            fadeOutAndHideImage(iv_3);
            iv_3.setVisibility(INVISIBLE);
        }
        else if(mClickedFirst == 3){
            fadeOutAndHideImage(iv_4);
            iv_4.setVisibility(INVISIBLE);
        }
        else if(mClickedFirst == 4){
            fadeOutAndHideImage(iv_5);
            iv_5.setVisibility(INVISIBLE);
        }
        else if(mClickedFirst == 5){
            fadeOutAndHideImage(iv_6);
            iv_6.setVisibility(INVISIBLE);
        }
        else if(mClickedFirst == 6){
            fadeOutAndHideImage(iv_7);
            iv_7.setVisibility(INVISIBLE);
        }
        else if(mClickedFirst == 7){
            fadeOutAndHideImage(iv_8);
            iv_8.setVisibility(INVISIBLE);
        }
        else if(mClickedFirst == 8){
            fadeOutAndHideImage(iv_9);
            iv_9.setVisibility(INVISIBLE);
        }
        else if(mClickedFirst == 9){
            fadeOutAndHideImage(iv_10);
            iv_10.setVisibility(INVISIBLE);
        }
        else if(mClickedFirst == 10){
            fadeOutAndHideImage(iv_11);
            iv_11.setVisibility(INVISIBLE);
        }
        else if(mClickedFirst == 11){
            fadeOutAndHideImage(iv_12);
            iv_12.setVisibility(INVISIBLE);
        }
        else if(mClickedFirst == 12){
            fadeOutAndHideImage(iv_13);
            iv_13.setVisibility(INVISIBLE);
        }
        else if(mClickedFirst == 13){
            fadeOutAndHideImage(iv_14);
            iv_14.setVisibility(INVISIBLE);
        }
        else if(mClickedFirst == 14){
            fadeOutAndHideImage(iv_15);
            iv_15.setVisibility(INVISIBLE);
        }
        else if(mClickedFirst == 15){
            fadeOutAndHideImage(iv_16);
            iv_16.setVisibility(INVISIBLE);
        }
        else if(mClickedFirst == 16){
            fadeOutAndHideImage(iv_17);
            iv_17.setVisibility(INVISIBLE);
        }
        else if(mClickedFirst == 17){
            fadeOutAndHideImage(iv_18);
            iv_18.setVisibility(INVISIBLE);
        }
        else if(mClickedFirst == 18){
            fadeOutAndHideImage(iv_19);
            iv_19.setVisibility(INVISIBLE);
        }
        else if(mClickedFirst == 19){
            fadeOutAndHideImage(iv_20);
            iv_20.setVisibility(INVISIBLE);
        }

        if(mClickedSecond==0){
            fadeOutAndHideImage(iv_1);
            iv_1.setVisibility(INVISIBLE);
        }
        else if(mClickedSecond == 1){
            fadeOutAndHideImage(iv_2);
            iv_2.setVisibility(INVISIBLE);
        }
        else if(mClickedSecond == 2){
            fadeOutAndHideImage(iv_3);
            iv_3.setVisibility(INVISIBLE);
        }
        else if(mClickedSecond == 3){
            fadeOutAndHideImage(iv_4);
            iv_4.setVisibility(INVISIBLE);
        }
        else if(mClickedSecond == 4){
            fadeOutAndHideImage(iv_5);
            iv_5.setVisibility(INVISIBLE);
        }
        else if(mClickedSecond == 5){
            fadeOutAndHideImage(iv_6);
            iv_6.setVisibility(INVISIBLE);
        }
        else if(mClickedSecond == 6){
            fadeOutAndHideImage(iv_7);
            iv_7.setVisibility(INVISIBLE);
        }
        else if(mClickedSecond == 7){
            fadeOutAndHideImage(iv_8);
            iv_8.setVisibility(INVISIBLE);
        }
        else if(mClickedSecond == 8){
            fadeOutAndHideImage(iv_9);
            iv_9.setVisibility(INVISIBLE);
        }
        else if(mClickedSecond == 9){
            fadeOutAndHideImage(iv_10);
            iv_10.setVisibility(INVISIBLE);
        }
        else if(mClickedSecond == 10){
            fadeOutAndHideImage(iv_11);
            iv_11.setVisibility(INVISIBLE);
        }
        else if(mClickedSecond == 11){
            fadeOutAndHideImage(iv_12);
            iv_12.setVisibility(INVISIBLE);
        }
        else if(mClickedSecond == 12){
            fadeOutAndHideImage(iv_13);
            iv_13.setVisibility(INVISIBLE);
        }
        else if(mClickedSecond == 13){
            fadeOutAndHideImage(iv_14);
            iv_14.setVisibility(INVISIBLE);
        }
        else if(mClickedSecond == 14){
            fadeOutAndHideImage(iv_15);
            iv_15.setVisibility(INVISIBLE);
        }
        else if(mClickedSecond == 15){
            fadeOutAndHideImage(iv_16);
            iv_16.setVisibility(INVISIBLE);
        }
        else if(mClickedSecond == 16){
            fadeOutAndHideImage(iv_17);
            iv_17.setVisibility(INVISIBLE);
        }
        else if(mClickedSecond == 17){
            fadeOutAndHideImage(iv_18);
            iv_18.setVisibility(INVISIBLE);
        }
        else if(mClickedSecond == 18){
            fadeOutAndHideImage(iv_19);
            iv_19.setVisibility(INVISIBLE);
        }
        else if(mClickedSecond == 19){
            fadeOutAndHideImage(iv_20);
            iv_20.setVisibility(INVISIBLE);
        }
    }

    private void setBackOfCard() {
        iv_1.setImageResource(mCurrentLevel.mIcon);
        iv_2.setImageResource(mCurrentLevel.mIcon);
        iv_3.setImageResource(mCurrentLevel.mIcon);
        iv_4.setImageResource(mCurrentLevel.mIcon);
        iv_5.setImageResource(mCurrentLevel.mIcon);
        iv_6.setImageResource(mCurrentLevel.mIcon);
        iv_7.setImageResource(mCurrentLevel.mIcon);
        iv_8.setImageResource(mCurrentLevel.mIcon);
        iv_9.setImageResource(mCurrentLevel.mIcon);
        iv_10.setImageResource(mCurrentLevel.mIcon);
        iv_11.setImageResource(mCurrentLevel.mIcon);
        iv_12.setImageResource(mCurrentLevel.mIcon);
        iv_13.setImageResource(mCurrentLevel.mIcon);
        iv_14.setImageResource(mCurrentLevel.mIcon);
        iv_15.setImageResource(mCurrentLevel.mIcon);
        iv_16.setImageResource(mCurrentLevel.mIcon);
        iv_17.setImageResource(mCurrentLevel.mIcon);
        iv_18.setImageResource(mCurrentLevel.mIcon);
        iv_19.setImageResource(mCurrentLevel.mIcon);
        iv_20.setImageResource(mCurrentLevel.mIcon);
    }

    private void enableAllPhotos() {
        iv_1.setEnabled(true);
        iv_2.setEnabled(true);
        iv_3.setEnabled(true);
        iv_4.setEnabled(true);
        iv_5.setEnabled(true);
        iv_6.setEnabled(true);
        iv_7.setEnabled(true);
        iv_8.setEnabled(true);
        iv_9.setEnabled(true);
        iv_10.setEnabled(true);
        iv_11.setEnabled(true);
        iv_12.setEnabled(true);
        iv_13.setEnabled(true);
        iv_14.setEnabled(true);
        iv_15.setEnabled(true);
        iv_16.setEnabled(true);
        iv_17.setEnabled(true);
        iv_18.setEnabled(true);
        iv_19.setEnabled(true);
        iv_20.setEnabled(true);
    }

    private void checkIfGameEnds() {
        if(     iv_1.getVisibility() == INVISIBLE
                && iv_2.getVisibility() == INVISIBLE
                && iv_3.getVisibility() == INVISIBLE
                && iv_4.getVisibility() == INVISIBLE
                && iv_5.getVisibility() == INVISIBLE
                && iv_6.getVisibility() == INVISIBLE
                && iv_7.getVisibility() == INVISIBLE
                && iv_8.getVisibility() == INVISIBLE
                && iv_9.getVisibility() == INVISIBLE
                && iv_10.getVisibility() == INVISIBLE
                && iv_11.getVisibility() == INVISIBLE
                && iv_12.getVisibility() == INVISIBLE
                && iv_13.getVisibility() == INVISIBLE
                && iv_14.getVisibility() == INVISIBLE
                && iv_15.getVisibility() == INVISIBLE
                && iv_16.getVisibility() == INVISIBLE
                && iv_17.getVisibility() == INVISIBLE
                && iv_18.getVisibility() == INVISIBLE
                && iv_19.getVisibility() == INVISIBLE
                && iv_20.getVisibility() == INVISIBLE
                && mNumOfMistakes > 0
        ) {
            mIsWin = true;
            Toast.makeText(this, getString(R.string.win), Toast.LENGTH_SHORT).show();
            if(MainActivity.mModeGame == 3){
                moveToGameOverActivity(true, mLevelNum,  mNumOfMistakes, mScore);
            }
            else if(MainActivity.mModeGame == 1 || MainActivity.mModeGame == 2){
                moveToGameOverActivity(true, mLevelNum,  numOfMistakesWithoutCounterDown, mScore);
            }
        } else if(mNumOfMistakes == 0){
            mScore = 0;
            Toast.makeText(this, getString(R.string.lose), Toast.LENGTH_SHORT).show();
            moveToGameOverActivity(false, mLevelNum, numOfMistakesWithoutCounterDown, mScore);
        }
    }

    //--------------------------------End of Game Logic---------------------------------------------------

    private void moveToGameOverActivity(final boolean iIsWin, final int iLevelNum, final int  iNumOfMistakes, final int iScore) {
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                Intent intent = new Intent(singel_player.this, GameOver.class);
                intent.putExtra("livesLeft", iNumOfMistakes);
                intent.putExtra("levelNum",iLevelNum);
                intent.putExtra("score",iScore);
                intent.putExtra("win",iIsWin);
                startActivity(intent);
    }

    private void fadeOutAndHideImage(final ImageView img)
    {
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(300);

        fadeOut.setAnimationListener(new Animation.AnimationListener()
        {
            public void onAnimationEnd(Animation animation)
            {
                img.setVisibility(INVISIBLE);
            }
            public void onAnimationRepeat(Animation animation) {}
            public void onAnimationStart(Animation animation) {}
        });

        img.startAnimation(fadeOut);
    }

    private void setLevel() {
        mLevel_textview.setBackground(mCurrentLevel.mLevel);
    }

    private void setResources() {
        mExit = findViewById(R.id.exit_btn);
        zoomAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom);
        mIconImageView = findViewById(R.id.level_civ);
        mConstraintLayout = findViewById(R.id.single_game_main_layout);
        mLevel_textview = findViewById(R.id.level_tv);
        mLivesTextView = findViewById(R.id.single_game_lives_text_view);
        mScoreTextView = findViewById(R.id.score_tv);
        iv_1 = findViewById(R.id.card1);
        iv_2 = findViewById(R.id.card2);
        iv_3 = findViewById(R.id.card3);
        iv_4 = findViewById(R.id.card4);
        iv_5 = findViewById(R.id.card5);
        iv_6 = findViewById(R.id.card6);
        iv_7 = findViewById(R.id.card7);
        iv_8 = findViewById(R.id.card8);
        iv_9 = findViewById(R.id.card9);
        iv_10 = findViewById(R.id.card10);
        iv_11 = findViewById(R.id.card11);
        iv_12 = findViewById(R.id.card12);
        iv_13 = findViewById(R.id.card13);
        iv_14 = findViewById(R.id.card14);
        iv_15 = findViewById(R.id.card15);
        iv_16 = findViewById(R.id.card16);
        iv_17 = findViewById(R.id.card17);
        iv_18 = findViewById(R.id.card18);
        iv_19 = findViewById(R.id.card19);
        iv_20 = findViewById(R.id.card20);

        imageViewsArray[0] = iv_1;
        imageViewsArray[1] = iv_2;
        imageViewsArray[2] = iv_3;
        imageViewsArray[3] = iv_4;
        imageViewsArray[4] = iv_5;
        imageViewsArray[5] = iv_6;
        imageViewsArray[6] = iv_7;
        imageViewsArray[7] = iv_8;
        imageViewsArray[8] = iv_9;
        imageViewsArray[9] = iv_10;
        imageViewsArray[10] = iv_11;
        imageViewsArray[11] = iv_12;
        imageViewsArray[12] = iv_13;
        imageViewsArray[13] = iv_14;
        imageViewsArray[14] = iv_15;
        imageViewsArray[15] = iv_16;
        imageViewsArray[16] = iv_17;
        imageViewsArray[17] = iv_18;
        imageViewsArray[18] = iv_19;
        imageViewsArray[19] = iv_20;

        iv_1.setTag("0");
        iv_2.setTag("1");
        iv_3.setTag("2");
        iv_4.setTag("3");
        iv_5.setTag("4");
        iv_6.setTag("5");
        iv_7.setTag("6");
        iv_8.setTag("7");
        iv_9.setTag("8");
        iv_10.setTag("9");
        iv_11.setTag("10");
        iv_12.setTag("11");
        iv_13.setTag("12");
        iv_14.setTag("13");
        iv_15.setTag("14");
        iv_16.setTag("15");
        iv_17.setTag("16");
        iv_18.setTag("17");
        iv_19.setTag("18");
        iv_20.setTag("19");
        
        frontOfCards();
        Collections.shuffle(Arrays.asList(cardArray));
    }

    private void frontOfCards() {
            image1 = mCurrentLevel.mCards[0];
            image2 = mCurrentLevel.mCards[1];
            image3 = mCurrentLevel.mCards[2];
            image4 = mCurrentLevel.mCards[3];
            image5 = mCurrentLevel.mCards[4];
            image6 = mCurrentLevel.mCards[5];
            image7 = mCurrentLevel.mCards[6];
            image8 = mCurrentLevel.mCards[7];
            image9 = mCurrentLevel.mCards[8];
            image10 = mCurrentLevel.mCards[9];
            image11 = mCurrentLevel.mCards[0];
            image12 = mCurrentLevel.mCards[1];
            image13 = mCurrentLevel.mCards[2];
            image14 = mCurrentLevel.mCards[3];
            image15 = mCurrentLevel.mCards[4];
            image16 = mCurrentLevel.mCards[5];
            image17 = mCurrentLevel.mCards[6];
            image18 = mCurrentLevel.mCards[7];
            image19 = mCurrentLevel.mCards[8];
            image20 = mCurrentLevel.mCards[9];
    }

    private void setIcon() {
        mIconImageView.setImageResource(mCurrentLevel.mIcon);
    }

    private void setBackground() {
        mConstraintLayout.setBackground(mCurrentLevel.mBackground);
        setContentView(mConstraintLayout);
    }

    public void closeGame(View view) {
        if(MainActivity.isMusicOn) {
            FxManager.PlaySoundFx(FxManager.eSoundEffect.CLICK_FX, getApplicationContext());
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(R.string.leave_game_dialog_message)
                .setPositiveButton(R.string.positive_dialog_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(MainActivity.isMusicOn) {
                            FxManager.PlaySoundFx(FxManager.eSoundEffect.CLICK_FX, getApplicationContext());
                        }
                        exitToMainMenu();
                    }
                })
                .setNegativeButton(R.string.negative_dialog_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(MainActivity.isMusicOn) {
                            FxManager.PlaySoundFx(FxManager.eSoundEffect.CLICK_FX, getApplicationContext());
                        }
                        dialogInterface.dismiss();
                    }
                }).setCancelable(false).create().show();
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
