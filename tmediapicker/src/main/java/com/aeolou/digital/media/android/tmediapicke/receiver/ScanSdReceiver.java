package com.aeolou.digital.media.android.tmediapicke.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.aeolou.digital.media.android.tmediapicke.provider.ContextManager;
import com.aeolou.digital.media.android.tmediapicke.utils.LogUtils;

import java.util.List;

/**
 * Author: Aeolou
 * Date:2020/4/23 0023
 * Email:tg0804013x@gmail.com
 */
public class ScanSdReceiver extends BroadcastReceiver {
    private List<ContextManager.OnScanSDListener> onScanSDListenerList;

    public ScanSdReceiver(@NonNull List<ContextManager.OnScanSDListener> onScanSDListenerList) {
        this.onScanSDListenerList = onScanSDListenerList;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (Intent.ACTION_MEDIA_SCANNER_STARTED.equals(action)) {
            LogUtils.i("正在扫描");
            for (ContextManager.OnScanSDListener listener : onScanSDListenerList) {
                listener.onScanStarted();
            }

        } else if (Intent.ACTION_MEDIA_SCANNER_FINISHED.equals(action)) {
            LogUtils.i("结束扫描");
            for (ContextManager.OnScanSDListener listener : onScanSDListenerList) {
                listener.onScanFinished();
            }
        }
    }
}
