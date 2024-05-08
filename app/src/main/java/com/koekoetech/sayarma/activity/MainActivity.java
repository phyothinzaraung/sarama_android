package com.koekoetech.sayarma.activity;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.koekoetech.sayarma.R;
import com.koekoetech.sayarma.adapter.TabAdapter;
import com.koekoetech.sayarma.custom_control.MyanTextProcessor;
import com.koekoetech.sayarma.fragment.FragmentDashboard;
import com.koekoetech.sayarma.fragment.FragmentNewsFeed;
import com.koekoetech.sayarma.fragment.FragmentNotification;
import com.koekoetech.sayarma.helper.ServiceHelper;
import com.koekoetech.sayarma.helper.SharedPreferenceHelper;
import com.koekoetech.sayarma.helper.TextDictionaryHelper;
import com.koekoetech.sayarma.model.LastLoginViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    public static final String LIKE_EXTRA = "isLike";

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private TabAdapter adapter;
    SharedPreferenceHelper sharedPreferenceHelper;
    private String isLike;

    private void bindViews(){
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {

        setUpToolbar(false);
        setUpToolbarText(MyanTextProcessor.processText(this, TextDictionaryHelper.getText(this, TextDictionaryHelper.TEXT_THUKHAMAIN)));

        //view binding
        bindViews();

        sharedPreferenceHelper = SharedPreferenceHelper.getHelper(this);

        isLike = getIntent().getStringExtra("isLike");

        //get active user
        saveLastLoginUser();

        //Add tabs icon with setIcon() or simple text with .setText()
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.dashboard_select));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.newsfeed_unselect));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.noti_unselect));

        //Add Fragments
        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentDashboard());
        adapter.addFragment(new FragmentNewsFeed());
        adapter.addFragment(new FragmentNotification());

        //Setting Adapter
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {

                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        super.onTabSelected(tab);
                        switch (tab.getPosition()){
                            case 0 :
                                tab.setIcon(R.drawable.dashboard_select);
                                break;
                            case 1 :
                                tab.setIcon(R.drawable.newsfeed_select);
                                break;
                            case 2 :
                                tab.setIcon(R.drawable.noti_select);
                                break;
                        }
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        super.onTabUnselected(tab);
                        switch (tab.getPosition()){
                            case 0 :
                                tab.setIcon(R.drawable.dashboard_unselect);
                                break;
                            case 1 :
                                tab.setIcon(R.drawable.newsfeed_unselect);
                                break;
                            case 2 :
                                tab.setIcon(R.drawable.noti_unselect);
                                break;
                        }
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        super.onTabReselected(tab);

                        if(tab.getPosition() == 1){
                            Fragment fragment = adapter.getItem(1);
                            FragmentNewsFeed fragmentNewsFeed = (FragmentNewsFeed) fragment;
                            fragmentNewsFeed.recyclerviewNewsFeed.smoothScrollToPosition(0);
                        }else if (tab.getPosition() == 2){
                            Fragment fragment = adapter.getItem(2);
                            FragmentNotification fragmentNotification = (FragmentNotification) fragment;
                            fragmentNotification.recyclerViewNotification.smoothScrollToPosition(0);
                        }

                    }
                }
        );

        if(isLike != null){
            tabLayout.getTabAt(1).select();
        }

    }

    private void saveLastLoginUser(){
        LastLoginViewModel lastLoginViewModel = new LastLoginViewModel();
        lastLoginViewModel.setUserID(sharedPreferenceHelper.getUserId());
        lastLoginViewModel.setUsername(sharedPreferenceHelper.getUserName());

        Call<LastLoginViewModel> getActiveUser = ServiceHelper.getClient(this).setLastLoginUser(lastLoginViewModel);
        getActiveUser.enqueue(new Callback<LastLoginViewModel>() {
            @Override
            public void onResponse(Call<LastLoginViewModel> call, Response<LastLoginViewModel> response) {
                if (response.isSuccessful()){
                    Log.v("save", "success");
                }
            }

            @Override
            public void onFailure(Call<LastLoginViewModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_setting){
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(viewPager.getCurrentItem() != 0){
            viewPager.setCurrentItem(0);
        }else {
            super.onBackPressed();
        }
    }
}
