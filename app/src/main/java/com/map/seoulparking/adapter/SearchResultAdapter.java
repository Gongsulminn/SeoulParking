package com.map.seoulparking.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.map.seoulparking.R;
import com.map.seoulparking.model.SearchResultModel;

import java.util.List;

/**
 * Created by user on 2018-12-20.
 */

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {
    List<SearchResultModel> itemList;
    private int itemLayout;

    public SearchResultAdapter(List<SearchResultModel> itemList, int itemLayout){
        this.itemList = itemList;
        this.itemLayout = itemLayout;
    }

    @NonNull
    @Override
    public SearchResultAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(itemLayout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultAdapter.ViewHolder viewHolder, int position) {
        SearchResultModel item = itemList.get(position);
        viewHolder.recyclerTitle.setText(item.getTitle());
        viewHolder.recyclerLocation.setText(item.getLocation());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView recyclerTitle;
        TextView recyclerLocation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            recyclerTitle = (TextView) itemView.findViewById(R.id.recyclerTitle);
            recyclerLocation = (TextView) itemView.findViewById(R.id.recyclerLocation);
        }
    }

}
