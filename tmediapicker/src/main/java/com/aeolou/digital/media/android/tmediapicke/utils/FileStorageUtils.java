package com.aeolou.digital.media.android.tmediapicke.utils;

import android.annotation.SuppressLint;
import android.app.usage.StorageStatsManager;
import android.content.Context;
import android.os.Environment;
import android.os.storage.StorageManager;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by xiaobai on 2019/9/26.
 */

@SuppressLint("StaticFieldLeak")
public class FileStorageUtils {

    private Context context;
    private static FileStorageUtils instance;
    private StorageManager storageManager;
    private static String[] units = {"B", "K", "M", "G", "T"};
    private float unit = 1000;
    private String size;

    public static FileStorageUtils getInstance(Context context) {
        if (instance == null) {
            instance = new FileStorageUtils(context);
        }
        return instance;
    }

    private FileStorageUtils(Context context) {
        this.context = context;
        storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
    }

    /**
     * 本地
     * @return
     */
    public String getExternalStorageDirectory() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    public boolean isExistUSBDiskDir() {
        return getUSBDiskDir() != null;
    }

    /**
     * 获取U盘挂载路径
     *
     * @return 路径
     */
    public String getUSBDiskDir() {
        String sdcardDir = null;
        try {
            Class<?> diskInfoClazz = Class.forName("android.os.storage.DiskInfo");
            Method isUsb = diskInfoClazz.getMethod("isUsb");
            Class<?> volumeInfoClazz = Class.forName("android.os.storage.VolumeInfo");
            Method getType = volumeInfoClazz.getMethod("getType");
            Method getDisk = volumeInfoClazz.getMethod("getDisk");
            Field path = volumeInfoClazz.getDeclaredField("path");
            Method getVolumes = storageManager.getClass().getMethod("getVolumes");
            List<Class<?>> result = (List<Class<?>>) getVolumes.invoke(storageManager);
            for (int i = 0; i < result.size(); i++) {
                Object volumeInfo = result.get(i);
                if ((int) getType.invoke(volumeInfo) == 0) {
                    Object disk = getDisk.invoke(volumeInfo);
                    if (disk != null) {
                        if ((boolean) isUsb.invoke(disk)) {
                            sdcardDir = (String) path.get(volumeInfo);
                            break;
                        }
                    }
                }
            }
            return sdcardDir;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isExistSDCardDir() {
        return getSDCardDir() != null;
    }
    /**
     * 获取SD卡挂载路径
     *
     * @return 路径
     */
    public String getSDCardDir() {
        String sdcardDir = null;
        try {
            Class<?> diskInfoClazz = Class.forName("android.os.storage.DiskInfo");
            Method isSd = diskInfoClazz.getMethod("isSd");
            Class<?> volumeInfoClazz = Class.forName("android.os.storage.VolumeInfo");
            Method getType = volumeInfoClazz.getMethod("getType");
            Method getDisk = volumeInfoClazz.getMethod("getDisk");
            Field path = volumeInfoClazz.getDeclaredField("path");
            Method getVolumes = storageManager.getClass().getMethod("getVolumes");
            List<Class<?>> result = (List<Class<?>>) getVolumes.invoke(storageManager);
            for (int i = 0; i < result.size(); i++) {
                Object volumeInfo = result.get(i);
                if ((int) getType.invoke(volumeInfo) == 0) {
                    Object disk = getDisk.invoke(volumeInfo);
                    if (disk != null) {
                        if ((boolean) isSd.invoke(disk)) {
                            sdcardDir = (String) path.get(volumeInfo);
                            break;
                        }
                    }
                }
            }
            return sdcardDir;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取本地存储大小
     *
     * @param memoryType 总大小、空闲大小等
     * @return 空间大小
     */
    @SuppressLint({"PrivateApi", "NewApi"})
    public String getLocalAllMemorySize(MemoryType memoryType) {
        try {
            Method getVolumes = StorageManager.class.getDeclaredMethod("getVolumes");
            List<Object> getVolumeInfo = (List<Object>) getVolumes.invoke(storageManager);
            long used = 0L, systemSize = 0L;
            ;
            for (Object obj : getVolumeInfo) {
                Field getType = obj.getClass().getField("type");
                int fileType = getType.getInt(obj);
                if (fileType == 1) {
                    Method getFsUuid = obj.getClass().getDeclaredMethod("getFsUuid");
                    String fsUuid = (String) getFsUuid.invoke(obj);
                    long totalSize = getTotalSize(context, fsUuid);
                    Method isMountedReadable = obj.getClass().getDeclaredMethod("isMountedReadable");
                    boolean readable = (boolean) isMountedReadable.invoke(obj);
                    if (readable) {
                        Method file = obj.getClass().getDeclaredMethod("getPath");
                        File f = (File) file.invoke(obj);
                        if (totalSize == 0) {
                            totalSize = f.getTotalSpace();
                        }
                        systemSize = totalSize - f.getTotalSpace();
                        used += totalSize - f.getFreeSpace();
                    }
                    switch (memoryType) {
                        case TOTAL:
                            size = getUnit(totalSize, unit, MemoryType.TOTAL);
                            break;
                        case USED:
                            size = getUnit(used, unit, MemoryType.USED);
                            break;
                        case SYSTEM:
                            size = getUnit(systemSize, unit, MemoryType.SYSTEM);
                            break;
                        case FREE_TOTAL:
                            size = getUnit(totalSize - systemSize, unit, MemoryType.TOTAL);
                            break;
                        case USER_USED:
                            size = getUnit(used - systemSize, unit, MemoryType.USER_USED);
                            break;
                        default:
                            size = getUnit(totalSize - used, unit, MemoryType.FREE);
                            break;
                    }
                    return size;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取外置SD存储大小
     *
     * @param type 总大小、空闲大小等
     * @return 空间大小
     */
    public String getSDAllMemorySize(MemoryType type) {
        long total = 0, used = 0;
        try {
            Class<?> diskInfoClazz = Class.forName("android.os.storage.DiskInfo");
            Method isSd = diskInfoClazz.getMethod("isSd");
            Class<?> volumeInfoClazz = Class.forName("android.os.storage.VolumeInfo");
            Method getType = volumeInfoClazz.getMethod("getType");
            Method getDisk = volumeInfoClazz.getMethod("getDisk");
            Method getVolumes = storageManager.getClass().getMethod("getVolumes");
            List<Class<?>> result = (List<Class<?>>) getVolumes.invoke(storageManager);
            for (int i = 0; i < result.size(); i++) {
                Object volumeInfo = result.get(i);
                if ((int) getType.invoke(volumeInfo) == 0) {
                    Object disk = getDisk.invoke(volumeInfo);
                    if (disk != null) {
                        if ((boolean) isSd.invoke(disk)) {
                            Method isMountedReadable = volumeInfo.getClass().getDeclaredMethod("isMountedReadable");
                            boolean readable = (boolean) isMountedReadable.invoke(volumeInfo);
                            if (readable) {
                                Method file = volumeInfo.getClass().getDeclaredMethod("getPath");
                                File f = (File) file.invoke(volumeInfo);
                                used += f.getTotalSpace() - f.getFreeSpace();
                                total += f.getTotalSpace();
                            }
                            if (type == MemoryType.TOTAL) {
                                size = getUnit(total, 1000, MemoryType.TOTAL);
                            } else {
                                size = getUnit(used, 1000, MemoryType.USED);
                            }
                            return size;
                        }
                    }
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getUSBAllMemorySize(MemoryType type) {
        long total = 0, used = 0;
        try {
            Class<?> diskInfoClazz = Class.forName("android.os.storage.DiskInfo");
            Method isUsb = diskInfoClazz.getMethod("isUsb");
            Class<?> volumeInfoClazz = Class.forName("android.os.storage.VolumeInfo");
            Method getType = volumeInfoClazz.getMethod("getType");
            Method getDisk = volumeInfoClazz.getMethod("getDisk");
            Method getVolumes = storageManager.getClass().getMethod("getVolumes");
            List<Class<?>> result = (List<Class<?>>) getVolumes.invoke(storageManager);
            for (int i = 0; i < result.size(); i++) {
                Object volumeInfo = result.get(i);
                if ((int) getType.invoke(volumeInfo) == 0) {
                    Object disk = getDisk.invoke(volumeInfo);
                    if (disk != null) {
                        if ((boolean) isUsb.invoke(disk)) {
                            Method isMountedReadable = volumeInfo.getClass().getDeclaredMethod("isMountedReadable");
                            boolean readable = (boolean) isMountedReadable.invoke(volumeInfo);
                            if (readable) {
                                Method file = volumeInfo.getClass().getDeclaredMethod("getPath");
                                File f = (File) file.invoke(volumeInfo);
                                used += f.getTotalSpace() - f.getFreeSpace();
                                total += f.getTotalSpace();
                            }
                            if (type == MemoryType.TOTAL) {
                                size = getUnit(total, 1000, MemoryType.TOTAL);
                            } else {
                                size = getUnit(used, 1000, MemoryType.USED);
                            }
                            return size;
                        }
                    }
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getUnit(float size, float base, MemoryType type) {
        int index = 0;
        while (size > base && index < 4) {
            size = size / base;
            index++;
        }
        if (type == MemoryType.TOTAL) {
            BigDecimal b = new BigDecimal(size).setScale(0, BigDecimal.ROUND_HALF_UP);
            return String.format(Locale.getDefault(), "%s%s", b.intValue(), units[index]);
        }
        return String.format(Locale.getDefault(), " %.2f%s ", size, units[index]);
    }

    @SuppressLint("NewApi")
    private long getTotalSize(Context context, String fsUuid) {
        try {
            UUID id;
            if (fsUuid == null) {
                id = StorageManager.UUID_DEFAULT;
            } else {
                id = UUID.fromString(fsUuid);
            }
            StorageStatsManager stats = context.getSystemService(StorageStatsManager.class);
            return stats.getTotalBytes(id);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public String getFileSize(String path) {
        String fileSize = null;
        File file = new File(path);
        if (file.exists()) {
            try {
                FileInputStream stream = new FileInputStream(file);
                fileSize = getUnit(stream.available(), 1000, FileStorageUtils.MemoryType.USED);
                stream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fileSize;
    }

    public enum MemoryType {
        TOTAL,
        USED,
        FREE,
        FREE_TOTAL,
        USER_USED,
        SYSTEM
    }
}
