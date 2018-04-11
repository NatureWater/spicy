package com.spicy.food.record.widget;

import android.support.v7.widget.RecyclerView;

public interface SwipeAcitonInterface {

    float getActionWidth(RecyclerView.ViewHolder holder);
    int getDeleteId();
}
