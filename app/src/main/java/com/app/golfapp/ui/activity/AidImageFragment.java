package com.app.golfapp.ui.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import com.viewpagerindicator.CirclePageIndicator;


import com.app.golfapp.R;
import com.app.golfapp.ui.adapter.SlidingImageAdapter;
import com.app.golfapp.ui.model.AdModel;
import com.app.golfapp.ui.model.GolfHole;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import fr.arnaudguyon.xmltojsonlib.XmlToJson;

/**
 * Created by nnarayan on 6/29/2018.
 */

public class AidImageFragment extends Activity {
    private ViewPager vpImage;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    String Adsfile,filePath;
    private CirclePageIndicator circlePageIndicator;
    private View view;
    private SharedPreferences sharedPreferences;
    private AdModel adModel;
    boolean swapped;
    int x=0;

    ArrayList<Bitmap> mBitmap=new ArrayList<>();
    ArrayList<String> arraylListFileName=new ArrayList<>();
    String[] adimages;
    Bitmap mBitmapAds;
    String[] childfile;
   // private static final Integer[] IMAGES= {R.drawable.ad_01,R.drawable.ad_02,R.drawable.ad_03,R.drawable.ad_04,R.drawable.ad_05,R.drawable.ad_06,R.drawable.ad_07,R.drawable.ad_08};
    private ArrayList<Bitmap> ImagesArray = new ArrayList<Bitmap>();



//    @Override
//    public View onCreate() {
//        view = inflater.inflate(R.layout.aid_image_fragment, container, false);
//    return view;}


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aid_image_fragment);
        adModel=new AdModel();
        sharedPreferences=getSharedPreferences("idData",MODE_PRIVATE);
       Adsfile= sharedPreferences.getString("adsFile","");
       filePath=sharedPreferences.getString("filePath","");
        try {
            extractCoordinatesFromJson();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        getImagesfromPath();

        initView();
    }
    private void initView(){
//        for(int i=0;i<mBitmap.size();i++)
//            ImagesArray.add(mBitmap[i]);
        vpImage=findViewById(R.id.pager);
        circlePageIndicator=findViewById(R.id.indicator);
        vpImage.setAdapter(new SlidingImageAdapter(this,mBitmap));
        circlePageIndicator.setViewPager(vpImage);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        circlePageIndicator.setRadius(5 * density);

        NUM_PAGES =mBitmap.size();

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                circlePageIndicator.setCurrentItem(currentPage++);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        // Pager listener over indicator
        circlePageIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }

    public void extractCoordinatesFromJson() throws FileNotFoundException, JSONException {
        File file=new File(Adsfile);
        FileInputStream fis = new FileInputStream(file);
        XmlToJson xmlToJson = new XmlToJson.Builder(fis, null).build();
        //  inputStream.close();
        JSONObject jsonObject = xmlToJson.toJson();
        String jsonString = xmlToJson.toString();
        //String formatted = xmlToJson.toFormattedString();
    //    JSONObject jsonObj = new JSONObject(String.valueOf(jsonObject));
        JSONArray jsonArray = jsonObject.getJSONObject("adSet").getJSONObject("imageList").getJSONArray("image");
        String adVersion=jsonObject.getJSONObject("adSet").getString("id");
        sharedPreferences=getSharedPreferences("idData",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("AdVersion",adVersion);
        editor.commit();

        for(int i=0;i<jsonArray.length();i++){
         String jobj=   jsonArray.getJSONObject(i).getString("fileName");
    //     adModel=new AdModel();
     //    adModel.setFileName(jobj);
         arraylListFileName.add(jobj);



        }
        String[] stockArr = new String[arraylListFileName.size()];
        adimages = arraylListFileName.toArray(stockArr);
       //adimages=(String[])arraylListFileName.toArray();


    }

    public void getImagesfromPath(){
        File file=new File(filePath);
        if(!file.exists()){
            file.mkdir();
        }
        else if(file.exists()){
            childfile = file.list();
            List<String > list = Arrays.asList(childfile);
            for(int j=0;j<arraylListFileName.size();j++){
                for(int k=0;k<list.size();k++){
                    if(arraylListFileName.get(j).equals(list.get(k))){
                        if(list.get(k).endsWith(".jpg") || list.get(k).endsWith(".png")|| list.get(k).endsWith(".jpeg")){
                            mBitmapAds = BitmapFactory.decodeFile(file.getPath() + "/" + childfile[k]);
                            mBitmap.add(mBitmapAds);


                        }



                    }

                }
            }












        }





    }



    }


