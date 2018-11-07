package com.app.golfapp.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellSignalStrengthCdma;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthLte;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.test.UiThreadTest;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.golfapp.R;
import com.app.golfapp.ui.Services.MyService;
import com.app.golfapp.ui.adapter.NavigationAdapter;
import com.app.golfapp.ui.fragment.XMLParserFragment;
import com.app.golfapp.ui.listener.OnItemRecycleClickListener;
import com.app.golfapp.ui.model.GeoModel;
import com.app.golfapp.ui.model.GolfHole;
import com.app.golfapp.utils.ButtonCallback;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.NetworkInterface;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


import fr.arnaudguyon.xmltojsonlib.XmlToJson;

import static junit.framework.Assert.assertTrue;


public class NavigationDrawerActivity extends BaseActivity implements OnItemRecycleClickListener{

    public Toolbar toolbar;
    public String description = "77";
    String date;
    private Handler mToastHandler = null;
    private String hNumber;
    private static ArrayList<String> satPrn = new ArrayList<>();
    private static ArrayList<String> satSNR = new ArrayList<>();
    public StringBuilder res1;
    String strMac;
    ButtonCallback interfac;

    private RecyclerView rv_navigation;
    LocationManager locationManager;
    RelativeLayout logout_layout;
    private ArrayList<GeoModel> geoModels = new ArrayList<>();
    DrawerLayout drawer;
    Button SecrectSettings;
    int iClick = 0;
    String szSatellitesInView, szSatellitesInUse;
    private ArrayList<File> arrayListFile = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    int clickcount = 0;
    String s3, s4;
    ActionBarDrawerToggle toggle;
    int clickCount = 0;
    String h1cod1, h1cod2, h1cod3, h1cod4;
    String H1par;
    String satelite;
    ArrayList<GolfHole> mList;
    //ArrayList<GolfHole.GeoModel> newData =new ArrayList<>();
    public ImageView iButton, gButton;
    GolfHole mGolfHole;
    Handler mHandler;
    Runnable mRunnable;
    public static final String TAG = "AdvancedImmersiveModeFragment";
    RequestQueue requestQueue;
    PhoneStateListener phoneStateListener;

    public MyPhoneStateListener myPhoneStateListener = new MyPhoneStateListener();

    //  ArrayList<GolfHole> mGolfCourse1 = new ArrayList<>();
    //  ArrayList<GolfHole> mGolfCourse2 = new ArrayList<>();
    ArrayList<Bitmap> mGolfCourse3 = new ArrayList<>();
    ArrayList<Bitmap> mImage = new ArrayList<>();
    ArrayList<Bitmap> mImage1 = new ArrayList<>();
    ArrayList<Bitmap> mImage2 = new ArrayList<>();
    private static final int REQUEST_WRITE_PERMISSION = 786;
    String format;
    public String batterystats = "";
    public String BStatus = "";
    public static String BattPowerSource = "";
    private static LocationListener locationListener = new DummyLocationListener();

    String level;

    String temp, stmacc;

    SharedPreferences sharedPreferences1;
    String currentDateTimeString;
    String courseData = "0";
    String adDataVersion = "0";
    String geoPack = "0";
    String modelNumber;
    String s1, s2, s7, s8;
    String dBM, ASU;
    String data;
    String DeviceId, serial;
    GeoModel geo;
    String firmWareVersion = "";
    TelephonyManager mTelephonyManager;
    MyPhoneStateListener mPhoneStatelistener;
    int mSignalStrength = 0;

