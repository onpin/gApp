package com.app.golfapp.ui.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;
///mbgfklbmgf

import com.app.golfapp.LatLngInterpolator;
import com.app.golfapp.MarkerAnimation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
//kjnfg b

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.golfapp.MyDB;
import com.app.golfapp.MyDatabaseHelper;
import com.app.golfapp.R;
import com.app.golfapp.app.GeoFenceController;
import com.app.golfapp.app.GeoFenceControllerGeoConfig;
import com.app.golfapp.ui.Services.GeofenceTransitionsIntentService;
import com.app.golfapp.ui.Services.MyService;
import com.app.golfapp.ui.activity.NavigationDrawerActivity;
import com.app.golfapp.ui.model.GolfHole;
import com.app.golfapp.ui.model.NamedGeofence;
import com.app.golfapp.utils.BaseCommandUtil;
import com.app.golfapp.utils.ButtonCallback;
import com.app.golfapp.utils.PolyUtil;
import com.app.golfapp.utils.QuadToBox;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.maps.android.SphericalUtil;
import com.google.maps.android.ui.IconGenerator;


import org.json.JSONObject;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.security.Policy;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;


import br.com.joinersa.oooalertdialog.Animation;
import br.com.joinersa.oooalertdialog.OnClickListener;
import br.com.joinersa.oooalertdialog.OoOAlertDialog;

import static android.content.Context.MODE_PRIVATE;

import static java.lang.Math.toRadians;

/**
 * Created by hmnsharma on 5/3/2018.
 */

public class XMLParserFragment extends Fragment implements OnMapReadyCallback, LocationListener{

    Marker ballLocation, shotLocation,stnew, distanceBubble1, distanceBubble2;
    String macAddress, timestamp;
    LatLng currentLocation ;
    double lat2 ;
    double lng2 ;


    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 5445;
    private GoogleMap googleMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Marker currentLocationMarker;
    private Location currentLocation1;
    private boolean firstTimeFlag = true;

    Marker onTouchLocation,onMarkerDraggable;
    Geofence fence;
    LatLng onFingerTouch;
    LatLng onMarkerDrag;
    private LruCache<String, BitmapDrawable> mMemoryCache;
    private LruCache<String, GroundOverlayOptions> mMemoryCache1;
    private List<MyDB> notesList = new ArrayList<>();
    private MyDatabaseHelper db;
    Polyline polyline, polyline1;
    LatLng cartLocation;
    LatLng flag;
    LatLng shotPlanner;
    String code;

    LatLng holeFlag;
    String coordinatesofGeoConfig;
    private RectF rect = new RectF();
    private View view;
    MyService locationTracker;
    protected static double maxError = 1e-6;
    Marker mCurrLocationMarker, bubble;
    TextView tv;
    Button button;
    String h1cod1, h1cod2, h1cod3, h1cod4;
    String H1par;
    String H1greenBack, H1greenFront, H1greenCentre;
    String H1hazard1, H2hazard1, H3hazard1, H4hazard1;
    String H1number;
    RequestQueue requestQueue;
    PolylineOptions options;

    private final List<BitmapDescriptor> mImages = new ArrayList<BitmapDescriptor>();

    String coordinatefirstHolec1, coordinatefirstHolec2, coordinatefirstHolec3, coordinatefirstHolec4;
    ImageView iButton;
    ImageView gButton;
    Button centrebtn;
    Button frontbtn;
    Button backbtn;
    GoogleMap map;
    ImageView image;
     String lat,longi;
    LinearLayout lv;
    TextView flagDistance;
    String type,radiusofGeoConfig;
    TextView holeNumber, parNumber;
    TextClock time ;
    Toolbar toolbar;
    MapView mapView;
    LatLng target;
    double Centerlat, CenterLong, radius;
    CameraPosition currentPlace;
    int clickcount = 0;
    private GeofencingClient mGeofencingClient;
    private static final long GEO_DURATION = 60 * 60 * 1000;
    private static final String GEOFENCE_REQ_ID = "My Geofence";
    private static final float GEOFENCE_RADIUS = 10000; // in meters
    private ArrayList<Geofence> mGeofenceList = new ArrayList<Geofence>();
    private PendingIntent pendingIntent;
    private GolfHole mGolfHoleM = new GolfHole();
    int mParamType;
    Bitmap mBitmap;
    String temp;
     int buttonType=1;
    SharedPreferences sharedPreferencesLatlong;
    private Marker geoFenceMarker;
    private Circle geoFenceLimits;
    private GoogleApiClient googleApiClient;
    static Context context;
    GroundOverlayOptions hole1Map;
    NamedGeofence geofence = new NamedGeofence();

    private static final String TAG = "XMLFragment";
    private GeofencingRequest geofencingRequest;
    AddGeofenceFragmentListener listener;
    private TileOverlay mTileOverlay;
    String holeLast=null;
    private ButtonCallback buttonCallBack;

    public void setListener(AddGeofenceFragmentListener listener) {
        this.listener = listener;
    }

    public static XMLParserFragment newInstance(int param1, GolfHole mGolfHole1) {

        XMLParserFragment fragment = new XMLParserFragment();
        Bundle args = new Bundle();
        args.putInt("args", param1);
        if (mGolfHole1 != null) {
            args.putParcelable("mGolfHole", mGolfHole1);

        }


        fragment.setArguments(args);



        return fragment;
    }

