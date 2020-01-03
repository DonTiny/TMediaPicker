package com.aeolou.digital.media.android.tmediapicke.manager;

import android.content.Context;

import com.aeolou.digital.media.android.tmediapicke.loader.LoaderMediaType;

import java.lang.ref.WeakReference;

/**
 * Author: Aeolou
 * Date:2019/12/23 0003
 * Email:tg0804013x@gmail.com
 */
public class TMediaDataBuilder {
    private LoaderMediaType loaderMediaType;
    private Context mContext;

    public TMediaDataBuilder(Context mContext) {
        this.mContext = mContext;
    }

    public TMediaDataBuilder setDefLoaderMediaType(LoaderMediaType loaderMediaType) {
        this.loaderMediaType = loaderMediaType;
        return this;
    }

    public TMediaData build() {
        if (loaderMediaType == null) loaderMediaType = LoaderMediaType.PHOTO;//默认加载模式为所有照片片
        MediaCollection mediaCollection = new MediaCollection(new WeakReference<Context>(mContext), loaderMediaType);
        TMediaData tMediaData = new TMediaData(mediaCollection);
        return tMediaData;
    }
}
