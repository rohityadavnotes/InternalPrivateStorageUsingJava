package com.internal.storage.utilities;

import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

public class StorageUtils {

    public static final String TAG = StorageUtils.class.getSimpleName();

    /* Storage states */
    public static boolean externalStorageAvailable, externalStorageWriteable;

    private StorageUtils() {
        throw new UnsupportedOperationException("You can't create instance of Util class. Please use as static..");
    }

    /**
     * Checks the external storage's state and saves it in member attributes.
     */
    public static boolean isSDCardPresent() {
        /* Get the external storage's state */
        String state = Environment.getExternalStorageState();

        if (state.equals(Environment.MEDIA_MOUNTED)) {
            /* Storage is available and writeable */
            externalStorageAvailable = externalStorageWriteable = true;
        } else if (state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            /* Storage is only readable */
            externalStorageAvailable = true;
            externalStorageWriteable = false;
        } else {
            /* Storage is neither readable nor writeable */
            externalStorageAvailable = externalStorageWriteable = false;
        }
        return externalStorageWriteable;
    }

    /**
     * Checks the state of the external storage.
     *
     * @return True if the external storage is available, false otherwise.
     */
    public static boolean isExternalStorageAvailable() {
        isSDCardPresent();
        return externalStorageAvailable;
    }

    /**
     * Checks the state of the external storage.
     *
     * @return True if the external storage is writeable, false otherwise.
     */
    public static boolean isExternalStorageWriteable() {
        isSDCardPresent();
        return externalStorageWriteable;
    }

    /**
     * Checks the state of the external storage.
     *
     * @return True if the external storage is available and writeable, otherwise false
     */
    public static boolean isExternalStorageAvailableAndWriteable() {
        isSDCardPresent();
        if (!externalStorageAvailable) {
            return false;
        } else if (!externalStorageWriteable) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Get OS (System) Storage Path.
     *
     * call like : MemoryUnitUtils.getReadableFileSize(StorageUtils.getFreeSpace(StorageUtils.getOsStoragePath()))
     * call like : MemoryUnitUtils.getReadableFileSize(StorageUtils.getUsedSpace(StorageUtils.getOsStoragePath()))
     * call like : MemoryUnitUtils.getReadableFileSize(StorageUtils.getTotalSize(StorageUtils.getOsStoragePath()))
     *
     * @return return path
     */
    public static String getOsStoragePath() {
        return Environment.getRootDirectory().getAbsolutePath();
    }

    /**
     * Get Internal Storage Path.
     *
     * call like : MemoryUnitUtils.getReadableFileSize(StorageUtils.getFreeSpace(StorageUtils.getInternalStoragePath()))
     * call like : MemoryUnitUtils.getReadableFileSize(StorageUtils.getUsedSpace(StorageUtils.getInternalStoragePath()))
     * call like : MemoryUnitUtils.getReadableFileSize(StorageUtils.getTotalSize(StorageUtils.getInternalStoragePath()))
     *
     * @return return path
     */
    public static String getInternalStoragePath() {
        return Environment.getDataDirectory().getAbsolutePath();
    }

    /**
     * Get External Storage(SD CARD) Path.
     *
     * call like : MemoryUnitUtils.getReadableFileSize(StorageUtils.getFreeSpace(StorageUtils.getExternalStoragePath()))
     * call like : MemoryUnitUtils.getReadableFileSize(StorageUtils.getUsedSpace(StorageUtils.getExternalStoragePath()))
     * call like : MemoryUnitUtils.getReadableFileSize(StorageUtils.getTotalSize(StorageUtils.getExternalStoragePath()))
     *
     * @return return path
     */
    public static String getExternalStoragePath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * Get Free Memory Space for OS, Internal , External(SD CARD)
     */
    public static long getFreeSpace(String dir) {
        StatFs statFs = new StatFs(dir); /* A class that simulates the df command of Linux to get the usage of SD card and mobile phone memory */
        long availableBlocks;
        long blockSize;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            availableBlocks = statFs.getAvailableBlocks(); /* Return Int to get the currently available storage space */
            blockSize = statFs.getBlockSize(); /* returns Int, the size, in bytes, a file system */
        } else {
            availableBlocks = statFs.getAvailableBlocksLong();
            blockSize = statFs.getBlockSizeLong();
        }
        long freeBytes = availableBlocks * blockSize;
        return freeBytes;
    }

    /**
     * Get Used Memory Space for OS, Internal , External(SD CARD)
     */
    public static long getUsedSpace(String dir) {
        StatFs statFs = new StatFs(dir);
        long availableBlocks;
        long blockSize;
        long totalBlocks;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            availableBlocks = statFs.getAvailableBlocks();
            blockSize = statFs.getBlockSize();
            totalBlocks = statFs.getBlockCount(); /* Get the number of file systems available in the area */
        } else {
            availableBlocks = statFs.getAvailableBlocksLong();
            blockSize = statFs.getBlockSizeLong();
            totalBlocks = statFs.getBlockCountLong();
        }
        long usedBytes = totalBlocks * blockSize - availableBlocks * blockSize;
        return usedBytes;
    }

    /**
     * Get Total Memory Size for OS, Internal , External(SD CARD)
     */
    public static long getTotalSize(String dir) {
        StatFs statFs = new StatFs(dir);
        long blockSize;
        long totalBlocks;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = statFs.getBlockSize();
            totalBlocks = statFs.getBlockCount(); /* Get the number of file systems available in the area */
        } else {
            blockSize = statFs.getBlockSizeLong();
            totalBlocks = statFs.getBlockCountLong();
        }
        long totalBytes = totalBlocks * blockSize;
        return totalBytes;
    }
}
