package com.koekoetech.sayarma.activity;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import com.koekoetech.sayarma.R;

public abstract class BaseActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        toolbar = findViewById(R.id.toolbar);
        setUpContents(savedInstanceState);
    }

    protected void setUpToolbar(boolean isChild){
        if(toolbar != null){
            setSupportActionBar(toolbar);
        }

        if(isChild){
            if(getSupportActionBar() != null){
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void setUpToolbarText(String title){
        ActionBar supportActionBar = getSupportActionBar();
        if(supportActionBar != null){
            supportActionBar.setTitle(title);
        }
    }

    protected void setupToolbarBgColor(String color) {
        if (toolbar != null) {
            toolbar.setBackgroundColor(Color.parseColor(color));
        }
    }

    protected void setupToolbarTextColor(String color) {
        if (toolbar != null) {
            toolbar.setTitleTextColor(Color.parseColor(color));
        }
    }

    @LayoutRes
    protected abstract int getLayoutResource();

    protected abstract void setUpContents(Bundle savedInstanceState);

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }
}
