package com.koekoetech.sayarma.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;
import com.koekoetech.sayarma.R;
import com.koekoetech.sayarma.activity.QuestionAnswerActivity;
import com.koekoetech.sayarma.activity.SubChaptersActivity;
import com.koekoetech.sayarma.custom_control.MyanBoldTextView;
import com.koekoetech.sayarma.custom_control.MyanTextView;
import com.koekoetech.sayarma.helper.ExternalFileHelper;
import com.koekoetech.sayarma.helper.Pageable;
import com.koekoetech.sayarma.helper.TextDictionaryHelper;
import com.koekoetech.sayarma.model.ChapterModel;
import com.koekoetech.sayarma.model.ChapterScoreModel;
import com.koekoetech.sayarma.model.ContentHeaderModel;
import com.koekoetech.sayarma.model.ProgressModel;
import com.koekoetech.sayarma.model.QuestionModel;
import com.koekoetech.sayarma.model.ScoreModel;
import com.koekoetech.sayarma.model.SubChapterModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import io.realm.Realm;
import io.realm.RealmResults;

public class ChaptersAdapter extends BaseRecyclerViewAdapter {

    private static final int ITEM_CHAPTER = 5;
    private static final int ITEM_CHAPTER_SCORE = 6;

    private final String levelId;

