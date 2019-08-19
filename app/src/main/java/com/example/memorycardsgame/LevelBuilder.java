package com.example.memorycardsgame;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;

public class LevelBuilder {

    private Context context;
    private TypedArray mIcons, mBackgrounds;
    public Integer[] mCards;
    public Drawable mBackground, mLevel;
    int mIcon;
    public int mSong;


    LevelBuilder(int iLevel, Context current){
        this.context = current;
        SetBackGround(iLevel);
        returnCardsArrays(iLevel);
        returnIcon(iLevel);
        SetLevelPhoto(iLevel);
    }

    private void SetLevelPhoto(int iType) {
        switch (iType) {
            case 0:
                mLevel = context.getResources().getDrawable(R.drawable.level1);
                break;
            case 1:
                mLevel = context.getResources().getDrawable(R.drawable.level2);
                break;
            case 2:
                mLevel = context.getResources().getDrawable(R.drawable.level3);
                break;
            case 3:
                mLevel = context.getResources().getDrawable(R.drawable.level4);
                break;
            case 4:
                mLevel = context.getResources().getDrawable(R.drawable.level5);
                break;
            case 5:
                mLevel = context.getResources().getDrawable(R.drawable.level6);
                break;
            case 6:
                mLevel = context.getResources().getDrawable(R.drawable.level7);
                break;
            case 7:
                mLevel = context.getResources().getDrawable(R.drawable.level8);
                break;
            case 8:
                mLevel = context.getResources().getDrawable(R.drawable.level9);
                break;
            case 9:
                mLevel = context.getResources().getDrawable(R.drawable.level10);
                break;
            case 10:
                mLevel = context.getResources().getDrawable(R.drawable.level11);
                break;
        }
    }
    private void SetBackGround(int iType) {
        mBackgrounds = context.getResources().obtainTypedArray(R.array.backgrounds_array);
        switch (iType) {
            case 0:
                mBackground = mBackgrounds.getDrawable(0);
                break;
            case 1:
                mBackground = mBackgrounds.getDrawable(mBackgrounds.length() - 10);
                break;
            case 2:
                mBackground = mBackgrounds.getDrawable(mBackgrounds.length() - 9);
                break;
            case 3:
                mBackground = mBackgrounds.getDrawable(mBackgrounds.length() - 8);
                break;
            case 4:
                mBackground = mBackgrounds.getDrawable(mBackgrounds.length() - 7);
                break;
            case 5:
                mBackground = mBackgrounds.getDrawable(mBackgrounds.length() - 6);
                break;
            case 6:
                mBackground = mBackgrounds.getDrawable(mBackgrounds.length() - 5);
                break;
            case 7:
                mBackground = mBackgrounds.getDrawable(mBackgrounds.length() - 4);
                break;
            case 8:
                mBackground = mBackgrounds.getDrawable(mBackgrounds.length() - 3);
                break;
            case 9:
                mBackground = mBackgrounds.getDrawable(mBackgrounds.length() - 2);
                break;
            case 10:
                mBackground = mBackgrounds.getDrawable(mBackgrounds.length() - 1);
                break;
        }
    }
    private void returnCardsArrays(int iType){
        switch (iType) {
            case 0:
                mCards = new Integer[]{R.drawable.il_card1,R.drawable.il_card2,R.drawable.il_card3,R.drawable.il_card4,R.drawable.il_card5,
                        R.drawable.il_card6, R.drawable.il_card7,R.drawable.il_card8,R.drawable.il_card9,R.drawable.il_card10};
            break;
            case 1:
                mCards = new Integer[]{R.drawable.en_card1,R.drawable.en_card2,R.drawable.en_card3,R.drawable.en_card4,R.drawable.en_card5,
                        R.drawable.en_card6, R.drawable.en_card7,R.drawable.en_card8,R.drawable.en_card9,R.drawable.en_card10};
                break;
            case 2:
                mCards = new Integer[]{R.drawable.sp_card1,R.drawable.sp_card2,R.drawable.sp_card3,R.drawable.sp_card4,R.drawable.sp_card5,
                        R.drawable.sp_card6, R.drawable.sp_card7,R.drawable.sp_card8,R.drawable.sp_card9,R.drawable.sp_card10};
                break;
            case 3:
                mCards = new Integer[]{R.drawable.it_card1,R.drawable.it_card2,R.drawable.it_card3,R.drawable.it_card4,R.drawable.it_card5,
                        R.drawable.it_card6, R.drawable.it_card7,R.drawable.it_card8,R.drawable.it_card9,R.drawable.it_card10};
                break;
            case 4:
                mCards = new Integer[]{R.drawable.ge_card1,R.drawable.ge_card2,R.drawable.ge_card3,R.drawable.ge_card4,R.drawable.ge_card5,
                        R.drawable.ge_card6, R.drawable.ge_card7,R.drawable.ge_card8,R.drawable.ge_card9,R.drawable.ge_card10};
                break;
            case 5:
                mCards = new Integer[]{R.drawable.fr_card1,R.drawable.fr_card2,R.drawable.fr_card3,R.drawable.fr_card4,R.drawable.fr_card5,
                        R.drawable.fr_card6, R.drawable.fr_card7,R.drawable.fr_card8,R.drawable.fr_card9,R.drawable.fr_card10};
                break;
            case 6:
                mCards = new Integer[]{R.drawable.ne_card1,R.drawable.ne_card2,R.drawable.ne_card3,R.drawable.ne_card4,R.drawable.ne_card5,
                        R.drawable.ne_card6, R.drawable.ne_card7,R.drawable.ne_card8,R.drawable.ne_card9,R.drawable.ne_card10};
                break;
            case 7:
                mCards = new Integer[]{R.drawable.gr_card1,R.drawable.gr_card2,R.drawable.gr_card3,R.drawable.gr_card4,R.drawable.gr_card5,
                        R.drawable.gr_card6, R.drawable.gr_card7,R.drawable.gr_card8,R.drawable.gr_card9,R.drawable.gr_card10};
                break;
            case 8:
                mCards = new Integer[]{R.drawable.be_card1,R.drawable.be_card2,R.drawable.be_card3,R.drawable.be_card4,R.drawable.be_card5,
                        R.drawable.be_card6, R.drawable.be_card7,R.drawable.be_card8,R.drawable.be_card9,R.drawable.be_card10};
                break;
            case 9:
                mCards = new Integer[]{R.drawable.ar_card1,R.drawable.ar_card2,R.drawable.ar_card3,R.drawable.ar_card4,R.drawable.ar_card5,
                        R.drawable.ar_card6, R.drawable.ar_card7,R.drawable.ar_card8,R.drawable.ar_card9,R.drawable.ar_card10};
                break;
            case 10:
                mCards = new Integer[]{R.drawable.wc_card1,R.drawable.wc_card2,R.drawable.wc_card3,R.drawable.wc_card4,R.drawable.wc_card5,
                        R.drawable.wc_card6, R.drawable.wc_card7,R.drawable.wc_card8,R.drawable.wc_card9,R.drawable.wc_card10};
                break;
        }
    }
    private void returnIcon(int iType){
        mIcons = context.getResources().obtainTypedArray(R.array.cards_array);
        switch (iType) {
            case 0:
                mIcon = mIcons.getResourceId(0,-1);
                break;
            case 1:
                mIcon = mIcons.getResourceId(mIcons.length()-10,-1);
                break;
            case 2:
                mIcon = mIcons.getResourceId(mIcons.length()-9,-1);
                break;
            case 3:
                mIcon = mIcons.getResourceId(mIcons.length()-8,-1);
                break;
            case 4:
                mIcon = mIcons.getResourceId(mIcons.length()-7,-1);
                break;
            case 5:
                mIcon = mIcons.getResourceId(mIcons.length()-6,-1);
                break;
            case 6:
                mIcon = mIcons.getResourceId(mIcons.length()-5,-1);
                break;
            case 7:
                mIcon = mIcons.getResourceId(mIcons.length()-4,-1);
                break;
            case 8:
                mIcon = mIcons.getResourceId(mIcons.length()-3,-1);
                break;
            case 9:
                mIcon = mIcons.getResourceId(mIcons.length()-2,-1);
                break;
            case 10:
                mIcon = mIcons.getResourceId(mIcons.length()-1,-1);
                break;
        }

    }




}
