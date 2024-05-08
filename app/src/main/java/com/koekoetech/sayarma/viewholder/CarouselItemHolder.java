package com.koekoetech.sayarma.viewholder;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.koekoetech.sayarma.R;
import com.koekoetech.sayarma.helper.ExternalFileHelper;

import java.io.File;

public class CarouselItemHolder extends RecyclerView.ViewHolder {

    private final AppCompatImageView ivImage;
    private final Context context;

    public CarouselItemHolder(@NonNull View itemView) {
        super(itemView);

        ivImage = itemView.findViewById(R.id.itemCarouselIvPhoto);
        context = itemView.getContext();
    }

    public void bindItem(String image) {
        File file = ExternalFileHelper.getImageFile(context, image);
        Glide.with(context)
                .load(file)
                .placeholder(R.drawable.article_placeholder)
                .into(ivImage);

    }
}
