package com.aeolou.digital.media.android.tmediapicke.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author: Aeolou
 * Date:2019/12/23 0003
 * Email:tg0804013x@gmail.com
 */
public class AudioInfo extends BaseMediaInfo {
    private String artist;//作者
    private long duration;//媒体项目的持续时间
    private String album;//专辑音频文件来自的专辑（如果有）
    private String albumId;//音频文件来自的专辑的ID（如果有）
    private String bookmark;//音频项目中应恢复播放的位置
    private String track;//专辑中此歌曲的曲目号（如果有）

    public AudioInfo() {
        super();
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getBookmark() {
        return bookmark;
    }

    public void setBookmark(String bookmark) {
        this.bookmark = bookmark;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public static Creator<AudioInfo> getCREATOR() {
        return CREATOR;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeString(artist);
        out.writeLong(duration);
        out.writeString(album);
        out.writeString(albumId);
        out.writeString(bookmark);
        out.writeString(track);

    }

    public static final Creator<AudioInfo> CREATOR = new Creator<AudioInfo>() {
        @Override
        public AudioInfo createFromParcel(Parcel source) {
            return new AudioInfo(source);
        }

        @Override
        public AudioInfo[] newArray(int size) {
            return new AudioInfo[size];
        }
    };

    private AudioInfo(Parcel in) {
        super(in);
        artist = in.readString();
        duration = in.readLong();
        album = in.readString();
        albumId = in.readString();
        bookmark = in.readString();
        track = in.readString();
    }
}
