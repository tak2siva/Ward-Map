package com.wardmap.map.overlays;

import com.facebook.react.uimanager.ThemedReactContext;
import com.wardmap.map.OSMapView;

import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.MapEventsOverlay;

public class EventsOverlay {
    public void initEventReceiver(OSMapView mapView, ThemedReactContext reactContext) {
        MapEventsReceiver eventsReceiver = new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                return false;
            }

            @Override
            public boolean longPressHelper(GeoPoint p) {
                return false;
            }
        };
        MapEventsOverlay mapEventsOverlay = new MapEventsOverlay(eventsReceiver);
        mapView.getOverlays().add(mapEventsOverlay);
    }
}
