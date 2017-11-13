package com.wardmap.map.overlays;

import android.content.res.Resources;

import com.facebook.react.bridge.ReactContext;
import com.wardmap.R;
import com.wardmap.map.components.OSMapView;

import org.osmdroid.bonuspack.kml.KmlDocument;
import org.osmdroid.views.overlay.FolderOverlay;

import java.io.InputStream;

public class KMLOverlay{
    public void draw(OSMapView mapView, ReactContext reactContext) {
        Resources resources = reactContext.getResources();
        InputStream kmlInputStream = resources.openRawResource(R.raw.chennai_wards);

        KmlDocument kmlDocument = new KmlDocument();
        kmlDocument.parseKMLStream(kmlInputStream, null);
        FolderOverlay kmlOverlay = (FolderOverlay) kmlDocument.mKmlRoot.buildOverlay(mapView, null, null, kmlDocument);
        mapView.getOverlays().add(kmlOverlay);
    }
}
