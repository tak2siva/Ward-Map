package com.reactcalculator.map;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import org.osmdroid.tileprovider.MapTileProviderBase;
import org.osmdroid.views.MapView;

public class OSMapView extends MapView {
    double latitude;
    double longitude;
    boolean enableMarker;

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

    public void setEnableMarker(boolean enableMarker) {
        this.enableMarker = enableMarker;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
