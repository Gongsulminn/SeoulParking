package com.map.seoulparking;

import com.map.seoulparking.model.Model;
import com.map.seoulparking.model.ParkModel1;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by user on 2018-12-06.
 */

public interface ParkRetrofit {
    String URL  = "https://i533ffgfz5.execute-api.ap-northeast-2.amazonaws.com/gong_park/park/parkinfo/all";
    String AWS_URL = "https://i533ffgfz5.execute-api.ap-northeast-2.amazonaws.com/gong_park/";

    @GET("/json/GetParkInfo/{startRow}/{endRow}/")
    Call<ResponseBody> getData(
            @Path("startRow") String startRow,
            @Path("endRow") String endRow);

    @GET("park/parkinfo/all")
    Call<List<Model>> getParkAllData();

    @GET("park/parkinfo/used")
    Call<List<ParkModel1>> getParkUsedData();
}
