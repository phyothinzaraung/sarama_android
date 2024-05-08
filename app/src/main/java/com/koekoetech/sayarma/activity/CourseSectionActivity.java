package com.koekoetech.sayarma.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.koekoetech.sayarma.R;
import com.koekoetech.sayarma.custom_control.MyanBoldTextView;
import com.koekoetech.sayarma.custom_control.MyanTextProcessor;
import com.koekoetech.sayarma.custom_control.MyanTextView;
import com.koekoetech.sayarma.helper.ExternalFileHelper;
import com.koekoetech.sayarma.helper.TextDictionaryHelper;
import com.koekoetech.sayarma.model.ProgressModel;
import com.koekoetech.sayarma.model.SubChapterModel;

import java.io.File;
import java.util.ArrayList;
import io.realm.Realm;
import io.realm.RealmResults;

public class CourseSectionActivity extends BaseActivity {

    private LinearLayout linear_intro;
    private LinearLayout linear_level1;
    private LinearLayout linear_level2;
    private MyanTextView txtIntroEDevices;
    private MyanTextView txtLevel1;
    private FloatingActionButton fab_level1;
    private View view_level1;
    private MyanTextView txtLevel2;
    private FloatingActionButton fab_level2;
    private View view_level2;
    private ImageView imgLevel;
    private MyanBoldTextView txtLevel;
    private ImageView imgAudioIntro;
    private ImageView imgAudioIntroMute;
    private ImageView imgAudioLevel1;
    private ImageView imgAudioLevel1Mute;
    private ImageView imgAudioLevel2;
    private ImageView imgAudioLevel2Mute;

    private Realm realm;
    private final String intro_level = "5dfdc360-c1c5-40f2-8cd3-62029fcec2be";
    private final String level1 = "cf966661-4b60-414c-a062-73aa5b732981";
    private final String level2 = "2fbba33f-9adf-41b5-8160-e2a840ad750c";
    private MediaPlayer mediaPlayer1;
    private MediaPlayer mediaPlayer2;

