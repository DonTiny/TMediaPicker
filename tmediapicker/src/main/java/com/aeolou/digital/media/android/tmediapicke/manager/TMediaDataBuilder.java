package com.aeolou.digital.media.android.tmediapicke.manager;

import com.aeolou.digital.media.android.tmediapicke.helpers.LoaderMediaType;
import com.aeolou.digital.media.android.tmediapicke.helpers.LoaderStorageType;
import com.aeolou.digital.media.android.tmediapicke.provider.ContextManager;

/**
 * Author: Aeolou
 * Date:2019/12/23 0003
 * Email:tg0804013x@gmail.com
 */
public class TMediaDataBuilder {
    private LoaderMediaType loaderMediaType;
    private LoaderStorageType loaderStorageType;

    public TMediaDataBuilder() {
    }

    public TMediaDataBuilder setLoaderMediaType(LoaderMediaType loaderMediaType) {
        this.loaderMediaType = loaderMediaType;
        return this;
    }

    public TMediaDataBuilder setLoaderStorageType(LoaderStorageType loaderStorageType) {
        this.loaderStorageType = loaderStorageType;
        return this;
    }


    public TMediaData build() {
        if (loaderMediaType == null) loaderMediaType = LoaderMediaType.PHOTO;//默认加载模式为所有照片片
        if (loaderStorageType == null) loaderStorageType = LoaderStorageType.ALL;//默认加载模式为所有照片片
        MediaCollection mediaCollection = new MediaCollection(ContextManager.get().getContext(), loaderMediaType, loaderStorageType);
        TMediaData tMediaData = new TMediaData(mediaCollection);
        return tMediaData;
    }
}
