package com.koekoetech.sayarma.activity;

import android.os.Bundle;
import com.koekoetech.sayarma.R;
import com.koekoetech.sayarma.adapter.CurriculumChaptersAdapter;
import com.koekoetech.sayarma.custom_control.MyanTextView;
import com.koekoetech.sayarma.model.CurriculumChapterModel;
import com.koekoetech.sayarma.model.CurriculumLevelModel;
import java.util.ArrayList;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class CurriculumChapterActivity extends BaseActivity{

    public static String CURRICULUM_LEVEL_EXTRA = "CURRICULUM_LEVEL_MODEL";

    private RecyclerView recyclerviewCurriculumChapters;
    private MyanTextView txtCurriculumChapterTitle;

    private CurriculumChaptersAdapter chaptersAdapter;
    CurriculumLevelModel model;
    private Realm realm;

    private void bindViews(){
        recyclerviewCurriculumChapters = findViewById(R.id.recyclerviewCurriculumChapters);
        txtCurriculumChapterTitle = findViewById(R.id.txtCurriculumChapterTitle);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_curriculum_chapter;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {
        setUpToolbar(true);
        setUpToolbarText("");

        bindViews();

        realm = Realm.getDefaultInstance();

        init();
    }

    private void init(){
        chaptersAdapter = new CurriculumChaptersAdapter();
        recyclerviewCurriculumChapters.setHasFixedSize(true);
        recyclerviewCurriculumChapters.setAdapter(chaptersAdapter);

        model = (CurriculumLevelModel) getIntent().getSerializableExtra(CURRICULUM_LEVEL_EXTRA);

        if(model != null){
            txtCurriculumChapterTitle.setMyanmarText(model.getTitle());
            getCurriculumChapters(model.getLevelID());
        }
    }

    private void getCurriculumChapters(String id){
        RealmResults<CurriculumChapterModel> chapters = realm.where(CurriculumChapterModel.class)
                .equalTo("levelID", id)
                .sort("OrderingID", Sort.ASCENDING)
                .findAll();

        ArrayList<CurriculumChapterModel> chapterList = (ArrayList<CurriculumChapterModel>) realm.copyFromRealm(chapters);
        for(CurriculumChapterModel chapter: chapterList){
            chaptersAdapter.add(chapter);
        }
    }
}
