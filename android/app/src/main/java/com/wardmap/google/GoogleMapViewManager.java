package com.wardmap.google;

import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.maps.android.PolyUtil;
import com.google.maps.android.data.Feature;
import com.google.maps.android.data.Geometry;
import com.google.maps.android.data.kml.KmlContainer;
import com.google.maps.android.data.kml.KmlLayer;
import com.google.maps.android.data.kml.KmlPlacemark;
import com.google.maps.android.data.kml.KmlPolygon;
import com.wardmap.R;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

public class GoogleMapViewManager extends SimpleViewManager<MapView>
        implements OnMapReadyCallback, LifecycleEventListener {

    private MapView mapView;
    private ThemedReactContext reactContext;

    @Override
    public String getName() {
        return "GoogleMapView";
    }

    @Override
    protected MapView createViewInstance(ThemedReactContext reactContext) {
        mapView = new MapView(reactContext);
        this.reactContext = reactContext;
        mapView.getMapAsync(this);
        mapView.onCreate(null);

        reactContext.addLifecycleEventListener(this);
        return mapView;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        KmlLayer layer = null;
        System.out.println("=============== Initialized google maps ===================");
        googleMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(13.082680, 80.270718) , 14.0f) );
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        try {
            layer = new KmlLayer(googleMap, R.raw.chennai_wards, reactContext);
            layer.addLayerToMap();

            for (KmlContainer container: layer.getContainers()) {
                for (KmlPlacemark kmlPlacemark : container.getPlacemarks()) {
                    System.out.println(kmlPlacemark.getProperty("name"));
                }
            }

        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }


        final KmlLayer finalLayer = layer;

        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                for (KmlContainer container: finalLayer.getContainers()) {
                    for (KmlPlacemark kmlPlacemark : container.getPlacemarks()) {
                        KmlPolygon geometry = (KmlPolygon) kmlPlacemark.getGeometry();
                        List<List<LatLng>> geometryObjects = geometry.getGeometryObject();
                        for (List<LatLng> obj : geometryObjects) {
                            boolean contians = PolyUtil.containsLocation(latLng, obj, true);
                            if(contians) {
                                System.out.println("Clicked " + kmlPlacemark.getProperty("name"));
                                googleMap.addMarker(new MarkerOptions()
                                        .position(latLng)
                                        .title(kmlPlacemark.getProperty("name"))
                                        .snippet(kmlPlacemark.getProperty("ZONE_NAME")));

                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onHostResume() {
        mapView.onResume();
    }

    @Override
    public void onHostPause() {
        mapView.onPause();
    }

    @Override
    public void onHostDestroy() {
        mapView.onDestroy();
    }
}
