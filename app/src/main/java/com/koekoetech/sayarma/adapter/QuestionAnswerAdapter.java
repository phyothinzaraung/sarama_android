package com.koekoetech.sayarma.adapter;

import android.content.Context;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.koekoetech.sayarma.R;
import com.koekoetech.sayarma.custom_control.MyanTextView;
import com.koekoetech.sayarma.helper.DpToPxHelper;
import com.koekoetech.sayarma.interfaces.AnswerSelectionCallback;
import com.koekoetech.sayarma.model.AnswersViewModel;
import com.koekoetech.sayarma.model.QuestionModel;

public class QuestionAnswerAdapter extends BaseRecyclerViewAdapter {

    private final AnswerSelectionCallback answerSelectionCallback;


    public QuestionAnswerAdapter(AnswerSelectionCallback answerSelectionCallback)
    {
        this.answerSelectionCallback = answerSelectionCallback;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateCustomViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_answer, parent, false);
        return new QuestionAnswerAdapter.ViewHolder(view);
    }

    @Override
    protected void onBindCustomViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((QuestionAnswerAdapter.ViewHolder) holder).bindPost((AnswersViewModel) getItemsList().get(position), position);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateCustomHeaderViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected void onBindCustomHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final CardView cv_item;
        private final MyanTextView tv_answer;
        private final ImageView img_select;

        private final Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            this.context = itemView.getContext();

            cv_item = itemView.findViewById(R.id.cv_item);
            tv_answer = itemView.findViewById(R.id.tv_answer);
            img_select = itemView.findViewById(R.id.img_select);
        }

        void bindPost(final AnswersViewModel model, final int position) {

            if (position == 0) {
                ViewGroup.MarginLayoutParams layoutParams =
                        (ViewGroup.MarginLayoutParams) cv_item.getLayoutParams();
                layoutParams.setMargins(DpToPxHelper.dpToPx(context, 20), DpToPxHelper.dpToPx(context, 20),
                        DpToPxHelper.dpToPx(context, 20), DpToPxHelper.dpToPx(context, 10));
                cv_item.requestLayout();
            } else {
                ViewGroup.MarginLayoutParams layoutParams =
                        (ViewGroup.MarginLayoutParams) cv_item.getLayoutParams();
                layoutParams.setMargins(DpToPxHelper.dpToPx(context, 20), DpToPxHelper.dpToPx(context, 10),
                        DpToPxHelper.dpToPx(context, 20), DpToPxHelper.dpToPx(context, 10));
                cv_item.requestLayout();
            }

            tv_answer.setMyanmarText(model.getAnswer());

            if(model.isSelected())
            {
                img_select.setImageResource(R.mipmap.selected);
            }
            else
            {
                img_select.setImageResource(R.mipmap.un_selected);
            }

            cv_item.setOnClickListener(v -> {

                model.setSelected(!model.isSelected());

                answerSelectionCallback.select(position, model);

            });

        }
    }

    static class ViewHolderHeader extends RecyclerView.ViewHolder {

        private final MyanTextView tv_question;
        private final Context context;

        public ViewHolderHeader(View itemView) {
            super(itemView);
            this.context = itemView.getContext();
            tv_question = itemView.findViewById(R.id.tv_question);
        }

        void bindHeader(final QuestionModel model) {

            tv_question.setMyanmarText(model.getQuestionContent());


        }
    }

}
