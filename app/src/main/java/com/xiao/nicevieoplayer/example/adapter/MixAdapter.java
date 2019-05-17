package com.xiao.nicevieoplayer.example.adapter;

import android.content.Intent;
import android.view.View;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xiao.nicevideoplayer.NiceAudioPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.TxVideoPlayerController;
import com.xiao.nicevieoplayer.R;
import com.xiao.nicevieoplayer.example.MixRecyclerViewActivity;
import com.xiao.nicevieoplayer.example.bean.Audio;
import com.xiao.nicevieoplayer.example.bean.Constant;
import com.xiao.nicevieoplayer.example.bean.Text;
import com.xiao.nicevieoplayer.example.bean.Video;

import java.util.List;

/**
 * Created by XiaoJianjun on 2017/5/21.
 */

public class MixAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity,BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public MixAdapter(List<? extends MultiItemEntity> data) {
        super((List<MultiItemEntity>) data);
        addItemType(Constant.TYPE_VIDEO, R.layout.item_view_vedio);
        addItemType(Constant.TYPE_AUDIO, R.layout.item_view_audio);
        addItemType(Constant.TYPE_TEXT, R.layout.item_view_text);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        if(item instanceof Video){
            renderVideo(helper, (Video) item);
        }else if(item instanceof Audio){
            renderAudio(helper, (Audio) item);
        }else if(item instanceof Text){
            renderText(helper,(Text) item);
        }
    }

    private void renderText(BaseViewHolder helper, Text item) {
        helper.getView(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, MixRecyclerViewActivity.class));
            }
        });
    }

    private void renderAudio(BaseViewHolder helper, Audio item) {
        NiceAudioPlayer playerView = helper.getView(R.id.audio_view);
        playerView.setUp(item.getVideoUrl(),null);
    }

    private void renderVideo(BaseViewHolder helper, Video item) {

        TxVideoPlayerController controller = new TxVideoPlayerController(mContext);
        NiceVideoPlayer videoPlayer = helper.getView(R.id.player_video);
        videoPlayer.setController(controller);

        controller.setTitle(item.getTitle());
        controller.setLenght(item.getLength());
        Glide.with(mContext)
                .load(item.getImageUrl())
                .placeholder(R.drawable.img_default)
                .crossFade()
                .into(controller.imageView());
        videoPlayer.setUp(item.getVideoUrl(), null);

    }

}
