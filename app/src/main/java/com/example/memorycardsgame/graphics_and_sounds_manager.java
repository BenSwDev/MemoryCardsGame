package com.example.memorycardsgame;


import android.content.Context;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;

public class graphics_and_sounds_manager {
    private static MediaPlayer mMediaPlayer;

    private static eMusicState mMusicState;

    private static int mCurrentSongId;

    private static float mLeftVolume = 0.5f;
    private static float mRightVolume = 0.5f;

    public static void InitMediaPlayer(Context context, eSongTypes songType){

        if(mMediaPlayer != null){
            mMediaPlayer.release();
        }

        getSongResId(songType);
        mMediaPlayer = MediaPlayer.create(context, mCurrentSongId);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.setVolume(mLeftVolume, mRightVolume);
    }

    public static void Play(Context context){
        if(PreferenceManager.getDefaultSharedPreferences(context).getBoolean("pref_key_music", true)){
            mMediaPlayer.start();

            if(mMusicState != eMusicState.BETWEEN_ACTIVITIES) {
                mMusicState = eMusicState.PLAYING;
            }
        }
    }

    public static void Pause(){
        if(mMusicState == eMusicState.PLAYING){
            mMediaPlayer.pause();
            mMusicState = eMusicState.PAUSED;
        }
    }

    public static void Resume(){
        mMediaPlayer.start();
        mMusicState = eMusicState.PLAYING;
    }

    public static eMusicState getMusicState() {
        return mMusicState;
    }

    public static void setMusicState(eMusicState mMusicState) {
        graphics_and_sounds_manager.mMusicState = mMusicState;
    }

    private static void getSongResId(eSongTypes songType) {

        switch (songType){
            default:
            case MAIN_SONG: mCurrentSongId = R.raw.uefa_champions_league_anthem; break;
            case ISRAEL: mCurrentSongId = R.raw.israel__anthem; break;
            case ENGLAND: mCurrentSongId = R.raw.england_anthem; break;
            case SPAIN: mCurrentSongId = R.raw.spain_anthem; break;
            case ITALY: mCurrentSongId = R.raw.italy_anthem; break;
            case GERMANY: mCurrentSongId = R.raw.german_anthem; break;
            case FRANCE: mCurrentSongId = R.raw.france_anthem; break;
            case HOLLAND: mCurrentSongId = R.raw.netherlands_anthem; break;
            case GREECE: mCurrentSongId = R.raw.greece_anthem; break;
            case BELGIUM: mCurrentSongId = R.raw.belgium_anthem; break;
            case ARGENTINA: mCurrentSongId = R.raw.argentina_anthem; break;
            case WORLD: mCurrentSongId = R.raw.world_cup_anthem; break;
        }
    }

    public enum eSongTypes{
        MAIN_SONG,
        ISRAEL,
        ENGLAND,
        SPAIN,
        ITALY,
        GERMANY,
        FRANCE,
        HOLLAND,
        GREECE,
        BELGIUM,
        ARGENTINA,
        WORLD
    }

    public enum eMusicState{
        PLAYING,
        PAUSED,
        BETWEEN_ACTIVITIES,
    }

}
