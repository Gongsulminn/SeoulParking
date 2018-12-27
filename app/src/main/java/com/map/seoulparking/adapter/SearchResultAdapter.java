package com.map.seoulparking.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
    private Context context;


    public SearchResultAdapter(Context context,List<SearchResultModel> itemList, int itemLayout){
        this.context = context;
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
    public void onBindViewHolder(@NonNull final SearchResultAdapter.ViewHolder viewHolder, int position) {
        SearchResultModel item = itemList.get(position);
        viewHolder.recyclerTitle.setText(item.getTitle());
        viewHolder.recyclerLocation.setText(item.getLocation());

        viewHolder.recyclerAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("눌림?",viewHolder.recyclerLocation.getText().toString());

                Intent intent = ((Activity) context).getIntent();
                intent.putExtra("address",viewHolder.recyclerLocation.getText().toString());
                ((Activity)context).setResult(Activity.RESULT_OK,intent);
                ((Activity) context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout recyclerAll;
        TextView recyclerTitle;
        TextView recyclerLocation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            recyclerAll = (LinearLayout) itemView.findViewById(R.id.recyclerAll);
            recyclerTitle = (TextView) itemView.findViewById(R.id.recyclerTitle);
            recyclerLocation = (TextView) itemView.findViewById(R.id.recyclerLocation);
        }
    }

}
