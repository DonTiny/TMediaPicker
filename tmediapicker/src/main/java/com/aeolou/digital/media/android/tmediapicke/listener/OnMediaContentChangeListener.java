package com.aeolou.digital.media.android.tmediapicke.listener;

import android.net.Uri;

/**
 * Author: Aeolou
 * Date:2020/4/28 0028
 * Email:tg0804013x@gmail.com
 */
public interface OnMediaContentChangeListener {

    void onChange(boolean selfChange, Uri uri);

}