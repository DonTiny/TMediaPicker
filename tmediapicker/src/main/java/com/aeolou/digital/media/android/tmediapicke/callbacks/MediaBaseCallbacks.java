package com.aeolou.digital.media.android.tmediapicke.callbacks;

/**
 * Author: Aeolou
 * Date:2019/12/23 0003
 * Email:tg0804013x@gmail.com
 */
public interface MediaBaseCallbacks {

    //开始加载
    void onStarted();


    //加载失败
    void onError(Throwable throwable);
}
