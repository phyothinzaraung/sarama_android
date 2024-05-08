package com.koekoetech.sayarma.helper;

import android.content.Context;

import com.koekoetech.sayarma.custom_control.AndroidCommonSetup;
import com.koekoetech.sayarma.model.TextModel;

import java.util.ArrayList;

public class TextDictionaryHelper {

    public static final String TEXT_THUKHAMAIN = "TEXT_THUKHAMAIN";
    public static final String TEXT_LANGUAGE = "TEXT_LANGUAGE";
    public static final String TEXT_LESSON = "TEXT_LESSON";
    public static final String TEXT_LESSON_DESCRIPTION = "TEXT_LESSON_DESCRIPTION";
    public static final String TEXT_START_NOW = "TEXT_START_NOW";
    public static final String TEXT_SERVICE_HOTLINE = "TEXT_SERVICE_HOTLINE";
    public static final String TEXT_ONE_TO_ONE_CHAT = "TEXT_ONE_TO_ONE_CHAT";
    public static final String TEXT_ONLINE_FORUM = "TEXT_ONLINE_FORUM";
    public static final String TEXT_SAVED_ARTICLES = "TEXT_SAVED_ARTICLES";
    public static final String TEXT_WOMEN_WELLNESS = "TEXT_WOMEN_WELLNESS";
    public static final String TEXT_STORY_TELLING = "TEXT_STORY_TELLING";
    public static final String TEXT_USEFUL_APPS = "TEXT_USEFUL_APPS";
    public static final String TEXT_PROFILE = "TEXT_PROFILE";
    public static final String TEXT_NOTIFICATION = "TEXT_NOTIFICATION";
    public static final String TEXT_LOCATION = "TEXT_LOCATION";
    public static final String TEXT_LOGOUT = "TEXT_LOGOUT";
    public static final String TEXT_SETTING = "TEXT_SETTING";
    public static final String TEXT_COURSE_SECTION = "TEXT_COURSE_SECTION";
    public static final String TEXT_SUB_CHAPTERS = "TEXT_SUB_CHAPTERS";
    public static final String TEXT_INTRO_EDEVICES = "TEXT_INTRO_EDEVICES";
    public static final String TEXT_LEVEL1 = "TEXT_LEVEL1";
    public static final String TEXT_LEVEL2 = "TEXT_LEVEL2";
    public static final String TEXT_EMPTY_ARTICLE = "TEXT_EMPTY_ARTICLE";
    public static final String TEXT_BUTTON_ANSWER="TEXT_BUTTON_ANSWER";
    public static final String TEXT_BUTTON_FINISH="TEXT_BUTTON_FINISH";
    public static final String TEXT_LABEL_HINT="TEXT_LABEL_HINT";
    public static final String TEXT_TOTAL_MARKS="TEXT_TOTAL_MARKS";
    public static final String TEXT_POINTS="TEXT_POINTS";
    public static final String TEXT_QUIZ = "TEXT_QUIZ";
    public static final String TEXT_LEARN = "TEXT_LEARN";
    public static final String TEXT_ANSWER_RIGHT = "TEXT_ANSWER_RIGHT";
    public static final String TEXT_ANSWER_WRONG = "TEXT_ANSWER_WRONG";
    public static final String TEXT_POINT_RIGHT = "TEXT_POINT_RIGHT";
    public static final String TEXT_POINT_WRONG = "TEXT_POINT_WRONG";
    public static final String TEXT_PROGRESS_GENERAL = "TEXT_LOADING_GENERAL";
    public static final String TEXT_PROGRESS_DATA_IMPORT = "TEXT_PROGRESS_DATA_IMPORT";
    public static final String TEXT_MSG_ERR_DATA_NOT_FOUND = "TEXT_MSG_ERR_DATA_NOT_FOUND";
    public static final String TEXT_MSG_ERR_DATA_IMPORT_FAIL = "TEXT_MSG_ERR_DATA_IMPORT_FAIL";

