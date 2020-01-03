package com.aeolou.digital.media.android.tmediapicke.models;


import android.os.Parcel;

/**
 * Author: Aeolou
 * Date:2019/12/23 0003
 * Email:tg0804013x@gmail.com
 */
public class AudioAlbumInfo extends BaseAlbumInfo {


    public AudioAlbumInfo() {
        super();
    }


    public static Creator<AudioAlbumInfo> getCREATOR() {
        return CREATOR;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
    }

    public static final Creator<AudioAlbumInfo> CREATOR = new Creator<AudioAlbumInfo>() {
        @Override
        public AudioAlbumInfo createFromParcel(Parcel source) {
            return new AudioAlbumInfo(source);
        }

        @Override
        public AudioAlbumInfo[] newArray(int size) {
            return new AudioAlbumInfo[size];
        }
    };

    private AudioAlbumInfo(Parcel in) {
        super(in);
    }
}
