package com.reactcalculator.map;

import android.content.res.Resources;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.reactcalculator.R;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.kml.KmlDocument;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.FolderOverlay;
import org.osmdroid.views.overlay.Marker;

import java.io.InputStream;
import java.io.PipedReader;


public class OSMapViewManager extends SimpleViewManager<OSMapView>{
    public static final String REACT_CLASS = "OSMapView";
    public static final double chennaiLatitude = 13.082680;
    public static final double chennaiLongitude = 80.270718;


    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected OSMapView createViewInstance(ThemedReactContext reactContext) {
        OSMapView mapView = createMapView(reactContext);
        addKMLOverLay(mapView, reactContext);
        setCenter(mapView);
        return mapView;
    }

    private void setCenter(OSMapView mapView) {
        setCenter(mapView, chennaiLatitude, chennaiLongitude);
    }

    private void setCenter(OSMapView mapView, double latitude, double longitude) {
        System.out.println("------------------- Setting Center --------------------------");
        GeoPoint startPoint = new GeoPoint(latitude, longitude);
        IMapController mapController = mapView.getController();
        mapController.setZoom(9);
        mapController.setCenter(startPoint);
    }

    private OSMapView createMapView(ThemedReactContext reactContext) {
        OSMapView mapView = new OSMapView(reactContext);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);
        return mapView;
    }

    private void addKMLOverLay(OSMapView mapView, ThemedReactContext reactContext) {
        Resources resources = reactContext.getResources();
        InputStream kmlInputStream = resources.openRawResource(R.raw.chennai_wards);

        KmlDocument kmlDocument = new KmlDocument();
        kmlDocument.parseKMLStream(kmlInputStream, null);
        FolderOverlay kmlOverlay = (FolderOverlay) kmlDocument.mKmlRoot.buildOverlay(mapView, null, null, kmlDocument);
        mapView.getOverlays().add(kmlOverlay);
        mapView.invalidate();
    }

    @ReactProp(name = "latitude")
    public void setLatitude(OSMapView mapView, double latitude) {
        System.out.println("------------ Updating lat -----------------------------");
        System.out.println(latitude);
        IMapController mapController = mapView.getController();
        GeoPoint geoPoint = new GeoPoint(latitude, mapView.longitude);
        mapController.setCenter(geoPoint);
        mapView.setLatitude(latitude);
    }

    @ReactProp(name = "longitude")
    public void setLongitude(OSMapView mapView, double longitude) {
        System.out.println("------------ Updating long -----------------------------");
        System.out.println(longitude);
        IMapController mapController = mapView.getController();
        GeoPoint geoPoint = new GeoPoint(mapView.latitude, longitude);
        mapController.setCenter(geoPoint);
        mapView.setLongitude(longitude);
    }


    @ReactProp(name = "enableMarker")
    public void setEnableMarker(OSMapView mapView, boolean enableMarker) {
        System.out.println("========IN set Enable Marker+++++++++++++++++++");
        mapView.setEnableMarker(enableMarker);
        Marker marker = createMarker(mapView);
        mapView.getOverlays().add(marker);
        setCenter(mapView, mapView.latitude, mapView.longitude);
    }

    private Marker createMarker(OSMapView mapView) {
        System.out.println("========= Creating marker=========");
        System.out.println("========"+mapView.latitude+"======"+mapView.longitude);
        GeoPoint geoPoint = new GeoPoint(mapView.latitude, mapView.longitude);
        Marker marker = new Marker(mapView);
        marker.setPosition(geoPoint);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        return marker;
    }

    @ReactProp(name = "randomKey")
    public void setRandomKey(OSMapView mapView, double value) {
        System.out.println("==========called set random==============");
        IMapController mapController = mapView.getController();
        GeoPoint geoPoint = new GeoPoint(mapView.latitude, mapView.longitude);
        mapController.setCenter(geoPoint);
        mapController.setZoom(14);
        setEnableMarker(mapView, true);
    }

}
