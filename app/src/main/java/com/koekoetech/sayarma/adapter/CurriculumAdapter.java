package com.koekoetech.sayarma.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.koekoetech.sayarma.R;
import com.koekoetech.sayarma.activity.CurriculumChapterActivity;
import com.koekoetech.sayarma.custom_control.MyanBoldTextView;
import com.koekoetech.sayarma.custom_control.MyanTextView;
import com.koekoetech.sayarma.model.ContentHeaderModel;
import com.koekoetech.sayarma.model.CurriculumLevelModel;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

public class CurriculumAdapter extends BaseRecyclerViewAdapter{

    @Override
    protected RecyclerView.ViewHolder onCreateCustomViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_curriculum, parent, false);
        return new CurriculumViewHolder(view);
    }

    @Override
    protected void onBindCustomViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((CurriculumViewHolder)holder).bindCurriculum((CurriculumLevelModel) getItemsList().get(position));
    }

    @Override
    protected RecyclerView.ViewHolder onCreateCustomHeaderViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.curriculum_header, parent, false);

        return new HeaderHolder(view);
    }

    @Override
    protected void onBindCustomHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecyclerHeader recyclerHeader = (RecyclerHeader) getItemsList().get(position);
        ((CurriculumAdapter.HeaderHolder) holder).bindHeader((ContentHeaderModel) recyclerHeader.getHeaderData());
    }

    static class CurriculumViewHolder extends RecyclerView.ViewHolder{

        private MyanTextView txtCurriculumTitle;
        private ImageView imgCurriculumAudio;
        private ImageView imgCurriculumAudioMute;

        public CurriculumViewHolder(@NonNull View itemView) {
            super(itemView);
            bindViews(itemView);
        }

        private void bindViews(View itemView){
            txtCurriculumTitle = itemView.findViewById(R.id.txtCurriculumTitle);
            imgCurriculumAudio = itemView.findViewById(R.id.imgCurriculumAudio);
            imgCurriculumAudioMute = itemView.findViewById(R.id.imgCurriculumAudioMute);
        }

        void bindCurriculum(CurriculumLevelModel level){
            txtCurriculumTitle.setMyanmarText(level.getTitle());

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), CurriculumChapterActivity.class);
                intent.putExtra(CurriculumChapterActivity.CURRICULUM_LEVEL_EXTRA, level);
                itemView.getContext().startActivity(intent);
            });
        }
    }

    static class HeaderHolder extends RecyclerView.ViewHolder {

        private MyanBoldTextView tv_title;
        private AppCompatImageView imageView;

        private void bindViews(View itemView){
            tv_title = itemView.findViewById(R.id.tv_curriculum_title);
            imageView = itemView.findViewById(R.id.img_curriculum_header);
        }

        public HeaderHolder(View itemView) {
            super(itemView);
            bindViews(itemView);
        }

        private void bindHeader(final ContentHeaderModel model) {
            imageView.setImageResource(R.drawable.curriculum_header);
            tv_title.setMyanmarText(model.getTitle());

        }
    }
}
