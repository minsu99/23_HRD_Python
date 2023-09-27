package com.example.map;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

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

        // 서울의 위치 설정
        LatLng seoulLocation = new LatLng(37.554891, 126.970814);

        // 서울 중심으로부터 멀지 않은 골고루 분포된 쓰레기장 위치 설정
        LatLng garbageDumpLocation1 = new LatLng(37.558123, 126.970430); // 서울에서 조금 북쪽
        LatLng garbageDumpLocation2 = new LatLng(37.549352, 126.968078); // 서울에서 조금 남쪽
        LatLng garbageDumpLocation3 = new LatLng(37.55361, 126.9729); // 서울에서 조금 동쪽
        LatLng garbageDumpLocation4 = new LatLng(37.555792, 126.965759); // 서울에서 조금 서쪽
        LatLng garbageDumpLocation5 = new LatLng(37.554023, 126.965839); // 서울에서 조금 북쪽
        LatLng garbageDumpLocation6 = new LatLng(37.55366, 126.9731); // 서울에서 조금 남쪽





        // 지도 타입을 설정합니다.
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // 카메라를 서울 위치로 이동합니다.
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoulLocation, 15F));

        // 서울 위치에 파란색 아이콘 설정
        mGoogleMap.addMarker(new MarkerOptions()
                .position(seoulLocation)
                .title("서울")
                .snippet("대한민국의 수도")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));


        // 쓰레기장 위치에 마커를 추가합니다.
        // 쓰레기장 위치에 빨간색 아이콘 설정
        mGoogleMap.addMarker(new MarkerOptions()
                .position(garbageDumpLocation1)
                .title("쓰레기장 위치1")
                .snippet("분리수거 위치")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        mGoogleMap.addMarker(new MarkerOptions()
                .position(garbageDumpLocation2)
                .title("쓰레기장 위치2")
                .snippet("분리수거 위치")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        mGoogleMap.addMarker(new MarkerOptions()
                .position(garbageDumpLocation3)
                .title("쓰레기장 위치3")
                .snippet("분리수거 위치")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        mGoogleMap.addMarker(new MarkerOptions()
                .position(garbageDumpLocation4)
                .title("쓰레기장 위치4")
                .snippet("분리수거 위치")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        mGoogleMap.addMarker(new MarkerOptions()
                .position(garbageDumpLocation5)
                .title("쓰레기장 위치5")
                .snippet("분리수거 위치")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        mGoogleMap.addMarker(new MarkerOptions()
                .position(garbageDumpLocation6)
                .title("쓰레기장 위치6")
                .snippet("분리수거 위치")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
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
