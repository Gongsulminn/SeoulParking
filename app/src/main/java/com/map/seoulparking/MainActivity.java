package com.map.seoulparking;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.PointF;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.map.seoulparking.databinding.ActivityMainBinding;
import com.map.seoulparking.dialog.DetailDialog;
import com.map.seoulparking.model.ParkModel;
import com.map.seoulparking.sqlite.AppDataBase;
import com.map.seoulparking.sqlite.FavoritePark;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.LatLngBounds;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationSource;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.LocationOverlay;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.FusedLocationSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;

    private static final String PARKMODEL = "PARKMODEL";
    private FusedLocationSource locationSource;

    private int maxZIndex;
    private OnLocationListener mOnLocationListener;
    private String TAG = "Main";
    private ArrayList<ParkModel> parkModels = new ArrayList<>();
    private BottomSheetBehavior mBottomSheetBehavior;
    private List<Marker> markers = new ArrayList<>();
    private int mBeforeFocusMarker = -1;
    private ActivityMainBinding binding;
    private AppDataBase appDataBase;
    private FavoritePark favoritePark;
    private ParkModel detailPark;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private double latitude;
    private double longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this , R.layout.activity_main );
        appDataBase = AppDataBase.getInstance(this);

        mBottomSheetBehavior = BottomSheetBehavior.from(binding.mainBottomLayout);
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

        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.map, mapFragment).commit();
        }

        mapFragment.getMapAsync(this);

        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

        if (getIntent().getBooleanExtra("locationBoolean", true)) {
            Location userLocation = getMyLocation();
            if (userLocation != null) {
                // TODO 위치를 처음 얻어왔을 때 하고 싶은 것
                latitude = userLocation.getLatitude();
                longitude = userLocation.getLongitude();
                Log.e("현재위치", "" + latitude + longitude);
            }
        } else {
            latitude = 37.5670135;
            longitude = 126.9783740;
        }

        binding.mainBottomFavoritepark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                    boolean isFavorite = false;
                        if (appDataBase.parkDao().isFavorite(favoritePark.getParkingCode()) == 1 ){
                            appDataBase.parkDao().deleteData(favoritePark);
                            isFavorite = false;
                        }
                        else{
                            appDataBase.parkDao().insertData(favoritePark);
                            isFavorite = true;
                        }

                        final boolean finalIsFavorite = isFavorite;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            if (finalIsFavorite)
                                binding.setIsFavorite(1);
                            else
                                binding.setIsFavorite(0);
                            }
                        });
                    }
                }).start();
            }
        });

        binding.mainBottomDetailpark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailDialog detailDialog = new DetailDialog();
                Bundle detailDialogBundle = new Bundle();
                detailDialogBundle.putParcelable(PARKMODEL ,detailPark);
                detailDialog.setArguments(detailDialogBundle);
                detailDialog.show(getSupportFragmentManager() , "detailDialog");
            }
        });

        binding.mainBottomCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_DIAL , Uri.parse("tel:/" + detailPark.getTel())));
            }
        });

        binding.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        binding.mainFavoritelist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this , FavoriteActivity.class));
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Log.e("넘어온값", data.getStringExtra("address"));
            binding.address.setText(data.getStringExtra("address"));

            String location = data.getStringExtra("address");
            List<Address> addressList = null;

            if (location != null || !location.equals("")) {
                Geocoder geocoder = new Geocoder(MainActivity.this, Locale.KOREA);
                try {
                    addressList = geocoder.getFromLocationName(location, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Address address = addressList.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                mOnLocationListener.setLocation(latLng.latitude, latLng.longitude);
            }
        }
    }

    public void setOnLocationListener(OnLocationListener mOnLocationListener) {
        this.mOnLocationListener = mOnLocationListener;
    }
    public interface OnLocationListener {
        void setLocation(double latitude, double longitude);
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
    public void onMapReady(@NonNull final NaverMap naverMap) {
        naverMap.setLocationSource(locationSource);
        naverMap.setOnMapClickListener(mapClickListener);
        naverMap.setMinZoom(7.0);
       // naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setLocationButtonEnabled(true);

        LatLngBounds bounds = naverMap.getContentBounds();

        Log.e("확인", String.valueOf(naverMap.getContentBounds()));

        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(latitude, longitude));
        naverMap.moveCamera(cameraUpdate);

        LocationOverlay locationOverlay = naverMap.getLocationOverlay();
        locationOverlay.setVisible(true);
        locationOverlay.setPosition(new LatLng(latitude, longitude));

        for (int i = 0; i < parkModels.size(); ++i) {
            final Marker marker = new Marker();
            marker.setIcon(OverlayImage.fromResource(R.drawable.clickpoff));
            marker.setPosition(new LatLng(
                    Double.parseDouble(parkModels.get(i).getLat()),
                    Double.parseDouble(parkModels.get(i).getLng())
            ));
            //marker.setCaptionText("마커" + i);
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

        setOnLocationListener(new OnLocationListener() {
            @Override
            public void setLocation(double latitude, double longitude) {
                Log.e("인터페이스", latitude + "");
                Log.e("인터페이스", longitude + "");
                CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(latitude, longitude));
                naverMap.moveCamera(cameraUpdate);
            }
        });

        naverMap.addOnCameraChangeListener(new NaverMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(int reason, boolean b) {
                CameraPosition cameraPosition = naverMap.getCameraPosition();
                Log.e(TAG, "onCameraChange: " + cameraPosition.target.latitude  + " / " + cameraPosition.target.longitude + " / " + cameraPosition.zoom + " / " + cameraPosition.tilt );
            }
        });

        naverMap.addOnCameraIdleListener(new NaverMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                CameraPosition cameraPosition = naverMap.getCameraPosition();
                binding.address.setText(findAddress(cameraPosition.target.latitude, cameraPosition.target.longitude));
                if (cameraPosition.zoom < 10 ){
                    for (Marker marker : markers){
                        marker.setVisible(true);
                    }
                } else {
                    for (Marker marker : markers){
                        Location locationCamera = new Location("CAMERA");
                        locationCamera.setLatitude(cameraPosition.target.latitude);
                        locationCamera.setLongitude(cameraPosition.target.longitude);

                        Location locationMarker = new Location("makrger");
                        locationMarker.setLatitude(marker.getPosition().latitude);
                        locationMarker.setLongitude(marker.getPosition().longitude);

                        if (locationCamera.distanceTo(locationMarker) > 3000)
                            marker.setVisible(false);
                        else
                            marker.setVisible(true);
                    }
                }
//                Location locationCamera = new Location("CAMERA");
//                locationCamera.setLatitude(cameraPosition.target.latitude);
//                locationCamera.setLongitude(cameraPosition.target.longitude);
//
//                for (Marker marker : markers){
//
//                    Location locationMarker = new Location("makrger");
//                    locationMarker.setLatitude(marker.getPosition().latitude);
//                    locationMarker.setLongitude(marker.getPosition().longitude);
//[
//                    if (locationCamera.distanceTo(locationMarker) > 3000)
//                        marker.setVisible(false);
//                    else
//                        marker.setVisible(true);
//                }

                System.out.println("onCameraChange: " + cameraPosition.target.latitude  + " / " + cameraPosition.target.longitude + " / " + cameraPosition.zoom + " / " + cameraPosition.tilt);
            }
        });
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
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

            detailPark = parkModels.get((Integer)overlay.getTag());
            binding.setParkmodel(detailPark);
            favoritePark = new FavoritePark(detailPark.getParkingCode(),detailPark.getParkingName(),detailPark.getTel(),detailPark.getAddr());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    final int isFavoirte = appDataBase.parkDao().isFavorite(detailPark.getParkingCode());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                                binding.setIsFavorite(isFavoirte);
                        }
                    });
                }
            }).start();

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
    private Location getMyLocation() {
        Location currentLocation = null;

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        // Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

            // 수동으로 위치 구하기
            String locationProvider = LocationManager.GPS_PROVIDER;
            currentLocation = locationManager.getLastKnownLocation(locationProvider);
            if (currentLocation != null) {
                double lng = currentLocation.getLatitude();
                double lat = currentLocation.getLatitude();
                Log.d("Main", "longtitude=" + lng + ", latitude=" + lat);
            }
        }
        return currentLocation;
    }


    private String findAddress(double lat, double lng) {
        StringBuffer bf = new StringBuffer();
        Geocoder geocoder = new Geocoder(this);
        List<Address> address;
        try {
            if (geocoder != null) {
                // 세번째 인수는 최대결과값인데 하나만 리턴받도록 설정했다
                address = geocoder.getFromLocation(lat, lng, 1);
                // 설정한 데이터로 주소가 리턴된 데이터가 있으면
                if (address != null && address.size() > 0) {
                    // 주소
                    String currentLocationAddress = address.get(0).getAddressLine(0).toString();

                    // 전송할 주소 데이터 (위도/경도 포함 편집)
//                    bf.append(currentLocationAddress).append("#");
//                    bf.append(lat).append("#");
//                    bf.append(lng);
                    bf.append(currentLocationAddress);
                }
            }

        } catch (IOException e) {
            Toast.makeText(this, "주소취득 실패", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        return bf.toString();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED ){
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            markers.get(mBeforeFocusMarker).setIcon(OverlayImage.fromResource(R.drawable.clickpoff));
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("서울 공영 주차 정보 ")
                .setMessage("앱을 종료하시겠습니까?")
                .setPositiveButton("예",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                .setNegativeButton("아니요",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }
                );
        builder.show();

    }
}