    String voltage;
    int level1;
    String macAddress = null;
    private GpsStatus gpsStatus;
    String ssid = null;
    String rssi = null;
    String health = null;
    String Asu,Dbm;
    int freeBytesInternal, freeBytesExternal;
    String number;
    String imeiNumber1 = null;
    String imeiNumber2 = null;
    private GpsListener gpsListener = new GpsListener();
    private Button testButton;
    private ButtonCallback buttonCallBack;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        mPhoneStatelistener = new MyPhoneStateListener();
        mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        mTelephonyManager.listen(mPhoneStatelistener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
        Dbm=String.valueOf(myPhoneStateListener.SignalStrength_dBm);
        Asu=String.valueOf(myPhoneStateListener.SignalStrength_ASU);

        gpsStatus = locationManager.getGpsStatus(null);
        locationManager.addGpsStatusListener(gpsListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
        sharedPreferences1 = getSharedPreferences("battery", MODE_PRIVATE);
        if (sharedPreferences1.contains("level")) {
            level = sharedPreferences1.getString("level", "");
            level1 = Integer.valueOf(level);
        }
        if (sharedPreferences1.contains("temp")) {
            temp = sharedPreferences1.getString("temp", "");
        }
        if (sharedPreferences1.contains("volt")) {
            voltage = sharedPreferences1.getString("volt", "");
        }
        if (sharedPreferences1.contains("status")) {
            batterystats = sharedPreferences1.getString("status", "");
        }
        if (sharedPreferences1.contains("batpower")) {
            BattPowerSource = sharedPreferences1.getString("batpower", "");
        }


        DeviceId = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        //  String modelnumber=Build.MANUFACTURER;
        serial = Build.SERIAL;
        modelNumber = Build.MODEL;


        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        try{
            firmWareVersion = tm.getDeviceSoftwareVersion();}catch (Exception e){
            e.printStackTrace();
        }


        try {
            for (final CellInfo info : tm.getAllCellInfo()) {
                if (info instanceof CellInfoGsm) {
                    final CellSignalStrengthGsm gsm = ((CellInfoGsm) info).getCellSignalStrength();
                    gsm.getAsuLevel();
                    gsm.getDbm();
                    // do what you need
                } else if (info instanceof CellInfoCdma) {
                    final CellSignalStrengthCdma cdma = ((CellInfoCdma) info).getCellSignalStrength();
                    cdma.getAsuLevel();
                    cdma.getDbm();
                    // do what you need
                } else if (info instanceof CellInfoLte) {
                    final CellSignalStrengthLte lte = ((CellInfoLte) info).getCellSignalStrength();
                    lte.getAsuLevel();
                    lte.getDbm();
                    // do what you need
                } else {
                    try {
                        throw new Exception("Unknown type of cell signal!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if (Build.VERSION.SDK_INT >= 23) {
                imeiNumber1 = tm.getDeviceId(1);
                imeiNumber2 = tm.getDeviceId(2);
            } else {
                imeiNumber1 = tm.getDeviceId();
            }


            number = tm.getLine1Number();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }


        if (level1 <= 30) {
            health = "bad";
        } else if (level1 <= 70) {
            health = "good";

        } else {
            health = "excellent";
        }
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        //macAddress = wInfo.getMacAddress();
        ssid = wInfo.getSSID();
        rssi = String.valueOf(wInfo.getRssi());
        getMacAddr();
        //batteryLevel();

        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
        format = s.format(new Date());

        date=format.replace("-","");


        freeBytesInternal = (int) (new File(getFilesDir().getAbsoluteFile().toString()).getFreeSpace() / (1024 * 1024 * 1024));
        freeBytesExternal = (int) new File(getExternalFilesDir(null).toString()).getFreeSpace() / (1024 * 1024 * 1024);

        mList = new ArrayList<>();


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mGolfHole = new GolfHole();

        Date date = new Date(SystemClock.uptimeMillis());

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        currentDateTimeString = sdf.format(date);

        setContentView(R.layout.activity_navigation_drawer);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        testButton = (Button) findViewById(R.id.testButton);

        testButton.setVisibility(View.GONE);
        setToolbar();
        sharedPreferences = getSharedPreferences("idData", MODE_PRIVATE);
        if (sharedPreferences.contains("id")) {
            courseData = sharedPreferences.getString("id", "");
        } else {
            courseData = "0";
        }
        if (sharedPreferences.contains("GeoId")) {
            geoPack = sharedPreferences.getString("GeoId", "");
        } else {
            geoPack = "0";
        }
        if (sharedPreferences.contains("AdVersion")) {

            adDataVersion = sharedPreferences.getString("AdVersion", "");
        } else {
            adDataVersion = "0";
        }
        initView();
        initObjects();
        setNavigationDrawer();

        //  if(format!=null && DeviceId!=null && modelNumber!=null&&serial!=null && health!=null)
        //   data = format + "," + DeviceId + "," + "0"+"," + modelNumber + "," + serial + "," + health + "," + level1+"%" + "," + batterystats + "," + temp+" C" + "," + voltage+" mV" + "," + currentDateTimeString + "," + imeiNumber1 + "," + imeiNumber2 + "," + s7 + "," + s8 + "," + number + "," + macAddress + "," + ssid + "," + rssi + "," + String.valueOf(freeBytesInternal) + "," + String.valueOf(freeBytesExternal) + "," + szSatellitesInView + "," + szSatellitesInUse + "," + satelite + "," + firmWareVersion+"," + courseData + "," + adDataVersion + "," + geoPack;


        //   requestPermission();

        testFlagsChanged();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ActivityCompat.checkSelfPermission(NavigationDrawerActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                //  locationManager.addGpsStatusListener(gpsListener);


            }
        }, 1000);

        data = format + "," + macAddress + "," + description + "," + modelNumber + "," + serial + "," + health + "," + level1 + "%" + "," + batterystats + "(" + BattPowerSource + ")" + "," + temp + " C" + "," + voltage + " mV" + "," + currentDateTimeString + "," + imeiNumber1 + "," + imeiNumber2 + "," + Dbm + ","+ Asu +"asu"+ "," + number + "," + DeviceId + "," + ssid + "," + rssi + "," + String.valueOf(freeBytesInternal) + "," + String.valueOf(freeBytesExternal) + "," + szSatellitesInView + "," + szSatellitesInUse + "," + satelite + "," + firmWareVersion + "," + courseData + "," + adDataVersion + "," + geoPack;
        requestQueue = Volley.newRequestQueue(NavigationDrawerActivity.this);

        Url();



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

                res1 = new StringBuilder();
                // strMac=res1.toString();


                for (byte b : macBytes) {
                    res1.append(String.format("%02x", b));
                }

              /* if (res1.length() > 0) {
                  res1.deleteCharAt(res1.length() - 1);
              }*/
                macAddress=res1.toString().toUpperCase();

                return res1.toString();
            }
        } catch (Exception ex) {
            //handle exception
        }
        return "";
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (satelite != null) {

        }
    }