    private void bindViews(){
        linear_intro = findViewById(R.id.linear_intro);
        linear_level1 = findViewById(R.id.linear_level1);
        linear_level2 = findViewById(R.id.linear_level2);
        txtIntroEDevices = findViewById(R.id.txtIntroEDevices);
        txtLevel1 = findViewById(R.id.txtLevel1);
        fab_level1 = findViewById(R.id.fab_level1);
        view_level1 = findViewById(R.id.view_level1);
        txtLevel2 = findViewById(R.id.txtLevel2);
        fab_level2 = findViewById(R.id.fab_level2);
        view_level2 = findViewById(R.id.view_level2);
        imgLevel = findViewById(R.id.imgLevel);
        txtLevel = findViewById(R.id.txtLevel);
        imgAudioIntro = findViewById(R.id.imgAudioIntro);
        imgAudioIntroMute = findViewById(R.id.imgAudioIntroMute);
        imgAudioLevel1 = findViewById(R.id.imgAudioLevel1);
        imgAudioLevel1Mute = findViewById(R.id.imgAudioLevel1Mute);
        imgAudioLevel2 = findViewById(R.id.imgAudioLevel2);
        imgAudioLevel2Mute = findViewById(R.id.imgAudioLevel2Mute);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_course_section;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {
        setUpToolbar(true);
        setUpToolbarText(MyanTextProcessor.processText(this, TextDictionaryHelper.getText(this, TextDictionaryHelper.TEXT_COURSE_SECTION)));

        bindViews();

        txtIntroEDevices.setMyanmarText(TextDictionaryHelper.getText(this, TextDictionaryHelper.TEXT_INTRO_EDEVICES));
        txtLevel1.setMyanmarText(TextDictionaryHelper.getText(this, TextDictionaryHelper.TEXT_LEVEL1));
        txtLevel2.setMyanmarText(TextDictionaryHelper.getText(this, TextDictionaryHelper.TEXT_LEVEL2));

        realm = Realm.getDefaultInstance();

        linear_intro.setOnClickListener(v -> {
            Intent intent = new Intent(CourseSectionActivity.this, ChaptersActivity.class);
            intent.putExtra("title", TextDictionaryHelper.getText(CourseSectionActivity.this, TextDictionaryHelper.TEXT_INTRO_EDEVICES));
            intent.putExtra("levelId", intro_level);
            startActivity(intent);
        });

        gotoLevel1();
        gotoLevel2();

        playIntroMp3();
        playLevel1Mp3();
        playLevel2Mp3();
    }

    @SuppressLint("UseCompatLoadingForColorStateLists")
    private void gotoLevel2() {
        RealmResults<ProgressModel> level1_chapters = realm.where(ProgressModel.class).equalTo("levelID", level1).findAll();
        ArrayList<ProgressModel> level1_finished_list = (ArrayList<ProgressModel>) realm.copyFromRealm(level1_chapters);

        RealmResults<SubChapterModel> level1List = realm.where(SubChapterModel.class).equalTo("levelID", level1).findAll();
        ArrayList<SubChapterModel> level1_existing_list = (ArrayList<SubChapterModel>) realm.copyFromRealm(level1List);

        if(level1_finished_list.size() > 0 && level1_existing_list.size() > 0) {
            if (level1_finished_list.size() == level1_existing_list.size()) {
                linear_level2.setEnabled(true);

                imgLevel.setImageResource(R.drawable.level2);
                txtLevel.setMyanmarText("Level 2");

                fab_level2.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary));
                view_level2.setBackgroundColor(ContextCompat.getColor(CourseSectionActivity.this, R.color.colorPrimary));

                linear_level2.setOnClickListener(v -> {
                    Intent intent = new Intent(CourseSectionActivity.this, ChaptersActivity.class);
                    intent.putExtra("title", TextDictionaryHelper.getText(CourseSectionActivity.this, TextDictionaryHelper.TEXT_LEVEL2));
                    intent.putExtra("levelId", level2);
                    startActivity(intent);
                });
            }
        }
    }

    @SuppressLint("UseCompatLoadingForColorStateLists")
    private void gotoLevel1() {
        RealmResults<ProgressModel> intro_chapters = realm.where(ProgressModel.class).equalTo("levelID", intro_level).findAll();
        ArrayList<ProgressModel> intro_finished_list = (ArrayList<ProgressModel>) realm.copyFromRealm(intro_chapters);

        RealmResults<SubChapterModel> intro = realm.where(SubChapterModel.class).equalTo("levelID", intro_level).findAll();
        ArrayList<SubChapterModel> introList = (ArrayList<SubChapterModel>) realm.copyFromRealm(intro);

        if(intro_finished_list.size() > 0 && introList.size() > 0) {
            if (intro_finished_list.size() == introList.size()) {
                linear_level1.setEnabled(true);

                imgLevel.setImageResource(R.drawable.level1);
                txtLevel.setMyanmarText("Level 1");

                fab_level1.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary));
                view_level1.setBackgroundColor(ContextCompat.getColor(CourseSectionActivity.this, R.color.colorPrimary));

                linear_level1.setOnClickListener(v -> {
                    Intent intent = new Intent(CourseSectionActivity.this, ChaptersActivity.class);
                    intent.putExtra("title", TextDictionaryHelper.getText(CourseSectionActivity.this, TextDictionaryHelper.TEXT_LEVEL1));
                    intent.putExtra("levelId", level1);
                    startActivity(intent);
                });
            }else {
                linear_level1.setEnabled(false);
                linear_level2.setEnabled(false);
            }
        }else {
            linear_level1.setEnabled(false);
            linear_level2.setEnabled(false);
        }
    }

    private void playIntroMp3(){
        mediaPlayer1 = new MediaPlayer();

        try {
            File file = ExternalFileHelper.getTitleAudioFile(getApplicationContext(), "Intro_Maintitle.mp3");
            mediaPlayer1.setDataSource(file.getAbsolutePath());
            mediaPlayer1.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }

        imgAudioIntro.setOnClickListener(v -> {
            mediaPlayer1.start();
            imgAudioIntro.setVisibility(View.GONE);
            imgAudioIntroMute.setVisibility(View.VISIBLE);
        });

        imgAudioIntroMute.setOnClickListener(v -> {
            mediaPlayer1.pause();
            imgAudioIntro.setVisibility(View.VISIBLE);
            imgAudioIntroMute.setVisibility(View.GONE);
        });

        mediaPlayer1.setOnCompletionListener(mp -> {

            imgAudioIntro.setVisibility(View.VISIBLE);
            imgAudioIntroMute.setVisibility(View.GONE);
        });

    }

    private void playLevel1Mp3(){
        mediaPlayer2 = new MediaPlayer();

        try {
            File file = ExternalFileHelper.getTitleAudioFile(getApplicationContext(), "Level1_Maintitle.mp3");
            mediaPlayer2.setDataSource(file.getAbsolutePath());
            mediaPlayer2.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }

        imgAudioLevel1.setOnClickListener(v -> {
            mediaPlayer2.start();
            imgAudioLevel1.setVisibility(View.GONE);
            imgAudioLevel1Mute.setVisibility(View.VISIBLE);
        });

        imgAudioLevel1Mute.setOnClickListener(v -> {
            mediaPlayer2.pause();
            imgAudioLevel1.setVisibility(View.VISIBLE);
            imgAudioLevel1Mute.setVisibility(View.GONE);
        });

        mediaPlayer2.setOnCompletionListener(mp -> {

            imgAudioLevel1.setVisibility(View.VISIBLE);
            imgAudioLevel1Mute.setVisibility(View.GONE);
        });

    }

    private void playLevel2Mp3(){
        MediaPlayer mediaPlayer = new MediaPlayer();

        try {
            File file = ExternalFileHelper.getTitleAudioFile(getApplicationContext(), "Level2_Maintitle.mp3");
            mediaPlayer.setDataSource(file.getAbsolutePath());
            mediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }

        imgAudioLevel2.setOnClickListener(v -> {
            mediaPlayer.start();
            imgAudioLevel2.setVisibility(View.GONE);
            imgAudioLevel2Mute.setVisibility(View.VISIBLE);
        });

        imgAudioLevel2Mute.setOnClickListener(v -> {
            mediaPlayer.pause();
            imgAudioLevel2.setVisibility(View.VISIBLE);
            imgAudioLevel2Mute.setVisibility(View.GONE);
        });

        mediaPlayer.setOnCompletionListener(mp -> {

            imgAudioLevel2.setVisibility(View.VISIBLE);
            imgAudioLevel2Mute.setVisibility(View.GONE);
        });

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        gotoLevel1();
        gotoLevel2();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer1 != null){
            mediaPlayer1.release();
        }
        if(mediaPlayer2 != null){
            mediaPlayer2.release();
        }
    }
}
