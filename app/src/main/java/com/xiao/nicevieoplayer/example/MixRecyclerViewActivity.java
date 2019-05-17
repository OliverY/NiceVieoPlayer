package com.xiao.nicevieoplayer.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xiao.nicevideoplayer.NiceAudioPlayer;
import com.xiao.nicevideoplayer.NiceMediaPlayerManager;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevieoplayer.R;
import com.xiao.nicevieoplayer.example.adapter.MixAdapter;
import com.xiao.nicevieoplayer.example.bean.Constant;
import com.xiao.nicevieoplayer.example.util.DataUtil;

import java.util.List;

public class MixRecyclerViewActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<MultiItemEntity> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        init();
    }

    private void init() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        list = DataUtil.getMixData();
        final MixAdapter adapter = new MixAdapter(DataUtil.getMixData());
        mRecyclerView.setAdapter(adapter);

        mRecyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                RecyclerView.ViewHolder vh = mRecyclerView.getChildViewHolder(view);

                int position = vh.getLayoutPosition();
                MultiItemEntity item = list.get(position);
                if(item.getItemType() == Constant.TYPE_AUDIO){
                    NiceAudioPlayer videoPlayer = ((BaseViewHolder)vh).getView(R.id.audio_view);
                    if (videoPlayer == NiceMediaPlayerManager.instance().getCurrentNiceVideoPlayer()) {
                        NiceMediaPlayerManager.instance().releaseNiceVideoPlayer();
                    }
                }else if(item.getItemType() == Constant.TYPE_VIDEO){
                    NiceVideoPlayer videoPlayer = ((BaseViewHolder)vh).getView(R.id.player_video);
                    if (videoPlayer == NiceMediaPlayerManager.instance().getCurrentNiceVideoPlayer()) {
                        NiceMediaPlayerManager.instance().releaseNiceVideoPlayer();
                    }
                }
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        NiceMediaPlayerManager.instance().releaseNiceVideoPlayer();
    }

    @Override
    public void onBackPressed() {
        if (NiceMediaPlayerManager.instance().onBackPressd()) return;
        super.onBackPressed();
    }
}
