package com.koekoetech.sayarma.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.koekoetech.sayarma.R;
import com.koekoetech.sayarma.custom_control.AndroidCommonSetup;
import com.koekoetech.sayarma.custom_control.FontSharePreferenceHelper;
import com.koekoetech.sayarma.helper.THUKHAMAIN_CONSTANT;

public class LanguageActivity extends BaseActivity {

    private FontSharePreferenceHelper fontSharePreferenceHelper;

    private RadioGroup rd_language;
    private RadioButton rd_english;
    private RadioButton rd_myanmar;

    private void bindViews(){
        rd_language = findViewById(R.id.rd_language);
        rd_english = findViewById(R.id.rd_english);
        rd_myanmar = findViewById(R.id.rd_myanmar);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_language;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void setUpContents(Bundle savedInstanceState) {

        init();

        bindViews();

        if(fontSharePreferenceHelper.getLanguage().equals(THUKHAMAIN_CONSTANT.MYANMAR_LANGUAGE))
        {
            rd_myanmar.setChecked(true);
        }
        else if(fontSharePreferenceHelper.getLanguage().equals(THUKHAMAIN_CONSTANT.ENGLISH_LANGUAGE))
        {
            rd_english.setChecked(true);
        }
        else
        {
            rd_myanmar.setChecked(false);
            rd_english.setChecked(false);
        }


        rd_language.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId)
            {
                case R.id.rd_english:
                    AndroidCommonSetup.getInstance().changeLanguage(THUKHAMAIN_CONSTANT.ENGLISH_LANGUAGE);
                    initProcess();
                    break;
                case R.id.rd_myanmar:
                    AndroidCommonSetup.getInstance().changeLanguage(THUKHAMAIN_CONSTANT.MYANMAR_LANGUAGE);
                    initProcess();
                    break;
            }
        });

    }

    private void init()
    {
        setUpToolbar(true);
        setUpToolbarText("");

        fontSharePreferenceHelper = new FontSharePreferenceHelper(this);
    }

    private void initProcess()
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
