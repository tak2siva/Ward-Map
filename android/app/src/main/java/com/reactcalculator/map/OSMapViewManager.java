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

import java.io.InputStream;


public class OSMapViewManager extends SimpleViewManager<OSMapView>{
    public static final String REACT_CLASS = "OSMapView";

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
        IMapController mapController = mapView.getController();
        mapController.setZoom(9);
        GeoPoint startPoint = new GeoPoint(13.082680, 80.270718);
        System.out.println("------------------- Settin Center --------------------------");
        mapController.setCenter(startPoint);
    }

    public OSMapView createMapView(ThemedReactContext reactContext) {
        OSMapView mapView = new OSMapView(reactContext);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);
        return mapView;
    }

    public void addKMLOverLay(OSMapView mapView, ThemedReactContext reactContext) {
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

    @ReactProp(name = "randomKey")
    public void setRandomKey(OSMapView mapView, double value) {
        IMapController mapController = mapView.getController();
        GeoPoint geoPoint = new GeoPoint(mapView.latitude, mapView.longitude);
        mapController.setCenter(geoPoint);
    }

}
