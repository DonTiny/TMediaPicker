package com.aeolou.digital.media.android.tmediapicke.provider;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.aeolou.digital.media.android.tmediapicke.receiver.ScanSdReceiver;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Aeolou
 * Date:2020/4/23 0023
 * Email:tg0804013x@gmail.com
 */
public class ContextManager {
    @SuppressLint("StaticFieldLeak")
    private static volatile ContextManager instance;
    private Context mContext;
    private List<OnScanSDListener> onScanSDListenerList;

    private ContextManager(Context context) {
        mContext = context;
        onScanSDListenerList = new ArrayList<>();
        IntentFilter intentfilter = new IntentFilter(Intent.ACTION_MEDIA_SCANNER_STARTED);
        intentfilter.addAction(Intent.ACTION_MEDIA_SCANNER_FINISHED);
        intentfilter.addDataScheme("file");
        ScanSdReceiver scanSdReceiver = new ScanSdReceiver(onScanSDListenerList);
        mContext.registerReceiver(scanSdReceiver, intentfilter);
    }

    /**
     * 获取实例
     */
    public static ContextManager get() {
        if (instance == null) {
            synchronized (ContextManager.class) {
                if (instance == null) {
                    Context context = ApplicationContextProvider.mContext;
                    if (context == null) {
                        throw new IllegalStateException("context == null");
                    }
                    instance = new ContextManager(context);
                }
            }
        }
        return instance;
    }

    /**
     * 获取上下文
     */
    public Context getContext() {
        return mContext;
    }

    public Application getApplication() {
        return (Application) mContext.getApplicationContext();
    }

    public void addOnScanSDListenerList(OnScanSDListener listener) {
        if (!onScanSDListenerList.contains(listener)) {
            onScanSDListenerList.add(listener);
        }
    }

    public void removeOnScanSDListenerList(OnScanSDListener listener) {
        if (onScanSDListenerList.contains(listener)) {
            onScanSDListenerList.remove(listener);
        }
    }

    public interface OnScanSDListener {
        void onScanStarted();

        void onScanFinished();

    }

}
