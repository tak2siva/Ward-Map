package com.wardmap.map.overlays;

import android.content.res.Resources;

import com.facebook.react.bridge.ReactContext;
import com.wardmap.R;
import com.wardmap.map.components.OSMapView;
import com.wardmap.map.react.JSEventBus;

import org.osmdroid.bonuspack.kml.KmlDocument;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.FolderOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.Polygon;

import java.io.InputStream;

// Draw polygons based on kml document

public class KMLOverlay{
    private final ReactContext reactContext;
    private final JSEventBus jsEventBus;
    private final MarkerOverlay markerOverlay;
    private final Marker.OnMarkerClickListener listener;

    public KMLOverlay(ReactContext reactContext, MarkerOverlay markerOverlay) {
        this.reactContext = reactContext;
        this.markerOverlay = markerOverlay;
        this.jsEventBus = new JSEventBus(this.reactContext);
        this.listener = new Marker.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                System.out.println("======== marker click event + " + marker.getTitle() +  " =============");
                jsEventBus.sendEvent("ClickMarker", marker.getTitle());
                return true;
            }
        };
    }

    public void draw(OSMapView mapView, ReactContext reactContext) {
        final Resources resources = reactContext.getResources();
        InputStream kmlInputStream = resources.openRawResource(R.raw.chennai_wards);

        KmlDocument kmlDocument = new KmlDocument();
        kmlDocument.parseKMLStream(kmlInputStream, null);
        FolderOverlay kmlOverlay = (FolderOverlay) kmlDocument.mKmlRoot.buildOverlay(mapView, null, null, kmlDocument);

        mapView.getOverlays().add(kmlOverlay);
        createMarkerForEachWard(mapView, kmlOverlay);
    }

    private void createMarkerForEachWard(OSMapView mapView, FolderOverlay kmlOverlay) {
        for (Overlay ol : kmlOverlay.getItems()) {
            final Polygon ward = (Polygon) ol;
            GeoPoint geoPoint = ward.getPoints().get(0); // need to get center of polygon
            System.out.println("========= " + ward.getTitle() + " ======================");
            Marker marker = markerOverlay.createMarker(mapView, geoPoint, ward.getTitle());

            marker.setOnMarkerClickListener(listener);
            mapView.getOverlays().add(marker);
        }
    }
}
