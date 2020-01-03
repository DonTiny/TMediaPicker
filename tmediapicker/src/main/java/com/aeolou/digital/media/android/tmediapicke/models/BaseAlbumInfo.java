package com.aeolou.digital.media.android.tmediapicke.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author: Aeolou
 * Date:2019/12/23 0003
 * Email:tg0804013x@gmail.com
 */
public class BaseAlbumInfo implements Parcelable {
    private String bucketName;
    private int count=1;

    public BaseAlbumInfo() {

    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public static final Creator<BaseAlbumInfo> CREATOR = new Creator<BaseAlbumInfo>() {
        @Override
        public BaseAlbumInfo createFromParcel(Parcel source) {
            return new BaseAlbumInfo(source);
        }

        @Override
        public BaseAlbumInfo[] newArray(int size) {
            return new BaseAlbumInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(bucketName);
        out.writeInt(count);
    }

    protected BaseAlbumInfo(Parcel in) {
        bucketName = in.readString();
        count = in.readInt();
    }
}
