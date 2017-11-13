package com.wardmap.map.overlays;

import com.wardmap.map.components.OSMapView;

import org.osmdroid.api.IMapController;
import org.osmdroid.util.GeoPoint;

public class PositionHelper {
    private static final GeoPoint chennaiGeoPoint = new GeoPoint(13.082680, 80.270718);

    public void setDefaultCenter(OSMapView mapView) {
        setCenter(mapView, chennaiGeoPoint);
    }

    public void setCenter(OSMapView mapView, GeoPoint geoPoint) {
        System.out.println("------------------- Setting Center --------------------------");
        IMapController mapController = mapView.getController();
//        mapController.setZoom(15);
        mapController.setCenter(geoPoint);
    }

    public void setZoom(OSMapView mapView, int zoom) {
        IMapController mapController = mapView.getController();
        mapController.setZoom(zoom);
    }
}
