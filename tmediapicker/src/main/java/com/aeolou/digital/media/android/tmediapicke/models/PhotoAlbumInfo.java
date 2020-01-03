package com.aeolou.digital.media.android.tmediapicke.models;


import android.os.Parcel;

/**
 * Author: Aeolou
 * Date:2019/12/23 0003
 * Email:tg0804013x@gmail.com
 */
public class PhotoAlbumInfo extends BaseAlbumInfo {
    private String previewPath;
    private String bucketId;

    public PhotoAlbumInfo() {
        super();
    }


    public String getPreviewPath() {
        return previewPath;
    }

    public void setPreviewPath(String previewPath) {
        this.previewPath = previewPath;
    }

    public String getBucketId() {
        return bucketId;
    }

    public void setBucketId(String bucketId) {
        this.bucketId = bucketId;
    }

    public static Creator<PhotoAlbumInfo> getCREATOR() {
        return CREATOR;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeString(previewPath);
        out.writeString(bucketId);
    }

    public static final Creator<PhotoAlbumInfo> CREATOR = new Creator<PhotoAlbumInfo>() {
        @Override
        public PhotoAlbumInfo createFromParcel(Parcel source) {
            return new PhotoAlbumInfo(source);
        }

        @Override
        public PhotoAlbumInfo[] newArray(int size) {
            return new PhotoAlbumInfo[size];
        }
    };

    private PhotoAlbumInfo(Parcel in) {
        super(in);
        previewPath = in.readString();
        bucketId = in.readString();
    }
}
