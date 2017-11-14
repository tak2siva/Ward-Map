package com.wardmap.google;

import android.content.Context;
import android.util.AttributeSet;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

public class GMapView extends MapView {
    private GoogleMap googleMap;
    private List<Marker> markers = new ArrayList<>();

    public GMapView(Context context) {
        super(context);
    }

    public GMapView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public GMapView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public GMapView(Context context, GoogleMapOptions googleMapOptions) {
        super(context, googleMapOptions);
    }


    public List<Marker> getMarkers() {
        return markers;
    }

    public GoogleMap getGoogleMap() {
        return googleMap;
    }

    public void setGoogleMap(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }
}