    public static ArrayList<TextModel> list = new ArrayList<TextModel>() {{

        add(new TextModel(TEXT_THUKHAMAIN,"Sayarma","ဆရာမ"));
        add(new TextModel(TEXT_LANGUAGE,"Choose Language","ဘာသာစကား ရွေးရန်"));
        add(new TextModel(TEXT_LESSON, "Collection of Courses", "သင်ခန်းစာများ"));
        add(new TextModel(TEXT_LESSON_DESCRIPTION, "Develop a passion of learning. If you do, you will never cease to grow", "ဤနေရာတွင် သင်ခန်းစာများကို လေ့လာနိုင်ပါသည်"));
        add(new TextModel(TEXT_START_NOW, "START NOW", "စတင်မည်"));
        add(new TextModel(TEXT_SERVICE_HOTLINE, "Service Hotline", "၀န်ဆောင်မှုပေးသော Hot Line များ"));
        add(new TextModel(TEXT_ONE_TO_ONE_CHAT, "One to One Chat", "သီးသန့်စကားဝိုင်း"));
        add(new TextModel(TEXT_ONLINE_FORUM, "Online Forum", "ဆွေးနွေးခန်း"));
        add(new TextModel(TEXT_SAVED_ARTICLES, "Saved Articles", "သိမ်းဆည်းထားသော ဆောင်းပါးများ"));
        add(new TextModel(TEXT_WOMEN_WELLNESS, "Women's Wellness", "အမျိုးသမီး ကျန်းမာရေး"));
        add(new TextModel(TEXT_STORY_TELLING, "Story Telling", "ဇာတ်လမ်း မျှဝေခြင်း"));
        add(new TextModel(TEXT_USEFUL_APPS, "Useful Apps", "အသုံးဝင်သော အပရီကေးရှင်းများ"));
        add(new TextModel(TEXT_PROFILE, "Profile", "ကိုယ်ရေးအချက်အလက်"));
        add(new TextModel(TEXT_NOTIFICATION, "Notification", "အသိပေးချက်အဖွင့်/အပိတ်"));
        add(new TextModel(TEXT_LOCATION, "Location", "တည်နေရာ ဖော်ပြခြင်း"));
        add(new TextModel(TEXT_LOGOUT, "Logout", "ထွက်ခွာရန်"));
        add(new TextModel(TEXT_SETTING, "Setting", "ပြင်ဆင်ခြင်း"));
        add(new TextModel(TEXT_COURSE_SECTION, "Course Section", "သင်ခန်းစာကဏ္ဍ"));
        add(new TextModel(TEXT_SUB_CHAPTERS, "Sub Chapters", "သင်ခန်းစာများ"));
        add(new TextModel(TEXT_INTRO_EDEVICES, "Introduction to Electronic Devices", "အီလက်ထရောနစ်ပစ္စည်းများနှင့် မိတ်ဆက်ခြင်း"));
        add(new TextModel(TEXT_LEVEL1, "Level 1 - Apps and Programs", "အဆင့်(၁) အက်ပလီကေးရှင်းများနှင့် မိတ်ဆက်ခြင်း"));
        add(new TextModel(TEXT_LEVEL2, "Level 2 - Digital Literacy", "အဆင့်(၂) သတင်းအချက်အလက်များကို သုံးသပ်ခြင်း နည်းလမ်းများ"));
        add(new TextModel(TEXT_EMPTY_ARTICLE, "No article yet", "သိမ်းဆည်းထားသောဆောင်းပါးများ မရှိသေးပါ"));
        add(new TextModel(TEXT_BUTTON_ANSWER,"Answer","ဖြေဆိုပါမည်"));
        add(new TextModel(TEXT_BUTTON_FINISH,"Finish","ပြီးပါပြီ"));
        add(new TextModel(TEXT_LABEL_HINT,"hint:","ရှင်းလင်းချက်"));
        add(new TextModel(TEXT_TOTAL_MARKS,"Total Mark : ","စုစုပေါင်းရမှတ် "));
        add(new TextModel(TEXT_POINTS," Points"," မှတ်"));
        add(new TextModel(TEXT_QUIZ, "Quiz", "ဉာဏ်စမ်းမေးခွန်းများ"));
        add(new TextModel(TEXT_LEARN, "Learn", "လေ့လာမည်"));
        add(new TextModel(TEXT_ANSWER_RIGHT, "Answer is right.", "အဖြေမှန်ပါသည်"));
        add(new TextModel(TEXT_ANSWER_WRONG, "Answer is wrong.", "အဖြေမှားပါသည်"));
        add(new TextModel(TEXT_POINT_RIGHT, "You got 10 marks.", "သင် (၁၀)မှတ် ရရှိပါသည်"));
        add(new TextModel(TEXT_POINT_WRONG, "You didn't get mark.", "သင်အမှတ်မရရှိပါ"));
        add(new TextModel(TEXT_PROGRESS_GENERAL, "Just a sec...", "ခဏစောင့်ပါ။"));
        add(new TextModel(TEXT_PROGRESS_DATA_IMPORT, "Importing Data(%02d%%)", "ဒေတာတင်သွင်းခြင်း (%02d%%)"));
        add(new TextModel(TEXT_MSG_ERR_DATA_NOT_FOUND, "Data file not found.", "ဒေတာဖိုင်ကို ရှာမတွေ့ပါ။"));
        add(new TextModel(TEXT_MSG_ERR_DATA_IMPORT_FAIL, "Data import failed.", "ဒေတာတင်သွင်းမှု မအောင်မြင်ပါ။"));

    }};

    public static String getText(Context context, String key) {
        String result = "";
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getKey().equals(key)) {
                TextModel model = list.get(i);

                switch (AndroidCommonSetup.getInstance().getSelectedLanguage()) {
                    case THUKHAMAIN_CONSTANT.ENGLISH_LANGUAGE:
                        result = model.getEnglish();
                        break;
                    default:
                        result = model.getMyanmar();
                        break;
                }

            }
        }

        return result;
    }

}
