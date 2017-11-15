package com.wardmap.google;

import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.data.kml.KmlLayer;
import com.google.maps.android.data.kml.KmlPlacemark;
import com.wardmap.R;
import com.wardmap.map.react.JSEventBus;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class GMapViewManager extends SimpleViewManager<GMapView>
        implements OnMapReadyCallback, LifecycleEventListener {

    private GMapView gMapView;
    private ThemedReactContext reactContext;
    private JSEventBus jsEventBus;

    @Override
    public String getName() {
        return "GoogleMapView";
    }

    @Override
    protected GMapView createViewInstance(ThemedReactContext reactContext) {
        gMapView = new GMapView(reactContext);
        jsEventBus = new JSEventBus(reactContext);
        this.reactContext = reactContext;
        gMapView.getMapAsync(this);
        gMapView.onCreate(null);

        reactContext.addLifecycleEventListener(this);
        return gMapView;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        System.out.println("=============== Initialized google maps ===================");
        gMapView.setGoogleMap(googleMap);
        googleMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(13.082680, 80.270718) , 14.0f) );
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        final KmlLayer kmlLayer = createKmlLayer(googleMap);
        gMapView.setKmlLayer(kmlLayer);

        GoogleMap.OnMapLongClickListener onMapLongClickListener = new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                findKmlPlaceMarkAndResetMarker(latLng);
            }
        };

        googleMap.setOnMapLongClickListener(onMapLongClickListener);
    }

    private void findKmlPlaceMarkAndResetMarker(LatLng latLng) {
        KmlPlacemark kmlPlacemark1 = gMapView.containsInAnyPolygon(latLng);
        if (kmlPlacemark1 != null) {
            resetMarker(latLng, kmlPlacemark1);
            String wardNo = kmlPlacemark1.getProperty("name");
            jsEventBus.sendEvent("ClickMarker", wardNo);
        } else {
            jsEventBus.sendEvent("ClickMarker", null);
            System.out.println("KML Not found");
        }
    }

    private void resetMarker(LatLng latLng, KmlPlacemark kmlPlacemark) {
        for (Marker m : gMapView.getMarkers()) {
            m.remove();
        }
        gMapView.getMarkers().clear();

        System.out.println("Clicked " + kmlPlacemark.getProperty("name"));
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title(kmlPlacemark.getProperty("name"))
                .snippet(kmlPlacemark.getProperty("ZONE_NAME"));

        Marker locationMarker = gMapView.getGoogleMap().addMarker(markerOptions);
        locationMarker.showInfoWindow();
        gMapView.getMarkers().add(locationMarker);
    }

    private KmlLayer createKmlLayer(GoogleMap googleMap) {
        KmlLayer layer = null;
        try {
            layer = new KmlLayer(googleMap, R.raw.chennai_wards, reactContext);
            layer.addLayerToMap();
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        return layer;
    }

    @Override
    public void onHostResume() {
        gMapView.onResume();
    }

    @Override
    public void onHostPause() {
        gMapView.onPause();
    }

    @Override
    public void onHostDestroy() {
        gMapView.onDestroy();
    }

    @ReactProp(name = "userLocation")
    public void setMarkerToMyLocation(GMapView gMapView, ReadableMap map) {
        System.out.println("==========set user location==============");
        if (map == null || gMapView == null) {
            return;
        }

        try {
            double latitude = map.getDouble("latitude");
            double longitude = map.getDouble("longitude");
            LatLng latLng = new LatLng(latitude, longitude);
            gMapView.setUserLocation(latLng);
            findKmlPlaceMarkAndResetMarker(latLng);
        } catch (Exception e) {
            throw new RuntimeException("Error updating user location", e);
        }
    }

    @ReactProp(name = "randomKey")
    public void setRandomKey(GMapView gMapView, double value) {
        System.out.println("========== called set random==============");
        if (gMapView == null || gMapView.getUserLocation() == null) {
            return;
        }
        findKmlPlaceMarkAndResetMarker(gMapView.getUserLocation());
    }
}
