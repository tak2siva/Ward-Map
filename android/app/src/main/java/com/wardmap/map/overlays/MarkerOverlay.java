package com.wardmap.map.overlays;

import com.wardmap.map.components.MarkerWithLabel;
import com.wardmap.map.components.OSMapView;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.Marker;

// To add marker for a given Geopoint

public class MarkerOverlay {
    public void createMarker(OSMapView mapView) {
        System.out.println("========= Creating marker=========");
        System.out.println(mapView.getUserLocation());
        if (mapView.getUserLocation() == null) {
            System.out.println("User location is null");
            return;
        }

        Marker marker = createMarker(mapView, mapView.getUserLocation(), "Chennai Label");
        mapView.getOverlays().add(marker);
        mapView.setUserLocationMarker(marker);
    }

    public Marker createMarker(OSMapView mapView, GeoPoint geoPoint, String label) {
        Marker marker = new MarkerWithLabel(mapView, label);
        marker.setTitle(label);
        marker.setPosition(geoPoint);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        return marker;
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
