package com.map.seoulparking;

import android.graphics.PointF;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.map.seoulparking.model.ParkModel1;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.LatLngBounds;
import com.naver.maps.map.LocationSource;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.FusedLocationSource;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;

    private FusedLocationSource locationSource;

    private int maxZIndex;

    private String TAG = "Main";
    private ArrayList<ParkModel1> parkModels = new ArrayList<>();
    private BottomSheetBehavior mBottomSheetBehavior;
    private View mainBottomView;
    private TextView mainBottomParkName;
    private List<Marker> markers = new ArrayList<>();
    private int mBeforeFocusMarker = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainBottomView = findViewById(R.id.main_bottom_layout);
        mainBottomParkName = findViewById(R.id.main_bottom_parkname);
        mBottomSheetBehavior = BottomSheetBehavior.from(mainBottomView);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int newState) {
                switch (newState){
                    case BottomSheetBehavior.STATE_EXPANDED:
                        Log.e(TAG, "onStateChanged: STATE_EXPANDED"  );
                        break;
                    default:
                        Log.e(TAG, "onStateChanged: STATE_HIDDEN"  );
                        break;
                }
            }
            @Override
            public void onSlide(@NonNull View view, float v) {
            }
        });

        parkModels = getIntent().getParcelableArrayListExtra("data");
        Log.e(TAG, "onCreate: " + parkModels.size() );

        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.map, mapFragment).commit();
        }

        mapFragment.getMapAsync(this);

        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

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
        naverMap.setOnMapClickListener(mapClickListener);
        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setLocationButtonEnabled(true);

        LatLngBounds bounds = naverMap.getContentBounds();

        Log.e("확인", String.valueOf(naverMap.getContentBounds()));

        for (int i = 0; i < parkModels.size(); ++i) {
            final Marker marker = new Marker();
            marker.setIcon(OverlayImage.fromResource(R.drawable.clickpoff));
            marker.setPosition(new LatLng(
                    Double.parseDouble(parkModels.get(i).getLat()),
                    Double.parseDouble(parkModels.get(i).getLng())
            ));
            marker.setCaptionText("마커" + i);
            marker.setTag(i);
            marker.setOnClickListener(markerClickListener);
/*
            marker.setOnClickListener(new Overlay.OnClickListener() {
                @Override
                public boolean onClick(@NonNull Overlay overlay) {
                    //overlay.setZIndex(++maxZIndex);
                    //BottomDialog  bottomDialog = BottomDialog.newInstance();
                    // bottomDialog.show(getSupportFragmentManager() , "id");
//                    switch (mBottomSheetBehavior.getState()){
//                        case  BottomSheetBehavior.STATE_HIDDEN:
//                            Log.e(TAG, "mBottomSheetBehavior.getState: STATE_HIDDEN"  );
//                            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//                            break;
//                        case BottomSheetBehavior.STATE_EXPANDED:
//                            Log.e(TAG, "mBottomSheetBehavior.getState: STATE_EXPANDED"  );
//                            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
//                            break;
//                        default:
//                            Log.e(TAG, "default.getState: " + mBottomSheetBehavior.getState()  );
//                            break;
//                    }
                    marker.setIcon(marker.getIcon() == OverlayImage.fromResource(R.drawable.parkclickon) ? OverlayImage.fromResource(R.drawable.parkclickoff) : OverlayImage.fromResource(R.drawable.parkclickon));
                    mainBottomParkName.setText(parkModels.get(finalI).getParkingName());
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    return true;
                }
            });*/
            marker.setMap(naverMap);
            markers.add(marker);
        }
    }

    LocationSource.OnLocationChangedListener onLocationChangedListener = new LocationSource.OnLocationChangedListener() {
        @Override
        public void onLocationChanged(@Nullable Location location) {
        }
    };

    Overlay.OnClickListener markerClickListener = new Overlay.OnClickListener() {
        @Override
        public boolean onClick(@NonNull Overlay overlay ) {
            Log.e(TAG, "onClick: OVERLAY" );

            if (mBeforeFocusMarker != -1){
                markers.get(mBeforeFocusMarker).setIcon(OverlayImage.fromResource(R.drawable.clickpoff));
            }

            Marker marker = markers.get((Integer) overlay.getTag());
            marker.setIcon(marker.getIcon() == OverlayImage.fromResource(R.drawable.clickpon2424) ? OverlayImage.fromResource(R.drawable.clickpoff) : OverlayImage.fromResource(R.drawable.clickpon2424));
            mainBottomParkName.setText(parkModels.get((Integer) overlay.getTag()).getParkingName());
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            mBeforeFocusMarker = (int) overlay.getTag();
            return true;
        }
    };

    NaverMap.OnMapClickListener mapClickListener  = new NaverMap.OnMapClickListener() {
        @Override
        public void onMapClick(@NonNull PointF pointF, @NonNull LatLng latLng) {

            if (mBeforeFocusMarker != -1){
                markers.get(mBeforeFocusMarker).setIcon(OverlayImage.fromResource(R.drawable.clickpoff));
            }

            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    };
}
