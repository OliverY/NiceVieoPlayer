package com.xiao.nicevieoplayer.example.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Author:  Yxj
 * Time:    2019/5/17 上午9:12
 * -----------------------------------------
 * Description:
 */
public class Text implements MultiItemEntity {
    @Override
    public int getItemType() {
        return Constant.TYPE_TEXT;
    }
}
