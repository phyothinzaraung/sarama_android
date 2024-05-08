package com.koekoetech.sayarma.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.LinearLayout;
import com.koekoetech.sayarma.R;
import com.koekoetech.sayarma.custom_control.MyanTextProcessor;
import com.koekoetech.sayarma.helper.TextDictionaryHelper;

public class UsefulAppActivity extends BaseActivity {

    private LinearLayout layout_maymay;
    private LinearLayout layout_lanpya;

    private void bindViews(){
        layout_maymay = findViewById(R.id.layout_maymay);
        layout_lanpya = findViewById(R.id.layout_lanpya);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_useful_apps;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {
        setUpToolbar(true);
        setUpToolbarText(MyanTextProcessor.processText(this, TextDictionaryHelper.getText(this, TextDictionaryHelper.TEXT_USEFUL_APPS)));

        bindViews();

        layout_maymay.setOnClickListener(v -> {
            Intent downloadapp = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.koekoe.mayonline.myan.app"));
            startActivity(downloadapp);
        });

        layout_lanpya.setOnClickListener(v -> {
            Intent downloadapp = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.koekoetech.myjustice"));
            startActivity(downloadapp);
        });
    }
}
