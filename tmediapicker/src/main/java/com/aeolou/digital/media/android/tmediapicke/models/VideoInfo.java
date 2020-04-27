package com.aeolou.digital.media.android.tmediapicke.models;


import android.os.Parcel;

/**
 * Author: Aeolou
 * Date:2019/12/23 0003
 * Email:tg0804013x@gmail.com
 */
public class VideoInfo extends BaseMediaInfo {
    private String artist;//作者
    private long duration;//媒体项目的持续时间
    private String bookMark;//视频项目中应恢复播放的位置
    private int height;//媒体项目的高度
    private int width;//媒体项目的宽度
    private String miniThumbMagic;//在API级别29中已弃用。所有缩略图应通过来获取 MediaStore.Images.Thumbnails#getThumbnail，因为不再支持该值。
    private String thumbnailsData;//缩略图
    private String bucketDisplayName;//该媒体项目的主存储桶显示名称
    private String bucketId;//该媒体项目的主要存储区ID
    private String description;//说明

    public VideoInfo() {
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

    public String getMiniThumbMagic() {
        return miniThumbMagic;
    }

    public void setMiniThumbMagic(String miniThumbMagic) {
        this.miniThumbMagic = miniThumbMagic;
    }

    public String getThumbnailsData() {
        return thumbnailsData;
    }

    public void setThumbnailsData(String thumbnailsData) {
        this.thumbnailsData = thumbnailsData;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBookMark() {
        return bookMark;
    }

    public void setBookMark(String bookMark) {
        this.bookMark = bookMark;
    }


    public static Creator<VideoInfo> getCREATOR() {
        return CREATOR;
    }

    public static final Creator<VideoInfo> CREATOR = new Creator<VideoInfo>() {
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
        out.writeString(bookMark);
        out.writeInt(height);
        out.writeInt(width);
        out.writeString(bucketDisplayName);
        out.writeString(bucketId);
        out.writeString(miniThumbMagic);
        out.writeString(description);
        out.writeString(thumbnailsData);
    }

    protected VideoInfo(Parcel in) {
        // 调用父类的读取操作
        super(in);
        // 子类实现的读取操作
        artist = in.readString();
        duration = in.readLong();
        bookMark = in.readString();
        height = in.readInt();
        width = in.readInt();
        bucketDisplayName = in.readString();
        bucketId = in.readString();
        miniThumbMagic = in.readString();
        description = in.readString();
        thumbnailsData = in.readString();
    }
}