    private void gpsTest() {


        if (ActivityCompat.checkSelfPermission(NavigationDrawerActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


        GpsStatus gpsStatus = locationManager.getGpsStatus(null);
        if (gpsStatus != null) {
            Iterable<GpsSatellite> satellites = gpsStatus.getSatellites();
            Iterator<GpsSatellite> sat = satellites.iterator();
            int i = 0;
            while (sat.hasNext()) {
                GpsSatellite satellite = sat.next();
                i++;
                satellite.getPrn();
                satPrn.add(String.valueOf(satellite.getPrn()));
                satellite.getSnr();
                satSNR.add(String.valueOf(satellite.getSnr()));


            }
            int iTempCountInView = 0;
            int iTempCountInUse = 0;
            if (satellites != null) {
                for (GpsSatellite gpsSatellite : satellites) {
                    iTempCountInView++;
                    if (gpsSatellite.usedInFix()) {
                        iTempCountInUse++;
                    }
                }
                szSatellitesInView = String.valueOf(iTempCountInView);
                szSatellitesInUse = String.valueOf(iTempCountInUse);


            }


        }
        addData();

    }


    private void addData() {
        satelite = "";
        for (int j = 0; j < satPrn.size(); j++) {
            satPrn.get(j);
            satSNR.get(j);
            satelite = satelite + String.valueOf(satPrn.get(j)) + " : " + String.valueOf(satSNR.get(j));
            sharedPreferences = getSharedPreferences("idData", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("satellites", satelite);


        }


    }


    class GpsListener implements GpsStatus.Listener {


        @Override
        public void onGpsStatusChanged(int event) {
            gpsTest();

            //    addData();


        }

    }


    public void Url() {
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://verifeye.info/g8/deviceStatus", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        String s1 = jsonObject.getString("sentinel");
                        s2 = jsonObject.getString("geo");
                        s3 = jsonObject.getString("course");
                        s4 = jsonObject.getString("ad");
                        // getDataFromLocalStorageOptimised();
                                if (s2.equalsIgnoreCase("latest") && s3.equalsIgnoreCase("latest") && s4.equalsIgnoreCase("latest")) {

                            getDataFromLocalStorageOptimised();
                                      } else {
                            new RedefineData().execute();
                        }
                        // TODO Auto-generated method stub
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }, new Response.ErrorListener()

            {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.getMessage();


                }
            })

            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> paramer = new HashMap<String, String>();
                    //  paramer.put("data", "2017-05-02 03:26:39,54EF92A721F4,0,G8R1A4S,0123456789ABCDEF,good,52%,Charging (USB),22 C,3833 mV,02:51:59,864795085558674,861487713209911,MT8312CW_G8R1A4S_2016053117,22asu,864795085558674|null|||null|null|,54:ef:92:38:4e:3e,\"NETGEAR76\",3,n/a,n/a,3,1,[10:28,16:22,19:36],1.8.1,0,0,0");
                    paramer.put("data", data);
                    return paramer;
                }
            };
            requestQueue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    private boolean unpackZip(String filePath) {
        InputStream is;
        ZipInputStream zis;
        try {

            File zipfile = new File(filePath);
            String parentFolder = zipfile.getParentFile().getPath();
            String filename;

            is = new FileInputStream(filePath);
            zis = new ZipInputStream(new BufferedInputStream(is));
            ZipEntry ze;
            byte[] buffer = new byte[1024];
            int count;

            while ((ze = zis.getNextEntry()) != null) {
                filename = ze.getName();

                if (ze.isDirectory()) {
                    File fmd = new File(parentFolder + "/" + filename);
                    fmd.mkdirs();
                    continue;
                }

                FileOutputStream fout = new FileOutputStream(parentFolder + "/" + filename);

                while ((count = zis.read(buffer)) != -1) {
                    fout.write(buffer, 0, count);
                }

                fout.close();
                zis.closeEntry();
            }

            zis.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    class RedefineData extends AsyncTask<String, Integer, String> {
        FileOutputStream fileOutput;
        File dataFile;
        InputStream input;
        OutputStream output;
        HttpURLConnection connection = null;
        String destinationFilePath = "";

        RedefineData() {
            input = null;
            output = null;
        }




        @Override
        protected String doInBackground(String... strings) {
            try {
                String value = getDatatoLocalStorage(s2);
                String value1 = getDatatoLocalStorage(s3);
                String value2 = getDatatoLocalStorage(s4);


            } catch (Exception e) {
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            getDataFromLocalStorageOptimised();
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();


        }


    }

    private String getDatatoLocalStorage(String s) {

        FileOutputStream fileOutput;
        File dataFile;
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
        String destinationFilePath = "";
        try {

            URL url = new URL(s);
            destinationFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/golfapplication/";
//            if(destinationFilePath)
            File file = new File(destinationFilePath);
            if (!file.exists()) {
                file.mkdir();
            }

            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(60000);
            connection.setReadTimeout(60000);
            connection.connect();


            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "Server returned HTTP " + connection.getResponseCode() + " " + connection.getResponseMessage();
            }

            // download the file
            input = connection.getInputStream();

            Log.d("DownloadFragment ", "destinationFilePath=" + destinationFilePath);
            dataFile = new File(destinationFilePath, "/hello.zip");
            output = new FileOutputStream(dataFile);

            byte data1[] = new byte[10000];
            int count;
            while ((count = input.read(data1)) != -1) {
                output.write(data1, 0, count);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        } finally {
            try {
                if (output != null)
                    output.close();
                if (input != null)
                    input.close();
            } catch (IOException ignored) {

            }

            if (connection != null)
                connection.disconnect();
        }

        File f = new File(destinationFilePath);

        Log.d("DownloadFragment ", "f.getParentFile().getPath()=" + f.getParentFile().getPath());
        Log.d("DownloadFragment ", "f.getName()=" + f.getName().replace(".zip", ""));
        unpackZip(dataFile.toString());


        return destinationFilePath;
    }


    @UiThreadTest
    public void testFlagsChanged() {
        final int uiFlags = getWindow().getDecorView().getSystemUiVisibility();
        //mTestFragment.mImmersiveModeCheckBox.setChecked(true);
        //mTestFragment.mHideNavCheckbox.setChecked(true);
        // mTestFragment.mHideStatusBarCheckBox.setChecked(true);
        toggleUiFlags();
        mToastHandler = new Handler();

        final int newUiFlags = getWindow().getDecorView().getSystemUiVisibility();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                assertTrue("UI Flags didn't toggle.", uiFlags != newUiFlags);


            }
        }, 50);


    }


    @SuppressLint("LongLogTag")
    public void toggleUiFlags() {
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        ;

        // BEGIN_INCLUDE (get_current_ui_flags)
        // The "Decor View" is the parent view of the Activity.  It's also conveniently the easiest
        // one to find from within a fragment, since there's a handy helper method to pull it, and
        // we don't have to bother with picking a view somewhere deeper in the hierarchy and calling
        // "findViewById" on it.
        boolean isImmersiveModeEnabled =
                ((uiOptions | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == uiOptions);
        if (isImmersiveModeEnabled) {
            Log.i(TAG, "Turning immersive mode mode off. ");
            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        } else {
            Log.i(TAG, "Turning immersive mode mode on.");
        }

        // Immersive mode: Backward compatible to KitKat (API 19).
        // Note that this flag doesn't do anything by itself, it only augments the behavior
        // of HIDE_NAVIGATION and FLAG_FULLSCREEN.  For the purposes of this sample
        // all three flags are being toggled together.
        // This sample uses the "sticky" form of immersive mode, which will let the user swipe
        // the bars back in again, but will automatically make them disappear a few seconds later.
        newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);


        // dumpFlagStateToLog(uiOptions);
    }


    private void initView() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        iButton = (ImageView) findViewById(R.id.forward);
        iButton.setFocusableInTouchMode(true);
        gButton = (ImageView) findViewById(R.id.backward);
        gButton.setFocusableInTouchMode(true);
        rv_navigation = (RecyclerView) findViewById(R.id.rv_navigation);
        logout_layout = (RelativeLayout) findViewById(R.id.logout_layout);
        SecrectSettings = (Button) findViewById(R.id.secret);
        rv_navigation.setLayoutManager(new LinearLayoutManager(this));
        rv_navigation.setAdapter(new NavigationAdapter(this, this));


        SecrectSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickcount = clickcount + 1;

                if (clickcount == 5) {
                    Intent intent = new Intent(NavigationDrawerActivity.this, EnterPinActivity.class);
                    startActivity(intent);
                    clickcount = 0;

                }

            }
        });





        iButton.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           if (iClick < mList.size() - 1) {
                                             //  ButtonClickEvent("1");
                                               iClick++;
                                               String holNumber=mList.get(iClick).getHnumber();
                                               sharedPreferences = getSharedPreferences("idData1", MODE_PRIVATE);
                                               SharedPreferences.Editor editor = sharedPreferences.edit();
                                               editor.putString("holenumber", holNumber);
                                               editor.commit();
                                               int type=1;
                                               SharedPreferences sharedPreferencesClickevent1 = getSharedPreferences("onClickType1", MODE_PRIVATE);
                                               SharedPreferences.Editor editor1 = sharedPreferencesClickevent1.edit();
                                               editor1.putString("onClickType1", String.valueOf(type));
                                               editor1.commit();


                                               //   ButtonClickEvent(1,mList.get(iClick));
                                               replaceFragment(XMLParserFragment.newInstance(1, mList.get(iClick)));


                                           } else {


                                           }
                                       }

                                   }
        );


        gButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int i = mList.size();
                gButton.setFocusableInTouchMode(false);
              //  ButtonClickEvent("2");
                if (iClick > 0) {
                    iClick--;


                    String holNumber=mList.get(iClick).getHnumber();

                    sharedPreferences = getSharedPreferences("idData1", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("holenumber", holNumber);
                    editor.commit();

                    int type2=2;
                    SharedPreferences sharedPreferencesClickevent2 = getSharedPreferences("onClickType2", MODE_PRIVATE);
                    SharedPreferences.Editor editor2 = sharedPreferencesClickevent2.edit();
                    editor2.putString("onClickType2", String.valueOf(type2));
                    editor2.commit();



                 //   ButtonClickEvent(2,mList.get(iClick));
                    replaceFragment(XMLParserFragment.newInstance(1, mList.get(iClick)));


                } else {

                }


            }
        });
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                System.out.println();

            }

            @Override
            public void onDrawerOpened(View drawerView) {


            }

            @Override
            public void onDrawerClosed(View drawerView) {
                System.out.println();

            }

            @Override
            public void onDrawerStateChanged(int newState) {
                System.out.println();

            }
        });


    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);


        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) //To fullscreen
        {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_navigation_drawer);

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // no need to fullscreen
            setContentView(R.layout.activity_navigation_drawer);

        }
    }


    private void setNavigationDrawer() {
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

    }



    public void setDrawerEnabled(boolean enabled) {
        int lockMode = enabled ? DrawerLayout.LOCK_MODE_UNLOCKED :
                DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
        drawer.setDrawerLockMode(lockMode);
        toggle.setDrawerIndicatorEnabled(enabled);
    }

    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setVisibility(View.GONE);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            setDialog();
        }
    }

    public void setDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(NavigationDrawerActivity.this);
        // Setting Dialog Title
        alertDialog.setTitle("Notice");
        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want to go on Main Screen?");
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(getApplicationContext(), NavigationDrawerActivity.class);
                startActivity(intent);
                //finish();
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                //Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }


    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void closeDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }


    @Override
    public void onItemClick(View view, int position) {

        if (position == 0) {
            closeDrawer();
        } else if (position == 1) {
            startActivity(new Intent(NavigationDrawerActivity.this, EnterPinActivity.class));
            finish();
            closeDrawer();
        }


    }


    public void LockScreen() {

        Intent intent = new Intent(NavigationDrawerActivity.this, EnterPinActivity.class);
        startActivity(intent);

    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        sendBroadcast(closeDialog);

    }

    public void extractGolfCourseDatafromJson(File file) throws IOException, JSONException {

        //AssetManager assetManager = getApplicationContext().getAssets();
        FileInputStream fis = new FileInputStream(file);


        //    mList.add(mGolfCourse1.get);
        //InputStream inputStream = assetManager.open("bayview_golf_club.xml");
        XmlToJson xmlToJson = new XmlToJson.Builder(fis, null).build();
        //  inputStream.close();
        JSONObject jsonObject = xmlToJson.toJson();
        String jsonString = xmlToJson.toString();
        String formatted = xmlToJson.toFormattedString();
        JSONObject jsonObj = new JSONObject(String.valueOf(jsonObject));
        // String facilityName=jsonObj.getString()
        JSONArray jsonArray = jsonObj.getJSONObject("facility").getJSONArray("course");
        courseData = jsonObj.getJSONObject("facility").getJSONObject("vcourse").getString("id");
        String facilityName = jsonObj.getJSONObject("facility").getJSONObject("vcourse").getString("name");

        if (courseData != null) {

            sharedPreferences = getSharedPreferences("idData", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("id", courseData);
            editor.putString("facilityname", facilityName);
            editor.commit();
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            jsonArray.getJSONObject(i);


            JSONArray hole = jsonArray.getJSONObject(i).getJSONArray("hole");
            for (int j = 0; j < hole.length(); j++) {
                JSONObject holeData = hole.getJSONObject(j);
                 mGolfHole = new GolfHole();

                if (holeData.has("c1")) {

                    mGolfHole.setHcod1((String) holeData.get("c1"));

                }
                if (holeData.has("c2")) {
                    mGolfHole.setHcod2((String) holeData.get("c2"));

                }
                if (holeData.has("c3")) {
                    mGolfHole.setHcod3((String) holeData.get("c3"));

                }
                if (holeData.has("c4")) {
                    mGolfHole.setHcod4((String) holeData.get("c4"));

                }
                if (holeData.has("label")) {
                    hNumber=String.valueOf(holeData.get("label"));


                    mGolfHole.setHnumber(hNumber);
                    sharedPreferences=getSharedPreferences("idData",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("holeLastValue",hNumber);
                    editor.commit();
                }
                if (holeData.has("par")) {
                    mGolfHole.setHpar((String) holeData.get("par"));

                }
                if (holeData.getJSONObject("greenCenter").has("coordinate")) {
                    mGolfHole.setHgreenCenter((String) holeData.getJSONObject("greenCenter").get("coordinate"));

                }
                if (holeData.getJSONObject("greenBack").has("coordinate")) {
                    mGolfHole.setHgreenBack((String) holeData.getJSONObject("greenBack").get("coordinate"));

                }
                if (holeData.getJSONObject("greenFront").has("coordinate")) {
                    mGolfHole.setHgreenFront((String) holeData.getJSONObject("greenFront").get("coordinate"));

                }


                mList.add(mGolfHole);
            }
            Log.d("mGolfHole", "" + mGolfHole);

        }


        Log.d("document", "" + jsonString);
        Log.d("document", "" + formatted);


    }


    static class DummyLocationListener implements LocationListener {
        //Empty class just to ease instatiation
        @Override
        public void onLocationChanged(Location location) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }


    private class MyPhoneStateListener extends PhoneStateListener {
        public static final int INVALID = Integer.MAX_VALUE;

        public int signalStrengthDbm = INVALID;
        public int signalStrengthAsuLevel = INVALID;
        public int SignalStrength_ASU ;
        public int SignalStrength_dBm;

        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength)
        {
            signalStrengthDbm = getSignalStrengthByName(signalStrength, "getDbm");
            signalStrengthAsuLevel = getSignalStrengthByName(signalStrength, "getAsuLevel");
        }

        private int getSignalStrengthByName(SignalStrength signalStrength, String methodName)
        {
            try
            {
                Class classFromName = Class.forName(SignalStrength.class.getName());
                java.lang.reflect.Method method = classFromName.getDeclaredMethod(methodName);
                Object object = method.invoke(signalStrength);
                SignalStrength_ASU = signalStrength.getGsmSignalStrength();
                SignalStrength_dBm = (2 * SignalStrength_ASU) - 113;
                return (int)SignalStrength_dBm;
            }
            catch (Exception ex)
            {
                return INVALID;
            }
        }
    }


    private void getDataFromLocalStorageOptimised() {
        try {


            geoModels.clear();
            File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/golfApplication");
            // File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/golfapplication/");
            boolean success = true;
            if (!dir.exists()) {
                dir.mkdir();
            }
            if (dir.exists()) {
                if (dir.listFiles() == null) {

                } //it never execute this line even though its null
                else {
                    File childfile[] = dir.listFiles();
                    Log.i("file no is ", Integer.toString(childfile.length));
                    if (childfile.length == 0) {

                    } else {
                        ArrayList<File> file = new ArrayList<File>();
                        ArrayList<File> subFolder1 = new ArrayList<File>();
                        ArrayList<File> SubFolder = new ArrayList<File>();
                        ArrayList<File> files = new ArrayList<File>();
                        ArrayList<File> fileImagesCourse1 = new ArrayList<File>();
                   /* for (int i = 0; i < childfile.length; i++) {

                        file.add(childfile[i]);
                    }*/
                        File childfile1[] = null;

                        if (childfile[1] != null && childfile[1].isDirectory() && childfile[1].list() != null && !childfile[1].getName().startsWith("_")) {
                            if (childfile[1].exists()) {
                                File file1 = childfile[1];
                                childfile1 = file1.listFiles();

                                for (File file2 : childfile1) {
                                    if (file2.isDirectory() && file2.list() != null) {
                                        // File childFile2[]=file2;
                                        files.add(file2);


                                    }
                                    try {
                                        if (file2.getName().endsWith(".xml"))
                                            extractGolfCourseDatafromJson(file2);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                if (files.get(0).exists() && files.get(0).list() != null) {

                                    File geoFile[] = files.get(0).listFiles();

                                    FileInputStream fileInputStream = null;
                                    try {
                                        fileInputStream = new FileInputStream(geoFile[0]);
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }

                                    XmlToJson xmlToJson = new XmlToJson.Builder(fileInputStream, null).build();
                                    JSONObject jsonObject1 = xmlToJson.toJson();
                                    try {
                                        int temp = 1;
                                        geoPack = jsonObject1.getJSONObject("geoSet").getString("id");
                                        temp = 1;

                                        JSONObject data = jsonObject1.getJSONObject("geoSet").getJSONObject("geoCircle");


                                        JSONObject geoPolyGon = jsonObject1.getJSONObject("geoSet").getJSONObject("geoPolygon");

                                        if (geoPolyGon instanceof JSONObject) {
                                            String Json = "[" + geoPolyGon + "]";
                                            JSONArray jobj = new JSONArray(Json);


                                            for (int i = 0; i < jobj.length(); i++) {
                                                JSONObject geoData = jobj.getJSONObject(i);


                                                geo = new GeoModel();
                                                if (geoData.has("coordinates")) {
                                                    geo.setCordinates((String) geoData.get("coordinates"));

                                                }

                                                if (geoData.has("type")) {

                                                    geo.setType((String) geoData.get("type"));

                                                }
                                                if (geoData.has("name")) {

                                                    geo.setName((String) geoData.get("name"));

                                                }
                                                if (geoData.has("hole")) {
                                                    if (geoData.get("hole").equals("")) {
                                                        geo.setHole(null);
                                                    } else {

                                                        geo.setHole((String) geoData.get("hole"));
                                                    }

                                                }
                                                geo.setTemp("2");

                                                geoModels.add(geo);
                                            }


                                        } else {
                                            JSONArray jsonArray = jsonObject1.getJSONObject("geoSet").getJSONArray("geoPolygon");
                                            for (int i = 0; i < jsonArray.length(); i++) {
                                                JSONObject geoData = jsonArray.getJSONObject(i);
                                                geo = new GeoModel();
                                                if (geoData.has("coordinates")) {
                                                    geo.setCordinates(geoData.getString("coordinates"));


                                                }
                                                if (geoData.has("type")) {

                                                    geo.setType((String) geoData.get("type"));

                                                }
                                                if (geoData.has("name")) {

                                                    geo.setName((String) geoData.get("name"));

                                                }
                                                if (geoData.has("hole")) {

                                                    geo.setHole((String) geoData.get("hole"));

                                                }
                                                geo.setTemp("2");
                                                geoModels.add(geo);


                                            }
                                        }


                                        if (data instanceof JSONObject) {
                                            String Json = "[" + data + "]";
                                            JSONArray jobj = new JSONArray(Json);


                                            for (int i = 0; i < jobj.length(); i++) {
                                                JSONObject geoData = jobj.getJSONObject(i);


                                                geo = new GeoModel();
                                                if (geoData.has("coordinates")) {
                                                    geo.setCordinates((String) geoData.get("coordinates"));

                                                }

                                                if (geoData.has("radius")) {

                                                    geo.setRadius((String) geoData.get("radius"));

                                                }
                                                if (geoData.has("name")) {

                                                    geo.setName((String) geoData.get("name"));

                                                }
                                                if (geoData.has("hole")) {
                                                    if (geoData.get("hole").equals("")) {
                                                        geo.setHole(null);
                                                    } else {

                                                        geo.setHole((String) geoData.get("hole"));
                                                    }

                                                }
                                                geo.setTemp("1");

                                                geoModels.add(geo);
                                            }


                                        } else {
                                            JSONArray jsonArray = jsonObject1.getJSONObject("geoSet").getJSONArray("geoCircle");
                                            for (int i = 0; i < jsonArray.length(); i++) {
                                                JSONObject geoData = jsonArray.getJSONObject(i);
                                                geo = new GeoModel();
                                                if (geoData.has("coordinates")) {
                                                    geo.setCordinates(geoData.getString("coordinates"));


                                                }
                                                if (geoData.has("radius")) {

                                                    geo.setRadius((String) geoData.get("radius"));

                                                }
                                                if (geoData.has("name")) {

                                                    geo.setName((String) geoData.get("name"));

                                                }
                                                if (geoData.has("hole")) {

                                                    geo.setHole((String) geoData.get("hole"));

                                                }
                                                geo.setTemp("1");
                                                geoModels.add(geo);


                                            }
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    sharedPreferences = getSharedPreferences("idData", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("GeoId", geoPack);

                                    editor.apply();


                                }
                                if (files.get(1).exists() && files.get(1).list() != null) {
                                    GolfHole mGolfHoleImages = new GolfHole();
                                    GolfHole mGolfHoleImages1 = new GolfHole();
                                    String[] fileNames1 = new String[0];
                                    String[] fileNames2 = new String[0];
                                    // String[] fileNames = new String[0];

                                    Bitmap mBitmap, bitmap;
                                    File courseImages[] = files.get(1).listFiles();
                                    for (int i = 0; i < courseImages.length; i++) {
                                        if (courseImages[i].list() != null) {
                                            fileImagesCourse1.add(courseImages[i]);


                                        }
                                        if (courseImages[i].getName().endsWith("jpg")) {

                                            SharedPreferences sharedPreferences = getSharedPreferences("welcomescreen", MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("welcome", courseImages[i].toString());
                                            editor.commit();


                                        }
                                    }


                                    for (int a = 0; a < fileImagesCourse1.size(); a++) {

                                        if (fileImagesCourse1.get(a).equals(fileImagesCourse1.get(0))) {

                                            fileNames1 = fileImagesCourse1.get(0).list();
                                            // ArrayList<File> tempElements = new ArrayList<File>();
                                            Collections.reverse(Arrays.asList(fileImagesCourse1.get(0).list()));
                                            //Arrays.sort(fileNames1);
                                            // List<String> myList = Arrays.asList(fileNames1);


                                            for (int i = 0; i < fileNames1.length; i++) {
                                                if (fileNames1[i].endsWith(".jpg") || fileNames1[i].endsWith(".jpeg") || fileNames1[i].endsWith(".png")) {
                                                    //   mBitmap= new BitmapFactory();

                                                        try {
                                                            mBitmap = BitmapFactory.decodeFile(fileImagesCourse1.get(a).getPath() + "/" + fileNames1[i]);
                                                            mGolfHoleImages = new GolfHole();
                                                            mImage.add(mBitmap);

                                                        }catch (OutOfMemoryError e)
                                                        {
                                                            e.printStackTrace();
                                                        }


                                                }


                                            }
                                        }
                                        if (fileImagesCourse1.get(a).equals(fileImagesCourse1.get(1))) {


                                            fileNames2 = fileImagesCourse1.get(1).list();


                                            for (int i = 0; i < fileNames2.length; i++) {
                                                if (fileNames2[i].endsWith(".jpg") || fileNames2[i].endsWith(".jpeg") || fileNames2[i].endsWith(".png")) {

                                                    //     mBitmap = BitmapFactory.decodeFile(fileImagesCourse1.get(a).getPath() + "/" + fileNames2[i]);
                                                    try
                                                    {
                                                        bitmap = BitmapFactory.decodeFile(fileImagesCourse1.get(a).getPath() + "/" + fileNames2[i]);
                                                        mGolfHoleImages = new GolfHole();
                                                        mImage1.add(bitmap);

                                                    }catch (OutOfMemoryError e){

                                                        e.printStackTrace();
                                                    }

                                                }

                                            }
                                        }
                                    }
                                }
                                if (files.get(2).exists() && files.get(2).list() != null) {

                                    String filePath = files.get(2).toString();
                                    File AdsFile[] = files.get(2).listFiles();
                                    File AdsFile1[] = files.get(2).listFiles();
                                    for (int i = 0; i < AdsFile1.length; i++) {
                                        if (AdsFile[i].getName().endsWith(".xml")) {

                                            sharedPreferences = getSharedPreferences("idData", MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("adsFile", AdsFile1[i].toString());
                                            editor.putString("filePath", filePath);
                                            editor.commit();

                                        }


                                    }


                                }


//


                            } else {

                            }
                        } else {

                        }
                    }


                }

            }
            mGolfCourse3.addAll(mImage);
            mGolfCourse3.addAll(mImage1);
            //   if(geo.getHole().equals())
            // mGolfCourse3.addAll(mGolfCourse2);

            for (int i = 0; i < mGolfCourse3.size(); i++) {
                //  mList.addAll(mGolfCourse1);
                // mList.addAll(mGolfCourse2);
                //     mList=new ArrayList<>();
                mList.get(i).setImages(mGolfCourse3.get(i));

                //   mList.get(i).setImages(mGolfCourse3.get(i).getImages());
                //  mGolfHole.setImages(mList.get(i).getImages());

            }
            for (int a = 0; a < mList.size(); a++) {

                for (int i = 0; i < geoModels.size(); i++) {
                    if (mList.get(a).getHnumber().equals(geoModels.get(i).getHole())) {
                        mList.get(a).setRadius(geoModels.get(i).getRadius());
                        mList.get(a).setType(geoModels.get(i).getType());
                        mList.get(a).setName(geoModels.get(i).getName());
                        mList.get(a).setCordinates(geoModels.get(i).getCordinates());
                        mList.get(a).setTemp(geoModels.get(i).getTemp());


                    }
                }
            }

            String holeNumber = mList.get(0).getHnumber();
            sharedPreferences = getSharedPreferences("idData1", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("holenumber", holeNumber);
            editor.commit();


            replaceFragment(XMLParserFragment.newInstance(1, mList.get(0)));

            //    hitWebService();

        } catch (Exception e) {
            e.printStackTrace();


        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        NavigationHide();

    }
    public void NavigationHide(){

        int currentApiVersion = android.os.Build.VERSION.SDK_INT;

        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                |View.SYSTEM_UI_FLAG_LOW_PROFILE;

        // This work only for android 4.4+
        if(currentApiVersion >= Build.VERSION_CODES.KITKAT)
        {

            getWindow().getDecorView().setSystemUiVisibility(flags);

            // Code below is to handle presses of Volume up or Volume down.
            // Without this, after pressing volume buttons, the navigation bar will
            // show up and won't hide
            final View decorView = getWindow().getDecorView();
            decorView
                    .setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener()
                    {

                        @Override
                        public void onSystemUiVisibilityChange(int visibility)
                        {
                            if((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0)
                            {
                                decorView.setSystemUiVisibility(flags);
                            }
                        }
                    });
        }



    }




}