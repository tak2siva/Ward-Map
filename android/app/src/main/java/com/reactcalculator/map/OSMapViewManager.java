package com.reactcalculator.map;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import org.osmdroid.api.IMapController;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;


public class OSMapViewManager extends SimpleViewManager<MapView>{
    public static final String REACT_CLASS = "MapView";

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected MapView createViewInstance(ThemedReactContext reactContext) {
        MapView mapView = new MapView(reactContext);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);
        IMapController mapController = mapView.getController();
        mapController.setZoom(9);
        GeoPoint startPoint = new GeoPoint(13.082680, 80.270718);
        mapController.setCenter(startPoint);
        return mapView;
    }
}
