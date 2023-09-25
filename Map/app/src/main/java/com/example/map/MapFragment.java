package com.example.map;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private MapView mMapView;
    private GoogleMap mGoogleMap;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_layout, container, false);

        // MapView 초기화
        mMapView = view.findViewById(R.id.map_view);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
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
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}

