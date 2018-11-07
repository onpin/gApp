package com.app.golfapp.ui.activity;

        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.widget.SeekBar;

        import com.app.golfapp.R;
        import com.google.android.gms.maps.CameraUpdateFactory;
        import com.google.android.gms.maps.GoogleMap;
        import com.google.android.gms.maps.OnMapReadyCallback;
        import com.google.android.gms.maps.SupportMapFragment;
        import com.google.android.gms.maps.model.BitmapDescriptor;
        import com.google.android.gms.maps.model.BitmapDescriptorFactory;
        import com.google.android.gms.maps.model.GroundOverlayOptions;
        import com.google.android.gms.maps.model.LatLng;
        import com.google.android.gms.maps.model.LatLngBounds;

        import java.util.ArrayList;
        import java.util.List;

public class GroundOverlay extends AppCompatActivity
        implements OnMapReadyCallback,
        GoogleMap.OnGroundOverlayClickListener {
    private final List<BitmapDescriptor> mImages = new ArrayList<BitmapDescriptor>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ground_overlay);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onGroundOverlayClick(com.google.android.gms.maps.model.GroundOverlay groundOverlay) {

    }

    @Override
    public void onMapReady(GoogleMap map) {

        map.setOnGroundOverlayClickListener(this);

        // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.614631, -122.385153), 18));

/*
        LatLng sw = new LatLng(151.30209310,Coordinates.COORDINATES.get("Place1").longitude);
        LatLng ne = new LatLng(151.30269820,Coordinates.COORDINATES.get("Place2").longitude);
        LatLng nw  = new LatLng(151.30026840,Coordinates.COORDINATES.get("Place3").longitude);
        LatLng se = new LatLng(151.29966320,Coordinates.COORDINATES.get("Place4").longitude);
*/

        //  map.moveCamera(CameraUpdateFactory.newLatLngZoom(NEWARK, 15));

        mImages.clear();
        mImages.add(BitmapDescriptorFactory.fromResource(R.drawable.h1));

        LatLngBounds newarkBounds = new LatLngBounds(
                new LatLng(40.712216, -74.22655),       // South west corner
                new LatLng(40.773941, -74.12544));      // North east corner
        GroundOverlayOptions newarkMap = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.h1))
                .positionFromBounds(newarkBounds);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(40.712216, -74.22655), 8));

        map.addGroundOverlay(newarkMap);
        map.setContentDescription("Google Map with ground overlay.");

    }
}