    @SuppressLint("MissingPermission")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.parse, container, false);
        button = (Button) this.view.findViewById(R.id.button);
        holeNumber = (TextView) view.findViewById(R.id.holenumber_parse);
        parNumber = (TextView) view.findViewById(R.id.parnumber);
        time = (TextClock) view.findViewById(R.id.time);
        frontbtn = (Button) this.view.findViewById(R.id.front);
        backbtn = (Button) this.view.findViewById(R.id.back);
        centrebtn = (Button) this.view.findViewById(R.id.centre);
        iButton = (ImageView) getActivity().findViewById(R.id.forward);
        gButton = (ImageView) getActivity().findViewById(R.id.backward);


        db = new MyDatabaseHelper(getContext());
        notesList.addAll(db.getAllData());






        //extractCoodinates();
        locationTracker = new MyService(getActivity());


        SupportMapFragment mapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map));
        mapFragment.getMapAsync(this);
       // mapFragment.getView().setBackgroundColor(Color.WHITE);
        getMacAddr();


        TimeZone tz = TimeZone.getTimeZone("GMT+10:00");
        Calendar c = Calendar.getInstance(tz);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
        String timeZone = String.format("%02d", c.get(Calendar.HOUR_OF_DAY)) + ":" + String.format("%02d", c.get(Calendar.MINUTE));
        //String finalTimeZone ="Time:" + timeZone;
        if (getArguments() != null) {
            mParamType = getArguments().getInt("args");


        }
        if (mParamType == 1) {
            mGolfHoleM = (GolfHole) getArguments().getParcelable("mGolfHole");
        }
        H1number = mGolfHoleM.getHnumber();
        H1par = mGolfHoleM.getHpar();
        h1cod1 = mGolfHoleM.getHcod1();
        h1cod2 = mGolfHoleM.getHcod2();
        h1cod3 = mGolfHoleM.getHcod3();
        h1cod4 = mGolfHoleM.getHcod4();
        H1greenBack = mGolfHoleM.getHgreenBack();
        H1greenCentre = mGolfHoleM.getHgreenCenter();
        H1greenFront = mGolfHoleM.getHgreenFront();
        mBitmap = mGolfHoleM.getImages();
        if(mGolfHoleM.getType()!=null
                && !mGolfHoleM.getType().equals("")
                && !mGolfHoleM.getCordinates().equals("")
                &&  mGolfHoleM.getCordinates()!=null){
            type=mGolfHoleM.getType();
            //radiusofGeoConfig=mGolfHoleM.getRadius();
            coordinatesofGeoConfig=mGolfHoleM.getCordinates();

        }
        temp=mGolfHoleM.getTemp();



        String holeNum = "Hole " + H1number;
        holeNumber.setText("");
        holeNumber.setText(H1number);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setBatterySaverMode(true);

        } else {
        }
        requestQueue = Volley.newRequestQueue(getActivity());


        String parNum = "Par -" + H1par;
        parNumber.setText(H1par);
         lat=String.valueOf(locationTracker.getLatitude());
         longi =String.valueOf(locationTracker.getLongitude());
         sharedPreferencesLatlong=getActivity().getSharedPreferences("latlong",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferencesLatlong.edit();
        editor.putString("lat",lat);
        editor.putString("long",longi);
        editor.commit();
      //  time.setText(timeZone);


        centrebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickcount = clickcount + 1;

                if (clickcount == 5) {
                    ((NavigationDrawerActivity) getActivity()).LockScreen();
                    clickcount = 0;
                }

            }
        });
        GeoFenceController.getInstance().init(getActivity());
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;

        mMemoryCache = new LruCache<String, BitmapDrawable>(cacheSize) {
            @Override
            protected int sizeOf(String key, BitmapDrawable bitmap) {

                return bitmap.getBitmap().getByteCount() / 1024;
            }
        };
        mMemoryCache1 = new LruCache<String, GroundOverlayOptions>(cacheSize) {
            @Override
            protected int sizeOf(String key, GroundOverlayOptions value) {
                return (Integer.valueOf(String.valueOf(value.getImage())));
            }
        };


