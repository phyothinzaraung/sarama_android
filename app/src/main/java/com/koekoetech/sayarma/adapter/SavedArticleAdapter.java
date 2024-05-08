package com.koekoetech.sayarma.adapter;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.koekoetech.sayarma.R;
import com.koekoetech.sayarma.activity.NewsFeedDetailsActivity;
import com.koekoetech.sayarma.custom_control.MyanTextView;
import com.koekoetech.sayarma.helper.MySpannable;
import com.koekoetech.sayarma.model.ArticleModel;
import java.util.Arrays;
import java.util.List;
import io.realm.Realm;
import io.realm.RealmResults;

public class SavedArticleAdapter extends BaseRecyclerViewAdapter {

    @Override
    protected RecyclerView.ViewHolder onCreateCustomViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_newsfeed, parent, false);
        return new SavedArticleViewHolder(view);
    }

    @Override
    protected void onBindCustomViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((SavedArticleAdapter.SavedArticleViewHolder) holder).bindArticle((ArticleModel) getItemsList().get(position));
    }

    @Override
    protected RecyclerView.ViewHolder onCreateCustomHeaderViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected void onBindCustomHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    static class SavedArticleViewHolder extends RecyclerView.ViewHolder{

        private MyanTextView txtContentBody;
        private MyanTextView txtContentTitle;
        private ImageView imgArticle;
        private ImageView imgSave;
        private TextView txtSave;
        private MyanTextView txtOriganizationName;
        private TextView txtDate;
        private TextView txtTime;
        private LinearLayout layout_like_comment;

        Realm realm;
        String image;

        private void bindViews(View itemView){
            txtContentBody = itemView.findViewById(R.id.txtContentBody);
            txtContentTitle = itemView.findViewById(R.id.txtContentTitle);
            imgArticle = itemView.findViewById(R.id.imgArticle);
            imgSave = itemView.findViewById(R.id.imgSave);
            txtSave = itemView.findViewById(R.id.txtSave);
            txtOriganizationName = itemView.findViewById(R.id.txtOriganizationName);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtTime = itemView.findViewById(R.id.txtTime);
            layout_like_comment = itemView.findViewById(R.id.layout_like_comment);
        }

        public SavedArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            bindViews(itemView);
            realm = Realm.getDefaultInstance();
        }

        void bindArticle (ArticleModel articleModel){

            layout_like_comment.setVisibility(View.GONE);

            if(!TextUtils.isEmpty(articleModel.getContentBody())){
                txtContentBody.setMyanmarText(articleModel.getContentBody());
            }

            if(!TextUtils.isEmpty(articleModel.getTitle())){
                txtContentTitle.setText(articleModel.getTitle());
            }

            if(!TextUtils.isEmpty(articleModel.getOrganizationTitle())){
                txtOriganizationName.setMyanmarText(articleModel.getOrganizationTitle());
            }

            if(!TextUtils.isEmpty(articleModel.getCreatedDate())){
                String datetime = articleModel.getCreatedDate();

                List<String> datetimelist = Arrays.asList(datetime.split("T"));
                txtDate.setText(datetimelist.get(0));
                txtTime.setText(datetimelist.get(1));
            }

            if(articleModel.getPhotos().size() > 0){
                image = articleModel.getPhotos().get(0);
            }else {
                imgArticle.setImageResource(R.drawable.article_placeholder);
            }

            Glide.with(itemView.getContext())
                    .load(image)
                    .placeholder(R.drawable.article_placeholder)
                    .into(imgArticle);

            RealmResults<ArticleModel> articleList = realm.where(ArticleModel.class).findAll();
            if(articleList != null) {
                List<ArticleModel> articles = realm.copyFromRealm(articleList);
                for (ArticleModel model : articles) {
                    if (model.getContentID().equals(articleModel.getContentID())) {
                        imgSave.setImageResource(R.drawable.save_orange);
                        txtSave.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.colorPrimary));
                    }
                }
            }

            imgSave.setOnClickListener(v -> {
                imgSave.setImageResource(R.drawable.save_black);
                txtSave.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.color_black));
                unsaveArticle(articleModel.getContentID());
            });

            txtSave.setOnClickListener(v -> {
                imgSave.setImageResource(R.drawable.save_black);
                txtSave.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.color_black));
                unsaveArticle(articleModel.getContentID());
            });

            txtContentBody.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), NewsFeedDetailsActivity.class);
                intent.putExtra(NewsFeedDetailsActivity.CONTENT_ID_EXTRA, articleModel.getContentID());
                itemView.getContext().startActivity(intent);
            });

            txtContentTitle.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), NewsFeedDetailsActivity.class);
                intent.putExtra(NewsFeedDetailsActivity.CONTENT_ID_EXTRA, articleModel.getContentID());
                itemView.getContext().startActivity(intent);
            });

            imgArticle.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), NewsFeedDetailsActivity.class);
                intent.putExtra(NewsFeedDetailsActivity.CONTENT_ID_EXTRA, articleModel.getContentID());
                itemView.getContext().startActivity(intent);
            });

        }

        private void unsaveArticle(String contentID){
            realm.executeTransaction(realm -> {
                RealmResults<ArticleModel> rows = realm.where(ArticleModel.class).equalTo("ContentID", contentID).findAll();
                rows.deleteAllFromRealm();
            });
        }

        public void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

            if (tv.getTag() == null) {
                tv.setTag(tv.getText());
            }
            ViewTreeObserver vto = tv.getViewTreeObserver();
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

                @SuppressWarnings("deprecation")
                @Override
                public void onGlobalLayout() {

                    ViewTreeObserver obs = tv.getViewTreeObserver();
                    obs.removeGlobalOnLayoutListener(this);
                    if (maxLine == 0) {
                        int lineEndIndex = tv.getLayout().getLineEnd(0);
                        String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                        tv.setText(text);
                        tv.setMovementMethod(LinkMovementMethod.getInstance());
                        tv.setText(
                                addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, expandText,
                                        viewMore), TextView.BufferType.SPANNABLE);
                    } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                        int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                        String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                        tv.setText(text);
                        tv.setMovementMethod(LinkMovementMethod.getInstance());
                        tv.setText(
                                addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, expandText,
                                        viewMore), TextView.BufferType.SPANNABLE);
                    } else {
                        int lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                        String text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                        tv.setText(text);
                        tv.setMovementMethod(LinkMovementMethod.getInstance());
                        tv.setText(
                                addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, expandText,
                                        viewMore), TextView.BufferType.SPANNABLE);
                    }
                }
            });

        }

        private SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
                                                                         final String spanableText, final boolean viewMore) {
            String str = strSpanned.toString();
            SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

            if (str.contains(spanableText)) {


                ssb.setSpan(new MySpannable(false){
                    @Override
                    public void onClick(View widget) {
                        if (viewMore) {
                            tv.setLayoutParams(tv.getLayoutParams());
                            tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                            tv.invalidate();
                            makeTextViewResizable(tv, -1, "See Less", false);
                        } else {
                            tv.setLayoutParams(tv.getLayoutParams());
                            tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                            tv.invalidate();
                            makeTextViewResizable(tv, 3, ".. See More", true);
                        }
                    }
                }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

            }
            return ssb;

        }
    }
}
