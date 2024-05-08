package com.koekoetech.sayarma.activity;

import android.os.Bundle;
import com.koekoetech.sayarma.R;
import com.koekoetech.sayarma.adapter.CurriculumSubchapterAdapter;
import com.koekoetech.sayarma.model.CurriculumSubChapterModel;
import java.util.List;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;

public class CurriculumSubchapterActivity extends BaseActivity{

    private RecyclerView recyclerviewEDeviceLessons;
    private CurriculumSubchapterAdapter adapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_content;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {
        setUpToolbar(true);
        setUpToolbarText("");

        recyclerviewEDeviceLessons = findViewById(R.id.recyclerviewEDeviceLessons);
        adapter = new CurriculumSubchapterAdapter();
        recyclerviewEDeviceLessons.setHasFixedSize(true);
        recyclerviewEDeviceLessons.setAdapter(adapter);

        Realm realm = Realm.getDefaultInstance();
        RealmResults<CurriculumSubChapterModel> subChapters= realm.where(CurriculumSubChapterModel.class).findAll();
        List<CurriculumSubChapterModel> subChapterList = realm.copyFromRealm(subChapters);
        for(CurriculumSubChapterModel curriculumSubChapter: subChapterList){
            adapter.add(curriculumSubChapter);
        }
    }
}
