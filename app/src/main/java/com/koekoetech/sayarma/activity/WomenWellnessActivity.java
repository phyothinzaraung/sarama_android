package com.koekoetech.sayarma.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;
import com.koekoetech.sayarma.R;
import com.koekoetech.sayarma.adapter.WomenWellnessAdapter;
import com.koekoetech.sayarma.helper.ServiceHelper;
import com.koekoetech.sayarma.helper.TextDictionaryHelper;
import com.koekoetech.sayarma.model.WomenWellnessModel;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WomenWellnessActivity extends BaseActivity {

    RecyclerView recyclerviewWomenWellness;

    WomenWellnessAdapter adapter;

    ProgressDialog progressDialog;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_women_wellness;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {

        setUpToolbar(true);
        setUpToolbarText(TextDictionaryHelper.getText(this, TextDictionaryHelper.TEXT_WOMEN_WELLNESS));

        recyclerviewWomenWellness = findViewById(R.id.recyclerviewWomenWellness);

        adapter = new WomenWellnessAdapter();
        recyclerviewWomenWellness.setHasFixedSize(true);
        recyclerviewWomenWellness.setAdapter(adapter);

        progressDialog = new ProgressDialog(WomenWellnessActivity.this);
        progressDialog.setMessage("Loading...");

        getWomenWellness();

    }

    private void getWomenWellness() {

        progressDialog.show();

        Call<ArrayList<WomenWellnessModel>> getWomenWellness = ServiceHelper.getClient(this).getWomenWellness();
        getWomenWellness.enqueue(new Callback<ArrayList<WomenWellnessModel>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<WomenWellnessModel>> call, @NonNull Response<ArrayList<WomenWellnessModel>> response) {
                if(response.isSuccessful()){
                    if(progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                    ArrayList<WomenWellnessModel> womenWellnessModelArrayList = response.body();
                    if(womenWellnessModelArrayList != null) {
                        for (WomenWellnessModel model : womenWellnessModelArrayList) {
                            adapter.add(model);
                        }
                    }
                }else {
                    if(progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                    Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<WomenWellnessModel>> call, @NonNull Throwable t) {
                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
