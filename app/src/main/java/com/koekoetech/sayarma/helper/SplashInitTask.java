package com.koekoetech.sayarma.helper;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koekoetech.sayarma.model.AnswerModel;
import com.koekoetech.sayarma.model.ChapterModel;
import com.koekoetech.sayarma.model.CoursesAllModel;
import com.koekoetech.sayarma.model.CurriculumAllModel;
import com.koekoetech.sayarma.model.CurriculumChapterModel;
import com.koekoetech.sayarma.model.CurriculumLevelModel;
import com.koekoetech.sayarma.model.CurriculumSectionCardsModel;
import com.koekoetech.sayarma.model.CurriculumSubChapterModel;
import com.koekoetech.sayarma.model.LessonQuestionAnswersModel;
import com.koekoetech.sayarma.model.LevelModel;
import com.koekoetech.sayarma.model.QuestionModel;
import com.koekoetech.sayarma.model.SectionCardsModel;
import com.koekoetech.sayarma.model.SubChapterModel;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.progress.ProgressMonitor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import io.realm.Realm;

public final class SplashInitTask extends LifecycleAwareUiRelatedProgressTask<String, SplashInitTask.Progress> {

    private static final String TAG = "SplashInitTask";
    private static final int DATA_VERSION = 1;
    private static final String DATA_ZIP_PASSWORD = "_sarama@koekoetech2O22_";

    public static final String RESULT_SUCCESS = "SplashTask_SUCCESS";
    public static final String RESULT_FAILED_IMPORT = "SplashTask_DATA_IMPORT";
    public static final String RESULT_FAILED_DATA_ZIP_MISSING = "SplashTask_DataZipMissing";


    @NonNull
    private final Context mContext;

    @NonNull
    private final Callback mCallback;

    private final SharedPreferenceHelper sharedPreferenceHelper;
    private final String loadingMessage;
    private final String dataImportProgressMessage;

    public SplashInitTask(@NonNull LifecycleOwner lifecycleOwner, @NonNull Context mContext, @NonNull Callback callback) {
        super(lifecycleOwner);
        this.mContext = mContext;
        this.mCallback = callback;
        this.sharedPreferenceHelper = SharedPreferenceHelper.getHelper(mContext);
        this.loadingMessage = TextDictionaryHelper.getText(mContext, TextDictionaryHelper.TEXT_PROGRESS_GENERAL);
        this.dataImportProgressMessage = TextDictionaryHelper.getText(mContext, TextDictionaryHelper.TEXT_PROGRESS_DATA_IMPORT);
    }

    @Override
    protected void onProgressUpdate(@NonNull Progress progress) {
        mCallback.onProgressUpdate(progress);
    }

    @NonNull
    @Override
    protected String doWork() {

        publishProgress(new Progress(loadingMessage));
        try {
            if (!checkAndSetupDB()) {
                return RESULT_FAILED_IMPORT;
            }
        } catch (Exception e) {
            Log.e(TAG, "doWork: Json Data Import (DB setup)", e);
            return RESULT_FAILED_IMPORT;
        }

        @Nullable final DataZip latestDataZipFile = getLatestDataZipFile(sharedPreferenceHelper.getMediaDataMinorVersion());

        if (latestDataZipFile == null) {
            if (ExternalFileHelper.getRootDirectory(mContext).exists() &&
                    sharedPreferenceHelper.isMediaDataImported() &&
                    sharedPreferenceHelper.getMediaDataMajorVersion() == DATA_VERSION) {
                return RESULT_SUCCESS;
            } else {
                return RESULT_FAILED_DATA_ZIP_MISSING;
            }
        } else {
            final boolean isExtracted = extractDataZipFile(latestDataZipFile.getDataFile());
            if (isExtracted) {
                sharedPreferenceHelper.setMediaDataImported(true);
                sharedPreferenceHelper.setMediaDataMajorVersion(latestDataZipFile.getVersionMajor());
                sharedPreferenceHelper.setMediaDataMinorVersion(latestDataZipFile.getVersionMinor());
                publishProgress(new Progress(loadingMessage));
                FileUtils.delete(latestDataZipFile.getDataFile());
            } else {
                return RESULT_FAILED_IMPORT;
            }
        }

        return RESULT_SUCCESS;
    }

