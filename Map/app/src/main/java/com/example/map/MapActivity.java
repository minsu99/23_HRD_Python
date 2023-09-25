package com.example.map;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mMapView;
    private GoogleMap mGoogleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // MapView 초기화
        mMapView = findViewById(R.id.map_view);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        // 서울의 위도와 경도를 설정합니다.
        LatLng seoul = new LatLng(37.554891, 126.970814);

        // 지도 타입을 설정합니다.
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // 카메라를 서울 위치로 이동합니다.
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul, 15F));

        // 서울에 마커를 추가합니다.
        mGoogleMap.addMarker(new MarkerOptions()
                .position(seoul)
                .title("서울")
                .snippet("대한민국의 수도"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
