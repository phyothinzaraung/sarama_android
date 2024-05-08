package com.koekoetech.sayarma.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.koekoetech.sayarma.R;
import com.koekoetech.sayarma.activity.CardListActivity;
import com.koekoetech.sayarma.activity.QuestionAnswerActivity;
import com.koekoetech.sayarma.custom_control.MyanTextView;
import com.koekoetech.sayarma.helper.ExternalFileHelper;
import com.koekoetech.sayarma.helper.TextDictionaryHelper;
import com.koekoetech.sayarma.model.SubChapterModel;

import java.io.File;

public class SubChaptersAdapter extends BaseRecyclerViewAdapter {

    String chapter_id;

    public SubChaptersAdapter(String chapterID) {
        chapter_id = chapterID;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateCustomViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_sub_chapters, parent, false);
        return new SubChaptersViewHolder(view);
    }

    @Override
    protected void onBindCustomViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((SubChaptersViewHolder)holder).bindSubChapter((SubChapterModel) getItemsList().get(position));
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
        private ImageView imgSubTitle;
        private ImageView imgAudioSubChapter;
        private ImageView imgAudioSubChapterMute;

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

        void bindSubChapter(SubChapterModel model) {

            txtSubTitle.setMyanmarText(model.getContent());
            txtLearn.setMyanmarText(TextDictionaryHelper.getText(context, TextDictionaryHelper.TEXT_LEARN));

            if (model.getContent().equals("Quizzes")) {
                imgSubTitle.setImageResource(R.drawable.quiz);
                txtSubTitle.setMyanmarText(TextDictionaryHelper.getText(context, TextDictionaryHelper.TEXT_QUIZ));
                layoutLearn.setOnClickListener(v -> {
                    //Quizzes
                    //Type = 0
                    Intent intent = new Intent(itemView.getContext(), QuestionAnswerActivity.class);
                    intent.putExtra(QuestionAnswerActivity.TYPE_EXTRA, 0);
                    intent.putExtra(QuestionAnswerActivity.LEVEL_ID_OR_SUB_CHAPTER_ID_EXTRA, chapter_id);
                    itemView.getContext().startActivity(intent);

                });

                playMp3();
            } else {
                imgSubTitle.setImageResource(R.drawable.lesson);
                layoutLearn.setOnClickListener(v -> {
                    Intent intent = new Intent(itemView.getContext(), CardListActivity.class);
                    intent.putExtra(CardListActivity.SUB_CHAPTER_EXTRA, model);
                    itemView.getContext().startActivity(intent);
                });

                playMp3(model.getTitleAudio());
            }


        }

        private void playMp3(String audio){
            mediaPlayer1 = new MediaPlayer();

            try {
                File file = ExternalFileHelper.getTitleAudioFile(context, audio);
                mediaPlayer1.setDataSource(file.getAbsolutePath());
                mediaPlayer1.prepare();
            } catch (Exception e) {
                e.printStackTrace();
            }

            imgAudioSubChapter.setOnClickListener(v -> {
                mediaPlayer1.start();
                imgAudioSubChapter.setVisibility(View.GONE);
                imgAudioSubChapterMute.setVisibility(View.VISIBLE);
            });

            imgAudioSubChapterMute.setOnClickListener(v -> {
                mediaPlayer1.pause();
                imgAudioSubChapter.setVisibility(View.VISIBLE);
                imgAudioSubChapterMute.setVisibility(View.GONE);
            });

            mediaPlayer1.setOnCompletionListener(mp -> {

                imgAudioSubChapter.setVisibility(View.VISIBLE);
                imgAudioSubChapterMute.setVisibility(View.GONE);
            });

        }

        private void playMp3(){
            mediaPlayer2 = new MediaPlayer();

            try {
                File file = ExternalFileHelper.getTitleAudioFile(context, "Quizzes.mp3");
                mediaPlayer2.setDataSource(file.getAbsolutePath());
                mediaPlayer2.prepare();
            } catch (Exception e) {
                e.printStackTrace();
            }

            imgAudioSubChapter.setOnClickListener(v -> {
                mediaPlayer2.start();
                imgAudioSubChapter.setVisibility(View.GONE);
                imgAudioSubChapterMute.setVisibility(View.VISIBLE);
            });

            imgAudioSubChapterMute.setOnClickListener(v -> {
                mediaPlayer2.pause();
                imgAudioSubChapter.setVisibility(View.VISIBLE);
                imgAudioSubChapterMute.setVisibility(View.GONE);
            });

            mediaPlayer2.setOnCompletionListener(mp -> {

                imgAudioSubChapter.setVisibility(View.VISIBLE);
                imgAudioSubChapterMute.setVisibility(View.GONE);
            });

        }
    }
}
