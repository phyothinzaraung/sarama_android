package com.koekoetech.sayarma.helper;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

public class JsonFileHelper {

    public static String getQuzzesFromJson(Context context) throws IOException {

        InputStream is = context.getAssets().open("quizzes.json");

        int size = is.available();

        byte[] buffer = new byte[size];

        is.read(buffer);

        is.close();

        String json = new String(buffer, "UTF-8");
        return json;

    }

    public static String getCoursesFromJson(Context context) throws IOException {

        InputStream is = context.getAssets().open("courses.json");

        int size = is.available();

        byte[] buffer = new byte[size];

        is.read(buffer);

        is.close();

        String json = new String(buffer, "UTF-8");
        return json;

    }

}
