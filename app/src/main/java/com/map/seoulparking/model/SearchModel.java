package com.map.seoulparking.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by user on 2018-12-20.
 */

public class SearchModel {
    @SerializedName("items")
    @Expose
    private List<SearchItem> items;

    public List<SearchItem> getItems() {
        return items;
    }

    public void setItems(List<SearchItem> items) {
        this.items = items;
    }
}
