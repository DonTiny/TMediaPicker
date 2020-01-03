package com.aeolou.digital.media.android.tmediapicke.base;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.aeolou.digital.media.android.tmediapicke.R;
import com.aeolou.digital.media.android.tmediapicke.helpers.TConstants;
import com.aeolou.digital.media.android.tmediapicke.utils.GsonUtil;
import com.aeolou.digital.media.android.tmediapicke.utils.LogUtils;
import com.google.android.material.snackbar.Snackbar;


public abstract class TBaseActivity extends AppCompatActivity {
    protected Context mContext = null;
    private final String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            getBundleExtras(extras);
        }
        if (getContentViewLayoutID() != 0) {
            setContentView(getContentViewLayoutID());
        } else {
            throw new IllegalArgumentException("You must return a right contentView layout resource Id");
        }
        init(savedInstanceState);
        initView();
        initData();
        initListener();
    }

    /**
     * get bundle data
     *
     * @param extras
     */
    protected abstract void getBundleExtras(Bundle extras);

    /**
     * bind layout resource file
     *
     * @return id of layout resource
     */
    protected abstract int getContentViewLayoutID();

    /**
     * init
     */
    protected abstract void init(Bundle savedInstanceState);

    /**
     * init all views and add events
     */
    protected abstract void initView();

    /**
     * init all data
     */
    protected abstract void initData();


    /**
     * init all Listener
     */
    protected abstract void initListener();

    protected void checkPermission() {
        for (String p : onPermission()) {
            if (ContextCompat.checkSelfPermission(this, p) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, onPermission(), TConstants.PERMISSION_REQUEST_CODE);
                return;
            }
        }
        permissionGranted();

    }


    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            showRequestPermissionRationale();

        } else {
            showAppPermissionSettings();
        }
    }

    private void showRequestPermissionRationale() {
        Snackbar snackbar = Snackbar.make(
                findViewById(android.R.id.content),
                onPermissionExplanation(),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.permission_ok), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityCompat.requestPermissions(
                                TBaseActivity.this,
                                onPermission(),
                                TConstants.PERMISSION_REQUEST_CODE);
                    }
                });
        snackbar.show();
    }

    private void showAppPermissionSettings() {
        Snackbar snackbar = Snackbar.make(
                findViewById(android.R.id.content),
                onPermissionForce(),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.permission_settings), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.fromParts(
                                getString(R.string.permission_package),
                                TBaseActivity.this.getPackageName(),
                                null);

                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.setData(uri);
                        startActivityForResult(intent, TConstants.PERMISSION_REQUEST_CODE);
                    }
                });

        snackbar.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LogUtils.i(requestCode + "权限数组" + GsonUtil.gsonString(permissions), "结果数组" + GsonUtil.gsonString(grantResults));
        for (int grs : grantResults) {
            LogUtils.i(grs + "==" + PackageManager.PERMISSION_DENIED + "结果" + (grs == PackageManager.PERMISSION_DENIED));
            if (grs == PackageManager.PERMISSION_DENIED) {
                LogUtils.i("存在未获取权限");
                permissionDenied();
                return;
            }
        }

        permissionGranted();
    }

    protected void permissionGranted() {

    }

    private void permissionDenied() {
        hideViews();
        requestPermission();
    }

    protected void hideViews() {
    }


    /**
     * 要申请的权限
     *
     * @return
     */
    protected String[] onPermission() {
        return permissions;
    }

    /**
     * 被拒绝后的权限说明
     *
     * @return
     */
    protected String onPermissionExplanation() {
        return getString(R.string.permission_info);
    }


    /**
     * 不再询问后的强行许可说明
     *
     * @return
     */
    protected String onPermissionForce() {
        return getString(R.string.permission_force);
    }

}
