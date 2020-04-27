package com.aeolou.digital.media.android.tmediapicke.loader;

import android.content.Context;

import com.aeolou.digital.media.android.tmediapicke.helpers.LoaderStorageType;
import com.aeolou.digital.media.android.tmediapicke.helpers.TConstants;
import com.aeolou.digital.media.android.tmediapicke.utils.FileStorageUtils;

/**
 * Author: Aeolou
 * Date:2020/4/18 0018
 * Email:tg0804013x@gmail.com
 */
public class BaseMediaLoader {


    public String getSelectionType(Context context, LoaderStorageType loaderStorageType) {
        switch (loaderStorageType) {
            case ALL:
                return TConstants.PHOTO_PROJECTION[3] +" NOT LIKE" + " '%" + TConstants.MNT_PATH + "%'";
            case LOCAL:
                return TConstants.PHOTO_PROJECTION[3] + " LIKE" + " '%" + FileStorageUtils.getInstance(context).getExternalStorageDirectory() + "%'";
            case SD_CARD:
                return TConstants.PHOTO_PROJECTION[3] + " LIKE" + " '%" + FileStorageUtils.getInstance(context).getSDCardDir() + "%'";
            case USB:
                return TConstants.PHOTO_PROJECTION[3] + " LIKE" + " '%" + FileStorageUtils.getInstance(context).getUSBDiskDir() + "%'";
            case LOCAL_AND_SD:
                return TConstants.PHOTO_PROJECTION[3] + " LIKE" + " '%" + FileStorageUtils.getInstance(context).getExternalStorageDirectory() + "%'"
                        + " OR " + TConstants.PHOTO_PROJECTION[3] + " LIKE" + " '%" + FileStorageUtils.getInstance(context).getSDCardDir() + "%'";
            case LOCAL_AND_USB:
                return TConstants.PHOTO_PROJECTION[3] + " LIKE" + " '%" + FileStorageUtils.getInstance(context).getExternalStorageDirectory() + "%'"
                        + " OR " + TConstants.PHOTO_PROJECTION[3] + " LIKE" + " '%" + FileStorageUtils.getInstance(context).getUSBDiskDir() + "%'";
            case USB_AND_SD:
                return TConstants.PHOTO_PROJECTION[3] + " LIKE" + " '%" + FileStorageUtils.getInstance(context).getSDCardDir() + "%'"
                        + " OR " + TConstants.PHOTO_PROJECTION[3] + " LIKE" + " '%" + FileStorageUtils.getInstance(context).getUSBDiskDir() + "%'";
        }
        return "NOT " + TConstants.PHOTO_PROJECTION[3] + " LIKE" + " '%" + "mnt" + "%'";
    }

    public LoaderStorageType getFileStorageType(Context context, String path) {
        if (FileStorageUtils.getInstance(context).getExternalStorageDirectory() != null && path.indexOf(FileStorageUtils.getInstance(context).getExternalStorageDirectory()) != -1) {
            return LoaderStorageType.LOCAL;
        } else if (FileStorageUtils.getInstance(context).getSDCardDir() != null && path.indexOf(FileStorageUtils.getInstance(context).getSDCardDir()) != -1) {
            return LoaderStorageType.SD_CARD;
        } else if (FileStorageUtils.getInstance(context).getUSBDiskDir() != null && path.indexOf(FileStorageUtils.getInstance(context).getUSBDiskDir()) != -1) {
            return LoaderStorageType.USB;
        } else if ((FileStorageUtils.getInstance(context).getUSBDiskDir() != null && path.indexOf(FileStorageUtils.getInstance(context).getUSBDiskDir()) != -1) || (FileStorageUtils.getInstance(context).getSDCardDir() != null && path.indexOf(FileStorageUtils.getInstance(context).getSDCardDir()) != -1)) {
            return LoaderStorageType.USB_AND_SD;
        } else if ((FileStorageUtils.getInstance(context).getExternalStorageDirectory() != null && path.indexOf(FileStorageUtils.getInstance(context).getExternalStorageDirectory()) != -1) || (FileStorageUtils.getInstance(context).getSDCardDir() != null && path.indexOf(FileStorageUtils.getInstance(context).getSDCardDir()) != -1)) {
            return LoaderStorageType.LOCAL_AND_SD;
        } else if ((FileStorageUtils.getInstance(context).getExternalStorageDirectory() != null && path.indexOf(FileStorageUtils.getInstance(context).getExternalStorageDirectory()) != -1) || (FileStorageUtils.getInstance(context).getUSBDiskDir() != null && path.indexOf(FileStorageUtils.getInstance(context).getUSBDiskDir()) != -1)) {
            return LoaderStorageType.LOCAL_AND_SD;
        } else {
            return LoaderStorageType.ALL;
        }

    }
}