    public ChaptersAdapter(String levelId) {
        this.levelId = levelId;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateCustomViewHolder(ViewGroup parent, int viewType) {

        if(viewType == ITEM_CHAPTER) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_content, parent, false);
            return new EDeviceChapterViewHolder(view);

        } else {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_quiz_score, parent, false);
            return new EDeviceChapterScoreViewHolder(view);

        }

    }

    @Override
    protected void onBindCustomViewHolder(RecyclerView.ViewHolder holder, int position) {

        int viewType = getItemViewType(position);

        if(viewType == ITEM_CHAPTER) {

            ((EDeviceChapterViewHolder) holder).bindChapters((ChapterModel) getItemsList().get(position));

        } else {

            ((EDeviceChapterScoreViewHolder) holder).bindChapters((ChapterScoreModel) getItemsList().get(position));

        }

    }

    @Override
    public int getItemViewType(int position) {
        Pageable item = getItemsList().get(position);
        if (item instanceof ChapterModel) {
            return ITEM_CHAPTER;
        } else if (item instanceof ChapterScoreModel) {
            return ITEM_CHAPTER_SCORE;
        } else {
            return super.getItemViewType(position);
        }
    }


    @Override
    protected RecyclerView.ViewHolder onCreateCustomHeaderViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.header_content, parent, false);

        return new HeaderHolder(view);
    }

    @Override
    protected void onBindCustomHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecyclerHeader recyclerHeader = (RecyclerHeader) getItemsList().get(position);
        ((HeaderHolder) holder).bindHeader((ContentHeaderModel) recyclerHeader.getHeaderData());

    }

    static class EDeviceChapterViewHolder extends RecyclerView.ViewHolder {

        private MyanTextView txtIntroChapterTitle;
        private ProgressBar progressBarChapter;
        private MyanTextView txtContentCompletedCount;
        private MyanTextView txtContentTotalCount;
        private ImageView imgChapterAudio;
        private ImageView imgChapterAudioMute;

        Realm realm;
        Context context;

        private void bindViews(View itemView){
            txtIntroChapterTitle = itemView.findViewById(R.id.txtIntroChapterTitle);
            progressBarChapter = itemView.findViewById(R.id.progressBarChapter);
            txtContentCompletedCount = itemView.findViewById(R.id.txtContentCompletedCount);
            txtContentTotalCount = itemView.findViewById(R.id.txtContentTotalCount);
            imgChapterAudio = itemView.findViewById(R.id.imgChapterAudio);
            imgChapterAudioMute = itemView.findViewById(R.id.imgChapterAudioMute);
        }

        public EDeviceChapterViewHolder(@NonNull final View itemView) {
            super(itemView);
            bindViews(itemView);
            realm = Realm.getDefaultInstance();
            context = itemView.getContext();
        }

        @SuppressLint("SetTextI18n")
        void bindChapters(ChapterModel model){
            txtIntroChapterTitle.setMyanmarText(model.getTitle());

            //Calculate Progress
            RealmResults<SubChapterModel> subChapterList = realm.where(SubChapterModel.class).equalTo("ChapterID", model.getChapterID()).findAll();
            List<SubChapterModel> chapterList = realm.copyFromRealm(subChapterList);

            RealmResults<ProgressModel> completedLessonList = realm.where(ProgressModel.class).equalTo("Chapter_ID", model.getChapterID()).findAll();
            List<ProgressModel> lessonList = realm.copyFromRealm(completedLessonList);
            if(lessonList.size() > 0 && subChapterList.size() > 0){
                if(lessonList.size() == subChapterList.size()){
                    progressBarChapter.setProgress(100);
                }else {
                    int progress = 100 / chapterList.size();
                    progressBarChapter.setProgress(progress * lessonList.size());
                }
            }else {
                progressBarChapter.setProgress(0);
            }

            txtContentCompletedCount.setText(lessonList.size() + "");
            txtContentTotalCount.setText(chapterList.size() + "");

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), SubChaptersActivity.class);
                intent.putExtra(SubChaptersActivity.CHAPTER_MODEL_EXTRA, model);
                itemView.getContext().startActivity(intent);
            });

            playMp3(model.getAudio());
        }

        private void playMp3(String audio){
            MediaPlayer mediaPlayer = new MediaPlayer();

            try {
                File file = ExternalFileHelper.getTitleAudioFile(context, audio);
                mediaPlayer.setDataSource(file.getAbsolutePath());
                mediaPlayer.prepare();
            } catch (Exception e) {
                e.printStackTrace();
            }

            imgChapterAudio.setOnClickListener(v -> {
                mediaPlayer.start();
                imgChapterAudio.setVisibility(View.GONE);
                imgChapterAudioMute.setVisibility(View.VISIBLE);
            });

            imgChapterAudioMute.setOnClickListener(v -> {
                mediaPlayer.pause();
                imgChapterAudio.setVisibility(View.VISIBLE);
                imgChapterAudioMute.setVisibility(View.GONE);
            });

            mediaPlayer.setOnCompletionListener(mp -> {

                imgChapterAudio.setVisibility(View.VISIBLE);
                imgChapterAudioMute.setVisibility(View.GONE);
            });

        }
    }

    static class EDeviceChapterScoreViewHolder extends RecyclerView.ViewHolder {

        private MyanBoldTextView tv_title;
        private MyanTextView tv_levelQuizMark;
        private ImageView imgAudioQuiz;
        private ImageView imgAudioQuizMute;

        private final Context context;

        private void bindViews(View itemView){
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_levelQuizMark = itemView.findViewById(R.id.tv_levelQuizMark);
            imgAudioQuiz = itemView.findViewById(R.id.imgAudioQuiz);
            imgAudioQuizMute = itemView.findViewById(R.id.imgAudioQuizMute);
        }

        public EDeviceChapterScoreViewHolder(@NonNull final View itemView) {
            super(itemView);
            bindViews(itemView);

            context = itemView.getContext();
        }

        void bindChapters(ChapterScoreModel model){
            tv_title.setMyanmarText(TextDictionaryHelper.getText(context, TextDictionaryHelper.TEXT_QUIZ));

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), QuestionAnswerActivity.class);
                intent.putExtra(QuestionAnswerActivity.TYPE_EXTRA, 1);
                intent.putExtra(QuestionAnswerActivity.LEVEL_ID_OR_SUB_CHAPTER_ID_EXTRA, model.getLevelID());
                itemView.getContext().startActivity(intent);
            });

            getMarks(model);
            playMp3();
        }

        private void playMp3(){
            MediaPlayer mediaPlayer = new MediaPlayer();

            try {
                File file = ExternalFileHelper.getTitleAudioFile(context,"Quizzes.mp3");
                mediaPlayer.setDataSource(file.getAbsolutePath());
                mediaPlayer.prepare();
            } catch (Exception e) {
                e.printStackTrace();
            }

            imgAudioQuiz.setOnClickListener(v -> {
                mediaPlayer.start();
                imgAudioQuiz.setVisibility(View.GONE);
                imgAudioQuizMute.setVisibility(View.VISIBLE);
            });

            imgAudioQuizMute.setOnClickListener(v -> {
                mediaPlayer.pause();
                imgAudioQuiz.setVisibility(View.VISIBLE);
                imgAudioQuizMute.setVisibility(View.GONE);
            });

            mediaPlayer.setOnCompletionListener(mp -> {

                imgAudioQuiz.setVisibility(View.VISIBLE);
                imgAudioQuizMute.setVisibility(View.GONE);
            });

        }

        @SuppressLint("SetTextI18n")
        private void getMarks(ChapterScoreModel model) {
            int get_mark;
            Realm realm = Realm.getDefaultInstance();
            int total_mark = realm.where(QuestionModel.class)
                    .equalTo("levelID", model.getLevelID())
                    .equalTo("Type", 1).sum("Point").intValue();

            Log.v("LevelID", model.getLevelID());
            Log.v("TotalMarks", total_mark + " marks");

            RealmResults<ScoreModel> scoreModelList= realm.where(ScoreModel.class)
                    .equalTo("LessonID", model.getLevelID())
                    .equalTo("Type", "1")
                    .findAll();
            ArrayList<ScoreModel> scoreList = (ArrayList<ScoreModel>) realm.copyFromRealm(scoreModelList);

            if(scoreList.size() > 0) {
                get_mark = scoreList.get(scoreList.size()-1).getScorePoint();
                if(total_mark != 0 && get_mark != 0){
                    tv_levelQuizMark.setText("Total Marks : " + get_mark + "/" + total_mark);
                }
            }
        }
    }

    class HeaderHolder extends RecyclerView.ViewHolder {

        private final Context context;

        private MyanBoldTextView tv_title;
        private VideoView videoView;

        private void bindViews(View itemView){
            tv_title = itemView.findViewById(R.id.tv_title);
            videoView = itemView.findViewById(R.id.videoview);
        }

        public HeaderHolder(View itemView) {
            super(itemView);
            this.context = itemView.getContext();
            bindViews(itemView);
        }

        private void bindHeader(final ContentHeaderModel model) {

            switch (levelId) {
                case "5dfdc360-c1c5-40f2-8cd3-62029fcec2be": {
                    Uri uri = ExternalFileHelper.getVideoFileUri(context, "intro.mp4");
                    videoView.setVideoURI(uri);
                    break;
                }
                case "cf966661-4b60-414c-a062-73aa5b732981": {
                    Uri uri = ExternalFileHelper.getVideoFileUri(context,"level1.mp4");
                    videoView.setVideoURI(uri);
                    break;
                }
                case "2fbba33f-9adf-41b5-8160-e2a840ad750c": {
                    Uri uri = ExternalFileHelper.getVideoFileUri(context,"level2.mp4");
                    videoView.setVideoURI(uri);
                    break;
                }
            }


            MediaController mediaController = new MediaController(context);
            videoView.setMediaController(mediaController);
            mediaController.setAnchorView(videoView);
            videoView.start();

            tv_title.setMyanmarText(model.getTitle());

        }
    }

}
