package com.reactcalculator.map;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import org.osmdroid.tileprovider.MapTileProviderBase;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

public class OSMapView extends MapView {
    private boolean enableMarker;
    private GeoPoint userLocation;
    private Marker userLocationMarker;

    public OSMapView(Context context, MapTileProviderBase tileProvider, Handler tileRequestCompleteHandler, AttributeSet attrs) {
        super(context, tileProvider, tileRequestCompleteHandler, attrs);
    }

    public OSMapView(Context context, MapTileProviderBase tileProvider, Handler tileRequestCompleteHandler, AttributeSet attrs, boolean hardwareAccelerated) {
        super(context, tileProvider, tileRequestCompleteHandler, attrs, hardwareAccelerated);
    }

    public OSMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OSMapView(Context context) {
        super(context);
    }

    public OSMapView(Context context, MapTileProviderBase aTileProvider) {
        super(context, aTileProvider);
    }

    public OSMapView(Context context, MapTileProviderBase aTileProvider, Handler tileRequestCompleteHandler) {
        super(context, aTileProvider, tileRequestCompleteHandler);
    }

    public GeoPoint getUserLocation() {
        return this.userLocation;
    }

    public Marker getUserLocationMarker() {
        return this.userLocationMarker;
    }

    public void setEnableMarker(boolean enableMarker) {
        this.enableMarker = enableMarker;
    }

    public void setUserLocation(GeoPoint geoPoint) {
        this.userLocation = geoPoint;
    }

    public void setUserLocationMarker(Marker marker) {
        this.userLocationMarker = marker;
    }
}
