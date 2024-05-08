package com.koekoetech.sayarma.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.koekoetech.sayarma.R;
import com.koekoetech.sayarma.adapter.NotificationAdapter;
import com.koekoetech.sayarma.helper.ServiceHelper;
import com.koekoetech.sayarma.model.NotificationModel;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentNotification extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    public RecyclerView recyclerViewNotification;
    private SwipeRefreshLayout swipeRefreshLayoutNotification;

    NotificationAdapter adapter;
    private boolean isLoading;

    private void bindViews(View view){
        recyclerViewNotification = view.findViewById(R.id.recyclerviewNotification);
        swipeRefreshLayoutNotification = view.findViewById(R.id.swipeRefreshLayoutNotification);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_notification;
    }

    @Override
    protected void onViewReady(View view, @Nullable Bundle savedInstanceState) {
        bindViews(view);
        init();
    }

    private void init() {
        adapter = new NotificationAdapter();
        recyclerViewNotification.setHasFixedSize(true);
        recyclerViewNotification.setAdapter(adapter);
        swipeRefreshLayoutNotification.setOnRefreshListener(this);

        getNotification();
    }

    private void getNotification() {
        isLoading = true;
        adapter.clearFooter();
        swipeRefreshLayoutNotification.setRefreshing(false);

        adapter.showLoading();

        Call<ArrayList<NotificationModel>> getNotifications = ServiceHelper.getClient(getContext()).getNotifications();
        getNotifications.enqueue(new Callback<ArrayList<NotificationModel>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<NotificationModel>> call, @NonNull Response<ArrayList<NotificationModel>> response) {
                isLoading = false;
                adapter.clearFooter();
                if(response.isSuccessful()){
                    ArrayList<NotificationModel> arrayList = response.body();
                    if(arrayList != null){
                        for (NotificationModel model : arrayList){
                            adapter.add(model);
                        }
                    }else {
                        //show empty view
                        handleFailure();
                    }
                }else {
                    handleFailure();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<NotificationModel>> call, @NonNull Throwable t) {
                handleFailure();
            }

            private void handleFailure() {
                adapter.clearFooter();
                adapter.showRetry(R.layout.recycler_footer_retry, R.id.btn_retry, () -> getNotification());
            }
        });

    }

    @Override
    public void onRefresh() {
        if(!isLoading){
            swipeRefreshLayoutNotification.setRefreshing(false);
            adapter.clear();
            getNotification();
        }
    }
}
