package com.koekoetech.sayarma.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.koekoetech.sayarma.R;

public class SharedPreferenceHelper {

    private static final String PREF_IS_LOG_IN = "isLoggedIn";
    private static final String PREF_PHONE_NUMBER = "phoneNumber";
    private static final String PREF_USER_ID = "UserID";
    private static final String PREF_IS_PUSH_NOTI_ON = "isPushNotiOn";
    private static final String PREF_USER_NAME = "UserName";
    private static final String PREF_IS_DATA_IMPORTED = "isMediaDataImported";
    private static final String PREF_DATA_VERSION_MAJOR = "MediaDataVersionMajor";
    private static final String PREF_DATA_VERSION_MINOR = "MediaDataVersionMinor";

    private ContextHelper contextHelper;

    public static SharedPreferenceHelper getHelper(Context context) {
        SingletonHelper.accessor.init(context);
        return SingletonHelper.accessor;
    }

    private void init(Context context) {
        contextHelper = new ContextHelper(context);
    }

    private SharedPreferences getSharedPreferences() {
        return contextHelper.getContext().getSharedPreferences(contextHelper.getContext().getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
    }

    private SharedPreferences.Editor getSharedPreferencesEditor() {
        return getSharedPreferences().edit();
    }

    public boolean isLogIn() {
        return getSharedPreferences().getBoolean(PREF_IS_LOG_IN, false);
    }

    public void setLogIn(boolean flag) {
        getSharedPreferencesEditor().putBoolean(PREF_IS_LOG_IN, flag).apply();
    }

    public String getPhoneNumber() {
        return getSharedPreferences().getString(PREF_PHONE_NUMBER, "");
    }

    public void setPhoneNumber(String phoneNumber) {
        getSharedPreferencesEditor().putString(PREF_PHONE_NUMBER, phoneNumber).apply();
    }

    public String getUserId() {
        return getSharedPreferences().getString(PREF_USER_ID, "");
    }

    public void setUserId(String user_id) {
        getSharedPreferencesEditor().putString(PREF_USER_ID, user_id).apply();
    }

    public String getUserName() {
        return getSharedPreferences().getString(PREF_USER_NAME, "");
    }

    public void setUserName(String userName) {
        getSharedPreferencesEditor().putString(PREF_USER_NAME, userName).apply();
    }

    public boolean isPushNotiOn() {
        return getSharedPreferences().getBoolean(PREF_IS_PUSH_NOTI_ON, true);
    }

    public void setPrefIsPushNotiOn(boolean flag) {
        getSharedPreferencesEditor().putBoolean(PREF_IS_PUSH_NOTI_ON, flag).apply();
    }

    public boolean isMediaDataImported() {
        return getSharedPreferences().getBoolean(PREF_IS_DATA_IMPORTED, false);
    }

    public void setMediaDataImported(boolean isImported) {
        getSharedPreferencesEditor().putBoolean(PREF_IS_DATA_IMPORTED, isImported).apply();
    }

    public int getMediaDataMinorVersion() {
        return getSharedPreferences().getInt(PREF_DATA_VERSION_MINOR, -1);
    }

    public void setMediaDataMinorVersion(int version) {
        getSharedPreferencesEditor().putInt(PREF_DATA_VERSION_MINOR, version).apply();
    }

    public int getMediaDataMajorVersion() {
        return getSharedPreferences().getInt(PREF_DATA_VERSION_MAJOR, -1);
    }

    public void setMediaDataMajorVersion(int version) {
        getSharedPreferencesEditor().putInt(PREF_DATA_VERSION_MAJOR, version).apply();
    }

    /* SharedPreferences Helper Classes (For Singleton and Context)*/
    private static class SingletonHelper {
        private static final SharedPreferenceHelper accessor = new SharedPreferenceHelper();
    }


    private static class ContextHelper {
        private final Context context;

        ContextHelper(Context context) {
            this.context = context;
        }

        Context getContext() {
            return context;
        }
    }
}
