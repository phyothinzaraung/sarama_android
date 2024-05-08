package com.koekoetech.sayarma.helper;

import android.util.Log;

import java.io.File;

import androidx.annotation.Nullable;

public final class FileUtils {

    private static final String TAG = "FileUtils";

    private FileUtils() {
        //no instance
    }

    public static void delete(@Nullable final File file) {
        if (file == null || !file.exists()) {
            Log.d(TAG, "delete: target file doesn't exists!");
            return;
        }

        Log.d(TAG, "delete: Target " + file.getAbsolutePath());

        try {
            if (file.isDirectory()) {
                //directory is empty, then delete it
                @Nullable final File[] fileContents = file.listFiles();

                if (fileContents == null || fileContents.length == 0) {
                    final boolean isDirectoryDeleted = file.delete();
                    Log.d(TAG, "delete: Is " + file.getAbsolutePath() + " deleted ? : " + isDirectoryDeleted);
                } else {
                    //list all the directory contents
                    for (File contentFile : fileContents) {
                        //recursive delete
                        delete(contentFile);
                    }

                    delete(file);
                }
            } else {
                //if file, then delete it
                final boolean isFileDeleted = file.delete();
                Log.d(TAG, "delete: Is " + file.getAbsolutePath() + " deleted ? : " + isFileDeleted);

            }
        } catch (Exception e) {
            Log.e(TAG, "delete: ", e);
        }

    }
}