//        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
//        }
//        if (locationTrack.canGetLocation()) {
//
//            longitude = locationTrack.getLongitude();
//            latitude = locationTrack.getLatitude();
//        } else {
//
//            locationTrack.showSettingsAlert();
//        }
        String holeval= holeNumber.getText().toString();
        return view;

    }



    public void addBitmapToMemoryCache(String key, BitmapDrawable bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public BitmapDrawable getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

    private void addGroundOverLayoption(String key
            , GroundOverlayOptions value) {
        if (getGoundKey(key) == null) {
            mMemoryCache1.put(key, value);
        }

    }


    public String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

               StringBuilder res1 = new StringBuilder();
                // strMac=res1.toString();


                for (byte b : macBytes) {
                    res1.append(String.format("%02x", b));
                }

//                if (res1.length() > 0) {
//                    res1.deleteCharAt(res1.length() - 1);
//                }
                macAddress=res1.toString().toUpperCase();

                return res1.toString();
            }
        } catch (Exception ex) {
            //handle exception
        }
        return "";
    }

    public GroundOverlayOptions getGoundKey(String key) {
        return mMemoryCache1.get(key);
    }

    private PendingIntent getGeofencePendingIntent() {

        if (pendingIntent != null) {
            return pendingIntent;
        }
        Intent intent = new Intent(getActivity(), GeofenceTransitionsIntentService.class);
        pendingIntent = PendingIntent.getService(getActivity(), 1, intent, PendingIntent.
                FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }



    /*private void setToolbar() {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar_hidden);
        setSupportActionBar(toolbar);tg
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }*/


    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            setBatterySaverMode(true);
            // only for gingerbread and newer versions
        } else {
        }

    }

    @Override
    public void onPause() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setBatterySaverMode(false);
            // only for gingerbread and newer versions
        } else {
        }
        super.onPause();
    }




    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;
        setMaproperties();
        map.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        getContext(), R.raw.style_json));


        ((NavigationDrawerActivity) getActivity()).iButton.setFocusableInTouchMode(true);
        ((NavigationDrawerActivity) getActivity()).gButton.setFocusableInTouchMode(true);



        // Longitudes and latitudes manually copied from the doc.kml overlay for hole 1.
        // You should extract them from there.
        double LL_lon = getLongitude(h1cod1);
        double LL_lat = getLatitude(h1cod1);
        double LR_lon = getLongitude(h1cod2);
        double LR_lat = getLatitude(h1cod2);
        double UR_lon = getLongitude(h1cod3);
        double UR_lat = getLatitude(h1cod3);
        double UL_lon = getLongitude(h1cod4);
        double UL_lat = getLatitude(h1cod4);

        double[] corners = {UL_lon, UL_lat, UR_lon, UR_lat, LR_lon, LR_lat, LL_lon, LL_lat};
        double[] box = QuadToBox.quadToBox(corners);

        double north = box[0];
        double south = box[1];
        double east = box[2];
        double west = box[3];
        double rotation = -box[4];

        LatLng NE = new LatLng(north, east);
        LatLng SW = new LatLng(south, west);
        final LatLngBounds latLngBox = new LatLngBounds(SW, NE);

        LatLngBounds bounds = new LatLngBounds(SW, NE);
        target = bounds.getCenter();
        // Centerlat=target.latitude;
        //  CenterLong=target.longitude;
        radius = 350;

         locationTracker = new MyService(getContext());
        if (locationTracker.canGetLocation()) {
            CenterLong = locationTracker.getLongitude();
            Centerlat = locationTracker.getLatitude();

        } else {

            locationTracker.showSettingsAlert();
        }

        geofence.name = "";
        geofence.latitude = Centerlat;
        geofence.longitude = CenterLong;
        geofence.radius = (float) radius;
        GeoFenceController.getInstance().addGeofence(geofence);


        if(mGolfHoleM.getTemp()=="1"){

            geofence.name="GeoCircle";
            geofence.radius=Float.valueOf( mGolfHoleM.getRadius());
            String cord[]=mGolfHoleM.getCordinates().split(",");
            String latitude=cord[0];
            String longitude=cord[1];
            geofence.latitude =Double.valueOf( latitude);
            geofence.longitude = Double.valueOf( longitude);
           // geofence.radius=


            GeoFenceControllerGeoConfig.getInstance().addGeofence(geofence);



        }
        if(mGolfHoleM.getTemp()=="2") {

            List<Double> latitudeConfig = new ArrayList<>();
            List<Double> longitudeConfig = new ArrayList<>();
            List<LatLng> points = new ArrayList<>();
            ArrayList <String>coordinates=new ArrayList();
            String lat[]=null;
            String logi[]=null;
            String altitude[]=null;


            String cords[] = mGolfHoleM.getCordinates().split(" ");

            for(int a=0;a<cords.length;a++){

                //coordinates=cords.toString().split(",");
                //coordinates.addAll(cords.toString().split(","));

               // lat=coordinates[0].toString();

                getLatitudePoly(cords[a]);
                getLongitudePoly(cords[a]);
                LatLng latlngsPoly=new LatLng(getLatitudePoly(cords[a]),getLongitudePoly(cords[a]));
                points.add(latlngsPoly);






            }
            //lat[0]=coordinates[0];
           // logi[0]=coordinates[1];
//
//            for (int i = 0; i < cords[0].length(); i++) {
//
//                latitudeConfig.add(Double.valueOf(cords[0]));
//
//            }
//            for (int i = 0; i < cords[1].length(); i++) {
//
//                longitudeConfig.add(Double.valueOf(cords[1]));
//
//            }
//            for (int j = 0; j < latitudeConfig.size(); j++) {
//                for (int k = 0; k < longitudeConfig.size(); k++) {
//                    points.add(new LatLng(latitudeConfig.get(j), longitudeConfig.get(k)));
//
//                }
//            }
            Double latitude = locationTracker.getLatitude();
            Double longitude = locationTracker.getLongitude();
        //    currentLocation = new LatLng(  latitude,longitude);
            currentLocation=new LatLng(-33.66739091980345,151.2902766663142);


            Polygon polygon = map.addPolygon(new PolygonOptions().addAll(points));
          /*  polygon.setStrokeColor(getResources().getColor(R.color.transparentPoly));*/

            polygon.setStrokeColor(Color.TRANSPARENT);
            boolean contain = PolyUtil.containsLocation(currentLocation, points, true);
            if (contain) {
                /*AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                builder1.setMessage(mGolfHoleM.getType());
                builder1.setTitle("Restricted Area");
                builder1.setCancelable(true);


                builder1.setNegativeButton(
                        "Close",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();*/
                final Dialog dialog = null;
                new OoOAlertDialog.Builder(getActivity())
                        .setTitle("Restricted Area")
                        .setMessage(mGolfHoleM.getType())
                        .setImage(R.drawable.dialogimg)
                        .setAnimation(Animation.POP)
                        .setPositiveButton("Yes", new OnClickListener() {
                            @Override
                            public void onClick() {

                            }
                        })
                        .setNegativeButton("No", new OnClickListener() {
                            @Override
                            public void onClick() {

                            }
                        })
                        .build();

            }


        }









        //mImages.clear();
        //  mImages.add(BitmapDescriptorFactory.fromBitmap(mBitmap));
        //   int imagesize = sizeOf(mBitmap);
      /*  DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height1 = displayMetrics.heightPixels;
        int width1 = displayMetrics.widthPixels;*/
       // LatLngBounds borders = mGoogleMap.getProjection().getVisibleRegion().latLngBounds;
        if(mBitmap!=null){

        }
