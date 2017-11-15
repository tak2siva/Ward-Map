package com.wardmap.google;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.PolyUtil;
import com.google.maps.android.data.kml.KmlContainer;
import com.google.maps.android.data.kml.KmlLayer;
import com.google.maps.android.data.kml.KmlPlacemark;
import com.google.maps.android.data.kml.KmlPolygon;

import java.util.ArrayList;
import java.util.List;

public class GMapView extends MapView {
    private GoogleMap googleMap;
    private List<Marker> markers = new ArrayList<>();
    private KmlLayer kmlLayer;
    private LatLng userLocation;

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

    public void setKmlLayer(KmlLayer kmlLayer) {
        this.kmlLayer = kmlLayer;
    }

    public LatLng getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(LatLng location) {
        this.userLocation = location;
    }

    @Nullable public KmlPlacemark containsInAnyPolygon(LatLng latLng) {
        if (kmlLayer == null) {
            return null;
        }

        for (KmlContainer container: kmlLayer.getContainers()) {
            for (KmlPlacemark kmlPlacemark : container.getPlacemarks()) {
                KmlPolygon geometry = (KmlPolygon) kmlPlacemark.getGeometry();
                List<List<LatLng>> geometryObjects = geometry.getGeometryObject();
                for (List<LatLng> obj : geometryObjects) {
                    boolean contains = PolyUtil.containsLocation(latLng, obj, true);
                    if (contains) {
                        return kmlPlacemark;
                    }
                }
            }
        }

        return null;
    }
}
