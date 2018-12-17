package com.map.seoulparking;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.LatLngBounds;
import com.naver.maps.map.LocationSource;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.util.FusedLocationSource;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;

    private FusedLocationSource locationSource;

    private int maxZIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.map, mapFragment).commit();
        }

        mapFragment.getMapAsync(this);

        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

        startActivity(new Intent(this , SubActivity.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationSource = null;
    }

    @UiThread
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        naverMap.setLocationSource(locationSource);

        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setLocationButtonEnabled(true);

//        Marker marker = new Marker();
//        marker.setPosition(new LatLng(37.5326096, 126.8290307));
////        marker.setIcon(OverlayImage.fromResource(R.drawable.marker_icon));
////        marker.setWidth(50);
////        marker.setHeight(80);
////        marker.setWidth(Marker.SIZE_AUTO);
////        marker.setHeight(Marker.SIZE_AUTO);
//        marker.setMap(naverMap);

        List<Marker> markers = new ArrayList<>();
        LatLngBounds bounds = naverMap.getContentBounds();

        Log.e("확인", String.valueOf(naverMap.getContentBounds()));

        for (int i = 0; i < 10; ++i) {
            Marker marker = new Marker();
            marker.setPosition(new LatLng(
                    (bounds.getNorthLatitude() - bounds.getSouthLatitude()) * Math.random() + bounds.getSouthLatitude(),
                    (bounds.getEastLongitude() - bounds.getWestLongitude()) * Math.random() + bounds.getWestLongitude()
            ));
            marker.setCaptionText("마커" + i);
            marker.setOnClickListener(new Overlay.OnClickListener() {
                @Override
                public boolean onClick(@NonNull Overlay overlay) {
                    Log.e("클릭", overlay.getZIndex() + "");
                    Log.e("클릭", overlay.getTag() + "");
                    overlay.setZIndex(++maxZIndex);
                    return true;
                }
            });
            marker.setMap(naverMap);
            markers.add(marker);
        }
    }

    LocationSource.OnLocationChangedListener onLocationChangedListener = new LocationSource.OnLocationChangedListener() {
        @Override
        public void onLocationChanged(@Nullable Location location) {
        }
    };
}