/**
 * Copyright 2013 Joan Zapata
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.spicy.food.record.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spicy.food.record.R;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public abstract class RecyclerBaseAdapter<T, K extends RecyclerViewHolder> extends RecyclerView
        .Adapter<K> {
    protected Context mContext;
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    public static final int TYPE_FOOTER = -1;
    protected int mLayoutResId;
    private int mHeaderId;
    protected LayoutInflater mLayoutInflater;
    protected List<T> mListData;
    private RecyclerView mRecyclerView;

    private View mHeaderLayout;
    private View mFooterLayout;
    private boolean isShowHeader = false;
    private boolean isShowFooter = false;


    public void setHeaderView(View view) {
        if (view == null) {
            mHeaderLayout = null;
            isShowHeader = false;
        } else {
            mHeaderLayout = view;
            isShowHeader = true;
        }
        notifyDataSetChanged();
    }

    public void setHeaderView(int headerId) {
        if (headerId == 0) {
            mHeaderId = 0;
            isShowHeader = false;
        } else {
            mHeaderId = headerId;
            isShowHeader = true;
        }
        notifyDataSetChanged();
    }

    public View getHeaderView() {
        return mHeaderLayout;
    }

    public void setFooterView(View view) {
        mFooterLayout = view;
        isShowFooter = true;
        notifyDataSetChanged();
    }

    public void showFooter() {
        isShowFooter = true;
        notifyDataSetChanged();
    }

    public void hideFooter() {
        isShowFooter = false;
        notifyDataSetChanged();
    }

    protected RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    private void setRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    private void checkNotNull() {
        if (getRecyclerView() == null) {
            throw new RuntimeException("please bind recyclerView first!");
        }
    }

    /**
     * same as recyclerView.setAdapter(), and save the instance of recyclerView
     */
    public void bindToRecyclerView(RecyclerView recyclerView) {
        if (getRecyclerView() != null) {
            throw new RuntimeException("Don't bind twice");
        }
        setRecyclerView(recyclerView);
        getRecyclerView().setAdapter(this);
    }

    /**
     * @param layoutResId The layout resource id of each item.
     * @param data        A new list is created out of this one to avoid mutable list
     */
    public RecyclerBaseAdapter(int layoutResId, List<T> data) {
        this.mListData = data == null ? new ArrayList<T>() : data;
        if (layoutResId != 0) {
            this.mLayoutResId = layoutResId;
        }
    }

    public RecyclerBaseAdapter(View view) {

    }

    public RecyclerBaseAdapter(List<T> data) {
        this(0, data);
    }

    public RecyclerBaseAdapter(int layoutResId) {
        this(layoutResId, null);
    }

    /**
     * setting up a new instance to data;
     *
     * @param data
     */
    public void setData(List<T> data) {
        if (!isShowHeader && (data == null || data.size() == 0)) {
            return;
        }
        if (data == null) {
            data = new ArrayList<>();
        }
        if (mListData == null) {
            mListData = new ArrayList<T>();
        }
        mListData.clear();
        mListData.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        mListData.clear();
        notifyDataSetChanged();
    }

    /**
     * insert  a item associated with the specified position of adapter
     *
     * @param position
     * @param item
     * @deprecated use {@link #addData(int, Object)} instead
     */
    @Deprecated
    public void add(int position, T item) {
        addData(position, item);
    }

    /**
     * add one new data in to certain location
     *
     * @param position
     */
    public void addData(int position, T data) {
        if (mListData == null) {
            mListData = new ArrayList<T>();
        }
        mListData.add(position, data);
        notifyItemInserted(position);
    }

    /**
     * add one new data
     */
    public void addData(T data) {
        if (mListData == null) {
            mListData = new ArrayList<T>();
        }
        mListData.add(data);
        notifyDataSetChanged();
    }

    /**
     * remove the item associated with the specified position of adapter
     *
     * @param position
     */
    public void remove(int position) {
        if (mListData == null) {
            mListData = new ArrayList<T>();
        }
        mListData.remove(position);
        notifyDataSetChanged();
    }

    /**
     * change data
     */
    public void setData(int index, T data) {
        if (mListData == null) {
            mListData = new ArrayList<T>();
        }
        mListData.set(index, data);
        notifyDataSetChanged();
    }

    /**
     * add new data in to certain location
     *
     * @param position
     */
    public void addData(int position, List<T> data) {
        if (mListData == null) {
            mListData = new ArrayList<T>();
        }
        mListData.addAll(position, data);
        notifyDataSetChanged();
    }

    /**
     * additional data;
     *
     * @param newData
     */
    public void addData(List<T> newData) {
        if (mListData == null) {
            mListData = new ArrayList<T>();
        }
        int position = mListData.size();
        this.mListData.addAll(newData);
        if (newData == null || newData.size() == 0) {
            return;
        }
        if (isShowHeader) {
            notifyItemInserted(position + 1);
        } else {
            notifyItemInserted(position);
        }
        //        notifyDataSetChanged();
    }


    /**
     * Get the data of list
     *
     * @return
     */
    public List<T> getData() {
        return mListData;
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    public T getItem(int position) {
        if (isShowHeader && position == 0) {
            return null;
        }
        if (position != -1) {
            return mListData.get(position);
        } else {
            return null;
        }
    }


    @Override
    public int getItemCount() {
        if (mListData == null || mListData.size() == 0) {
            if (isShowHeader) {
                return 1;
            }
            return 0;
        }
        int size = mListData.size();
        if (isShowFooter && isShowHeader) {
            return size + 2;
        } else if (isShowHeader) {
            return size + 1;
        } else if (isShowFooter) {
            return size + 1;
        }
        return size;
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return isShowHeader ? position - 1 : position;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && isShowHeader) {
            return TYPE_HEADER;
        }

        if (isShowFooter && position == getItemCount() - 1) {
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }


    @Override
    public K onCreateViewHolder(ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        this.mLayoutInflater = LayoutInflater.from(mContext);
        if (viewType == TYPE_HEADER) {
            if (mHeaderLayout == null) {
                mHeaderLayout = getHeaderView(mLayoutInflater, parent);
            }
            return createBaseViewHolder(mHeaderLayout);
        }

        if (viewType == TYPE_FOOTER) {
            if (mFooterLayout == null) {
                mFooterLayout = getFooterView(mLayoutInflater, parent);
            }
            return createBaseViewHolder(mFooterLayout);
        }
        int id = getContentLayoutId(viewType);
        if (id == 0) {
            id = mLayoutResId;
        }
        K baseViewHolder = createBaseViewHolder(parent, id);
        return baseViewHolder;

    }

    public View getFooterView(LayoutInflater layoutInflater, ViewGroup parent) {
        return layoutInflater.inflate(R.layout.loadmore_layout, parent, false);
    }

    public View getHeaderView(LayoutInflater layoutInflater, ViewGroup parent) {
        return mLayoutInflater.inflate(mHeaderId, parent, false);
    }

    /**
     * 使用初始化时默认的layout item 返回0即可
     *
     * @param viewType
     * @return
     */
    protected abstract int getContentLayoutId(int viewType);

    /**
     * To bind different types of holder and solve different the bind events
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(K holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) {
            return;
        }
        if (isShowFooter && position == getItemCount() - 1) {
            covertFooter();
            return;
        }
        int pos = position;
        if (isShowHeader) {
            pos = getRealPosition(holder);
        }
        //        int pos = getRealPosition(holder);
        //        convert(holder, mListData.get(pos));
        convert(holder, mListData.get(pos));

    }

    protected void covertFooter() {
        mFooterLayout.findViewById(R.id.progressBar);
    }

    public View getFooterLayout() {
        return mFooterLayout;
    }

    //	protected K onCreateDefViewHolder(ViewGroup parent, int viewType) {
    //		return createBaseViewHolder(parent, mLayoutResId);
    //	}

    protected K createBaseViewHolder(ViewGroup parent, int layoutResId) {
        return createBaseViewHolder(getItemView(layoutResId, parent));
    }

    /**
     * if you want to use subclass of BaseViewHolder in the adapter,
     * you must override the method to create new ViewHolder.
     *
     * @param view view
     * @return new ViewHolder
     */
    protected K createBaseViewHolder(View view) {
        Class temp = getClass();
        Class z = null;
        while (z == null && null != temp) {
            z = getInstancedGenericKClass(temp);
            temp = temp.getSuperclass();
        }
        K k = createGenericKInstance(z, view);
        return null != k ? k : (K) new RecyclerViewHolder(view);
    }

    /**
     * try to create Generic K instance
     *
     * @param z
     * @param view
     * @return
     */
    private K createGenericKInstance(Class z, View view) {
        try {
            Constructor constructor;
            String buffer = Modifier.toString(z.getModifiers());
            String className = z.getName();
            // inner and unstatic class
            if (className.contains("$") && !buffer.contains("static")) {
                constructor = z.getDeclaredConstructor(getClass(), View.class);
                return (K) constructor.newInstance(this, view);
            } else {
                constructor = z.getDeclaredConstructor(View.class);
                return (K) constructor.newInstance(view);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get generic parameter K
     *
     * @param z
     * @return
     */
    private Class getInstancedGenericKClass(Class z) {
        Type type = z.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            for (Type temp : types) {
                if (temp instanceof Class) {
                    Class tempClass = (Class) temp;
                    if (RecyclerViewHolder.class.isAssignableFrom(tempClass)) {
                        return tempClass;
                    }
                }
            }
        }
        return null;
    }

    /**
     * @param layoutResId ID for an XML layout resource to load
     * @param parent      Optional view to be the parent of the generated hierarchy or else simply
     *                    an object that
     *                    provides a set of LayoutParams values for root of the returned
     *                    hierarchy
     * @return view will be return
     */
    protected View getItemView(int layoutResId, ViewGroup parent) {
        return mLayoutInflater.inflate(layoutResId, parent, false);
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    protected abstract void convert(K helper, T item);

    /**
     * get the specific view by position,e.g. getViewByPosition(2, R.id.textView)
     * <p>
     * bind recyclerView {@link #bindToRecyclerView(RecyclerView)} before use!
     *
     * @see #bindToRecyclerView(RecyclerView)
     */
    public View getViewByPosition(int position, int viewId) {
        checkNotNull();
        return getViewByPosition(getRecyclerView(), position, viewId);
    }

    public View getViewByPosition(RecyclerView recyclerView, int position, int viewId) {
        if (recyclerView == null) {
            return null;
        }
        RecyclerViewHolder viewHolder = (RecyclerViewHolder) recyclerView
                .findViewHolderForLayoutPosition(position);
        return viewHolder.getView(viewId);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }


    //    private int getItemPosition(T item) {
    //        return item != null && mListData != null && !mListData.isEmpty() ? mListData.indexOf(item)
    //                : -1;
    //    }


}