    @Override
    protected void thenDoUiRelatedWork(@NonNull String result) {
        mCallback.onComplete(result);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Realm Data Setup Methods
    ///////////////////////////////////////////////////////////////////////////
    private boolean checkAndSetupDB() {
        try (final Realm realm = Realm.getDefaultInstance()) {
            if (isDBSetupDone(realm)) {
                Log.d(TAG, "checkAndSetupDB: Json setup already done!");
                return true;
            }

            realm.executeTransaction(txRealm -> {
                importQuizzes(txRealm);
                importCourses(txRealm);
                importCurriculum(txRealm);
            });

            return true;
        } catch (Exception e) {
            Log.e(TAG, "checkAndSetupDB: ", e);
            return false;
        }

    }

    private boolean isDBSetupDone(@NonNull final Realm realm) {
        final long chapter_count = realm.where(ChapterModel.class).count();
        final long sub_chapter_count = realm.where(SubChapterModel.class).count();
        final long level_count = realm.where(LevelModel.class).count();
        final long section_count = realm.where(SectionCardsModel.class).count();
        final long question_count = realm.where(QuestionModel.class).count();
        final long answer_count = realm.where(AnswerModel.class).count();
        final long curriculum_chapter_count = realm.where(CurriculumChapterModel.class).count();
        final long curriculum_sub_chapter_count = realm.where(CurriculumSubChapterModel.class).count();
        final long curriculum_level_count = realm.where(CurriculumLevelModel.class).count();

        return chapter_count != 0 &&
                sub_chapter_count != 0 &&
                level_count != 0 &&
                question_count != 0 &&
                section_count != 0 &&
                answer_count != 0 &&
                curriculum_chapter_count != 0 &&
                curriculum_sub_chapter_count != 0 &&
                curriculum_level_count != 0;
    }

    private void importQuizzes(@NonNull final Realm realm) {
        @Nullable final String quizzesJson = readAssetFile("quizzes.json");

        if (TextUtils.isEmpty(quizzesJson)) {
            throw new IllegalArgumentException("Didn't get json for quizzes from assets.");
        }

        final Gson gson = new Gson();
        @Nullable final LessonQuestionAnswersModel lessonQuestionAnswersModel = gson.fromJson(quizzesJson,
                new TypeToken<LessonQuestionAnswersModel>() {
                }.getType());
        if (lessonQuestionAnswersModel == null) {
            throw new IllegalArgumentException("Failed to parse quizzes json.");
        }

        realm.delete(QuestionModel.class);
        realm.insertOrUpdate(lessonQuestionAnswersModel.getQuizModels());

        realm.delete(AnswerModel.class);
        realm.insertOrUpdate(lessonQuestionAnswersModel.getAnswerModels());
    }

    private void importCourses(@NonNull final Realm realm) {
        @Nullable final String coursesJson = readAssetFile("courses.json");

        if (TextUtils.isEmpty(coursesJson)) {
            throw new IllegalArgumentException("Didn't get json for courses from assets.");
        }

        final Gson gson = new Gson();
        @Nullable final CoursesAllModel coursesAllModel = gson.fromJson(coursesJson, new TypeToken<CoursesAllModel>() {
        }.getType());

        if (coursesAllModel == null) {
            throw new IllegalArgumentException("Failed to parse courses json.");
        }

        realm.delete(LevelModel.class);
        realm.insertOrUpdate(coursesAllModel.getLevels());

        realm.delete(ChapterModel.class);
        realm.insertOrUpdate(coursesAllModel.getChapters());

        realm.delete(SubChapterModel.class);
        realm.insertOrUpdate(coursesAllModel.getSubChapters());

        realm.delete(SectionCardsModel.class);
        realm.insertOrUpdate(coursesAllModel.getSectionCards());
    }

    private void importCurriculum(@NonNull final Realm realm) {
        @Nullable final String curriculumJson = readAssetFile("curriculum.json");

        if (TextUtils.isEmpty(curriculumJson)) {
            throw new IllegalArgumentException("Didn't get json for curriculum from assets.");
        }

        final Gson gson = new Gson();
        @Nullable final CurriculumAllModel curriculumAllModel = gson.fromJson(curriculumJson, new TypeToken<CurriculumAllModel>() {
        }.getType());

        if (curriculumAllModel == null) {
            throw new IllegalArgumentException("Failed to parse curriculum json.");
        }

        realm.delete(CurriculumLevelModel.class);
        realm.insertOrUpdate(curriculumAllModel.getLevels());

        realm.delete(CurriculumChapterModel.class);
        realm.insertOrUpdate(curriculumAllModel.getChapters());

        realm.delete(CurriculumSubChapterModel.class);
        realm.insertOrUpdate(curriculumAllModel.getSubChapters());

        realm.delete(CurriculumSectionCardsModel.class);
        realm.insertOrUpdate(curriculumAllModel.getSectionCards());
    }

    @Nullable
    private String readAssetFile(@NonNull final String fileName) {
        Log.d(TAG, "readAssetFile() called with: fileName = [" + fileName + "]");
        @Nullable String content = null;

        try (final InputStream is = mContext.getAssets().open(fileName)) {
            final byte[] buffer = new byte[is.available()];
            final int readBytes = is.read(buffer);
            Log.d(TAG, "readAssetFile: Read " + readBytes + " bytes!");
            content = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            Log.e(TAG, "readAssetFile: ", e);
        }

        Log.d(TAG, "readAssetFile() returned: " + content);
        return content;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Sarama Media Data Import Update methods
    ///////////////////////////////////////////////////////////////////////////

    private boolean extractDataZipFile(@NonNull final File dataFile) {
        try {
            final File extractDestination = ExternalFileHelper.getRootDirectory(mContext);
            if (extractDestination.exists()) {
                FileUtils.delete(extractDestination);
            } else {
                final boolean isCreated = extractDestination.mkdirs();
                Log.d(TAG, "extractDataZipFile: Create " + extractDestination.getAbsolutePath() + " : " + isCreated);
            }
            final ZipFile dataZipFile = new ZipFile(dataFile, DATA_ZIP_PASSWORD.toCharArray());
            dataZipFile.setRunInThread(true);
            dataZipFile.extractAll(extractDestination.getAbsolutePath());
            final ProgressMonitor progressMonitor = dataZipFile.getProgressMonitor();
            while (progressMonitor.getState() == ProgressMonitor.State.BUSY) {
                final int percentDone = progressMonitor.getPercentDone();
                final String progressMessage = String.format(dataImportProgressMessage, percentDone);
                publishProgress(new Progress(progressMessage, percentDone));
                try {
                    //noinspection BusyWait
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            publishProgress(new Progress(0));
            return true;
        } catch (ZipException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Nullable
    private DataZip getLatestDataZipFile(final int currentDataVersionMinor) {
        Log.d(TAG, "getLatestDataZipFile() called with: currentDataVersionMinor = [" + currentDataVersionMinor + "]");
        try {
            @Nullable final File externalFilesDir = mContext.getExternalFilesDir(null);
            if (externalFilesDir == null) return null;
            final File[] dataZips = externalFilesDir.listFiles((dir, fileName) -> fileName.endsWith(".zip") &&
                    Pattern.compile("sarama_data_[a-zA-Z]+_[0-9]+_[0-9]+\\.zip", Pattern.CASE_INSENSITIVE)
                            .matcher(fileName)
                            .matches());

            if (dataZips == null || dataZips.length == 0) {
                return null;
            }

            final List<DataZip> filteredDataZips = new ArrayList<>();
            for (File dataZip : dataZips) {
                final String zipFileName = dataZip.getName().replace(".zip", "");
                Log.d(TAG, "getLatestDataZipFile: Zip file name without extension " + zipFileName);
                final String[] fileNameParts = TextUtils.split(zipFileName, "_");
                if (fileNameParts.length != 5) continue;
                final int versionMajor = Integer.parseInt(fileNameParts[3]);
                if (versionMajor != DATA_VERSION) continue;
                final int versionMinor = Integer.parseInt(fileNameParts[4]);
                if (versionMinor > currentDataVersionMinor) {
                    filteredDataZips.add(new DataZip(versionMajor, versionMinor, dataZip));
                }
            }

            if (!filteredDataZips.isEmpty()) {
                return Collections.max(filteredDataZips);
            }

        } catch (Exception e) {
            Log.e(TAG, "getLatestDataZipFile: ", e);
        }

        return null;
    }

    public static final class DataZip implements Comparable<DataZip> {
        private final int versionMajor;
        private final int versionMinor;

        @NonNull
        private final File dataFile;

        public DataZip(int versionMajor, int versionMinor, @NonNull File dataFile) {
            this.versionMajor = versionMajor;
            this.versionMinor = versionMinor;
            this.dataFile = dataFile;
        }

        public int getVersionMajor() {
            return versionMajor;
        }

        public int getVersionMinor() {
            return versionMinor;
        }

        @NonNull
        public File getDataFile() {
            return dataFile;
        }

        @Override
        public int compareTo(DataZip dataZip) {
            final int majorVersionComparison = Integer.compare(this.versionMajor, dataZip.versionMajor);
            if (majorVersionComparison != 0) {
                return majorVersionComparison;
            } else {
                return Integer.compare(this.versionMinor, dataZip.versionMinor);
            }
        }

        @NonNull
        @Override
        public String toString() {
            return "DataZip{" +
                    "versionMajor=" + versionMajor +
                    ", versionMinor=" + versionMinor +
                    ", dataFile=" + dataFile.getAbsolutePath() +
                    '}';
        }
    }

    public static final class Progress {
        @Nullable
        private final String message;
        private final int percentage;

        public Progress(@Nullable String message, int percentage) {
            this.message = message;
            this.percentage = percentage;
        }

        public Progress(@Nullable String message) {
            this.message = message;
            this.percentage = 0;
        }

        public Progress(int percentage) {
            this.percentage = percentage;
            this.message = null;
        }

        @Nullable
        public String getMessage() {
            return message;
        }

        public int getPercentage() {
            return percentage;
        }

        @NonNull
        @Override
        public String toString() {
            return "Progress{" +
                    "message='" + message + '\'' +
                    ", percentage=" + percentage +
                    '}';
        }
    }

    public interface Callback {
        void onProgressUpdate(@NonNull final Progress progress);

        void onComplete(@NonNull final String result);
    }
}
