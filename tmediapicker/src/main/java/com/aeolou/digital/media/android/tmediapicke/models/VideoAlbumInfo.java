package com.aeolou.digital.media.android.tmediapicke.models;


import android.net.Uri;
import android.os.Parcel;

/**
 * Author: Aeolou
 * Date:2019/12/23 0003
 * Email:tg0804013x@gmail.com
 */
public class VideoAlbumInfo extends BaseAlbumInfo {
    private String previewUri;
    private String bucketId;

    public VideoAlbumInfo() {
        super();
    }


    public String getBucketId() {
        return bucketId;
    }

    public void setBucketId(String bucketId) {
        this.bucketId = bucketId;
    }

    public static Creator<VideoAlbumInfo> getCREATOR() {
        return CREATOR;
    }


    public String getPreviewUri() {
        return previewUri;
    }

    public void setPreviewUri(String previewUri) {
        this.previewUri = previewUri;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeString(previewUri);
        out.writeString(bucketId);
    }

    public static final Creator<VideoAlbumInfo> CREATOR = new Creator<VideoAlbumInfo>() {
        @Override
        public VideoAlbumInfo createFromParcel(Parcel source) {
            return new VideoAlbumInfo(source);
        }

        @Override
        public VideoAlbumInfo[] newArray(int size) {
            return new VideoAlbumInfo[size];
        }
    };

    private VideoAlbumInfo(Parcel in) {
        super(in);
        previewUri = in.readString();
        bucketId = in.readString();
    }
}
