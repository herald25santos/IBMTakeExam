package com.santos.herald.ibmpracticalexam.ui.weatherlist;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.santos.herald.ibmpracticalexam.R;
import com.santos.herald.ibmpracticalexam.data.WeatherCoordinatesEntity;
import com.santos.herald.ibmpracticalexam.data.WeatherEntity;
import com.santos.herald.ibmpracticalexam.data.WeatherMainEntity;
import com.santos.herald.ibmpracticalexam.data.WeatherResponse;
import com.santos.herald.ibmpracticalexam.ui.base.BaseAdapter;
import com.santos.herald.ibmpracticalexam.utils.listener.onRecyclerViewListener;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by phhesa on 7/10/2017.
 */

public class WeatherListAdapter extends BaseAdapter<WeatherEntity> {

    public final String TAG = WeatherListAdapter.this.getClass().getSimpleName();
    private Context mContext;
    private String location;
    private WeatherMainEntity weatherMainEntity;
    private WeatherCoordinatesEntity weatherCoordinatesEntity;
    private onRecyclerViewListener mOnClickListener;


    public WeatherListAdapter(Context context, List<WeatherEntity> data, boolean withHeader, boolean withFooter) {
        super(data, withHeader, withFooter);
        //setHasStableIds(true);
        this.mContext = context;
    }

    @Override
    protected RecyclerView.ViewHolder getItemView(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_weather, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    protected RecyclerView.ViewHolder getHeaderView(LayoutInflater inflater, ViewGroup parent) {
        return null;
    }

    @Override
    protected RecyclerView.ViewHolder getFooterView(LayoutInflater inflater, ViewGroup parent) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            //Glide.clear(itemViewHolder.imgProduct);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            prepareItemViewHolder(itemViewHolder, position);
        }
    }

    private void prepareItemViewHolder(ItemViewHolder itemViewHolder, int position) {
        WeatherEntity weatherEntity = getData().get(position);
        itemViewHolder.tvWeather.setText(String.format("Weather Condition: %s", weatherEntity.main));
        itemViewHolder.tvTemp.setText(String.format("Temperature: %s", weatherMainEntity.temp));

//        StringBuilder coordinatesStringBuilder = new StringBuilder();
//        coordinatesStringBuilder.append("Longitude: " + String.valueOf(weatherCoordinatesEntity.lon));
//        coordinatesStringBuilder.append("\n");
//        coordinatesStringBuilder.append("Latitude: " + String.valueOf(weatherCoordinatesEntity.lat));
        itemViewHolder.tvLocation.setText(String.format("Location: %s", location));
        Glide.with(mContext)
                .load("http://openweathermap.org/img/w/"+weatherEntity.icon+".png")
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(itemViewHolder.imgWeather);
    }

    public void setOnClickListener(onRecyclerViewListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    public void setBasedContent(String location, WeatherCoordinatesEntity weatherCoordinatesEntity, WeatherMainEntity weatherMainEntity) {
        this.location = location;
        this.weatherCoordinatesEntity = weatherCoordinatesEntity;
        this.weatherMainEntity = weatherMainEntity;
    }

    public String getLocation() {
        return location;
    }

    public WeatherMainEntity getWeatherMainEntity() {
        return weatherMainEntity;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tvWeather)
        public TextView tvWeather;

        @BindView(R.id.tvTemp)
        public TextView tvTemp;

        @BindView(R.id.tvLocation)
        public TextView tvLocation;

        @BindView(R.id.imgWeather)
        public AppCompatImageView imgWeather;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnClickListener.onItemClick(v, getAdapterPosition());
        }
    }

}