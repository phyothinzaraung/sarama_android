package com.koekoetech.sayarma.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import com.koekoetech.sayarma.R;
import com.koekoetech.sayarma.adapter.SubChaptersAdapter;
import com.koekoetech.sayarma.custom_control.MyanTextProcessor;
import com.koekoetech.sayarma.custom_control.MyanTextView;
import com.koekoetech.sayarma.helper.TextDictionaryHelper;
import com.koekoetech.sayarma.model.ChapterModel;
import com.koekoetech.sayarma.model.ProgressModel;
import com.koekoetech.sayarma.model.QuestionModel;
import com.koekoetech.sayarma.model.ScoreModel;
import com.koekoetech.sayarma.model.SubChapterModel;
import java.util.ArrayList;
import java.util.List;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class SubChaptersActivity extends BaseActivity {

    public static String CHAPTER_MODEL_EXTRA = "CHAPTER_MODEL";

    private RecyclerView recyclerviewSubChapters;
    private ProgressBar subChapterProgressBar;
    private MyanTextView txtCompletedCount;
    private MyanTextView txtTotalCount;
    private MyanTextView txtTotalMarks;
    private MyanTextView txtChapterTitle;

    private SubChaptersAdapter subChaptersAdapter;
    ChapterModel model;
    private Realm realm;

    private void bindViews(){
        recyclerviewSubChapters = findViewById(R.id.recyclerviewSubChapters);
        subChapterProgressBar = findViewById(R.id.subChapterProgressBar);
        txtCompletedCount = findViewById(R.id.txtCompletedCount);
        txtTotalCount = findViewById(R.id.txtTotalCount);
        txtTotalMarks = findViewById(R.id.txtTotalMarks);
        txtChapterTitle = findViewById(R.id.txtChapterTitle);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_sub_chapters;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {

        setUpToolbar(true);
        setUpToolbarText(MyanTextProcessor.processText(this, TextDictionaryHelper.getText(this, TextDictionaryHelper.TEXT_SUB_CHAPTERS)));

        bindViews();

        realm = Realm.getDefaultInstance();

        init();
    }

    private void init(){
        model = (ChapterModel) getIntent().getSerializableExtra(CHAPTER_MODEL_EXTRA);

        getMarks(model);

        subChaptersAdapter = new SubChaptersAdapter(model.getChapterID());
        recyclerviewSubChapters.setHasFixedSize(true);
        recyclerviewSubChapters.setAdapter(subChaptersAdapter);

        if(model != null) {
            getSubChapters(model.getChapterID());
            txtChapterTitle.setMyanmarText(model.getTitle());
        }
    }

    @SuppressLint("SetTextI18n")
    private void getMarks(ChapterModel model) {
        int total_mark = realm.where(QuestionModel.class)
                .equalTo("SubChapterID", model.getChapterID()).sum("Point").intValue();

        RealmResults<ScoreModel> scoreModelList= realm.where(ScoreModel.class)
                .equalTo("LessonID", model.getChapterID())
                .equalTo("Type", "0")
                .findAll();
        ArrayList<ScoreModel> scoreList = (ArrayList<ScoreModel>) realm.copyFromRealm(scoreModelList);

        if(scoreList.size() > 0) {
            int get_mark = scoreList.get(scoreList.size() - 1).getScorePoint();
            if(total_mark != 0 && get_mark != 0){
                txtTotalMarks.setVisibility(View.VISIBLE);
                txtTotalMarks.setText("Total Marks : " + get_mark + "/" + total_mark);
            }
        } else {
            txtTotalMarks.setVisibility(View.GONE);
        }
    }

    private void getSubChapters(String id) {

        Log.d("CHAPTER_ID", id);

        RealmResults<SubChapterModel> subChapterList = realm.where(SubChapterModel.class)
                .equalTo("ChapterID", id)
                .equalTo("levelID", model.getLevelID())
                .sort("OrderingID", Sort.ASCENDING)
                .findAll();

        ArrayList<SubChapterModel> chapterList = (ArrayList<SubChapterModel>) realm.copyFromRealm(subChapterList);

        SubChapterModel quiz_model = new SubChapterModel();
        quiz_model.setLevelID(model.getLevelID());
        quiz_model.setChapterID(id);
        quiz_model.setContent("Quizzes");
        chapterList.add(quiz_model);

        for(SubChapterModel model : chapterList){
            subChaptersAdapter.add(model);
        }

        RealmResults<ProgressModel> completedLessonList = realm.where(ProgressModel.class).equalTo("Chapter_ID", id).findAll();
        List<ProgressModel> lessonList = realm.copyFromRealm(completedLessonList);
        if(lessonList.size() > 0){
            if(lessonList.size() == (chapterList.size()-1)){
                subChapterProgressBar.setProgress(100);
            }else {
                int progress = 100 / (chapterList.size() - 1);
                subChapterProgressBar.setProgress(progress * lessonList.size());
            }
        }

        txtCompletedCount.setMyanmarText(lessonList.size()+"");
        txtTotalCount.setMyanmarText((chapterList.size()-1)+"");

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        if(model != null) {
            subChaptersAdapter.clear();
            getSubChapters(model.getChapterID());
            getMarks(model);
        }
    }
}
