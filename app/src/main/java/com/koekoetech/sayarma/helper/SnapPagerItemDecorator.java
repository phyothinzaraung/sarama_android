package com.koekoetech.sayarma.helper;

import android.graphics.Rect;
import androidx.annotation.NonNull;
import androidx.annotation.Px;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;

public class SnapPagerItemDecorator extends RecyclerView.ItemDecoration {
    private static final String TAG = "SnapPagerItemDecorator";

    private final int mInterItemsGap;
    private final int mNetOneSidedGap;

    /**
     * @param totalHeight        Total height of the container view (mostly RecyclerView) in Pixels
     * @param itemHeight         RecyclerView's item height in Pixels
     * @param itemPeekingPercent Percentage of the container(RecyclerView) height for
     *                           the top and/or bottom neighbouring item to appear as hint
     */
    public SnapPagerItemDecorator(@Px int totalHeight, @Px int itemHeight, float itemPeekingPercent) {
        Log.d(TAG, "SnapPagerItemDecorator() called with: totalHeight = [" + totalHeight + "], itemHeight = [" + itemHeight + "], itemPeekingPercent = [" + itemPeekingPercent + "]");

        /* Calculate item's peeking height based on provided itemPeekingPercent */
        final int cardPeekingHeight = Math.round(itemHeight * itemPeekingPercent + .5f);

        /* Calculate the gap between each items */
        mInterItemsGap = (totalHeight - itemHeight) / 2;

        /* Calculate the gap between the container and first/last items */
        mNetOneSidedGap = mInterItemsGap / 2 - cardPeekingHeight;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.State state) {
        final int index = recyclerView.getChildAdapterPosition(view);
        final boolean isFirstItem = index == 0;
        final RecyclerView.Adapter adapter = recyclerView.getAdapter();
        final boolean isLastItem = adapter != null && index == adapter.getItemCount() - 1;
        final int topInset = isFirstItem ? mInterItemsGap : mNetOneSidedGap;
        final int bottomInset = isLastItem ? mInterItemsGap : mNetOneSidedGap;
        outRect.set(0, topInset, 0, bottomInset);
    }
}
