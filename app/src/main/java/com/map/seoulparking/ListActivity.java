package com.map.seoulparking;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.map.seoulparking.adapter.SearchResultAdapter;
import com.map.seoulparking.model.SearchModel;
import com.map.seoulparking.model.SearchResultModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity {

    EditText editQuery;
    TextView list_cancel;
    RecyclerView recycler;
    List<SearchResultModel> itemList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        editQuery = (EditText) findViewById(R.id.edit_query);
        list_cancel = (TextView) findViewById(R.id.list_cancel);
        recycler = (RecyclerView) findViewById(R.id.recycler);

        itemList = new ArrayList<>();

        list_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        editQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
                Log.e("beforeTextChanged", charSequence.toString());

                if (!charSequence.toString().equals("")) {
                    itemList.clear();
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                Log.e("onTextChanged", charSequence.toString());

                RetroApiProvider.provideSearchApi().getLocationModel(
                        ParkRetrofit.CLIENT_ID,
                        ParkRetrofit.CLIENT_SECRET_ID,
                        charSequence.toString(),
                        20
                ).enqueue(new Callback<SearchModel>() {
                    @Override
                    public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {
                        if (response.body() != null) {
                            for (int i = 0; i < response.body().getItems().size(); i++) {
                                SearchResultModel recyclerItem = new SearchResultModel();
                                recyclerItem.setTitle(Html.fromHtml(response.body().getItems().get(i).getTitle()).toString());
                                recyclerItem.setLocation(response.body().getItems().get(i).getAddress());
                                itemList.add(recyclerItem);
                            }

                            recycler.setAdapter(new SearchResultAdapter(ListActivity.this,itemList, R.layout.list_item));
                            recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recycler.setItemAnimator(new DefaultItemAnimator());
                        }else {
                            Log.e("테스트","null 일때");
                            itemList.clear();
                        }
                    }
                    @Override
                    public void onFailure(Call<SearchModel> call, Throwable t) {

                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.e("afterTextChanged", editable.toString());
            }
        });
    }
}