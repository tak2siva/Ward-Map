package com.reactcalculator.map;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.api.IMapController;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.Marker;


public class OSMapViewManager extends SimpleViewManager<OSMapView>{
    public static final String REACT_CLASS = "OSMapView";

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected OSMapView createViewInstance(ThemedReactContext reactContext) {
        OSMapView mapView = new OSMapView(reactContext);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);
        IMapController mapController = mapView.getController();
        mapController.setZoom(9);
        GeoPoint startPoint = new GeoPoint(13.082680, 80.270718);
        mapController.setCenter(startPoint);
        return mapView;
    }

    @ReactProp(name = "latitude")
    public void setLatitude(OSMapView mapView, double latitude) {
        IGeoPoint mapCenter = mapView.getMapCenter();
        IMapController mapController = mapView.getController();
        GeoPoint geoPoint = new GeoPoint(latitude, mapCenter.getLongitude());
        mapController.setCenter(geoPoint);
        mapView.setLatitude(latitude);
    }

    @ReactProp(name = "longitude")
    public void setLongitude(OSMapView mapView, double longitude) {
        IGeoPoint mapCenter = mapView.getMapCenter();
        IMapController mapController = mapView.getController();
        GeoPoint geoPoint = new GeoPoint(mapCenter.getLatitude(), longitude);
        mapController.setCenter(geoPoint);
        mapView.setLongitude(longitude);
    }

    @ReactProp(name = "enableMarker")
    public void setEnableMarker(OSMapView mapView, boolean enableMarker) {
        mapView.setEnableMarker(enableMarker);
        Marker marker = new Marker(mapView);
        IGeoPoint mapCenter = mapView.getMapCenter();
        GeoPoint geoPoint = new GeoPoint(mapCenter.getLatitude(), mapCenter.getLongitude());
        marker.setPosition(geoPoint);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        mapView.getOverlays().add(marker);
    }

    @ReactProp(name = "randomKey")
    public void setRandomKey(OSMapView mapView, double value) {
        IMapController mapController = mapView.getController();
        GeoPoint geoPoint = new GeoPoint(mapView.latitude, mapView.longitude);
        mapController.setCenter(geoPoint);
    }

}
