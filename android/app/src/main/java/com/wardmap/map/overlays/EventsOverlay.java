package com.wardmap.map.overlays;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.wardmap.JSEventBus;
import com.wardmap.map.OSMapView;

import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.MapEventsOverlay;

public class EventsOverlay {
    public static final String MapLongPressEvent = "MAP_LONG_PRESS_EVENT";

    public void initEventReceiver(OSMapView mapView,
                                  final ReactContext reactContext, final JSEventBus jsEventBus) {
        MapEventsReceiver eventsReceiver = new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                return false;
            }

            @Override
            public boolean longPressHelper(GeoPoint p) {
                WritableMap map = Arguments.createMap();
                map.putDouble("latitude", p.getLatitude());
                map.putDouble("longitude", p.getLongitude());
                jsEventBus.sendEvent(reactContext, MapLongPressEvent, map);
                return false;
            }
        };
        MapEventsOverlay mapEventsOverlay = new MapEventsOverlay(eventsReceiver);
        mapView.getOverlays().add(mapEventsOverlay);
    }
}
