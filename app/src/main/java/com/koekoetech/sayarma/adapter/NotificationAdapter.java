package com.koekoetech.sayarma.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.koekoetech.sayarma.R;
import com.koekoetech.sayarma.custom_control.MyanTextView;
import com.koekoetech.sayarma.helper.ServiceHelper;
import com.koekoetech.sayarma.model.NotificationModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationAdapter extends BaseRecyclerViewAdapter {

    public NotificationAdapter() {
    }

    @Override
    protected RecyclerView.ViewHolder onCreateCustomViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    protected void onBindCustomViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((NotificationViewHolder)holder).bindNotification((NotificationModel) getItemsList().get(position));

        if(!((NotificationModel) getItemsList().get(position)).isNew()){
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        ((NotificationViewHolder)holder).itemView.setOnClickListener(v -> {
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));

            String notification_id = ((NotificationModel) getItemsList().get(position)).getNotificationID();
            Call<NotificationModel> updateNotification = ServiceHelper.getClient(holder.itemView.getContext()).updateNotificationStatus(notification_id);
            updateNotification.enqueue(new Callback<NotificationModel>() {
                @Override
                public void onResponse(@NonNull Call<NotificationModel> call, @NonNull Response<NotificationModel> response) {

                }

                @Override
                public void onFailure(@NonNull Call<NotificationModel> call, @NonNull Throwable t) {

                }
            });

        });
    }

    @Override
    protected RecyclerView.ViewHolder onCreateCustomHeaderViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected void onBindCustomHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    static class NotificationViewHolder extends RecyclerView.ViewHolder{

        private final MyanTextView txtNotification;
        private final TextView txtNotificationTitle;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNotification = itemView.findViewById(R.id.txtNotification);
            txtNotificationTitle = itemView.findViewById(R.id.txtNotificationTitle);
        }

        @SuppressLint("SetTextI18n")
        public void bindNotification(NotificationModel model){
            txtNotificationTitle.setText("New Post");
            if(!TextUtils.isEmpty(model.getTitle())){
                txtNotification.setText(model.getTitle());
            }
        }
    }
}
