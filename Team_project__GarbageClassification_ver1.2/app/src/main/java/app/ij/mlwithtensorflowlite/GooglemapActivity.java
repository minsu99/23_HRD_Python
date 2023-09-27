package app.ij.mlwithtensorflowlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

public class GooglemapActivity extends AppCompatActivity implements OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback {

    private GoogleMap mMap;
    private Marker currentMarker = null;

    private static final String TAG = "googlemap";
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int UPDATE_INTERVAL_MS = 1000; // 1초
    private static final int FASTEST_UPDATE_INTERVAL_MS = 500; // 0.5초

    // OnRequestPermissionsResultCallback에서 수신된 결과에서 ActivityCompat.OnRequestPermissionsResultCallback를 사용한 퍼미션 요청을 구별하기 위함
    private static final int PERMISSION_REQUEST_CODE = 100;
    boolean needRequest = false;
    private boolean isMapMovedByUser = false;

    // 앱을 실행하기 위해 필요한 퍼미션 정의
    String[] REQUIRED_PERMISSION = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    Location mCurrentLocation;

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationRequest; // 주의
    private Location location;

    private View mLayout; // snackbar 사용하기 위함.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.googlemap);

        locationRequest = new LocationRequest()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL_MS)
                .setFastestInterval(FASTEST_UPDATE_INTERVAL_MS);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();

        builder.addLocationRequest(locationRequest);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        Log.d(TAG, "onMapReady: 들어옴 ");
        mMap = googleMap;

        // 지도의 초기위치 이동
        setDefaultLocation();

        // 서울의 쓰레기장 좌표 (예시)
        double seoulGarbageLat = 37.55;
        double seoulGarbageLng = 126.98;

        // 대전대학교 근처 쓰레기장 좌표 (예시)
        double daejeonGarbageLat = 36.3488;
        double daejeonGarbageLng = 127.3681;

        // 마커의 제목과 설명을 설정합니다
        String seoulGarbageTitle = "서울 쓰레기장 위치";
        String seoulGarbageSnippet = "이곳은 서울의 쓰레기장입니다";

        String daejeonGarbageTitle = "대전대학교 쓰레기장 위치";
        String daejeonGarbageSnippet = "이곳은 대전대학교 근처의 쓰레기장입니다";

        // 마커 옵션을 생성하고 마커를 추가합니다
        MarkerOptions seoulGarbageMarkerOptions = new MarkerOptions()
                .position(new LatLng(seoulGarbageLat, seoulGarbageLng))
                .title(seoulGarbageTitle)
                .snippet(seoulGarbageSnippet)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)); // 마커 아이콘 색상을 빨간색으로 설정

        MarkerOptions daejeonGarbageMarkerOptions = new MarkerOptions()
                .position(new LatLng(daejeonGarbageLat, daejeonGarbageLng))
                .title(daejeonGarbageTitle)
                .snippet(daejeonGarbageSnippet)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)); // 마커 아이콘 색상을 초록색으로 설정

        // 마커를 지도에 추가합니다
        mMap.addMarker(seoulGarbageMarkerOptions);
        mMap.addMarker(daejeonGarbageMarkerOptions);

        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                Log.d(TAG, "onMapClick: ");
            }
        });

        mMap.setOnCameraMoveStartedListener(reason -> {
            if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
                isMapMovedByUser = true;
            }
        });

        // 현재 위치 업데이트를 시작합니다
        startLocationUpdates();

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker.getTitle().equals("서울 쓰레기장 위치") || marker.getTitle().equals("대전대학교 쓰레기장 위치")) {
                    // 해당 쓰레기장 마커를 클릭했을 때
                    calculateDistanceToGarbage(marker.getPosition()); // 거리 계산 메서드 호출
                    return true; // 이벤트 처리 완료
                }
                return false; // 기타 마커 클릭 시 기본 동작을 유지
            }
        });
    }


    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            super.onLocationResult(locationResult);

            List<Location> locationList = locationResult.getLocations();

            if (locationList.size() > 0) {
                location = locationList.get(locationList.size() - 1);

                mCurrentLocation = location; // mCurrentLocation 초기화

                // 현재 위치에 마커 생성하고 이동
                if (!isMapMovedByUser) { // 추가: 사용자가 지도를 움직인 경우에만 카메라 이동
                    setCurrentLocation(location, "현재 위치", "이곳은 현재 위치입니다");
                }
            }
        }
    };


    private void setCurrentLocation(Location location, String markerTitle, String markerSnippet) {
        if (currentMarker != null) {
            currentMarker.remove();
        }

        LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentLatLng);
        markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.draggable(true);

        currentMarker = mMap.addMarker(markerOptions);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(currentLatLng);
        mMap.moveCamera(cameraUpdate);
    }



    private void startLocationUpdates() {
        if (!checkLocationServicesStatus()) {
            showDiologForLocationServiceSetting();

        } else {
            int hasFineLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

            if (hasFineLocationPermission != PackageManager.PERMISSION_GRANTED || hasCoarseLocationPermission != PackageManager.PERMISSION_GRANTED) {

                Log.d(TAG, "startLocationUpdates: 퍼미션 없음");
                return;
            }

            mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
            if (checkPermission()) {
                //mMap.setMyLocationEnabled(true);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "onStart: ");

        if (checkPermission()) {
            mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);

            if (mMap != null) {
                //mMap.setMyLocationEnabled(true);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    private boolean checkPermission() {
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        if (hasFineLocationPermission != PackageManager.PERMISSION_GRANTED || hasCoarseLocationPermission != PackageManager.PERMISSION_GRANTED) {

            Log.d(TAG, "startLocationUpdates: 퍼미션 없음");
            return true;
        }

        return false;
    }

    private void showDiologForLocationServiceSetting() {
        AlertDialog.Builder builder = new AlertDialog.Builder(GooglemapActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다. 위치설정을 수정하시겠습니까?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent callGPSSettingIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // GPS를 활성화하지 않는 경우, 디폴트 위치를 서울로 설정
                setDefaultLocation();
                dialogInterface.cancel();
            }
        });
        builder.create().show();
    }

    private boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void setDefaultLocation() {
        // 기본 위치를 서울의 좌표로 설정
        LatLng DEFAULT_LOCATION = new LatLng(37.5665, 126.9780);
        String markerTitle = "현재 위치"; // 현재 위치의 제목
        String markerSnippet = "이곳은 현재 위치입니다"; // 현재 위치의 설명

        if (currentMarker != null) {
            currentMarker.remove();
        }

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(DEFAULT_LOCATION);
        markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.draggable(true);
        currentMarker = mMap.addMarker(markerOptions);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 15);
        mMap.moveCamera(cameraUpdate);
    }
    private void calculateDistanceToGarbage(LatLng garbageLatLng) {
        if (mCurrentLocation == null) {
            // 현재 위치 정보가 없을 경우 처리 (예: 서울로 가정)
            Toast.makeText(this, "현재 위치 정보를 가져올 수 없습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        Location garbageLocation = new Location("Garbage");
        garbageLocation.setLatitude(garbageLatLng.latitude);
        garbageLocation.setLongitude(garbageLatLng.longitude);

        float distance = mCurrentLocation.distanceTo(garbageLocation) / 1000; // 미터에서 킬로미터로 변환

        String distanceText = String.format(Locale.getDefault(), "%.2f Km", distance);

        // 거리 정보를 다이얼로그로 표시
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("쓰레기장까지의 거리");
        builder.setMessage("거리: " + distanceText);
        builder.setPositiveButton("닫기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }






}