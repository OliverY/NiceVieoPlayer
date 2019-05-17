package com.xiao.nicevideoplayer;

/**
 * Created by XiaoJianjun on 2017/5/5.
 * 视频播放器管理器.
 */
public class NiceMediaPlayerManager {

    private PlayerView mPlayer;

    private NiceMediaPlayerManager() {
    }

    private static NiceMediaPlayerManager sInstance;

    public static synchronized NiceMediaPlayerManager instance() {
        if (sInstance == null) {
            sInstance = new NiceMediaPlayerManager();
        }
        return sInstance;
    }

    public PlayerView getCurrentNiceVideoPlayer() {
        return mPlayer;
    }

    public void setCurrentNiceVideoPlayer(PlayerView playerView) {
        if (mPlayer != playerView) {
            releaseNiceVideoPlayer();
            mPlayer = playerView;
        }
    }

    public void suspendNiceVideoPlayer() {
        if(mPlayer != null){
            if(mPlayer instanceof NiceVideoPlayer){
                if(((NiceVideoPlayer) mPlayer).isPlaying() || ((NiceVideoPlayer) mPlayer).isBufferingPlaying()){
                    ((NiceVideoPlayer) mPlayer).pause();
                }
            }else if(mPlayer instanceof NiceAudioPlayer){
                if(((NiceAudioPlayer) mPlayer).isPlaying() || ((NiceAudioPlayer) mPlayer).isBufferingPlaying()){
                    ((NiceAudioPlayer) mPlayer).pause();
                }
            }
        }
    }

    public void resumeNiceVideoPlayer() {
        if(mPlayer != null){
            if(mPlayer instanceof NiceVideoPlayer){
                if(((NiceVideoPlayer) mPlayer).isPaused() || ((NiceVideoPlayer) mPlayer).isBufferingPaused()){
                    ((NiceVideoPlayer) mPlayer).restart();
                }
            }else if(mPlayer instanceof NiceAudioPlayer){
                if(((NiceAudioPlayer) mPlayer).isPaused() || ((NiceAudioPlayer) mPlayer).isBufferingPaused()){
                    ((NiceAudioPlayer) mPlayer).restart();
                }
            }
        }
    }

    public void releaseNiceVideoPlayer() {
        if (mPlayer != null) {
            if(mPlayer instanceof NiceVideoPlayer){
                ((NiceVideoPlayer) mPlayer).release();
            }else if(mPlayer instanceof NiceAudioPlayer){
                ((NiceAudioPlayer) mPlayer).release();
            }
            mPlayer = null;
        }
    }

    public boolean onBackPressd() {
        if (mPlayer != null && mPlayer instanceof NiceVideoPlayer) {
            if (((NiceVideoPlayer) mPlayer).isFullScreen()) {
                return ((NiceVideoPlayer) mPlayer).exitFullScreen();
            } else if (((NiceVideoPlayer) mPlayer).isTinyWindow()) {
                return ((NiceVideoPlayer) mPlayer).exitTinyWindow();
            }
        }
        return false;
    }
}
