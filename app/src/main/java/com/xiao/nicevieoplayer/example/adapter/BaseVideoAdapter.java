package com.xiao.nicevieoplayer.example.adapter;

import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.TxVideoPlayerController;
import com.xiao.nicevieoplayer.R;
import com.xiao.nicevieoplayer.example.bean.Video;

import java.util.List;

/**
 * Created by XiaoJianjun on 2017/5/21.
 */

public class BaseVideoAdapter extends BaseQuickAdapter<Video,BaseViewHolder> {

    public BaseVideoAdapter(@Nullable List<Video> data) {
        super(R.layout.item_view_vedio,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Video item) {
        TxVideoPlayerController controller = new TxVideoPlayerController(mContext);
        NiceVideoPlayer mVideoPlayer = helper.getView(R.id.player_video);

        ViewGroup.LayoutParams params = mVideoPlayer.getLayoutParams();
        params.width = helper.itemView.getResources().getDisplayMetrics().widthPixels; // 宽度为屏幕宽度
        params.height = (int) (params.width * 9f / 16f);    // 高度为宽度的9/16
        mVideoPlayer.setLayoutParams(params);

        controller.setTitle(item.getTitle());
        controller.setLenght(item.getLength());
        mVideoPlayer.setController(controller);
        Glide.with(mContext)
                .load(item.getImageUrl())
                .placeholder(R.drawable.img_default)
                .crossFade()
                .into(controller.imageView());
        mVideoPlayer.setUp(item.getVideoUrl(), null);
    }

//    public VideoViewHolder(View itemView) {
//        super(itemView);
//        mVideoPlayer = (NiceVideoPlayer) itemView.findViewById(R.id.nice_video_player);
//        // 将列表中的每个视频设置为默认16:9的比例
//        ViewGroup.LayoutParams params = mVideoPlayer.getLayoutParams();
//        params.width = itemView.getResources().getDisplayMetrics().widthPixels; // 宽度为屏幕宽度
//        params.height = (int) (params.width * 9f / 16f);    // 高度为宽度的9/16
//        mVideoPlayer.setLayoutParams(params);
//    }
//
//    public void setController(TxVideoPlayerController controller) {
//        mController = controller;
//        mVideoPlayer.setController(mController);
//    }
//
//    public void bindData(Video video) {
//        mController.setTitle(video.getTitle());
//        mController.setLenght(video.getLength());
//        Glide.with(itemView.getContext())
//                .load(video.getImageUrl())
//                .placeholder(R.drawable.img_default)
//                .crossFade()
//                .into(mController.imageView());
//        mVideoPlayer.setUp(video.getVideoUrl(), null);
//    }
}
