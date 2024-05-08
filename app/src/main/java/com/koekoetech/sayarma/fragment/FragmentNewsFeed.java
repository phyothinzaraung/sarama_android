package com.koekoetech.sayarma.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import com.koekoetech.sayarma.R;
import com.koekoetech.sayarma.adapter.NewsFeedAdapter;
import com.koekoetech.sayarma.custom_control.MyanTextView;
import com.koekoetech.sayarma.helper.ServiceHelper;
import com.koekoetech.sayarma.helper.SharedPreferenceHelper;
import com.koekoetech.sayarma.helper.TextDictionaryHelper;
import com.koekoetech.sayarma.model.ArticleModel;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentNewsFeed extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    public RecyclerView recyclerviewNewsFeed;
    private SwipeRefreshLayout swipeRefreshLayoutNewsFeed;
    private LinearLayout emptyView;
    private MyanTextView txtEmptyArticle;

    NewsFeedAdapter adapter;
    private boolean isLoading;
    SharedPreferenceHelper sharedPreferenceHelper;

    private void bindViews(View view){
        recyclerviewNewsFeed = view.findViewById(R.id.recyclerviewNewsFeed);
        swipeRefreshLayoutNewsFeed = view.findViewById(R.id.swipeRefreshLayoutNewsFeed);
        emptyView = view.findViewById(R.id.emptyView);
        txtEmptyArticle = view.findViewById(R.id.txtEmptyArticle);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_newsfeed;
    }

    @Override
    protected void onViewReady(View view, @Nullable Bundle savedInstanceState) {
        bindViews(view);
        init();
    }

    private void init() {
        adapter = new NewsFeedAdapter();
        recyclerviewNewsFeed.setHasFixedSize(true);
        recyclerviewNewsFeed.setAdapter(adapter);
        swipeRefreshLayoutNewsFeed.setOnRefreshListener(this::init);

        sharedPreferenceHelper = SharedPreferenceHelper.getHelper(getActivity());

        getArticles();
    }

    private void getArticles (){

        isLoading = true;
        adapter.clearFooter();
        swipeRefreshLayoutNewsFeed.setRefreshing(false);

        adapter.showLoading();

        Call<ArrayList<ArticleModel>> getArticles = ServiceHelper.getClient(getContext()).getArticles(sharedPreferenceHelper.getUserId());
        getArticles.enqueue(new Callback<ArrayList<ArticleModel>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<ArticleModel>> call, @NonNull Response<ArrayList<ArticleModel>> response) {
                isLoading = false;
                adapter.clearFooter();
                if(response.isSuccessful()){
                    ArrayList<ArticleModel> arrayList = response.body();
                    if(arrayList != null){
                        emptyView.setVisibility(View.GONE);
                        for (ArticleModel model : arrayList){
                            adapter.add(model);
                        }
                    }else {
                        emptyView.setVisibility(View.VISIBLE);
                        txtEmptyArticle.setMyanmarText(TextDictionaryHelper.getText(getActivity(), TextDictionaryHelper.TEXT_EMPTY_ARTICLE));
                    }
                }else {
                    handleFailure();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<ArticleModel>> call, @NonNull Throwable t) {
                handleFailure();
            }

            private void handleFailure() {
                adapter.clearFooter();
                adapter.showRetry(R.layout.recycler_footer_retry, R.id.btn_retry, () -> getArticles());
            }
        });

    }

    @Override
    public void onRefresh() {
        if(!isLoading){
            swipeRefreshLayoutNewsFeed.setRefreshing(false);
            adapter.clear();
            getArticles();
        }
    }
}