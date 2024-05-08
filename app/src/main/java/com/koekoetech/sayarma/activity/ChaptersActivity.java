package com.koekoetech.sayarma.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import com.koekoetech.sayarma.R;
import com.koekoetech.sayarma.adapter.ChaptersAdapter;
import com.koekoetech.sayarma.custom_control.MyanTextProcessor;
import com.koekoetech.sayarma.model.ChapterModel;
import com.koekoetech.sayarma.model.ChapterScoreModel;
import com.koekoetech.sayarma.model.ContentHeaderModel;
import java.util.List;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class ChaptersActivity extends BaseActivity {

    private RecyclerView recyclerviewEDeviceLessons;

    ChaptersAdapter adapter;

    private void bindViews(){
        recyclerviewEDeviceLessons = findViewById(R.id.recyclerviewEDeviceLessons);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_content;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {

        setUpToolbar(true);
        setUpToolbarText(MyanTextProcessor.processText(this, getIntent().getStringExtra("title")));

        bindViews();

        adapter = new ChaptersAdapter(getIntent().getStringExtra("levelId"));
        recyclerviewEDeviceLessons.setHasFixedSize(true);
        recyclerviewEDeviceLessons.setAdapter(adapter);

        Realm realm = Realm.getDefaultInstance();

        ContentHeaderModel contentHeaderModel = new ContentHeaderModel();
        contentHeaderModel.setLevelId(getIntent().getStringExtra("levelId"));
        contentHeaderModel.setTitle(getIntent().getStringExtra("title"));
        adapter.addHeader(contentHeaderModel);

        RealmResults<ChapterModel> level_chapterList = realm.where(ChapterModel.class)
                .equalTo("levelID", getIntent().getStringExtra("levelId"))
                .sort("OrderingID", Sort.ASCENDING)
                .findAll();
        List<ChapterModel> chapterList = realm.copyFromRealm(level_chapterList);
        for(ChapterModel model : chapterList){
            adapter.add(model);
        }
        ChapterScoreModel chapterScoreModel = new ChapterScoreModel();
        chapterScoreModel.setTitle("Quiz");
        chapterScoreModel.setLevelID(getIntent().getStringExtra("levelId"));
        adapter.add(chapterScoreModel);

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onPostResume() {
        super.onPostResume();
        adapter.notifyDataSetChanged();
    }
}
