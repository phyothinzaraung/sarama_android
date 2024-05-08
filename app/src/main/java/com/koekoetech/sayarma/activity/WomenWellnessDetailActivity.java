package com.koekoetech.sayarma.activity;

import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import com.koekoetech.sayarma.R;
import com.koekoetech.sayarma.adapter.WomenWellnessDetailAdapter;
import com.koekoetech.sayarma.model.WomenWellnessModel;
import java.util.ArrayList;
import java.util.List;

public class WomenWellnessDetailActivity extends BaseActivity {

    public static final String WOMEN_WELLNESS_EXTRA = "WOMEN_WELLNESS";

    List<String> photoList = new ArrayList<>();
    WomenWellnessDetailAdapter adapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_womenwellness_detail;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {

        WomenWellnessModel womenWellnessModel = (WomenWellnessModel) getIntent().getSerializableExtra(WOMEN_WELLNESS_EXTRA);
        setUpToolbar(true);

        RecyclerView recyclerviewWomenWellnessDetail = findViewById(R.id.recyclerviewWomenWellnessDetail);

        if (womenWellnessModel != null){
            setUpToolbarText(womenWellnessModel.getTitle());
            photoList = womenWellnessModel.getPhotos();

            Log.v("PhotoList", womenWellnessModel.getPhotos() + "here");

            adapter = new WomenWellnessDetailAdapter(photoList);
            recyclerviewWomenWellnessDetail.setHasFixedSize(true);
            recyclerviewWomenWellnessDetail.setAdapter(adapter);
        }

    }
}
