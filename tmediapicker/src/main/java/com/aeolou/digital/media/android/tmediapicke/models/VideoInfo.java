package com.aeolou.digital.media.android.tmediapicke.models;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author: Aeolou
 * Date:2019/12/23 0003
 * Email:tg0804013x@gmail.com
 */
public class VideoInfo extends BaseMediaInfo {
    private String artist;//作者
    private long duration;//媒体项目的持续时间
    private String bucketDisplayName;//该媒体项目的主存储桶显示名称
    private String bucketId;//该媒体项目的主要存储区ID
    private String bookMark;//视频项目中应恢复播放的位置
    private int height;//媒体项目的高度
    private int width;//媒体项目的宽度
    private String description;//说明
    private String miniThumbMagic;//该常数在API级别29中已弃用。所有缩略图应通过来获取 MediaStore.Images.Thumbnails#getThumbnail，因为不再支持该值。

    public VideoInfo() {
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

    public String getBucketDisplayName() {
        return bucketDisplayName;
    }

    public void setBucketDisplayName(String bucketDisplayName) {
        this.bucketDisplayName = bucketDisplayName;
    }

    public String getBucketId() {
        return bucketId;
    }

    public void setBucketId(String bucketId) {
        this.bucketId = bucketId;
    }

    public String getBookMark() {
        return bookMark;
    }

    public void setBookMark(String bookMark) {
        this.bookMark = bookMark;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMiniThumbMagic() {
        return miniThumbMagic;
    }

    public void setMiniThumbMagic(String miniThumbMagic) {
        this.miniThumbMagic = miniThumbMagic;
    }

    public static Creator<VideoInfo> getCREATOR() {
        return CREATOR;
    }

    public static final Parcelable.Creator<VideoInfo> CREATOR = new Parcelable.Creator<VideoInfo>() {
        public VideoInfo createFromParcel(Parcel in) {
            return new VideoInfo(in);
        }

        public VideoInfo[] newArray(int size) {
            return new VideoInfo[size];
        }
    };

    public void writeToParcel(Parcel out, int flags) {
        // 调用父类的写操作
        super.writeToParcel(out, flags);
        // 子类实现的写操作
        out.writeString(artist);
        out.writeLong(duration);
        out.writeString(bucketDisplayName);
        out.writeString(bucketId);
        out.writeString(bookMark);
        out.writeInt(height);
        out.writeInt(width);
        out.writeString(miniThumbMagic);
        out.writeString(description);
    }

    protected VideoInfo(Parcel in) {
        // 调用父类的读取操作
        super(in);
        // 子类实现的读取操作
        artist = in.readString();
        duration = in.readLong();
        bucketDisplayName = in.readString();
        bucketId = in.readString();
        bookMark = in.readString();
        height = in.readInt();
        width = in.readInt();
        miniThumbMagic = in.readString();
        description = in.readString();
    }
}
