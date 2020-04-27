package com.aeolou.digital.media.android.tmediapicke.helpers;

import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

/**
 * Author: Aeolou
 * Date:2019/12/23 0003
 * Email:tg0804013x@gmail.com
 */
public interface TConstants {
    int PERMISSION_REQUEST_CODE = 1000;

    int REQUEST_PHOTO_CODE = 2000;
    int REQUEST_VIDEO_CODE = 2001;
    int REQUEST_AUDIO_CODE = 2002;

    String MNT_PATH = File.separator + "mnt";

    String INTENT_EXTRA_PHOTO = "photo";
    String INTENT_EXTRA_VIDEO = "video";
    String INTENT_EXTRA_AUDIO = "audio";

    String INTENT_EXTRA_LIMIT = "limit";
    int DEFAULT_LIMIT = 9;

    String INTENT_EXTRA_PHOTO_AlBUM = "photoAlbum";
    String INTENT_EXTRA_VIDEO_AlBUM = "photoVideo";
    String INTENT_EXTRA_AUDIO_AlBUM = "photoAudio";


    Uri PHOTO_URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

    Uri VIDEO_URI = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

    Uri AUDIO_URI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

    String PHOTO_SORT_ORDER = MediaStore.Images.Media.DATE_ADDED;
    String VIDEO_SORT_ORDER = MediaStore.Video.Media.DATE_ADDED;
    String AUDIO_SORT_ORDER = MediaStore.Audio.Media.DATE_ADDED;


    String[] PHOTO_ALBUM_PROJECTION = new String[]{
            MediaStore.Images.Media.BUCKET_ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.DATA};

    String[] VIDEO_ALBUM_PROJECTION = new String[]{
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.BUCKET_ID,
            MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Video.Media.DATA};
    String[] AUDIO_ALBUM_PROJECTION = new String[]{
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DISPLAY_NAME};

    String[] PHOTO_PROJECTION = new String[]{
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.TITLE,//标题
            MediaStore.Images.Media.DISPLAY_NAME,//媒体项目的显示名称
            MediaStore.Images.Media.DATA,//文件路径,在API级别29中不推荐使用此常数。应用程序可能没有文件系统权限来直接访问此路径。应用程序应ContentResolver#openFileDescriptor(Uri, String) 尝试获取访问权限，而不是尝试直接打开此路径 。
            MediaStore.Images.Media.SIZE,//媒体项目的大小
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,//该媒体项目的主存储桶显示名称
            MediaStore.Images.Media.BUCKET_ID,//该媒体项目的主要存储区ID
            MediaStore.Images.Media.DATE_ADDED,//首次添加媒体项目的时间
            MediaStore.Images.Media.DATE_MODIFIED,//媒体项目上次修改的时间
            MediaStore.Images.Media.DATE_TAKEN,//媒体项目的获取时间
            MediaStore.Images.Media.HEIGHT,//媒体项目的高度
            MediaStore.Images.Media.WIDTH,//媒体项目的宽度
            MediaStore.Images.Media.MIME_TYPE,//媒体项目的MIME类型
            MediaStore.Images.Media.DESCRIPTION,//图片说明
            MediaStore.Images.Media.MINI_THUMB_MAGIC,//该常数在API级别29中已弃用。所有缩略图应通过来获取 MediaStore.Images.Thumbnails#getThumbnail，因为不再支持该值。
            MediaStore.Images.Thumbnails.DATA,//缩略图

    };

    String[] VIDEO_PROJECTION = new String[]{
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.TITLE,//标题
            MediaStore.Video.Media.DISPLAY_NAME,//媒体项目的显示名称
            MediaStore.Video.Media.ARTIST,//作者
            MediaStore.Video.Media.DURATION,//媒体项目的持续时间
            MediaStore.Video.Media.DATA,//文件路径
            MediaStore.Video.Media.SIZE,//媒体项目的大小
            MediaStore.Video.Media.BUCKET_DISPLAY_NAME,//该媒体项目的主存储桶显示名称
            MediaStore.Video.Media.BUCKET_ID,//该媒体项目的主要存储区ID
            MediaStore.Video.Media.BOOKMARK,//音频项目中应恢复播放的位置
            MediaStore.Video.Media.DATE_ADDED,//首次添加媒体项目的时间
            MediaStore.Video.Media.DATE_MODIFIED,//媒体项目上次修改的时间
            MediaStore.Video.Media.DATE_TAKEN,//媒体项目的获取时间
            MediaStore.Video.Media.HEIGHT,//媒体项目的高度
            MediaStore.Video.Media.WIDTH,//媒体项目的宽度
            MediaStore.Video.Media.MIME_TYPE,//媒体项目的MIME类型
            MediaStore.Video.Media.DESCRIPTION,//录像说明
            MediaStore.Video.Media.MINI_THUMB_MAGIC,//该常数在API级别29中已弃用。所有缩略图应通过来获取 MediaStore.Images.Thumbnails#getThumbnail，因为不再支持该值。
            MediaStore.Video.Thumbnails.DATA,//缩略图

    };

    String[] AUDIO_PROJECTION = new String[]{
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,//标题
            MediaStore.Audio.Media.DISPLAY_NAME,//媒体项目的显示名称
            MediaStore.Audio.Media.ARTIST,//作者
            MediaStore.Audio.Media.DURATION,//媒体项目的持续时间
            MediaStore.Audio.Media.DATA,//文件路径
            MediaStore.Audio.Media.SIZE,//媒体项目的大小
            MediaStore.Audio.Media.ALBUM,//专辑音频文件来自的专辑（如果有）
            MediaStore.Audio.Media.ALBUM_ID,//音频文件来自的专辑的ID（如果有）
            MediaStore.Audio.Media.TRACK,//专辑中此歌曲的曲目号（如果有）
            MediaStore.Audio.Media.BOOKMARK,//音频项目中应恢复播放的位置
            MediaStore.Audio.Media.DATE_ADDED,//首次添加媒体项目的时间
            MediaStore.Audio.Media.DATE_MODIFIED,//媒体项目上次修改的时间
            MediaStore.Audio.Media.MIME_TYPE,//媒体项目的MIME类型
    };
}
