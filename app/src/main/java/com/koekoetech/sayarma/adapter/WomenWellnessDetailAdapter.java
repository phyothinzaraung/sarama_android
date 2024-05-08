package com.koekoetech.sayarma.adapter;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.koekoetech.sayarma.R;
import com.koekoetech.sayarma.activity.PhotoViewActivity;
import java.util.List;

public class WomenWellnessDetailAdapter extends RecyclerView.Adapter<WomenWellnessDetailAdapter.WomenWellnessDetailViewHolder> {

    private final List<String> photos;

    public WomenWellnessDetailAdapter(List<String> photo) {
        this.photos = photo;
    }

    @NonNull
    @Override
    public WomenWellnessDetailViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_women_wellness_detail, viewGroup, false);
        return new WomenWellnessDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WomenWellnessDetailViewHolder womenWellnessDetailViewHolder, int i) {
        womenWellnessDetailViewHolder.bindPhoto(photos.get(i));
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }


    static class WomenWellnessDetailViewHolder extends RecyclerView.ViewHolder{

        private final ImageView imgWomenWellness;

        public WomenWellnessDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            imgWomenWellness = itemView.findViewById(R.id.imgWomenWellness);
        }

        void bindPhoto(String photo){

            Glide.with(itemView.getContext())
                    .load(photo)
                    .placeholder(R.drawable.article_placeholder)
                    .into(imgWomenWellness);

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), PhotoViewActivity.class);
                intent.putExtra(PhotoViewActivity.IMAGE_EXTRA, photo);
                itemView.getContext().startActivity(intent);
            });
        }
    }
}
