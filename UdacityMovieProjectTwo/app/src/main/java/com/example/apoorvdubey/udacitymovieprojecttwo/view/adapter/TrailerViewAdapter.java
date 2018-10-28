package com.example.apoorvdubey.udacitymovieprojecttwo.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.apoorvdubey.udacitymovieprojecttwo.R;
import com.example.apoorvdubey.udacitymovieprojecttwo.service.model.Trailer;


import java.util.List;

public class TrailerViewAdapter extends RecyclerView.Adapter<TrailerViewAdapter.ViewHolder> {

    private List<Trailer> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;

    public TrailerViewAdapter(Context context, List<Trailer> result) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = result;
        this.context = context;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.trailer_single_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mData.get(position).getName() == null)
            holder.trailerName.setText(R.string.na);
        else
            holder.trailerName.setText(mData.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView trailerName;

        ViewHolder(View itemView) {
            super(itemView);
            trailerName = itemView.findViewById(R.id.trailer_name_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null)
                mClickListener.onItemClick(view, mData.get(getAdapterPosition()).getKey());
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, String key);
    }
}
