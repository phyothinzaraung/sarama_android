package com.koekoetech.sayarma.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessaging;
import com.koekoetech.sayarma.R;
import com.koekoetech.sayarma.custom_control.MyanTextProcessor;
import com.koekoetech.sayarma.custom_control.MyanTextView;
import com.koekoetech.sayarma.helper.DialogHelperWithOkCancelButton;
import com.koekoetech.sayarma.helper.ServiceHelper;
import com.koekoetech.sayarma.helper.SharedPreferenceHelper;
import com.koekoetech.sayarma.helper.TextDictionaryHelper;
import com.koekoetech.sayarma.model.LoginViewModel;
import com.koekoetech.sayarma.model.ProgressModel;
import com.koekoetech.sayarma.model.ScoreModel;
import java.util.ArrayList;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.luongvo.widget.iosswitchview.SwitchView;

public class SettingActivity extends BaseActivity {

    private LinearLayout layout_profile;
    private MyanTextView txt_language;
    private LinearLayout linear_language;
    private LinearLayout layout_location;
    private SwitchView switchview;
    private LinearLayout layout_logout;
    private MyanTextView txtProfile;
    private MyanTextView txtNoti;
    private MyanTextView txtLocation;
    private MyanTextView txtLogout;

    private SharedPreferenceHelper sharedPreferenceHelper;
    DialogHelperWithOkCancelButton dialogHelperWithOkCancelButton;
    boolean isConnected = false;
    private Realm realm;
    private ProgressDialog progressDialog;

    private void bindViews(){
        layout_profile = findViewById(R.id.layout_profile);
        txt_language = findViewById(R.id.txt_language);
        linear_language = findViewById(R.id.linear_language);
        layout_location = findViewById(R.id.layout_location);
        switchview = findViewById(R.id.switchview);
        layout_logout = findViewById(R.id.layout_logout);
        txtProfile = findViewById(R.id.txtProfile);
        txtNoti = findViewById(R.id.txtNoti);
        txtLocation = findViewById(R.id.txtLocation);
        txtLogout = findViewById(R.id.txtLogout);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_setting;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {

        bindViews();

        txtProfile.setMyanmarText(TextDictionaryHelper.getText(this, TextDictionaryHelper.TEXT_PROFILE));
        txtNoti.setMyanmarText(TextDictionaryHelper.getText(this, TextDictionaryHelper.TEXT_NOTIFICATION));
        txtLocation.setMyanmarText(TextDictionaryHelper.getText(this, TextDictionaryHelper.TEXT_LOCATION));
        txtLogout.setMyanmarText(TextDictionaryHelper.getText(this, TextDictionaryHelper.TEXT_LOGOUT));

        init();

        if (sharedPreferenceHelper.isPushNotiOn()) {
            switchview.setChecked(true);
            FirebaseMessaging.getInstance().subscribeToTopic("news");
        } else {
            switchview.setChecked(false);
            FirebaseMessaging.getInstance().unsubscribeFromTopic("news");
        }

        layout_profile.setOnClickListener(v -> {
            Intent intent = new Intent(SettingActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        layout_location.setOnClickListener(v -> {
            Intent intent = new Intent(SettingActivity.this, LocationActivity.class);
            startActivity(intent);
        });

        linear_language.setOnClickListener(v -> {

            Intent intent = new Intent(SettingActivity.this, LanguageActivity.class);
            startActivity(intent);

        });

        switchview.setOnCheckedChangeListener((switchView, isChecked) -> sharedPreferenceHelper.setPrefIsPushNotiOn(isChecked));

        layout_logout.setOnClickListener(v -> {

            isConnected = checkConnection();

            if (isConnected) {
                progressDialog.show();

                dialogHelperWithOkCancelButton.showDialog(R.mipmap.app_icon,
                        "Are you sure want to logout?", "Logout",
                        "Cancel");

                dialogHelperWithOkCancelButton.getBtnOk().
                        setOnClickListener(v1 -> {

                            progressDialog.show();
                            syncScoreAndProgress();

                        });

                dialogHelperWithOkCancelButton.getBtnCancel()
                        .setOnClickListener(v12 -> {
                            if(progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                            dialogHelperWithOkCancelButton.hideDialog();
                        });
            } else {
                Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
            }

        });

    }

    private void init() {
        setUpToolbar(true);
        setUpToolbarText(MyanTextProcessor.processText(this, TextDictionaryHelper.getText(this, TextDictionaryHelper.TEXT_SETTING)));

        setTextForControl();

        sharedPreferenceHelper = SharedPreferenceHelper.getHelper(this);

        dialogHelperWithOkCancelButton = new DialogHelperWithOkCancelButton(this);

        realm = Realm.getDefaultInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Syncing...");

    }

    private void setTextForControl() {
        txt_language.setMyanmarText(TextDictionaryHelper.getText(SettingActivity.this, TextDictionaryHelper.TEXT_LANGUAGE));
    }

    private boolean checkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //we are connected to a network
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
    }

    private void syncScoreAndProgress() {
        RealmResults<ProgressModel> completedLessonList = realm.where(ProgressModel.class).equalTo("isSync", false).findAll();
        ArrayList<ProgressModel> lessonList = (ArrayList<ProgressModel>) realm.copyFromRealm(completedLessonList);

        RealmResults<ScoreModel> scoreModelList = realm.where(ScoreModel.class).equalTo("isSync", false).findAll();
        ArrayList<ScoreModel> scoreList = (ArrayList<ScoreModel>) realm.copyFromRealm(scoreModelList);

        if (lessonList.size() > 0 || scoreList.size() > 0) {
            LoginViewModel model = new LoginViewModel();
            model.setProgressModel(lessonList);
            model.setScoreModel(scoreList);

            Call<LoginViewModel> sync = ServiceHelper.getClient(this).sync(model);
            sync.enqueue(new Callback<LoginViewModel>() {
                @Override
                public void onResponse(@NonNull Call<LoginViewModel> call, @NonNull Response<LoginViewModel> response) {
                    if (response.isSuccessful()) {

                        if(progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }

                        realm.executeTransaction(realm -> {
                            realm.delete(ScoreModel.class);
                            realm.delete(ProgressModel.class);
                        });
                        logout();
                    } else {
                        if(progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                        Toast.makeText(getApplicationContext(), "sync fail", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<LoginViewModel> call, @NonNull Throwable t) {
                    if(progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {

            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }

            dialogHelperWithOkCancelButton.showDialog(R.mipmap.app_icon,
                    "No data to sync. Are you sure to logout?", "Logout",
                    "Cancel");

            dialogHelperWithOkCancelButton.getBtnOk().
                    setOnClickListener(v -> {

                        sharedPreferenceHelper.setLogIn(false);
                        Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    });

            dialogHelperWithOkCancelButton.getBtnCancel()
                    .setOnClickListener(v -> dialogHelperWithOkCancelButton.hideDialog());
        }
    }

    private void logout(){

        sharedPreferenceHelper.setLogIn(false);
        Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(dialogHelperWithOkCancelButton != null){
            dialogHelperWithOkCancelButton.hideDialog();
        }
    }
}