try
{
     hole1Map = new GroundOverlayOptions()
            .image(BitmapDescriptorFactory.fromBitmap(mBitmap))
            .positionFromBounds(latLngBox).bearing((float) rotation);

}catch (OutOfMemoryError e)
{
    e.printStackTrace();

}

      /*  GroundOverlayOptions hole1Map = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromBitmap(mBitmap))
                .positionFromBounds(latLngBox);
*/

        switch (H1par) {

            case "3":

                holeFlag = covertStringintoLatLong(H1greenCentre);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(holeFlag);
                markerOptions.title("Hole Position");
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.par4_red));
                mCurrLocationMarker = map.addMarker(markerOptions);

                break;

            case "4":

                LatLng holeFlag3 = covertStringintoLatLong(H1greenCentre);
                MarkerOptions markerOptions3 = new MarkerOptions();
                markerOptions3.position(holeFlag3);
                markerOptions3.title("Hole Position");
                markerOptions3.icon(BitmapDescriptorFactory.fromResource(R.drawable.par4_red));
                mCurrLocationMarker = map.addMarker(markerOptions3);

                break;


            case "5":

                LatLng holeFlag5 = covertStringintoLatLong(H1greenCentre);
                MarkerOptions markerOptions5 = new MarkerOptions();
                markerOptions5.position(holeFlag5);
                markerOptions5.title("Hole Position");
                markerOptions5.icon(BitmapDescriptorFactory.fromResource(R.drawable.par4_red));
                mCurrLocationMarker = map.addMarker(markerOptions5);

                break;

        }
        // addGroundOverLayoption("g1",hole1Map);


        try {
            map.addGroundOverlay(hole1Map);
            map.setOnMarkerClickListener(onClickListener);
         //   Log.e("Data value",hole1Map.toString());

        }catch (NullPointerException e)
        {

            e.printStackTrace();
         // Log.e("Data value", e.printStackTrace());


        }


       // mCurrLocationMarker.setOn

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.00); // offset from edges of the map 10% of screen

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(latLngBox, width, height, -45);

        map.moveCamera(cu);
        updateCamera((float) rotation);

        CameraUpdate zoom = CameraUpdateFactory.zoomTo(18.85f);
        currentPlace = new CameraPosition.Builder()
                .target(target)
                .bearing((float) rotation).tilt(0.0f).zoom(18.85f).build();


        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onMapClick(LatLng point) {

                shotLocation.remove();


                if (onTouchLocation != null)  {

                    onTouchLocation.remove();
                }

                if (onMarkerDraggable != null) {

                    onMarkerDraggable.remove();
                }

                MarkerOptions marker = new MarkerOptions().position(
                        new LatLng(point.latitude, point.longitude)).title("New Marker");



                onFingerTouch = new LatLng(point.latitude, point.longitude);

                int heighticon = 100;
                int widthicon = 100;




                double d1 = onFingerTouch.latitude;
                double d2 = onFingerTouch.longitude;

//
                double d3 = locationTracker.getLatitude();
                double d4 = locationTracker.getLongitude();
                double d5 = getLatitude(H1greenCentre);
                double d6 = getLongitude(H1greenCentre);

                double i = distance(d1, d2, d3, d4) * 1000;
                double j = distance(d1, d2, d5, d6) * 1000;


                Log.e("distances", "distances" + i);
                Log.e("distancesssss", "distancesssss" + j);
                DecimalFormat precision = new DecimalFormat("0.00");

                BitmapDrawable bitmapdraw1 = (BitmapDrawable) getResources().getDrawable(R.drawable.target);
                Bitmap b1 = bitmapdraw1.getBitmap();
                Bitmap smallMarker1 = Bitmap.createScaledBitmap(b1, widthicon, heighticon, false);

                onTouchLocation = map.addMarker(new MarkerOptions().position(onFingerTouch).draggable(true).visible(true).title(precision.format(i) + "Mtrs").icon(BitmapDescriptorFactory.fromBitmap(smallMarker1)));
                onTouchLocation.showInfoWindow();

                IconGenerator iconFactory = new IconGenerator(getActivity());
                iconFactory.setRotation(180);
                iconFactory.setContentRotation(-180);
                addIcon(iconFactory, precision.format(j) + "Mtrs", new LatLng(d5, d6));


                polyline.remove();
                polyline1.remove();
                showCurvedPolyline(cartLocation, onFingerTouch, flag, 0.2);


                System.out.println(point.latitude + "---" + point.longitude);
            }
        });
        cartLocation = new LatLng(locationTracker.getLatitude(), locationTracker.getLatitude());
      //  LatLng ant=new LatLng(-33.66954568,151.3015485);


        shotPlanner = covertStringintoLatLong(H1greenCentre);
        flag = covertStringintoLatLong(H1greenCentre);

        double d1 = getLatitude(H1greenCentre);
        double d2 = getLongitude(H1greenCentre);


        double d3 = locationTracker.getLatitude();
        double d4 = locationTracker.getLongitude();

        double i = distance(d3, d4, d1, d2) * 1000;
        Log.e("distances", "distances" + i);


        Timer timer=new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                    try {

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // This code will always run on the UI thread, therefore is safe to modify UI elements.
                                setValuesToButton();
                            }
                        });

                    } catch (NullPointerException e) {

                        e.printStackTrace();
                    }
                    // Your logic here...

                    // When you need to modify a UI element, do so on the UI thread.
                    // 'getActivity()' is required as this is being ran from a Fragment.

                }

        }, 0, 300);


        try {
            int heighticon = 40;
            int widthicon = 40;
            //onTouchLocation.remove();

            BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.cart);
            Bitmap b = bitmapdraw.getBitmap();
            Bitmap smallMarker = Bitmap.createScaledBitmap(b, widthicon, heighticon, false);
            addBitmapToMemoryCache("draw2", bitmapdraw);
            int Targetheight = 40;
            int Targetwidth = 40;
            BitmapDrawable bitmapdraw1 = (BitmapDrawable) getResources().getDrawable(R.drawable.target);
            Bitmap b1 = bitmapdraw1.getBitmap();
            Bitmap smallMarker1 = Bitmap.createScaledBitmap(b1, Targetwidth, Targetheight, false);
            addBitmapToMemoryCache("draw3", bitmapdraw1);

            DecimalFormat precision = new DecimalFormat("0.00");

