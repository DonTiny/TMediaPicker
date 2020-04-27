package com.aeolou.digital.media.android.tmediapicke.models;

import android.os.Parcel;

/**
 * Author: Aeolou
 * Date:2019/12/23 0003
 * Email:tg0804013x@gmail.com
 */
public class PhotoInfo extends BaseMediaInfo {
    private int height;//媒体项目的高度
    private int width;//媒体项目的宽度
    private String bucketDisplayName;//该媒体项目的主存储桶显示名称
    private String bucketId;//该媒体项目的主要存储区ID
    private String description;//说明
    private String miniThumbMagic;//在API级别29中已弃用。所有缩略图应通过来获取 MediaStore.Images.Thumbnails#getThumbnail，因为不再支持该值。
    private String thumbnailsData;//缩略图

    public PhotoInfo() {
        super();
    }

    public PhotoInfo(String id, String title, String displayName, String data, long size, long dateAdded, long dateModified, long dateTaken, String mimeType, int type, int height, int width, String bucketDisplayName, String bucketId, String description, String miniThumbMagic, String thumbnailsData) {
        super(id, title, displayName, data, size, dateAdded, dateModified, dateTaken, mimeType, type);
        this.height = height;
        this.width = width;
        this.bucketDisplayName = bucketDisplayName;
        this.bucketId = bucketId;
        this.description = description;
        this.miniThumbMagic = miniThumbMagic;
        this.thumbnailsData = thumbnailsData;
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
    public String getBucketDisplayName() {
        return bucketDisplayName;
    }

    public void setBucketDisplayName(String bucketDisplayName) {
        this.bucketDisplayName = bucketDisplayName;
    }

    public String getThumbnailsData() {
        return thumbnailsData;
    }

    public void setThumbnailsData(String thumbnailsData) {
        this.thumbnailsData = thumbnailsData;
    }

    public String getBucketId() {
        return bucketId;
    }

    public void setBucketId(String bucketId) {
        this.bucketId = bucketId;
    }

    public String getMiniThumbMagic() {
        return miniThumbMagic;
    }

    public void setMiniThumbMagic(String miniThumbMagic) {
        this.miniThumbMagic = miniThumbMagic;
    }

    public static Creator<PhotoInfo> getCREATOR() {
        return CREATOR;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static final Creator<PhotoInfo> CREATOR = new Creator<PhotoInfo>() {
        @Override
        public PhotoInfo createFromParcel(Parcel source) {
            return new PhotoInfo(source);
        }

        @Override
        public PhotoInfo[] newArray(int size) {
            return new PhotoInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        super.writeToParcel(out, i);
        out.writeInt(height);
        out.writeInt(width);
        out.writeString(bucketDisplayName);
        out.writeString(bucketId);
        out.writeString(miniThumbMagic);
        out.writeString(description);
        out.writeString(thumbnailsData);

    }

    private PhotoInfo(Parcel in) {
        super(in);
        height = in.readInt();
        width = in.readInt();
        bucketDisplayName = in.readString();
        bucketId = in.readString();
        miniThumbMagic = in.readString();
        description = in.readString();
        thumbnailsData = in.readString();

    }
}
