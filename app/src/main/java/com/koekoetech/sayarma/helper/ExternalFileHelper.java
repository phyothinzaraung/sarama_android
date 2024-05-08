package com.koekoetech.sayarma.helper;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import java.io.File;
import androidx.annotation.NonNull;

public class ExternalFileHelper {

    private static final String TAG = "ExternalFileHelper";

    public static final String DIR_DATA_ROOT = "sarama";
    public static final String DIR_DATA_CARDS = "cards";
    public static final String DIR_DATA_VIDEOS = "videos";

    public static final String DIR_DATA_AUDIO = "audio";
    public static final String DIR_DATA_AUDIO_CURRICULUM = "curriculum";
    public static final String DIR_DATA_AUDIO_QUIZ = "quiz";
    public static final String DIR_DATA_AUDIO_TITLE = "title";

    @NonNull
    public static File getRootDirectory(@NonNull final Context context) {
        return new File(context.getFilesDir(), DIR_DATA_ROOT);
    }

    @NonNull
    public static File getImageFile(@NonNull final Context context, String image) {
        Log.d(TAG, "getImageFilePath() called with: image = [" + image + "]");
        final File cardsDir = new File(getRootDirectory(context), DIR_DATA_CARDS);
        final File imageFile = new File(cardsDir, image);
        Log.d(TAG, "getImageFilePath() returned: " + imageFile.getAbsolutePath());
        return imageFile;
    }

    @NonNull
    public static File getCurriculumAudioFile(@NonNull final Context context, final String audio) {
        Log.d(TAG, "getCurriculumAudioFile() called with: audio = [" + audio + "]");
        return getAudioFile(context, DIR_DATA_AUDIO_CURRICULUM, audio);
    }

    @NonNull
    public static File getQuizAudioFile(@NonNull final Context context, final String audio) {
        Log.d(TAG, "getQuizAudioFile() called with: audio = [" + audio + "]");
        return getAudioFile(context, DIR_DATA_AUDIO_QUIZ, audio);
    }

    @NonNull
    public static File getTitleAudioFile(@NonNull final Context context, final String audio) {
        Log.d(TAG, "getTitleAudioFile() called with: audio = [" + audio + "]");
        return getAudioFile(context, DIR_DATA_AUDIO_TITLE, audio);
    }

    @NonNull
    private static File getAudioFile(@NonNull final Context context, @NonNull final String audioCategoryDir, final String audio) {
        Log.d(TAG, "getAudioDirectory() called with: audioCategoryDir = [" + audioCategoryDir + "], audio = [" + audio + "]");
        final File audioRootDirectory = new File(getRootDirectory(context), DIR_DATA_AUDIO);
        final File audioCategoryDirectory = new File(audioRootDirectory, audioCategoryDir);
        final File audioFile = new File(audioCategoryDirectory, audio);
        Log.d(TAG, "getAudioDirectory() returned: " + audioFile.getAbsolutePath());
        return audioFile;
    }

    @NonNull
    public static File getVideoFile(@NonNull final Context context, String video) {
        Log.d(TAG, "getVideoFile() called with: video = [" + video + "]");
        final File videoDirectory = new File(getRootDirectory(context), DIR_DATA_VIDEOS);
        final File videoFile = new File(videoDirectory, video);
        Log.d(TAG, "getVideoFile() returned: " + videoFile.getAbsolutePath());
        return videoFile;
    }

    @NonNull
    public static Uri getVideoFileUri(@NonNull final Context context, String video) {
        return Uri.parse(getVideoFile(context, video).getAbsolutePath());
    }
}
