package com.example.apoorvdubey.udacitymovieprojecttwo.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.apoorvdubey.udacitymovieprojecttwo.R;
import com.example.apoorvdubey.udacitymovieprojecttwo.Utils.Constants;
import com.example.apoorvdubey.udacitymovieprojecttwo.service.model.DetailResult;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PosterViewAdapter extends RecyclerView.Adapter<PosterViewAdapter.ViewHolder> {

    private List<DetailResult> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;

    public PosterViewAdapter(Context context, List<DetailResult> result) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = result;
        this.context = context;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.single_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mData.get(position).getPosterPath() != null)
            Picasso.with(context).load(Constants.getBaseUrlPoster() + Constants.getPosterSize() + mData.get(position).getPosterPath()).into(holder.moviePoster);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView moviePoster;

        ViewHolder(View itemView) {
            super(itemView);
            moviePoster = itemView.findViewById(R.id.poster_image_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
