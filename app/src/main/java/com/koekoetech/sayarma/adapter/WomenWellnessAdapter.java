package com.koekoetech.sayarma.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.koekoetech.sayarma.R;
import com.koekoetech.sayarma.activity.WomenWellnessDetailActivity;
import com.koekoetech.sayarma.custom_control.MyanTextView;
import com.koekoetech.sayarma.model.WomenWellnessModel;

public class WomenWellnessAdapter extends BaseRecyclerViewAdapter {
    @Override
    protected RecyclerView.ViewHolder onCreateCustomViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_womenwellness, parent, false);
        return new WomenWellnessViewHolder(view);
    }

    @Override
    protected void onBindCustomViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((WomenWellnessViewHolder)holder).bindData((WomenWellnessModel) getItemsList().get(position));
    }

    @Override
    protected RecyclerView.ViewHolder onCreateCustomHeaderViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected void onBindCustomHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    static class WomenWellnessViewHolder extends RecyclerView.ViewHolder {

        private final MyanTextView tv_womenwellness_title;

        private final Context context;

        public WomenWellnessViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_womenwellness_title = itemView.findViewById(R.id.tv_womenwellness_title);
            context = itemView.getContext();
        }

        void bindData(WomenWellnessModel model){
            tv_womenwellness_title.setMyanmarText(model.getTitle());

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, WomenWellnessDetailActivity.class);
                intent.putExtra(WomenWellnessDetailActivity.WOMEN_WELLNESS_EXTRA, model);
                context.startActivity(intent);
            });
        }
    }
}
