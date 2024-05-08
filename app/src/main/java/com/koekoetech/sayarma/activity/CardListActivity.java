package com.koekoetech.sayarma.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.SeekBar;
import com.koekoetech.sayarma.R;
import com.koekoetech.sayarma.adapter.CarouselAdapter;
import com.koekoetech.sayarma.custom_control.MyanTextProcessor;
import com.koekoetech.sayarma.helper.DialogHelperWithOkCancelButton;
import com.koekoetech.sayarma.helper.ExternalFileHelper;
import com.koekoetech.sayarma.helper.SharedPreferenceHelper;
import com.koekoetech.sayarma.helper.SnapPagerItemDecorator;
import com.koekoetech.sayarma.model.ProgressModel;
import com.koekoetech.sayarma.model.SectionCardsModel;
import com.koekoetech.sayarma.model.SubChapterModel;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import io.realm.Realm;
import io.realm.RealmResults;

public class CardListActivity extends BaseActivity {

    public static String SUB_CHAPTER_EXTRA = "SUB_CHAPTER";

    private AppCompatImageView btnPlay;
    private AppCompatImageView btnPause;
    private SeekBar seekBar;
    private RecyclerView rvCarousel;

    SubChapterModel model;

    private MediaPlayer mediaPlayer;
    private final Handler handler = new Handler();
    private Runnable runnable;
    private Realm realm;
    private SharedPreferenceHelper sharedPreferenceHelper;

    CarouselAdapter carouselAdapter;

    List<String> images = new ArrayList<>();

    private boolean isLessonFinished;

    DialogHelperWithOkCancelButton dialogHelperWithOkCancelButton;

    private void bindViews(){
        btnPlay = findViewById(R.id.btnPlay);
        btnPause = findViewById(R.id.btnPause);
        seekBar = findViewById(R.id.seekBar);
        rvCarousel = findViewById(R.id.rvCarousel);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_card_list;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {

        bindViews();

        init();

        images = getImages(model.getSubChapterID());

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;

        final int totalHeight = height - (int) convertDpToPx(100);

        final int itemHeight = Math.round(totalHeight * 0.8f);

        final float itemHintPercentage = 0.05f;

        carouselAdapter = new CarouselAdapter(itemHeight, images);
        rvCarousel.setAdapter(carouselAdapter);

        SnapPagerItemDecorator itemDecorator = new SnapPagerItemDecorator(totalHeight, itemHeight, itemHintPercentage);
        rvCarousel.addItemDecoration(itemDecorator);

        rvCarousel.setHasFixedSize(true);

        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(rvCarousel);

        scrollCards();

        playMp3(model.getAudio());
    }

    private void scrollCards() {
        rvCarousel.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {

                    dialogHelperWithOkCancelButton.showDialog(R.mipmap.app_icon,
                            "Does the lesson finish?", "Finish",
                            "Cancel");

                    dialogHelperWithOkCancelButton.getBtnOk().
                            setOnClickListener(v -> {
                                boolean isFinished = isLessonFinished();
                                if(isFinished){
                                    finish();
                                }else {
                                    addCompletedLessontoRealm();
                                    finish();
                                }
                            });

                    dialogHelperWithOkCancelButton.getBtnCancel()
                            .setOnClickListener(v -> dialogHelperWithOkCancelButton.hideDialog());
                }
            }
        });
    }

    private boolean isLessonFinished(){

        RealmResults<ProgressModel> completedLessonList = realm.where(ProgressModel.class).equalTo("Chapter_ID", model.getChapterID()).findAll();
        ArrayList<ProgressModel> lessonList = (ArrayList<ProgressModel>) realm.copyFromRealm(completedLessonList);

        if(lessonList.size() == 0){
            isLessonFinished = false;
        }else {
            lessonList.size();

            for(int i=0; i<lessonList.size(); i++){
                if(lessonList.get(i).getSubChapter_ID().equals(model.getSubChapterID())){
                    isLessonFinished = true;
                }
            }
        }

        return isLessonFinished;
    }

    private void addCompletedLessontoRealm(){
        //Add to realm
        realm.executeTransactionAsync(realm -> {

            ProgressModel progressModel = new ProgressModel();
            progressModel.setUserID(sharedPreferenceHelper.getUserId());
            progressModel.setChapter_ID(model.getChapterID());
            progressModel.setSubChapter_ID(model.getSubChapterID());
            progressModel.setLevelID(model.getLevelID());

            realm.insertOrUpdate(progressModel);
        });
    }

    private void init() {
        model = (SubChapterModel) getIntent().getSerializableExtra(SUB_CHAPTER_EXTRA);

        setUpToolbar(true);
        setUpToolbarText(MyanTextProcessor.processText(this, model.getContent()));

        sharedPreferenceHelper = SharedPreferenceHelper.getHelper(CardListActivity.this);

        realm = Realm.getDefaultInstance();

        dialogHelperWithOkCancelButton = new DialogHelperWithOkCancelButton(this);
    }

    public float convertDpToPx(int dp) {
        return dp * getApplicationContext().getResources().getDisplayMetrics().density;
    }

    private void playMp3(String audio) {
        mediaPlayer = new MediaPlayer();
        try {
            File file = ExternalFileHelper.getCurriculumAudioFile(getApplicationContext(), audio);
            mediaPlayer.setDataSource(file.getAbsolutePath());
            mediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnPlay.setVisibility(View.GONE);
                btnPause.setVisibility(View.VISIBLE);
                mediaPlayer.start();

                CardListActivity.this.runOnUiThread(runnable = new Runnable() {
                    @Override
                    public void run() {
                        seekBar.setMax(mediaPlayer.getDuration());
                        seekBar.setProgress(mediaPlayer.getCurrentPosition());
                        handler.postDelayed(this, 1000);
                    }
                });
            }
        });

        btnPause.setOnClickListener(v -> {

            btnPlay.setVisibility(View.VISIBLE);
            btnPause.setVisibility(View.GONE);

            mediaPlayer.pause();
            handler.removeCallbacks(runnable);
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mediaPlayer.setOnCompletionListener(mp -> {
            btnPlay.setVisibility(View.VISIBLE);
            btnPause.setVisibility(View.GONE);
            seekBar.setProgress(0);
            handler.removeCallbacks(runnable);
        });

    }

    private List<String> getImages(String subChapterId) {

        SectionCardsModel sectionCardsModel = realm.where(SectionCardsModel.class).equalTo("SubChapterID", subChapterId).findFirst();

        assert sectionCardsModel != null;
        return Arrays.asList(sectionCardsModel.getCardName().split(","));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
        handler.removeCallbacks(runnable);
        if(dialogHelperWithOkCancelButton != null){
            dialogHelperWithOkCancelButton.hideDialog();
        }
    }

}
