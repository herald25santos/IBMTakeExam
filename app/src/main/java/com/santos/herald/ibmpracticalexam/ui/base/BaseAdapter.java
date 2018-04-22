package com.santos.herald.ibmpracticalexam.ui.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by phhesa on 7/10/2017.
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;

    private List<T> data;

    private boolean mWithHeader;
    private boolean mWithFooter;

    public BaseAdapter(List<T> data, boolean withHeader, boolean withFooter) {
        this.data = data;
        mWithHeader = withHeader;
        mWithFooter = withFooter;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public boolean ismWithHeader() {
        return mWithHeader;
    }

    public void setmWithHeader(boolean mWithHeader) {
        this.mWithHeader = mWithHeader;
    }

    public boolean ismWithFooter() {
        return mWithFooter;
    }

    public void setmWithFooter(boolean mWithFooter) {
        this.mWithFooter = mWithFooter;
    }

    //region Get View
    protected abstract RecyclerView.ViewHolder getItemView(LayoutInflater inflater, ViewGroup parent);

    protected abstract RecyclerView.ViewHolder getHeaderView(LayoutInflater inflater, ViewGroup parent);

    protected abstract RecyclerView.ViewHolder getFooterView(LayoutInflater inflater, ViewGroup parent);
    //endregion

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == TYPE_ITEM) {
            return getItemView(inflater, parent);
        } else if (viewType == TYPE_HEADER) {
            return getHeaderView(inflater, parent);
        } else if (viewType == TYPE_FOOTER) {
            return getFooterView(inflater, parent);
        }

        return getHeaderView(inflater, parent);
        //  throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public int getItemCount() {
        int itemCount = data == null ? 0 : data.size();
        if (mWithHeader)
            itemCount++;
        if (mWithFooter)
            itemCount++;
        return itemCount;
    }


    @Override
    public int getItemViewType(int position) {
        if (mWithHeader && isPositionHeader(position))
            return TYPE_HEADER;
        if (mWithFooter && isPositionFooter(position))
            return TYPE_FOOTER;
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private boolean isPositionFooter(int position) {
        return position == getItemCount() - 1;
    }


    public T getItem(int position) {
        return mWithHeader ? data.get(position - 1) : data.get(position);
    }
}