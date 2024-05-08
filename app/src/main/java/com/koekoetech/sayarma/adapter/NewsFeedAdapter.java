package com.koekoetech.sayarma.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.koekoetech.sayarma.R;
import com.koekoetech.sayarma.activity.MainActivity;
import com.koekoetech.sayarma.activity.NewsFeedDetailsActivity;
import com.koekoetech.sayarma.custom_control.MyanTextView;
import com.koekoetech.sayarma.helper.MySpannable;
import com.koekoetech.sayarma.helper.ServiceHelper;
import com.koekoetech.sayarma.helper.SharedPreferenceHelper;
import com.koekoetech.sayarma.model.ArticleModel;
import com.koekoetech.sayarma.model.ContentLikeModel;
import java.util.Arrays;
import java.util.List;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFeedAdapter extends BaseRecyclerViewAdapter {

    @Override
    protected RecyclerView.ViewHolder onCreateCustomViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_newsfeed, parent, false);
        return new NewsFeedViewHolder(view);
    }

    @Override
    protected void onBindCustomViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((NewsFeedViewHolder) holder).bindArticle((ArticleModel) getItemsList().get(position));
    }

    @Override
    protected RecyclerView.ViewHolder onCreateCustomHeaderViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected void onBindCustomHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    static class NewsFeedViewHolder extends RecyclerView.ViewHolder {

        private MyanTextView txtContentBody;
        private MyanTextView txtContentTitle;
        private ImageView imgArticle;
        private ImageView imgLike;
        private TextView txtLike;
        private ImageView imgSave;
        private TextView txtSave;
        private MyanTextView txtOriganizationName;
        private TextView txtDate;
        private TextView txtTime;
        private TextView txtLikeCount;

        private final SharedPreferenceHelper sharedPreferenceHelper;
        private int likeCount = 0;

        Realm realm;

        private void bindViews(View itemView){
            txtContentBody = itemView.findViewById(R.id.txtContentBody);
            txtContentTitle = itemView.findViewById(R.id.txtContentTitle);
            imgArticle = itemView.findViewById(R.id.imgArticle);
            imgLike = itemView.findViewById(R.id.imgLike);
            txtLike = itemView.findViewById(R.id.txtLike);
            imgSave = itemView.findViewById(R.id.imgSave);
            txtSave = itemView.findViewById(R.id.txtSave);
            txtOriganizationName = itemView.findViewById(R.id.txtOriganizationName);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtLikeCount = itemView.findViewById(R.id.txtLikeCount);
        }

        NewsFeedViewHolder(@NonNull View itemView) {
            super(itemView);
            bindViews(itemView);
            realm = Realm.getDefaultInstance();
            Context context = itemView.getContext();
            sharedPreferenceHelper = SharedPreferenceHelper.getHelper(context);
        }

        @SuppressLint("SetTextI18n")
        void bindArticle(ArticleModel articleModel) {

            if(!TextUtils.isEmpty(articleModel.getContentBody())){
                txtContentBody.setText(articleModel.getContentBody());
            }

            if (!TextUtils.isEmpty(articleModel.getTitle())) {
                txtContentTitle.setText(articleModel.getTitle());
            }

            if (!TextUtils.isEmpty(articleModel.getOrganizationTitle())) {
                txtOriganizationName.setMyanmarText(articleModel.getOrganizationTitle());
            }

            if (!TextUtils.isEmpty(articleModel.getCreatedDate())) {
                String datetime = articleModel.getCreatedDate();

                List<String> datetimelist = Arrays.asList(datetime.split("T"));
                txtDate.setText(datetimelist.get(0));
                txtTime.setText(datetimelist.get(1));
            }

            if (articleModel.getPhotos().size() > 0) {
                Glide.with(itemView.getContext())
                        .load(articleModel.getPhotos().get(0))
                        .placeholder(R.drawable.article_placeholder)
                        .into(imgArticle);
            } else {
                imgArticle.setImageResource(R.drawable.article_placeholder);
            }

            RealmResults<ArticleModel> articleList = realm.where(ArticleModel.class).findAll();
            if (articleList != null) {
                List<ArticleModel> articles = realm.copyFromRealm(articleList);
                for (ArticleModel model : articles) {
                    if (model.getContentID().equals(articleModel.getContentID())) {
                        imgSave.setImageResource(R.drawable.save_orange);
                        txtSave.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.colorPrimary));
                    }
                }
            }

            //Like
            txtLikeCount.setText(articleModel.getLikeCount() + " Like");
            if(articleModel.isLike()){
                imgLike.setImageResource(R.drawable.like_orange);
                txtLike.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.colorPrimary));
            }

            if(articleModel.isLike()){
                imgLike.setOnClickListener(v -> unlikeArticle(articleModel));

                txtLike.setOnClickListener(v -> unlikeArticle(articleModel));
            }else {
                imgLike.setOnClickListener(v -> likeArticle(articleModel));
                txtLike.setOnClickListener(v -> likeArticle(articleModel));
            }

            imgSave.setOnClickListener(v -> {
                imgSave.setImageResource(R.drawable.save_orange);
                txtSave.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.colorPrimary));
                saveArticle(articleModel);
            });

            txtSave.setOnClickListener(v -> {
                imgSave.setImageResource(R.drawable.save_orange);
                txtSave.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.colorPrimary));
                saveArticle(articleModel);
            });

            imgArticle.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), NewsFeedDetailsActivity.class);
                intent.putExtra(NewsFeedDetailsActivity.CONTENT_ID_EXTRA, articleModel.getContentID());
                itemView.getContext().startActivity(intent);
            });
            txtContentTitle.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), NewsFeedDetailsActivity.class);
                intent.putExtra(NewsFeedDetailsActivity.CONTENT_ID_EXTRA, articleModel.getContentID());
                itemView.getContext().startActivity(intent);
            });
            txtContentBody.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), NewsFeedDetailsActivity.class);
                intent.putExtra(NewsFeedDetailsActivity.CONTENT_ID_EXTRA, articleModel.getContentID());
                itemView.getContext().startActivity(intent);
            });

        }

        private ContentLikeModel getLikeArticle(ArticleModel model) {
            ContentLikeModel contentLikeModel = new ContentLikeModel();
            contentLikeModel.setUserID(sharedPreferenceHelper.getUserId());
            contentLikeModel.setContentID(model.getContentID());
            contentLikeModel.setLike(true);
            return contentLikeModel;
        }

        private ContentLikeModel getUnLikeArticle(ArticleModel model) {
            ContentLikeModel contentLikeModel = new ContentLikeModel();
            contentLikeModel.setUserID(sharedPreferenceHelper.getUserId());
            contentLikeModel.setContentID(model.getContentID());
            contentLikeModel.setLike(false);
            return contentLikeModel;
        }

        private void likeArticle(ArticleModel articleModel) {

            ContentLikeModel model = getLikeArticle(articleModel);
            Call<ContentLikeModel> like = ServiceHelper.getClient(itemView.getContext()).like(model);
            like.enqueue(new Callback<ContentLikeModel>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(@NonNull Call<ContentLikeModel> call, @NonNull Response<ContentLikeModel> response) {
                    if (response.isSuccessful()) {
                        imgLike.setImageResource(R.drawable.like_orange);
                        txtLike.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.colorPrimary));
                        likeCount = articleModel.getLikeCount() + 1 ;
                        txtLikeCount.setText(likeCount + " Like");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ContentLikeModel> call, @NonNull Throwable t) {

                }
            });
        }

        private void unlikeArticle(ArticleModel articleModel) {

            ContentLikeModel model = getUnLikeArticle(articleModel);
            Call<ContentLikeModel> like = ServiceHelper.getClient(itemView.getContext()).like(model);
            like.enqueue(new Callback<ContentLikeModel>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(@NonNull Call<ContentLikeModel> call, @NonNull Response<ContentLikeModel> response) {
                    if (response.isSuccessful()) {
                        imgLike.setImageResource(R.drawable.like_black);
                        txtLike.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.color_black));
                        likeCount = articleModel.getLikeCount() - 1;
                        txtLikeCount.setText(likeCount + " Like");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ContentLikeModel> call, @NonNull Throwable t) {

                }
            });
        }

        private void gotoMainActivity(){
            Intent intent = new Intent(itemView.getContext(), MainActivity.class);
            intent.putExtra(MainActivity.LIKE_EXTRA, "isLike");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            itemView.getContext().startActivity(intent);
        }

        private void saveArticle(ArticleModel articleModel) {

            ArticleModel model = new ArticleModel();
            model.setContentID(articleModel.getContentID());
            model.setCategoryID(articleModel.getCategoryID());
            model.setTitle(articleModel.getTitle());
            model.setTitleInEnglish(articleModel.getTitleInEnglish());
            model.setCategoryDescription(articleModel.getCategoryDescription());
            model.setContentBody(articleModel.getContentBody());
            model.setImage(articleModel.getImage());
            model.setArticleType(articleModel.getArticleType());
            model.setVideoURL(articleModel.getVideoURL());
            model.setCreatedDate(articleModel.getCreatedDate());
            model.setCreatedby(articleModel.getCreatedby());
            model.setUpdatedDate(articleModel.getUpdatedDate());
            model.setUpdatedby(articleModel.getUpdatedby());
            model.setOrganizationDescription(articleModel.getOrganizationDescription());
            model.setOrganizationTitle(articleModel.getOrganizationTitle());
            model.setPhotos(articleModel.getPhotos());
            model.setUserId(sharedPreferenceHelper.getUserId());

            try {
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(model);
                Toast.makeText(itemView.getContext(), "saved successfully", Toast.LENGTH_SHORT).show();
                realm.commitTransaction();
            } catch (RealmException e) {
                e.printStackTrace();
            }


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


                ssb.setSpan(new MySpannable(false) {
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
