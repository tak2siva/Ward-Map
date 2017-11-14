package com.wardmap.google;

import android.content.Context;
import android.util.AttributeSet;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;

public class GoogleMapView extends MapView {
    private GoogleMap googleMap;

    public GoogleMapView(Context context) {
        super(context);
    }

    public GoogleMapView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public GoogleMapView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public GoogleMapView(Context context, GoogleMapOptions googleMapOptions) {
        super(context, googleMapOptions);
    }


}
