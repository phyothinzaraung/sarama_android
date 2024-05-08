package com.koekoetech.sayarma.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.koekoetech.sayarma.R;
import com.koekoetech.sayarma.activity.CourseSectionActivity;
import com.koekoetech.sayarma.activity.CurriculumActivity;
import com.koekoetech.sayarma.activity.SavedArticlesActivity;
import com.koekoetech.sayarma.activity.ServiceHotlineActivity;
import com.koekoetech.sayarma.activity.UsefulAppActivity;
import com.koekoetech.sayarma.activity.WomenWellnessActivity;
import com.koekoetech.sayarma.custom_control.MyanTextView;
import com.koekoetech.sayarma.helper.TextDictionaryHelper;

public class FragmentDashboard extends BaseFragment {

    private CardView cardViewServiceHotline;
    private CardView cardViewChat;
    private CardView cardViewWomanWellness;
    private CardView cardViewUsefulApp;
    private CardView cardViewSavedArticles;
    private CardView cardViewCurriculum;
    private LinearLayout btnCourseSection;
    private MyanTextView txtCourses;
    private MyanTextView txtDescription;
    private MyanTextView txtServiceHotline;
    private MyanTextView txtStartNow;
    private MyanTextView txtSavedArticles;
    private MyanTextView txtWomenWellness;
    private MyanTextView txtStoryTelling;
    private MyanTextView txtUsefulApps;

    private void bindViews(View view){
        cardViewServiceHotline = view.findViewById(R.id.cardViewServiceHotline);
        cardViewChat = view.findViewById(R.id.cardViewChat);
        cardViewWomanWellness = view.findViewById(R.id.cardViewWomanWellness);
        cardViewUsefulApp = view.findViewById(R.id.cardViewUsefulApp);
        cardViewSavedArticles = view.findViewById(R.id.cardViewSavedArticles);
        cardViewCurriculum = view.findViewById(R.id.cardViewCurriculum);
        btnCourseSection = view.findViewById(R.id.btnCourseSection);
        txtCourses = view.findViewById(R.id.txtCourses);
        txtDescription = view.findViewById(R.id.txtDescription);
        txtServiceHotline = view.findViewById(R.id.txtServiceHotline);
        txtStartNow = view.findViewById(R.id.txtStartNow);
        txtSavedArticles = view.findViewById(R.id.txtSavedArticles);
        txtWomenWellness = view.findViewById(R.id.txtWomenWellness);
        txtStoryTelling = view.findViewById(R.id.txtStoryTelling);
        txtUsefulApps = view.findViewById(R.id.txtUsefulApps);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_dashboard;
    }

    @Override
    protected void onViewReady(View view, @Nullable Bundle savedInstanceState) {

        bindViews(view);

        txtCourses.setMyanmarText(TextDictionaryHelper.getText(getContext(), TextDictionaryHelper.TEXT_LESSON));
        txtDescription.setMyanmarText(TextDictionaryHelper.getText(getContext(), TextDictionaryHelper.TEXT_LESSON_DESCRIPTION));
        txtServiceHotline.setMyanmarText(TextDictionaryHelper.getText(getContext(), TextDictionaryHelper.TEXT_SERVICE_HOTLINE));
        txtStartNow.setMyanmarText(TextDictionaryHelper.getText(getContext(), TextDictionaryHelper.TEXT_START_NOW));
        txtSavedArticles.setMyanmarText(TextDictionaryHelper.getText(getContext(), TextDictionaryHelper.TEXT_SAVED_ARTICLES));
        txtWomenWellness.setMyanmarText(TextDictionaryHelper.getText(getContext(), TextDictionaryHelper.TEXT_WOMEN_WELLNESS));
        txtStoryTelling.setMyanmarText(TextDictionaryHelper.getText(getContext(), TextDictionaryHelper.TEXT_STORY_TELLING));
        txtUsefulApps.setMyanmarText(TextDictionaryHelper.getText(getContext(), TextDictionaryHelper.TEXT_USEFUL_APPS));

        cardViewServiceHotline.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ServiceHotlineActivity.class);
            startActivity(intent);
        });

        btnCourseSection.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CourseSectionActivity.class);
            startActivity(intent);
        });

        cardViewChat.setOnClickListener(v -> Toast.makeText(getContext(), "Coming Soon", Toast.LENGTH_SHORT).show());

        cardViewWomanWellness.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), WomenWellnessActivity.class);
            startActivity(intent);
        });

        cardViewUsefulApp.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), UsefulAppActivity.class);
            startActivity(intent);
        });

        cardViewSavedArticles.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SavedArticlesActivity.class);
            startActivity(intent);
        });

        cardViewCurriculum.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CurriculumActivity.class);
            startActivity(intent);
        });
    }
}