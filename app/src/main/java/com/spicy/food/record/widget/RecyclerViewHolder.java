package com.spicy.food.record.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;


import java.util.HashSet;
import java.util.LinkedHashSet;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {


	/**
	 * Views indexed with their IDs
	 */
	private final SparseArray<View> views;

	private final LinkedHashSet<Integer> childClickViewIds;

	private final LinkedHashSet<Integer> itemChildLongClickViewIds;


	public View convertView;

	/**
	 * Package private field to retain the associated user object and detect a change
	 */
	Object associatedObject;


	public RecyclerViewHolder(final View view) {
		super(view);
		this.views = new SparseArray<View>();
		this.childClickViewIds = new LinkedHashSet<>();
		this.itemChildLongClickViewIds = new LinkedHashSet<>();
		convertView = view;


	}


	public HashSet<Integer> getItemChildLongClickViewIds() {
		return itemChildLongClickViewIds;
	}

	public HashSet<Integer> getChildClickViewIds() {
		return childClickViewIds;
	}

	public View getConvertView() {

		return convertView;
	}

	/**
	 * Will set the text of a TextView.
	 *
	 * @param viewId The view id.
	 * @param value  The text to put in the text view.
	 * @return The BaseViewHolder for chaining.
	 */
	public void setText(int viewId, CharSequence value) {
		TextView view = getView(viewId);
		view.setText(value);

	}


	/**
	 * Will set the image of an ImageView from a resource id.
	 *
	 * @param viewId     The view id.
	 * @param imageResId The image resource id.
	 * @return The BaseViewHolder for chaining.
	 */
	public void setImageResource(int viewId, @DrawableRes int imageResId) {
		ImageView view = getView(viewId);
		view.setImageResource(imageResId);

	}

	/**
	 * Will set background color of a view.
	 *
	 * @param viewId The view id.
	 * @param color  A color, not a resource id.
	 * @return The BaseViewHolder for chaining.
	 */
	public void setBackgroundColor(int viewId, int color) {
		View view = getView(viewId);
		view.setBackgroundColor(color);

	}

	/**
	 * Will set background of a view.
	 *
	 * @param viewId        The view id.
	 * @param backgroundRes A resource to use as a background.
	 * @return The BaseViewHolder for chaining.
	 */
	public void setBackgroundRes(int viewId, @DrawableRes int backgroundRes) {
		View view = getView(viewId);
		view.setBackgroundResource(backgroundRes);
	}

	/**
	 * Will set text color of a TextView.
	 *
	 * @param viewId    The view id.
	 * @param textColor The text color (not a resource id).
	 * @return The BaseViewHolder for chaining.
	 */
	public void setTextColor(int viewId, int textColor) {
		TextView view = getView(viewId);
		view.setTextColor(textColor);
	}


	/**
	 * Will set the image of an ImageView from a drawable.
	 *
	 * @param viewId   The view id.
	 * @param drawable The image drawable.
	 * @return The BaseViewHolder for chaining.
	 */
	public void setImageDrawable(int viewId, Drawable drawable) {
		ImageView view = getView(viewId);
		view.setImageDrawable(drawable);
	}

	/**
	 * Add an action to set the image of an image view. Can be called multiple times.
	 */
	public void setImageBitmap(int viewId, Bitmap bitmap) {
		ImageView view = getView(viewId);
		view.setImageBitmap(bitmap);

	}

	/**
	 * Add an action to set the alpha of a view. Can be called multiple times.
	 * Alpha between 0-1.
	 */
	public void setAlpha(int viewId, float value) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getView(viewId).setAlpha(value);
		} else {
			// Pre-honeycomb hack to set Alpha value
			AlphaAnimation alpha = new AlphaAnimation(value, value);
			alpha.setDuration(0);
			alpha.setFillAfter(true);
			getView(viewId).startAnimation(alpha);
		}

	}

	/**
	 * Set a view visibility to VISIBLE (true) or GONE (false).
	 *
	 * @param viewId  The view id.
	 * @param visible True for VISIBLE, false for GONE.
	 * @return The BaseViewHolder for chaining.
	 */
	public void setVisible(int viewId, boolean visible) {
		View view = getView(viewId);
		view.setVisibility(visible ? View.VISIBLE : View.GONE);
	}

	/**
	 * Add links into a TextView.
	 *
	 * @param viewId The id of the TextView to linkify.
	 * @return The BaseViewHolder for chaining.
	 */
	public void linkify(int viewId) {
		TextView view = getView(viewId);
		Linkify.addLinks(view, Linkify.ALL);
	}

	/**
	 * Apply the typeface to the given viewId, and enable subpixel rendering.
	 */
	public void setTypeface(int viewId, Typeface typeface) {
		TextView view = getView(viewId);
		view.setTypeface(typeface);
		view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
	}

	/**
	 * Apply the typeface to all the given viewIds, and enable subpixel rendering.
	 */
	public void setTypeface(Typeface typeface, int... viewIds) {
		for (int viewId : viewIds) {
			TextView view = getView(viewId);
			view.setTypeface(typeface);
			view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
		}
	}

	/**
	 * Sets the progress of a ProgressBar.
	 *
	 * @param viewId   The view id.
	 * @param progress The progress.
	 * @return The BaseViewHolder for chaining.
	 */
	public void setProgress(int viewId, int progress) {
		ProgressBar view = getView(viewId);
		view.setProgress(progress);
	}

	/**
	 * Sets the progress and max of a ProgressBar.
	 *
	 * @param viewId   The view id.
	 * @param progress The progress.
	 * @param max      The max value of a ProgressBar.
	 * @return The BaseViewHolder for chaining.
	 */
	public void setProgress(int viewId, int progress, int max) {
		ProgressBar view = getView(viewId);
		view.setMax(max);
		view.setProgress(progress);
	}

	/**
	 * Sets the range of a ProgressBar to 0...max.
	 *
	 * @param viewId The view id.
	 * @param max    The max value of a ProgressBar.
	 * @return The BaseViewHolder for chaining.
	 */
	public void setMax(int viewId, int max) {
		ProgressBar view = getView(viewId);
		view.setMax(max);
	}

	/**
	 * Sets the rating (the number of stars filled) of a RatingBar.
	 *
	 * @param viewId The view id.
	 * @param rating The rating.
	 * @return The BaseViewHolder for chaining.
	 */
	public void setRating(int viewId, float rating) {
		RatingBar view = getView(viewId);
		view.setRating(rating);
	}

	/**
	 * Sets the rating (the number of stars filled) and max of a RatingBar.
	 *
	 * @param viewId The view id.
	 * @param rating The rating.
	 * @param max    The range of the RatingBar to 0...max.
	 * @return The BaseViewHolder for chaining.
	 */
	public void setRating(int viewId, float rating, int max) {
		RatingBar view = getView(viewId);
		view.setMax(max);
		view.setRating(rating);
	}

	/**
	 * Sets the on click listener of the view.
	 *
	 * @param viewId   The view id.
	 * @param listener The on click listener;
	 * @return The BaseViewHolder for chaining.
	 */
	public void setOnClickListener(int viewId, View.OnClickListener listener) {
		View view = getView(viewId);
		view.setOnClickListener(listener);
	}


	/**
	 * Sets the on touch listener of the view.
	 *
	 * @param viewId   The view id.
	 * @param listener The on touch listener;
	 * @return The BaseViewHolder for chaining.
	 */
	public void setOnTouchListener(int viewId, View.OnTouchListener listener) {
		View view = getView(viewId);
		view.setOnTouchListener(listener);
	}


	public void setOnItemClickListener(int viewId, AdapterView.OnItemClickListener listener) {
		AdapterView view = getView(viewId);
		view.setOnItemClickListener(listener);
	}


	public void setOnItemSelectedClickListener(int viewId, AdapterView.OnItemSelectedListener
			listener) {
		AdapterView view = getView(viewId);
		view.setOnItemSelectedListener(listener);
	}

	/**
	 * Sets the on checked change listener of the view.
	 *
	 * @param viewId   The view id.
	 * @param listener The checked change listener of compound button.
	 * @return The BaseViewHolder for chaining.
	 */
	public void setOnCheckedChangeListener(int viewId, CompoundButton.OnCheckedChangeListener
			listener) {
		CompoundButton view = getView(viewId);
		view.setOnCheckedChangeListener(listener);
	}

	/**
	 * Sets the tag of the view.
	 *
	 * @param viewId The view id.
	 * @param tag    The tag;
	 * @return The BaseViewHolder for chaining.
	 */
	public void setTag(int viewId, Object tag) {
		View view = getView(viewId);
		view.setTag(tag);
	}

	/**
	 * Sets the tag of the view.
	 *
	 * @param viewId The view id.
	 * @param key    The key of tag;
	 * @param tag    The tag;
	 * @return The BaseViewHolder for chaining.
	 */
	public void setTag(int viewId, int key, Object tag) {
		View view = getView(viewId);
		view.setTag(key, tag);
	}

	/**
	 * Sets the checked status of a checkable.
	 *
	 * @param viewId  The view id.
	 * @param checked The checked status;
	 * @return The BaseViewHolder for chaining.
	 */
	public void setChecked(int viewId, boolean checked) {
		View view = getView(viewId);
		// View unable cast to Checkable
		if (view instanceof CompoundButton) {
			((CompoundButton) view).setChecked(checked);
		} else if (view instanceof CheckedTextView) {
			((CheckedTextView) view).setChecked(checked);
		}
	}


	@SuppressWarnings("unchecked")
	public <T extends View> T getView(int viewId) {
		View view = views.get(viewId);
		if (view == null) {
			view = convertView.findViewById(viewId);
			views.put(viewId, view);
		}
		return (T) view;
	}

	/**
	 * Retrieves the last converted object on this view.
	 */
	public Object getAssociatedObject() {
		return associatedObject;
	}

	/**
	 * Should be called during convert
	 */
	public void setAssociatedObject(Object associatedObject) {
		this.associatedObject = associatedObject;
	}


	public void setText(int viewId, String text) {
		TextView textView = getView(viewId);
		if (text == null) {
			text = "";
		}
		textView.setText(text);
	}

	public void setText(int viewId, int textId) {
		TextView textView = getView(viewId);
		textView.setText(textId);
	}

	public void setTextColor(int viewId, String text, int colorId) {
		TextView textView = getView(viewId);
		textView.setText(text);
		textView.setTextColor(colorId);
	}

	public void setTextBackground(int viewId, int drawableId) {
		TextView textView = getView(viewId);
		textView.setBackgroundResource(drawableId);
	}

	public void setMaxEcplise(int viewId, final int maxLines, final String content) {
		final TextView textView = getView(viewId);
		ViewTreeObserver observer = textView.getViewTreeObserver();
		observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				textView.setText(content);
				if (textView.getLineCount() > maxLines) {
					int lineEndIndex = textView.getLayout().getLineEnd(maxLines - 1);
					//下面这句代码中：我在项目中用数字3发现效果不好，改成1了
					String text = content.subSequence(0, lineEndIndex - 3) + "...";
					textView.setText(text);
				} else {
					removeGlobalOnLayoutListener(textView.getViewTreeObserver(), this);
				}
			}
		});
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	private void removeGlobalOnLayoutListener(ViewTreeObserver obs, ViewTreeObserver.OnGlobalLayoutListener listener) {
		if (obs == null)
			return;
		if (Build.VERSION.SDK_INT < 16) {
			obs.removeGlobalOnLayoutListener(listener);
		} else {
			obs.removeOnGlobalLayoutListener(listener);
		}
	}

	public void setFormatText(Context context, int viewId, String text, int textId) {
		TextView textView = getView(viewId);
		textView.setText(context.getString(textId, text));
	}


	public void setImageRes(int viewId, int resId) {
		ImageView imageView = getView(viewId);
		imageView.setImageResource(resId);
	}


	public void setImageBg(int viewId, int resId) {
		ImageView imageView = getView(viewId);
		imageView.setBackgroundResource(resId);
	}

	public void setBgRes(int viewId, int resId) {
		View view = getView(viewId);
		view.setBackgroundResource(resId);
	}

	public void setBgColor(int viewId, int colorId) {
		View view = getView(viewId);
		view.setBackgroundColor(colorId);
	}

	public void setVisibility(int viewId, int visibility) {
		View view = getView(viewId);
		view.setVisibility(visibility);
	}

	public void setViewOnclick(int viewId, View.OnClickListener clickListener) {
		View view = getView(viewId);
		if (clickListener != null) {
			view.setOnClickListener(clickListener);
		}
	}

	public float getViewWidth(int viewId) {
		View view = getView(viewId);
		return view.getWidth();
	}

	public float getViewHeight(int viewId) {
		View view = getView(viewId);
		return view.getHeight();
	}

	public void addView(int viewId, View addView) {
		ViewGroup view = getView(viewId);
		view.addView(addView);
	}
}
