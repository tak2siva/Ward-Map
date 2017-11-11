package com.reactcalculator.map;

import android.content.res.Resources;

import com.facebook.react.bridge.ReadableMap;
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


public class OSMapViewManager extends SimpleViewManager<OSMapView>{
    public static final String REACT_CLASS = "OSMapView";
    public static final GeoPoint chennaiGeoPoint = new GeoPoint(13.082680, 80.270718);

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

    @ReactProp(name = "enableMarker")
    public void setEnableMarker(OSMapView mapView, boolean enableMarker) {
        System.out.println("========IN set Enable Marker+++++++++++++++++++");
        mapView.setEnableMarker(enableMarker);

        deleteExistingMarker(mapView);

        if (enableMarker) {
            createMarker(mapView);
            setCenter(mapView, mapView.getUserLocation());
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
            System.out.println(geoPoint);
        } catch (Exception e) {
            throw new RuntimeException("Error updating user location", e);
        }
    }

    private void setCenter(OSMapView mapView) {
        setCenter(mapView, chennaiGeoPoint);
    }

    private void setCenter(OSMapView mapView, GeoPoint geoPoint) {
        System.out.println("------------------- Setting Center --------------------------");
        IMapController mapController = mapView.getController();
        mapController.setZoom(15);
        mapController.setCenter(geoPoint);
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

    private void deleteExistingMarker(OSMapView mapView) {
        Marker currentLocationMarker = mapView.getUserLocationMarker();
        if (currentLocationMarker != null) {
            mapView.getOverlays().remove(currentLocationMarker);
        }
    }

    private void createMarker(OSMapView mapView) {
        System.out.println("========= Creating marker=========");
        System.out.println(mapView.getUserLocation());
        Marker marker = new Marker(mapView);
        marker.setPosition(mapView.getUserLocation());
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        mapView.getOverlays().add(marker);
        mapView.setUserLocationMarker(marker);
    }
}