//cart

            if (ballLocation == null) {
                ballLocation = map.addMarker(new MarkerOptions().position(cartLocation).draggable(true).visible(true).title("Golf ball location").icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
            }else {
                MarkerAnimation.animateMarkerToGB(ballLocation, cartLocation, new LatLngInterpolator.Spherical());

            }

            shotLocation = map.addMarker(new MarkerOptions().position(shotPlanner).draggable(true).visible(true).title("Distance").snippet(precision.format(i) + "Mtrs").icon(BitmapDescriptorFactory.fromBitmap(smallMarker1)));
            shotLocation.showInfoWindow();

        } catch (Exception e) {
        }

        ballLocation.setSnippet(String.valueOf(i));

        showCurvedPolyline(cartLocation, shotPlanner, flag, 0.35);

        map.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

                onMarkerDrag = marker.getPosition();
                DragDistance();

            }


            @Override
            public void onMarkerDragEnd(Marker marker) {

                onMarkerDrag = marker.getPosition();
                DragDistance();





            }
        });

        map.setLatLngBoundsForCameraTarget(latLngBox);
        double meanDistance = distance(north, east, south, west);
        double finalMean = meanDistance / 2;

        Log.d("docu", "final" + finalMean);

            Timer timer1=new Timer();
        timer1.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    hitWebService();

                }catch (NullPointerException e)
                {

                    e.printStackTrace();
                }

            }
        }, 0, 5*60*1000);//5 Minutes
    }



    private void setMaproperties(){
        map.clear();


        map.getUiSettings().setRotateGesturesEnabled(false);
        map.getUiSettings().setScrollGesturesEnabled(false);
        map.getUiSettings().setTiltGesturesEnabled(false);
        map.getUiSettings().setCompassEnabled(true);
        map.getUiSettings().setMapToolbarEnabled(false);

        map.getUiSettings().setZoomControlsEnabled(false);
        map.getUiSettings().setZoomGesturesEnabled(false);
    }

    private GoogleMap.OnMarkerClickListener onClickListener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            if (mCurrLocationMarker != null) {
            // Close the info window
                mCurrLocationMarker.hideInfoWindow();

            // Is the marker the same marker that was already open
            if (mCurrLocationMarker.equals(marker)) {
                // Nullify the lastOpenned object
                mCurrLocationMarker = null;
                // Return so that the info window isn't openned again
                return true;
            }
        }
            mCurrLocationMarker = marker;

            // Event was handled by our code do not launch default behaviour.
            return true;
        }
    };


    private void updateCamera(float bearing) {
        CameraPosition oldPos = map.getCameraPosition();

        CameraPosition pos = CameraPosition.builder(oldPos).bearing(bearing).build();
        map.moveCamera(CameraUpdateFactory.newCameraPosition(pos));
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }



    private void addIcon(IconGenerator iconFactory, CharSequence text, LatLng position) {
        MarkerOptions markerOptions = new MarkerOptions().
                icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(text))).
                position(position).
                anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV());

        map.addMarker(markerOptions);
    }


    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }






    public LatLng covertStringintoLatLong(String coodinate) {

        String[] latlong = coodinate.split(",");
        double latitude = Double.parseDouble(latlong[0]);
        double longitude = Double.parseDouble(latlong[1]);
//        double latitude=locationTrack.getLatitude();
//        double longitude=locationTrack.getLongitude();
        return new LatLng(longitude, latitude);
    }



    public double getLongitude(String coodinate) {

        String[] latlong = coodinate.split(",");
        double latitude = Double.parseDouble(latlong[0]);
        double longitude = Double.parseDouble(latlong[1]);
        return latitude;
    }

    public double getLatitude(String coodinate) {

        String[] latlong = coodinate.split(",");
        double latitude = Double.parseDouble(latlong[0]);
        double longitude = Double.parseDouble(latlong[1]);
        return longitude;
    }

    //to Poli
    public double getLongitudePoly(String coodinate) {

        String[] latlong = coodinate.split(",");
        double latitude = Double.parseDouble(latlong[0]);
        double longitude = Double.parseDouble(latlong[1]);
        return latitude;
    }

    public double getLatitudePoly(String coodinate) {

        String[] latlong = coodinate.split(",");
        double latitude = Double.parseDouble(latlong[0]);
        double longitude = Double.parseDouble(latlong[1]);
        return longitude;
    }



    @Override
    public void onLocationChanged(Location location) {

         lat2 = location.getLatitude();
         lng2 = location.getLongitude();




        // lat1 and lng1 are the values of a previously stored location
//        if (distance(-117.90870212,-117.90870212, 28.535995, 77.398119) < 0.1)
        if (distance(location.getLatitude(), location.getLatitude(), locationTracker.getLongitude(), locationTracker.getLongitude()) < 0.1) { // if distance < 0.1 miles we take locations as equal
            //do what you want to do...

            Log.e("dis", "we got dis");
        }
    }

    private double distance(double lat1, double lng1, double lat2, double lng2) {

        double earthRadius = 6371; // in miles, change to 6371 for kilometer output and for miles 3958.75

        double dLat = toRadians(lat2 - lat1);
        double dLng = toRadians(lng2 - lng1);

        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);

        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(toRadians(lat1)) * Math.cos(toRadians(lat2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double dist = earthRadius * c;
        Log.e("dis", "dis" + dist);

        return dist;
        // output distance, in MILES

    }

    private void showCurvedPolyline(LatLng p1, LatLng p2, LatLng p3, double k) {
        //Calculate distance and heading between two points

        double d = SphericalUtil.computeDistanceBetween(p1, p2);
        double h = SphericalUtil.computeHeading(p1, p2);

        double e = SphericalUtil.computeDistanceBetween(p2, p3);
        double f = SphericalUtil.computeHeading(p2, p3);

        //Midpoint position
        LatLng p = SphericalUtil.computeOffset(p1, d * 0.5, h);
        LatLng q = SphericalUtil.computeOffset(p2, e * 0.5, f);

        //Apply some mathematics to calculate position of the circle center
        double x = (1 - k * k) * d * 0.5 / (2 * k);
        double r = (1 + k * k) * d * 0.5 / (2 * k);

        double l = (1 - k * k) * e * 0.5 / (2 * k);
        double j = (1 + k * k) * e * 0.5 / (2 * k);

        LatLng c = SphericalUtil.computeOffset(p, x, h + 90.0);
        LatLng m = SphericalUtil.computeOffset(q, l, f + 90.0);

        //Polyline options
        PolylineOptions options = new PolylineOptions();
        PolylineOptions options1 = new PolylineOptions();

        //Calculate heading between circle center and two points
        double h1 = SphericalUtil.computeHeading(c, p1);
        double h2 = SphericalUtil.computeHeading(c, p2);

        double h3 = SphericalUtil.computeHeading(m, p2);
        double h4 = SphericalUtil.computeHeading(m, p3);


        //Calculate positions of points on circle border and add them to polyline options
        int numpoints = 100;
        double step = (h2 - h1) / numpoints;

        for (int i = 0; i < numpoints; i++) {
            LatLng pi = SphericalUtil.computeOffset(c, r, h1 + i * step);
            options.add(pi);
        }

        int num = 100;
        double steps = (h4 - h3) / num;

        for (int i = 0; i < num; i++) {
            LatLng ci = SphericalUtil.computeOffset(m, j, h3 + i * steps);
            options1.add(ci);
            //options.color(getResources().getColor(R.color.colorPrimary));
        }

        //Draw polyline

        polyline = this.map.addPolyline(options.width(6).color(Color.WHITE).geodesic(false));
        polyline1 = this.map.addPolyline(options1.width(6).color(Color.WHITE).geodesic(false));

    }

    public void setValuesToButton() {
        double distance=0;

//        double d3 = 33.75334008;
//        double d4 = -117.90870212;

        double front1 = getLatitude(H1greenFront);//holelatlog
        double front2 = getLongitude(H1greenFront);
        double distancefront = distance(locationTracker.getLatitude(), locationTracker.getLongitude(), front1, front2) * 1000;
        int finalFront = (int) distancefront;

        double back1 = getLatitude(H1greenBack);
        double back2 = getLongitude(H1greenBack);
        double distanceBack = distance(locationTracker.getLatitude(), locationTracker.getLongitude(), back1, back2) * 1000;
        int finalBack = (int) distanceBack;

        double center1 = getLatitude(H1greenCentre);
        double center2 = getLongitude(H1greenCentre);
        double distanceCenter = distance(locationTracker.getLatitude(), locationTracker.getLongitude(), center1, center2) * 1000;
        int finalCenter = (int) distanceCenter;
        backbtn.setText(finalBack + "Back");
        centrebtn.setText(finalCenter + " Center");
        frontbtn.setText(finalFront + "Front");







    }
    public double getDistance(LatLng LatLng1, LatLng LatLng2) {
        double distance = 0;
        Location locationA = new Location("A");
        locationA.setLatitude(LatLng1.latitude);
        locationA.setLongitude(LatLng1.longitude);


        Location locationB = new Location("B");
        locationB.setLatitude(LatLng2.latitude);
        locationB.setLongitude(LatLng2.longitude);


        distance = locationA.distanceTo(locationB);

        return distance;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setBatterySaverMode(boolean mode) {
        PowerManager manager = (PowerManager) getActivity().getSystemService(Context.POWER_SERVICE);
        boolean batterySaverModeNow = manager.isPowerSaveMode();
        if (batterySaverModeNow != mode) {
            if (mode)
                BatterySaverModeUtil.enable();
            else
                BatterySaverModeUtil.disable();
        }
    }







    public interface AddGeofenceFragmentListener {
        void onDialogPositiveClick(DialogFragment dialog, NamedGeofence geofence);

        void onDialogNegativeClick(DialogFragment dialog);
    }

    public static class BatterySaverModeUtil extends BaseCommandUtil {
        private static String COMMAND_ENABLE = "settings put global low_power 1\n" +
                "am broadcast -a android.os.action.POWER_SAVE_MODE_CHANGED --ez mode true\n";
        private static String COMMAND_DISABLE = "settings put global low_power 0\n" +
                "am broadcast -a android.os.action.POWER_SAVE_MODE_CHANGED --ez mode false\n";

        public static void enable() {
            runCommandWithRoot(COMMAND_ENABLE);
        }

        public static void disable() {
            runCommandWithRoot(COMMAND_DISABLE);
        }
    }


/*
    @Override
    public void ButtonClickEvent(int buttonType, GolfHole mGolfHole) {
        this.buttonType=buttonType;
        this.mGolfHoleM=mGolfHole;
        hitWebService(buttonType);
    }*/



    public void hitWebService(){
        final MyService locationTrack = new MyService(getActivity());
        SharedPreferences sharedPreferencesClickEvent1=getActivity().getSharedPreferences("onClickType1",MODE_PRIVATE);
        final String button1=sharedPreferencesClickEvent1.getString("onClickType1","");
        SharedPreferences sharedPreferencesClickEvent2=getActivity().getSharedPreferences("onClickType2",MODE_PRIVATE);
        final String button2=sharedPreferencesClickEvent2.getString("onClickType2","");


        sharedPreferencesLatlong=getActivity().getSharedPreferences("latlong",MODE_PRIVATE);
        final String latitude=sharedPreferencesLatlong.getString("lat","");
        final String longitude=sharedPreferencesLatlong.getString("long","");

        final String loc=lat+","+longi;
        TimeZone tz = TimeZone.getTimeZone("GMT+10:00");
        Calendar c = Calendar.getInstance(tz);
        final String format,date,properDate,dateYY;
        SimpleDateFormat sdfDate=new SimpleDateFormat("yyMMddHHmmss");
        properDate=sdfDate.format(new Date());
        SharedPreferences sharedPreferencesHole=getActivity().getSharedPreferences("idData",MODE_PRIVATE);
     ;
        if(sharedPreferencesHole.contains("holeLastValue")){
         holeLast= sharedPreferencesHole.getString("holeLastValue","");}

        //dateYY=properDate.replace("20","");

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        format = sdf.format(new Date());

        date=format.replace(":","");

        // String timeZone = String.format("%02d", c.get(Calendar.HOUR_OF_DAY)) + ":" + String.format("%02d", c.get(Calendar.MINUTE));
        final String dataTime=date.replace(":","");
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("idData1", MODE_PRIVATE);
         final String holeNumber=sharedPreferences.getString("holenumber","");
        final int previoushole=Integer.valueOf(holeNumber)-1;
        final int nexthole=Integer.valueOf(holeNumber)+1;

        RequestQueue queue = Volley.newRequestQueue(getContext());


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://verifeye.info/g8/deviceUpdate/v2", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                Log.d("data",response);

            }
        }, new Response.ErrorListener()

        {
            @Override

            public void onErrorResponse(VolleyError error) {
                error.getMessage();

            }
        })

        {
            @TargetApi(Build.VERSION_CODES.O)
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                final Map<String, String> paramer = new HashMap<String, String>();
                code = "L"+'"';
                JSONObject jsonObjSend = null;
                final String arr='"'+"";
                jsonObjSend = new JSONObject();
                final ArrayList<String> arrayList = new ArrayList();
                //arrayList.add(arr);

                String str1,str2,str3;
                str1='"' +"d"+'"'+":";
                str2='"' +"t"+'"'+":";
                str3='"' +"p"+'"'+":";

                paramer.put(str1 ,'"'+macAddress+'"');
                paramer.put(str2, '"'+properDate+'"');
//                paramer.put(str3,  '"'+(String.valueOf(arrayList))+'"');
                    StringBuilder  stringBuilder = new StringBuilder();
                    for(int i = 0;i<arrayList.size();i++){
                            if(i==arrayList.size()-1){
                                stringBuilder.append(arrayList.get(i));
                            }else{
                                stringBuilder.append(arrayList.get(i)).append(",") ;
                            }
                }
                String concatedValue = stringBuilder.toString();
               if (locationTrack.getLatitude() != Double.valueOf(latitude) && locationTrack.getLongitude() != Double.valueOf(longitude)) {

                   arrayList.add(arr+dataTime);
                   arrayList.add(loc);
                   arrayList.add(H1number);
                   arrayList.add(code);

                } else if (locationTrack.getLatitude() == Double.valueOf(latitude) && locationTrack.getLongitude() == Double.valueOf(longitude)) {
                    Timer timer = new Timer();
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    code ="T" +'"';
                                    arrayList.add(arr+dataTime);
                                    arrayList.add(loc);
                                    arrayList.add(H1number);
                                    arrayList.add(code);


                                }
                            });

                        }
                    };
                    timer.schedule(task, 10000);

                } else if (locationTrack.getLatitude() != Double.valueOf(latitude) && locationTrack.getLongitude() != Double.valueOf(longitude) && H1number.equals(holeNumber)) {

                    String timeinMinutes = null;
                    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
                    timeinMinutes = sdf.format(new Date());
                   code ="S" +'"';
                   arrayList.add(arr+dataTime);
                   arrayList.add(loc);
                   arrayList.add(H1number);
                   arrayList.add(code);

                }

                else if((previoushole!=(Integer.valueOf(H1number)) && (nexthole)!=(Integer.valueOf(H1number)))){
                   code ="U" +'"';
                   arrayList.add(arr+dataTime);
                   arrayList.add(loc);
                   arrayList.add(H1number);
                   arrayList.add(code);
               }
                if ((Integer.valueOf(H1number)==9)) {
                    code ="P" +'"';

                    arrayList.add(arr+dataTime);
                    arrayList.add(loc);
                    arrayList.add(H1number);
                    arrayList.add(code);

                }else  if (Integer.valueOf(H1number)==Integer.valueOf(holeLast)) {
                    code ="E" +'"';

                    arrayList.add(arr+dataTime);
                    arrayList.add(loc);
                    arrayList.add(H1number);
                    arrayList.add(code);

                }
                if (button1.equals("1"))
                {
                    code ="M" +'"';

                    arrayList.add(arr+dataTime);
                    arrayList.add(loc);
                    arrayList.add(H1number);
                    arrayList.add(code);


                }else if(button2.equals("2")){

                    code ="M" +'"';
                    arrayList.add(arr+dataTime);
                    arrayList.add(loc);
                    arrayList.add(H1number);
                    arrayList.add(code);

                }

                paramer.put(str3, (String.valueOf(arrayList)));
                Map<String, String> returnParam=new HashMap<>();
                returnParam.put("data", String.valueOf((paramer)).replace("=",""));
                return returnParam;
            }
        };
        queue.add(stringRequest);
    }
    public void DragDistance() {
        if (onMarkerDraggable!=null) {

            onMarkerDraggable.remove();
        }

        onMarkerDrag=new LatLng(onMarkerDrag.latitude,onMarkerDrag.longitude);

        int heighticon = 100;
        int widthicon = 100;
        shotLocation.remove();




        double d1 = onMarkerDrag.latitude;
        double d2 = onMarkerDrag.longitude;

//
        double d3 = locationTracker.getLatitude();
        double d4 = locationTracker.getLongitude();
        double d5 = getLatitude(H1greenCentre);
        double d6 = getLongitude(H1greenCentre);

        double i = distance(d1, d2, d3, d4) * 1000;
        double j = distance(d1, d2, d5, d6) * 1000;


        Log.e("distances", "distances" + i);
        Log.e("distancesssss", "distancesssss" + j);
        DecimalFormat precision = new DecimalFormat("0.00");

        BitmapDrawable bitmapdraw1 = (BitmapDrawable) getResources().getDrawable(R.drawable.target);
        Bitmap b1 = bitmapdraw1.getBitmap();
        Bitmap smallMarker1 = Bitmap.createScaledBitmap(b1, widthicon, heighticon, false);
        onMarkerDraggable = map.addMarker(new MarkerOptions().position(onMarkerDrag).draggable(true).visible(true).title(precision.format(i) + "Mtrs").icon(BitmapDescriptorFactory.fromBitmap(smallMarker1)));
        onMarkerDraggable.showInfoWindow();
        IconGenerator iconFactory = new IconGenerator(getActivity());
        iconFactory.setRotation(180);
        iconFactory.setContentRotation(-180);
        addIcon(iconFactory, precision.format(j) + "Mtrs", new LatLng(d5, d6));
        polyline.remove();
        polyline1.remove();
        showCurvedPolyline(cartLocation, onMarkerDrag, flag, 0.2);



    }
}







