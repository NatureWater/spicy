
package com.spicy.food.record.widget;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.spicy.food.record.R;


public class CollectionLIstItemTouchHelperCallback extends ItemTouchHelperExtension.Callback {

	@Override
	public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
		return makeSwipFlags(ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, ItemTouchHelper.START);
//        return  makeFlag(ACTION_STATE_IDLE, swipeFlags | dragFlags) |
//                makeFlag(ACTION_STATE_SWIPE, swipeFlags) | makeFlag(ACTION_STATE_DRAG,
//                dragFlags)
	}

	@Override
	public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target) {
		ListItemAdapter adapter = (ListItemAdapter) recyclerView.getAdapter();
		adapter.move(viewHolder.getAdapterPosition(), target.getAdapterPosition());
		return true;
	}

	@Override
	public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

	}

	@Override
	public boolean isLongPressDragEnabled() {
		return true;
	}

	@Override
	public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder
			viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
		if (dY != 0 && dX == 0) {
			super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
		}

		if (viewHolder instanceof RecyclerViewHolder) {
			if (dX < -((RecyclerViewHolder) viewHolder).getViewWidth(R.id.item_content_view)) {
				dX = -((RecyclerViewHolder) viewHolder).getViewWidth(R.id.item_content_view);
			}

			((RecyclerViewHolder) viewHolder).getView(R.id.item_content_view).setTranslationX(dX);
			return;
		}
	}
}
