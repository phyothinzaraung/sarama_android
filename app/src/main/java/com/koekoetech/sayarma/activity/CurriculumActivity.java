package com.koekoetech.sayarma.activity;

import android.os.Bundle;

import com.koekoetech.sayarma.R;
import com.koekoetech.sayarma.adapter.CurriculumAdapter;
import com.koekoetech.sayarma.model.ContentHeaderModel;
import com.koekoetech.sayarma.model.CurriculumLevelModel;
import com.koekoetech.sayarma.model.LevelModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;

public class CurriculumActivity extends BaseActivity{

    private RecyclerView recyclerviewEDeviceLessons;
    private CurriculumAdapter adapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_content;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {
        setUpToolbar(true);
        setUpToolbarText("Curriculum");

        recyclerviewEDeviceLessons = findViewById(R.id.recyclerviewEDeviceLessons);
        adapter = new CurriculumAdapter();
        recyclerviewEDeviceLessons.setHasFixedSize(true);
        recyclerviewEDeviceLessons.setAdapter(adapter);

        ContentHeaderModel contentHeaderModel = new ContentHeaderModel();
        contentHeaderModel.setLevelId("0");
        contentHeaderModel.setTitle("Curriculum");
        adapter.addHeader(contentHeaderModel);

        Realm realm = Realm.getDefaultInstance();
        RealmResults<CurriculumLevelModel> levels= realm.where(CurriculumLevelModel.class).findAll();
        List<CurriculumLevelModel> levelList = realm.copyFromRealm(levels);
        for(CurriculumLevelModel level: levelList){
            adapter.add(level);
        }

    }
}
