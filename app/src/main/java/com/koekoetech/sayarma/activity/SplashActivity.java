package com.koekoetech.sayarma.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.textview.MaterialTextView;
import com.koekoetech.sayarma.R;
import com.koekoetech.sayarma.helper.SharedPreferenceHelper;
import com.koekoetech.sayarma.helper.SplashInitTask;
import com.koekoetech.sayarma.helper.TextDictionaryHelper;

import androidx.annotation.NonNull;
import needle.Needle;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends BaseActivity implements SplashInitTask.Callback {

    private LinearProgressIndicator progressIndicator;
    private MaterialTextView tvProgressMessage;

    private SharedPreferenceHelper sharedPreferenceHelper;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_splash;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {

        init();

        final SplashInitTask splashInitTask = new SplashInitTask(this, getApplicationContext(), this);
        Needle.onBackgroundThread().execute(splashInitTask);

    }

    @Override
    public void onProgressUpdate(@NonNull SplashInitTask.Progress progress) {
        tvProgressMessage.setText(progress.getMessage());

        final int percentage = progress.getPercentage();
        if (percentage > 0) {
            progressIndicator.setIndeterminate(false);
            progressIndicator.setProgressCompat(percentage, true);
        } else {
            progressIndicator.setIndeterminate(true);
        }
    }

    @Override
    public void onComplete(@NonNull String result) {

        switch (result) {
            case SplashInitTask.RESULT_SUCCESS:
                proceedToNextScreen();
                break;
            case SplashInitTask.RESULT_FAILED_DATA_ZIP_MISSING:
                Toast.makeText(this, TextDictionaryHelper.getText(this,
                        TextDictionaryHelper.TEXT_MSG_ERR_DATA_NOT_FOUND), Toast.LENGTH_SHORT)
                        .show();
                finish();
                break;
            default:
                Toast.makeText(this, TextDictionaryHelper.getText(this,
                        TextDictionaryHelper.TEXT_MSG_ERR_DATA_IMPORT_FAIL), Toast.LENGTH_SHORT)
                        .show();
                finish();
        }
    }

    private void init() {

        progressIndicator = findViewById(R.id.lpiProgressIndicator);
        tvProgressMessage = findViewById(R.id.tvProgressMsg);

        sharedPreferenceHelper = SharedPreferenceHelper.getHelper(getApplicationContext());

    }

    private void proceedToNextScreen() {
        final Class<? extends BaseActivity> nextDestination =
                sharedPreferenceHelper.isLogIn() ? MainActivity.class : LoginActivity.class;
        final Intent intent = new Intent(this, nextDestination);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


}
