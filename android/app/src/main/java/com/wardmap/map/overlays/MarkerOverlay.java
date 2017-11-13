package com.wardmap.map.overlays;

import com.wardmap.map.components.MarkerWithLabel;
import com.wardmap.map.components.OSMapView;

import org.osmdroid.views.overlay.Marker;

public class MarkerOverlay {
    public void createMarker(OSMapView mapView) {
        System.out.println("========= Creating marker=========");
        System.out.println(mapView.getUserLocation());
        if (mapView.getUserLocation() == null) {
            System.out.println("User location is null");
            return;
        }

        Marker marker = new MarkerWithLabel(mapView, "Chennai Label");
        marker.setPosition(mapView.getUserLocation());
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        mapView.getOverlays().add(marker);
        mapView.setUserLocationMarker(marker);
    }

    public void deleteExistingMarker(OSMapView mapView) {
        Marker currentLocationMarker = mapView.getUserLocationMarker();
        if (currentLocationMarker != null) {
            mapView.getOverlays().remove(currentLocationMarker);
        }
    }

    public void resetMarker(OSMapView mapView) {
        deleteExistingMarker(mapView);
        createMarker(mapView);
    }
}
