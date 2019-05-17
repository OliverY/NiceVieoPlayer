package com.xiao.nicevieoplayer.example.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Author:  Yxj
 * Time:    2019/5/16 下午3:59
 * -----------------------------------------
 * Description:
 */
public class Audio implements MultiItemEntity {

    private long length;
    private String videoUrl;

    public Audio(long length, String videoUrl) {
        this.length = length;
        this.videoUrl = videoUrl;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    @Override
    public int getItemType() {
        return Constant.TYPE_AUDIO;
    }
}
