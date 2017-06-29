package com.sourcey.intelligentmedicalsystem.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by StFranky on 2017/6/25.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    int mSpace ;

    /**
     * @param space 传入的值，其单位视为dp
     */
    public SpaceItemDecoration(int space) {
        this.mSpace = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.right = mSpace;
        outRect.left = mSpace;
        outRect.top = mSpace/2;
        outRect.bottom = mSpace/2;
    }
}
