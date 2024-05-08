package com.koekoetech.sayarma.helper;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koekoetech.sayarma.model.AnswerModel;
import com.koekoetech.sayarma.model.ChapterModel;
import com.koekoetech.sayarma.model.CoursesAllModel;
import com.koekoetech.sayarma.model.LessonQuestionAnswersModel;
import com.koekoetech.sayarma.model.LevelModel;
import com.koekoetech.sayarma.model.QuestionModel;
import com.koekoetech.sayarma.model.SectionCardsModel;
import com.koekoetech.sayarma.model.SubChapterModel;

import java.io.IOException;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmObject;

public class JsonDBSetUp {

    Context context;
    Realm realm;

    public JsonDBSetUp (Context context){
        this.context = context;
        realm = Realm.getDefaultInstance();
    }

    public boolean isDBSetupDone(){

        long chapter_count = realm.where(ChapterModel.class).count();
        long sub_chapter_count = realm.where(SubChapterModel.class).count();
        long level_count = realm.where(LevelModel.class).count();
        long section_count = realm.where(SectionCardsModel.class).count();
        long question_count = realm.where(QuestionModel.class).count();
        long answer_count = realm.where(AnswerModel.class).count();

        return chapter_count != 0 && sub_chapter_count != 0 && level_count != 0 && question_count != 0 && section_count != 0 && answer_count != 0;
    }

    public void quizzesSetup() throws IOException {
        Gson gson = new Gson();

        LessonQuestionAnswersModel lessonQuestionAnswersModel = gson.fromJson(JsonFileHelper.
                        getQuzzesFromJson(context),
                new TypeToken<LessonQuestionAnswersModel>() {
                }.getType());

        ArrayList<QuestionModel> questionList = lessonQuestionAnswersModel.getQuizModels();
        ArrayList<AnswerModel> answerList = lessonQuestionAnswersModel.getAnswerModels();

        for (int i = 0; i < questionList.size(); i++) {
            QuestionModel questionModel = questionList.get(i);
            realmUpdate(questionModel);
        }

        for (int i = 0; i < answerList.size(); i++) {
            AnswerModel lessonModel = answerList.get(i);
            realmUpdate(lessonModel);
        }
    }

    public void coursesSetup() throws IOException {
        Gson gson = new Gson();

        CoursesAllModel coursesAllModel = gson.fromJson(JsonFileHelper.
                        getCoursesFromJson(context),
                new TypeToken<CoursesAllModel>() {
                }.getType());

        ArrayList<LevelModel> levelList = coursesAllModel.getLevels();
        ArrayList<ChapterModel> chapterList = coursesAllModel.getChapters();
        ArrayList<SubChapterModel> subChapterList = coursesAllModel.getSubChapters();
        ArrayList<SectionCardsModel> sectionCardList = coursesAllModel.getSectionCards();

        for (int i = 0; i < levelList.size(); i++) {
            LevelModel levelModel = levelList.get(i);
            realmUpdate(levelModel);
        }

        for (int i = 0; i < chapterList.size(); i++) {
            ChapterModel chapterModel = chapterList.get(i);
            realmUpdate(chapterModel);
        }

        for (int i = 0; i < subChapterList.size(); i++) {
            SubChapterModel subChapterModel = subChapterList.get(i);
            realmUpdate(subChapterModel);
        }

        for (int i = 0; i < sectionCardList.size(); i++) {
            SectionCardsModel sectionCardsModel = sectionCardList.get(i);
            realmUpdate(sectionCardsModel);
        }

    }

    private void realmUpdate(RealmObject model){
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(model);
        realm.commitTransaction();
    }

}
