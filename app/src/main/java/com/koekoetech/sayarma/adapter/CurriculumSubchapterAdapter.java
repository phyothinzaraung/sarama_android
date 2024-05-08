package com.koekoetech.sayarma.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.koekoetech.sayarma.R;
import com.koekoetech.sayarma.custom_control.MyanTextView;
import com.koekoetech.sayarma.model.CurriculumSubChapterModel;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

public class CurriculumSubchapterAdapter extends BaseRecyclerViewAdapter{
    @Override
    protected RecyclerView.ViewHolder onCreateCustomViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_curriculum, parent, false);
        return new CurriculumSubChapterViewHolder(view);
    }

    @Override
    protected void onBindCustomViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((CurriculumSubChapterViewHolder)holder).bindCurriculum((CurriculumSubChapterModel) getItemsList().get(position));
    }

    @Override
    protected RecyclerView.ViewHolder onCreateCustomHeaderViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected void onBindCustomHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    static class CurriculumSubChapterViewHolder extends RecyclerView.ViewHolder{

        private MyanTextView txtCurriculumTitle;
        private AppCompatImageView imgCurriculumAudio;
        private AppCompatImageView imgCurriculumAudioMute;

        public CurriculumSubChapterViewHolder(@NonNull View itemView) {
            super(itemView);
            bindViews(itemView);
        }

        private void bindViews(View itemView){
            txtCurriculumTitle = itemView.findViewById(R.id.txtCurriculumTitle);
            imgCurriculumAudio = itemView.findViewById(R.id.imgCurriculumAudio);
            imgCurriculumAudioMute = itemView.findViewById(R.id.imgCurriculumAudioMute);
        }

        void bindCurriculum(CurriculumSubChapterModel subChapterModel){
            txtCurriculumTitle.setMyanmarText(subChapterModel.getContent()); //title

//            itemView.setOnClickListener(v -> {
//                Intent intent = new Intent(itemView.getContext(), CurriculumChapterActivity.class);
//                intent.putExtra(CurriculumChapterActivity.CURRICULUM_LEVEL_EXTRA, level);
//                itemView.getContext().startActivity(intent);
//            });
        }
    }
}
