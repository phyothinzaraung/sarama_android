package com.koekoetech.sayarma.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.koekoetech.sayarma.R;
import com.koekoetech.sayarma.activity.CurriculumSubchapterActivity;
import com.koekoetech.sayarma.custom_control.MyanTextView;
import com.koekoetech.sayarma.helper.TextDictionaryHelper;
import com.koekoetech.sayarma.model.CurriculumChapterModel;
import com.koekoetech.sayarma.model.CurriculumSubChapterModel;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

public class CurriculumChaptersAdapter extends BaseRecyclerViewAdapter{

    @Override
    protected RecyclerView.ViewHolder onCreateCustomViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_sub_chapters, parent, false);
        return new CurriculumChaptersAdapter.SubChaptersViewHolder(view);
    }

    @Override
    protected void onBindCustomViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((SubChaptersViewHolder)holder).bindSubChapter((CurriculumChapterModel) getItemsList().get(position));
    }

    @Override
    protected RecyclerView.ViewHolder onCreateCustomHeaderViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected void onBindCustomHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    class SubChaptersViewHolder extends RecyclerView.ViewHolder {

        private MyanTextView txtSubTitle;
        private LinearLayout layoutLearn;
        private MyanTextView txtLearn;
        private AppCompatImageView imgSubTitle;
        private AppCompatImageView imgAudioSubChapter;
        private AppCompatImageView imgAudioSubChapterMute;

        private final Context context;
        private MediaPlayer mediaPlayer1;
        private MediaPlayer mediaPlayer2;

        private void bindViews(View itemView){
            txtSubTitle = itemView.findViewById(R.id.txtSubTitle);
            layoutLearn = itemView.findViewById(R.id.layoutLearn);
            txtLearn = itemView.findViewById(R.id.txtLearn);
            imgSubTitle = itemView.findViewById(R.id.imgSubTitle);
            imgAudioSubChapter = itemView.findViewById(R.id.imgAudioSubChapter);
            imgAudioSubChapterMute = itemView.findViewById(R.id.imgAudioSubChapterMute);
        }

        public SubChaptersViewHolder(@NonNull View itemView) {
            super(itemView);
            bindViews(itemView);
            context = itemView.getContext();
        }

        void bindSubChapter(CurriculumChapterModel model) {

            txtSubTitle.setMyanmarText(model.getTitle());
            txtLearn.setMyanmarText(TextDictionaryHelper.getText(context, TextDictionaryHelper.TEXT_LEARN));

            layoutLearn.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), CurriculumSubchapterActivity.class);
                itemView.getContext().startActivity(intent);
            });

        }
    }
}
