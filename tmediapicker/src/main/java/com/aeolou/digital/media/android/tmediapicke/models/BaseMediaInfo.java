package com.aeolou.digital.media.android.tmediapicke.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Author: Aeolou
 * Date:2019/12/23 0003
 * Email:tg0804013x@gmail.com
 */
public class BaseMediaInfo implements Parcelable {
    public static final int PHOTO = 101, VIDEO = 102, AUDIO = 103;
    private String id;
    private String title;//标题
    private String displayName;//媒体项目的显示名称
    private String data;//文件路径;在API级别29中不推荐使用。应用程序可能没有文件系统权限来直接访问此路径。应用程序应ContentResolver#openFileDescriptor(Uri; String) 尝试获取访问权限，而不是尝试直接打开此路径 。
    private long size;//媒体项目的大小
    private long dateAdded;//首次添加媒体项目的时间
    private long dateModified;//媒体项目上次修改的时间
    private long dateTaken;//媒体项目的获取时间
    private String mimeType;//媒体项目的MIME类型
    private int type;

    public BaseMediaInfo() {
    }

    public BaseMediaInfo(String id, String title, String displayName, String data, long size, long dateAdded, long dateModified, long dateTaken, String mimeType, int type) {
        this.id = id;
        this.title = title;
        this.displayName = displayName;
        this.data = data;
        this.size = size;
        this.dateAdded = dateAdded;
        this.dateModified = dateModified;
        this.dateTaken = dateTaken;
        this.mimeType = mimeType;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Type
    public int getType() {
        return type;
    }

    public void setType(@Type int type) {
        this.type = type;
    }

    public long getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(long dateAdded) {
        this.dateAdded = dateAdded;
    }

    public long getDateModified() {
        return dateModified;
    }

    public void setDateModified(long dateModified) {
        this.dateModified = dateModified;
    }

    public long getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(long dateTaken) {
        this.dateTaken = dateTaken;
    }


    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }


    public static final Creator<BaseMediaInfo> CREATOR = new Creator<BaseMediaInfo>() {
        @Override
        public BaseMediaInfo createFromParcel(Parcel source) {
            return new BaseMediaInfo(source);
        }

        @Override
        public BaseMediaInfo[] newArray(int size) {
            return new BaseMediaInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(id);
        out.writeString(title);
        out.writeString(displayName);
        out.writeString(data);
        out.writeLong(size);
        out.writeLong(dateAdded);
        out.writeLong(dateModified);
        out.writeLong(dateTaken);
        out.writeString(mimeType);
        out.writeInt(type);
    }

    protected BaseMediaInfo(Parcel in) {
        id = in.readString();
        title = in.readString();
        displayName = in.readString();
        data = in.readString();
        size = in.readLong();
        dateAdded = in.readLong();
        dateModified = in.readLong();
        dateTaken = in.readLong();
        mimeType = in.readString();
        type = in.readInt();
    }

    @IntDef({PHOTO, VIDEO, AUDIO})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }
}
