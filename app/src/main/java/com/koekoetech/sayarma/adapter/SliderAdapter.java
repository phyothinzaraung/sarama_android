package com.koekoetech.sayarma.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.koekoetech.sayarma.R;
import com.smarteist.autoimageslider.SliderViewAdapter;
import java.util.ArrayList;

import androidx.appcompat.widget.AppCompatImageView;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderViewHolder> {

    private final ArrayList<String> photos;
    public SliderAdapter(ArrayList<String> photos) {
        this.photos = photos;
    }

    @Override
    public SliderViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_slider, parent, false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SliderViewHolder viewHolder, int position) {
        viewHolder.bind(photos.get(position));
    }

    @Override
    public int getCount() {
        return photos.size();
    }

    static class SliderViewHolder extends SliderViewAdapter.ViewHolder{

        AppCompatImageView sliderImage;

        SliderViewHolder(View itemView){
            super(itemView);

            sliderImage = itemView.findViewById(R.id.imageView_slider);
        }

        private void bind(String photo){
            Glide.with(itemView.getContext())
                    .load(photo)
                    .centerCrop()
                    .placeholder(R.drawable.article_placeholder)
                    .into(sliderImage);
        }

    }
}
