package com.koekoetech.sayarma.activity;

import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import com.koekoetech.sayarma.R;
import com.koekoetech.sayarma.adapter.SavedArticleAdapter;
import com.koekoetech.sayarma.custom_control.MyanTextProcessor;
import com.koekoetech.sayarma.custom_control.MyanTextView;
import com.koekoetech.sayarma.helper.SharedPreferenceHelper;
import com.koekoetech.sayarma.helper.TextDictionaryHelper;
import com.koekoetech.sayarma.model.ArticleModel;
import java.util.List;
import io.realm.Realm;
import io.realm.RealmResults;

public class SavedArticlesActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerviewSavedArticles;
    private SwipeRefreshLayout swipeRefreshLayoutSavedArticles;
    private LinearLayout savedArticle_emptyView;
    private MyanTextView txtEmptySavedArticle;

    SavedArticleAdapter adapter;
    Realm realm;
    private SharedPreferenceHelper sharedPreferenceHelper;

    private void bindViews(){
        recyclerviewSavedArticles = findViewById(R.id.recyclerviewSavedArticles);
        swipeRefreshLayoutSavedArticles = findViewById(R.id.swipeRefreshLayoutSavedArticles);
        savedArticle_emptyView = findViewById(R.id.savedArticle_emptyView);
        txtEmptySavedArticle = findViewById(R.id.txtEmptySavedArticle);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_saved_articles;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {
        setUpToolbar(true);
        setUpToolbarText(MyanTextProcessor.processText(this, TextDictionaryHelper.getText(this, TextDictionaryHelper.TEXT_SAVED_ARTICLES)));

        bindViews();

        sharedPreferenceHelper = SharedPreferenceHelper.getHelper(this);

        adapter = new SavedArticleAdapter();
        recyclerviewSavedArticles.setHasFixedSize(true);
        recyclerviewSavedArticles.setAdapter(adapter);
        swipeRefreshLayoutSavedArticles.setOnRefreshListener(SavedArticlesActivity.this);

        getArticles();

    }

    private void getArticles() {

        adapter.clearFooter();
        swipeRefreshLayoutSavedArticles.setRefreshing(false);

        //get content list from realm
        realm = Realm.getDefaultInstance();

        RealmResults<ArticleModel> articleList = realm.where(ArticleModel.class)
                .equalTo("userId", sharedPreferenceHelper.getUserId())
                .findAll();
        List<ArticleModel> articles = realm.copyFromRealm(articleList);

        if(articles.size() == 0){
            savedArticle_emptyView.setVisibility(View.VISIBLE);
            txtEmptySavedArticle.setMyanmarText(TextDictionaryHelper.getText(this, TextDictionaryHelper.TEXT_EMPTY_ARTICLE));
        }else {
            savedArticle_emptyView.setVisibility(View.GONE);
            for (ArticleModel model : articles) {
                adapter.add(model);
            }
        }
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayoutSavedArticles.setRefreshing(false);
        adapter.clear();
        getArticles();
    }
}
