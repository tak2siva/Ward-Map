package com.wardmap.map.react;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.wardmap.map.components.OSMapView;
import com.wardmap.map.overlays.EventsOverlay;
import com.wardmap.map.overlays.KMLOverlay;
import com.wardmap.map.overlays.MarkerOverlay;
import com.wardmap.map.overlays.PositionHelper;

import org.osmdroid.api.IMapController;
import org.osmdroid.util.GeoPoint;


public class OSMapViewManager extends SimpleViewManager<OSMapView>{
    private static final String REACT_CLASS = "OSMapView";

    private JSEventBus jsEventBus;
    private KMLOverlay kmlOverlay = new KMLOverlay();
    private MarkerOverlay markerOverlay = new MarkerOverlay();
    private PositionHelper positionHelper = new PositionHelper();
    private EventsOverlay eventsOverlay = new EventsOverlay();

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected OSMapView createViewInstance(ThemedReactContext reactContext) {
        jsEventBus = new JSEventBus(reactContext);
        OSMapView mapView = createMapView(reactContext);

        kmlOverlay.draw(mapView, reactContext);
        markerOverlay.createMarker(mapView);
        positionHelper.setDefaultCenter(mapView);
        positionHelper.setZoom(mapView, 15);
        eventsOverlay.initEventReceiver(mapView, reactContext, jsEventBus);
        return mapView;
    }

    @ReactProp(name = "enableMarker")
    public void setEnableMarker(OSMapView mapView, boolean enableMarker) {
        System.out.println("========IN set Enable Marker+++++++++++++++++++");
        if (mapView.getEnableMarker() == enableMarker) {
            return;
        }
        mapView.setEnableMarker(enableMarker);

        if (enableMarker) {
            markerOverlay.resetMarker(mapView);
            positionHelper.setCenter(mapView, mapView.getUserLocation());
        } else {
            markerOverlay.deleteExistingMarker(mapView);
        }
    }

    @ReactProp(name = "randomKey")
    public void setRandomKey(OSMapView mapView, double value) {
        System.out.println("==========called set random==============");
        IMapController mapController = mapView.getController();
        mapController.setCenter(mapView.getUserLocation());
        mapController.setZoom(14);
        setEnableMarker(mapView, true);
    }

    @ReactProp(name = "userLocation")
    public void setUserLocation(OSMapView mapView, ReadableMap value) {
        System.out.println("==========set user location==============");
        try {
            double latitude = value.getDouble("latitude");
            double longitude = value.getDouble("longitude");
            GeoPoint geoPoint = new GeoPoint(latitude, longitude);
            mapView.setUserLocation(geoPoint);
            markerOverlay.resetMarker(mapView);
            System.out.println(geoPoint);
        } catch (Exception e) {
            throw new RuntimeException("Error updating user location", e);
        }
    }

    private OSMapView createMapView(ThemedReactContext reactContext) {
        OSMapView mapView = new OSMapView(reactContext);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);
        return mapView;
    }
}
