package com.koekoetech.sayarma.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.koekoetech.sayarma.R;
import com.koekoetech.sayarma.viewholder.CarouselItemHolder;
import java.util.List;

public class CarouselAdapter extends RecyclerView.Adapter<CarouselItemHolder> {

    private final int itemHeight;
    private final List<String> images;


    public CarouselAdapter(int itemHeight, List<String> images) {
        this.itemHeight = itemHeight;
        this.images = images;
    }

    @NonNull
    @Override
    public CarouselItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View itemView = layoutInflater.inflate(R.layout.layout_item_carousel_card, parent, false);
        return new CarouselItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CarouselItemHolder holder, int position) {
        holder.bindItem(images.get(position));

        holder.itemView.getLayoutParams().height = itemHeight;
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

}
