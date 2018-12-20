package com.map.seoulparking;

import com.map.seoulparking.model.ParkModel1;
import com.map.seoulparking.model.SearchModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by user on 2018-12-06.
 */

public interface ParkRetrofit {

    String SEARCH_URL = "https://openapi.naver.com/v1/search/";
    String CLIENT_ID = "4isDZgkA7H4P_GShId8h";
    String CLIENT_SECRET_ID = "sZnoL7M0Cg";
    String AWS_URL = "https://i533ffgfz5.execute-api.ap-northeast-2.amazonaws.com/gong_park/";
    String LOCATION_URL = "https://openapi.naver.com/v1/search/local.json?query=투썸 오류";

    @GET("/json/GetParkInfo/{startRow}/{endRow}/")
    Call<ResponseBody> getData(
            @Path("startRow") String startRow,
            @Path("endRow") String endRow);

//    @GET("park/parkinfo/all")
//    Call<List<Model>> getParkAllData();

    @GET("park/parkinfo/used")
    Call<List<ParkModel1>> getParkUsedData();

    @GET("local.json")
    Call<SearchModel> getLocationModel(
            @Header("X-Naver-Client-Id") String clientId,
            @Header("X-Naver-Client-Secret") String clientSecretId,
            @Query("query") String title);

}
