package com.spicy.food.record.widget;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.spicy.food.data.Diary;
import com.spicy.food.record.R;

import java.text.SimpleDateFormat;
import java.util.Date;


public class ListItemAdapter extends RecyclerBaseAdapter<Diary,
		RecyclerViewHolder> implements SwipeAcitonInterface {

	private ClickItemListener mClickItemListener;

	/**
	 * Same as QuickAdapter#QuickAdapter(Context,int) but with
	 * some initialization data.
	 */
	public ListItemAdapter() {
		super(R.layout.collection_list_item, null);
	}


	private void setCallBack(Diary item) {
		if (mClickItemListener != null) {
			mClickItemListener.onClickItem(item);
		}
	}


	public void setClickItemListener(ClickItemListener clickItemListener) {
		this.mClickItemListener = clickItemListener;
	}

	@Override
	protected int getContentLayoutId(int viewType) {
		return 0;
	}

	/**
	 * Implement this method and use the helper to adapt the view to the given item.
	 *
	 * @param holder A fully initialized helper.
	 * @param entity The item that needs to be displayed.
	 */
	@Override
	protected void convert(final RecyclerViewHolder holder, final Diary entity) {
		// 设置内容
		holder.setViewOnclick(R.id.swip_delete, new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				doDelete(holder.getAdapterPosition());
			}
		});
		String time = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss").format(new Date());
		// 设置内容
		holder.setText(R.id.item_desc, TextUtils.isEmpty(entity.getContent())?"":entity.getContent());
		holder.setText(R.id.item_title, TextUtils.isEmpty(entity.getTitle())?time:entity.getTitle());
		//		holder.setText(R.id.item_desc, entity.messageText);
		//        if (entity.extendParam != null && !TextUtils.isEmpty(entity.extendParam.btnText)) {
		//            holder.setText(R.id.item_remark, entity.extendParam.btnText);
		//        }
		holder.setViewOnclick(R.id.item_content_view, new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setCallBack(entity);
			}
		});
	}


	@Override
	public float getActionWidth(RecyclerView.ViewHolder holder) {
		if (holder instanceof RecyclerViewHolder) {
			return ((RecyclerViewHolder) holder).getViewWidth(R.id.swip_delete);
		}
		return 0;
	}

	@Override
	public int getDeleteId() {
		return R.id.swip_delete;
	}


	public interface ClickItemListener {
		void onClickItem(Diary item);

		void delete(Diary item);
	}

	private void doDelete(int adapterPosition) {
		if (mClickItemListener != null) {
			mClickItemListener.delete(mListData.get(adapterPosition));
		}
		mListData.remove(adapterPosition);
		notifyItemRemoved(adapterPosition);
	}

	public void move(int from, int to) {
		Diary prev = mListData.remove(from);
		mListData.add(to > from ? to - 1 : to, prev);
		notifyItemMoved(from, to);
	}
}
