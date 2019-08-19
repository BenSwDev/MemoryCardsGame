package com.example.memorycardsgame;

public class User {
    private String mName;
    private int mScore;

    User(String iName, int iScore){
        mScore = iScore;
        mName = iName;
    }

    public int getmScore() {
        return mScore;
    }

    public String getmName() {
        return mName;
    }
}
