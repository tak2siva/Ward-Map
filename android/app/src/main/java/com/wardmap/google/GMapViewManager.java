package com.wardmap.google;

import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.PolyUtil;
import com.google.maps.android.data.kml.KmlContainer;
import com.google.maps.android.data.kml.KmlLayer;
import com.google.maps.android.data.kml.KmlPlacemark;
import com.google.maps.android.data.kml.KmlPolygon;
import com.wardmap.R;
import com.wardmap.map.react.JSEventBus;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

public class GMapViewManager extends SimpleViewManager<GMapView>
        implements OnMapReadyCallback, LifecycleEventListener {

    private GMapView gMapView;
    private ThemedReactContext reactContext;
    private JSEventBus jsEventBus;

    @Override
    public String getName() {
        return "GoogleMapView";
    }

    @Override
    protected GMapView createViewInstance(ThemedReactContext reactContext) {
        gMapView = new GMapView(reactContext);
        jsEventBus = new JSEventBus(reactContext);
        this.reactContext = reactContext;
        gMapView.getMapAsync(this);
        gMapView.onCreate(null);

        reactContext.addLifecycleEventListener(this);
        return gMapView;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        System.out.println("=============== Initialized google maps ===================");
        gMapView.setGoogleMap(googleMap);
        googleMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(13.082680, 80.270718) , 14.0f) );
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        final KmlLayer finalLayer = createKmlLayer(googleMap);

        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                for (KmlContainer container: finalLayer.getContainers()) {
                    for (KmlPlacemark kmlPlacemark : container.getPlacemarks()) {
                        KmlPolygon geometry = (KmlPolygon) kmlPlacemark.getGeometry();
                        List<List<LatLng>> geometryObjects = geometry.getGeometryObject();
                        for (List<LatLng> obj : geometryObjects) {
                            boolean contains = PolyUtil.containsLocation(latLng, obj, true);
                            if(contains) {
                                resetMarker(latLng, kmlPlacemark);
                                jsEventBus.sendEvent("ClickMarker", kmlPlacemark.getProperty("name"));
                            }
                        }
                    }
                }
            }
        });
    }

    private void resetMarker(LatLng latLng, KmlPlacemark kmlPlacemark) {
        for (Marker m : gMapView.getMarkers()) {
            m.remove();
        }

        gMapView.getMarkers().clear();

        System.out.println("Clicked " + kmlPlacemark.getProperty("name"));
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title(kmlPlacemark.getProperty("name"))
                .snippet(kmlPlacemark.getProperty("ZONE_NAME"));

        Marker locationMarker = gMapView.getGoogleMap().addMarker(markerOptions);
        locationMarker.showInfoWindow();
        gMapView.getMarkers().add(locationMarker);
    }

    private KmlLayer createKmlLayer(GoogleMap googleMap) {
        KmlLayer layer = null;
        try {
            layer = new KmlLayer(googleMap, R.raw.chennai_wards, reactContext);
            layer.addLayerToMap();
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        return layer;
    }

    @Override
    public void onHostResume() {
        gMapView.onResume();
    }

    @Override
    public void onHostPause() {
        gMapView.onPause();
    }

    @Override
    public void onHostDestroy() {
        gMapView.onDestroy();
    }
}
