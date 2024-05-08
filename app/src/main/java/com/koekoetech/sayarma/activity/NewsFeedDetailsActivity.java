package com.koekoetech.sayarma.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.koekoetech.sayarma.R;
import com.koekoetech.sayarma.adapter.SliderAdapter;
import com.koekoetech.sayarma.custom_control.MyanTextView;
import com.koekoetech.sayarma.helper.ServiceHelper;
import com.koekoetech.sayarma.model.ArticleModel;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import androidx.annotation.NonNull;
import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFeedDetailsActivity extends BaseActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{

    public static String CONTENT_ID_EXTRA = "CONTENT_ID";

    private MyanTextView txtNewsFeedDetailsOrganizationName;
    private TextView txtNewsFeedDetailsDate;
    private TextView txtNewsFeedDetailsTime;
    private MyanTextView txtNewsFeedDetailsContentTitle;
    private MyanTextView txtNewsFeedDetailsContentBody;
    private SliderView sliderView;

    String content_id;
    private ProgressDialog progressDialog;

    private void bindViews(){
        txtNewsFeedDetailsOrganizationName = findViewById(R.id.txtNewsFeedDetailsOrganizationName);
        txtNewsFeedDetailsDate = findViewById(R.id.txtNewsFeedDetailsDate);
        txtNewsFeedDetailsTime = findViewById(R.id.txtNewsFeedDetailsTime);
        txtNewsFeedDetailsContentTitle = findViewById(R.id.txtNewsFeedDetailsContentTitle);
        txtNewsFeedDetailsContentBody = findViewById(R.id.txtNewsFeedDetailsContentBody);
        sliderView = findViewById(R.id.imageSlider);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_newsfeed_details;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {

        setUpToolbar(true);
        setUpToolbarText("Sayarma");

        bindViews();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        content_id = getIntent().getStringExtra(CONTENT_ID_EXTRA);

        if(content_id != null){
            getArticleByContentID(content_id);
        }

    }

    private void getArticleByContentID(String content_id) {
        progressDialog.show();
        Call<ArticleModel> getArticle = ServiceHelper.getClient(NewsFeedDetailsActivity.this).getArticlesByContentID(content_id);
        getArticle.enqueue(new Callback<ArticleModel>() {
            @Override
            public void onResponse(@NonNull Call<ArticleModel> call, @NonNull Response<ArticleModel> response) {
                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                if (response.isSuccessful()) {
                    ArticleModel model = response.body();
                    if(model != null) {
                        txtNewsFeedDetailsContentTitle.setMyanmarText(model.getTitle());
                        txtNewsFeedDetailsContentBody.setMyanmarText(model.getContentBody());

                        if (!TextUtils.isEmpty(model.getOrganizationTitle())) {
                            txtNewsFeedDetailsOrganizationName.setMyanmarText(model.getOrganizationTitle());
                        }

                        if (!TextUtils.isEmpty(model.getCreatedDate())) {
                            String datetime = model.getCreatedDate();

                            List<String> datetimelist = Arrays.asList(datetime.split("T"));
                            txtNewsFeedDetailsDate.setText(datetimelist.get(0));
                            txtNewsFeedDetailsTime.setText(datetimelist.get(1));
                        }

                        RealmList<String> photos = model.getPhotos();
                        ArrayList<String> photoList = new ArrayList<>(photos);
                        SliderAdapter sliderAdapter = new SliderAdapter(photoList);
                        sliderView.setSliderAdapter(sliderAdapter);
                        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
                        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
                        sliderView.startAutoCycle();
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<ArticleModel> call, @NonNull Throwable t) {
                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
