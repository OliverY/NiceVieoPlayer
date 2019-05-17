package com.xiao.nicevideoplayer;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by XiaoJianjun on 2017/6/21.
 * 仿腾讯视频热点列表页播放器控制器.
 */
public class TxAudioPlayerController
        extends NiceAudioPlayerController
        implements View.OnClickListener,
        SeekBar.OnSeekBarChangeListener{

    private Context mContext;
    private TextView mPosition;
    private ImageView mBtnPlay;
    private TextView mDuration;
    private SeekBar mSeek;
    private ImageView mLoading;

    public TxAudioPlayerController(Context context) {
        super(context);
        mContext = context;
        init();
    }

    private void init() {
        LayoutInflater.from(mContext).inflate(R.layout.tx_audio_player_controller, this, true);

        mBtnPlay = (ImageView) findViewById(R.id.btn_play);
        mLoading = (ImageView) findViewById(R.id.image_loading);
        mPosition = (TextView) findViewById(R.id.tv_current);
        mDuration = (TextView) findViewById(R.id.tv_duration);
        mSeek = (SeekBar) findViewById(R.id.seek_bar);

        mBtnPlay.setOnClickListener(this);
        mSeek.setOnSeekBarChangeListener(this);
        this.setOnClickListener(this);
    }

    @Override
    public void setLenght(long length) {
        mDuration.setText(NiceUtil.formatTime(length));
    }

    @Override
    protected void onPlayStateChanged(int playState) {
        switch (playState) {
            case NiceVideoPlayer.STATE_IDLE:
                Log.e("yxj","NiceVideoPlayer.STATE_IDLE");
                mBtnPlay.setVisibility(View.VISIBLE);
                mLoading.setVisibility(View.GONE);
                mSeek.setEnabled(false);
                mBtnPlay.setImageResource(R.drawable.icon_audio_play_start);
                break;
            case NiceVideoPlayer.STATE_PREPARING:
                Log.e("yxj","NiceVideoPlayer.STATE_PREPARING");
                /*
                 btnPlay gone
                 loading visible
                 seekbar unable
                  */
                mBtnPlay.setVisibility(View.GONE);
                mLoading.setVisibility(View.VISIBLE);
                mSeek.setEnabled(false);

                break;
            case NiceVideoPlayer.STATE_PREPARED:
                Log.e("yxj","NiceVideoPlayer.STATE_PREPARED");
                startUpdateProgressTimer();
                break;
            case NiceVideoPlayer.STATE_PLAYING:
                Log.e("yxj","NiceVideoPlayer.STATE_PLAYING");
                /*
                 btnPlay visible pause
                 loading gone
                 seekbar enable
                  */

                mBtnPlay.setVisibility(View.VISIBLE);
                mBtnPlay.setImageResource(R.drawable.icon_audio_play_pause);
                mLoading.setVisibility(View.GONE);
                mSeek.setEnabled(true);

                break;
            case NiceVideoPlayer.STATE_PAUSED:
                Log.e("yxj","NiceVideoPlayer.STATE_PAUSED");
                /*
                 btnPlay visible play
                 loading gone
                 seekbar unable
                  */

                mBtnPlay.setVisibility(View.VISIBLE);
                mBtnPlay.setImageResource(R.drawable.icon_audio_play_start);
                mLoading.setVisibility(View.GONE);
                mSeek.setEnabled(true);

                break;
            case NiceVideoPlayer.STATE_BUFFERING_PLAYING:
                Log.e("yxj","NiceVideoPlayer.STATE_BUFFERING_PLAYING");
                /*
                 btnPlay gone
                 loading visible
                 seekbar unable
                  */

                mBtnPlay.setVisibility(View.GONE);
                mLoading.setVisibility(View.VISIBLE);
                mSeek.setEnabled(false);

                break;
            case NiceVideoPlayer.STATE_BUFFERING_PAUSED:
                Log.e("yxj","NiceVideoPlayer.STATE_BUFFERING_PAUSED");
                /*
                 btnPlay gone
                 loading visible
                 seekbar unable
                  */

                mBtnPlay.setVisibility(View.GONE);
                mLoading.setVisibility(View.VISIBLE);
                mSeek.setEnabled(false);

                break;
            case NiceVideoPlayer.STATE_ERROR:
                Log.e("yxj","NiceVideoPlayer.STATE_ERROR");
                cancelUpdateProgressTimer();

                mBtnPlay.setVisibility(View.GONE);
                mLoading.setVisibility(View.VISIBLE);
                mSeek.setEnabled(false);
                break;
            case NiceVideoPlayer.STATE_COMPLETED:
                Log.e("yxj","NiceVideoPlayer.STATE_COMPLETED");
                cancelUpdateProgressTimer();

                /*
                 btnPlay visible play
                 loading gone
                 seekbar unable
                  */
                mBtnPlay.setVisibility(View.VISIBLE);
                mBtnPlay.setImageResource(R.drawable.icon_audio_play_start);
                mLoading.setVisibility(View.GONE);
                mPosition.setText(NiceUtil.formatTime(0));
                mSeek.setProgress(0);
                mSeek.setEnabled(false);
                break;
        }
    }

    @Override
    protected void reset() {
        cancelUpdateProgressTimer();
        mSeek.setProgress(0);
        mSeek.setSecondaryProgress(0);
        mPosition.setText(NiceUtil.formatTime(0));
        
        /*
         btnPlay visible play
         loading gone
         seekbar unable
          */
        mBtnPlay.setVisibility(View.VISIBLE);
        mBtnPlay.setImageResource(R.drawable.icon_audio_play_start);
        mLoading.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        if (v == mBtnPlay) {
            if (mNiceAudioPlayer.isIdle()) {
                mNiceAudioPlayer.start();
            } else if(mNiceAudioPlayer.isPlaying()){
                mNiceAudioPlayer.pause();
            } else if(mNiceAudioPlayer.isPaused() || mNiceAudioPlayer.isCompleted()){
                mNiceAudioPlayer.restart();
            }
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (mNiceAudioPlayer.isBufferingPaused() || mNiceAudioPlayer.isPaused()) {
            mNiceAudioPlayer.restart();
        }
        long position = (long) (mNiceAudioPlayer.getDuration() * seekBar.getProgress() / 100f);
        mNiceAudioPlayer.seekTo(position);
    }

    @Override
    protected void updateProgress() {
        long position = mNiceAudioPlayer.getCurrentPosition();
        long duration = mNiceAudioPlayer.getDuration();
        int bufferPercentage = mNiceAudioPlayer.getBufferPercentage();
        mSeek.setSecondaryProgress(bufferPercentage);
        int progress = (int) (100f * position / duration);
        mSeek.setProgress(progress);
        mPosition.setText(NiceUtil.formatTime(position));
        mDuration.setText(NiceUtil.formatTime(duration));
    }
}
