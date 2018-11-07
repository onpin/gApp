package com.app.golfapp.utils;


import java.util.LinkedHashMap;


import io.reactivex.Observable;
import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by hmnsharma on 12/11/2017.
 */

public interface WebServiceInterface {

    @GET("eq_webservices.php?")
    Observable<Object> login(@Query("action") String url, @Query("deviceId") String usadrl);

    @GET("/getexpense1")
    public void getExpense(Callback<String> callback);

    @POST("eq_webservices.php/")
    @FormUrlEncoded
    Observable<Object> fetchedList(@FieldMap LinkedHashMap<String, String> linkedHashMap);



    @POST("eq_webservices.php?")
    Observable<Object> setting(@Query("action") String url,
                               @Query("deviceId") String usadrl,
                               @Query("isNotification") boolean isNotification,
                               @Query("soundName") String soundName,
                               @Query("disappear") String disappear,
                               @Query("magnitude") double magnitude,
                               @Query("depth") double depth,
                               @Query("regions") String regions,
                               @Query("deviceToken") String dtoken,
                               @Query("deviceType") String dtid,
                               @Query("timeZone") String timezone,
                               @Query("lastUpdated") String lastdate,
                               @Query("nightMode") String nightMode);

    @POST("eq_webservices.php?")
    @FormUrlEncoded
    Observable<Object> iFeelIt_service(@Field("action") String url,
                                       @Field("earthQuakeId") String eqid,
                                       @Field("earthQuakeTime") String eqtime,
                                       @Field("countryId") Integer countryid,
                                       @Field("city") String cityname,
                                       @Field("street") String streetname,
                                       @Field("earthQuakeDes") String eqdes,
                                       @Field("add_lat") String eqlat,
                                       @Field("add_lon") String eqlong,
                                       @Field("local_eq_time") String loctime,
                                       @Field("earthQuakeStrength") Integer eqstrength,
                                       @Field("deviceType") String deviceType,
                                       @Field("countryCode") String countrycode,
                                       @Field("countryName") String countryname,
                                       @Field("deviceId") String deviceID) ;

    @GET("eq_webservices.php?")
    Observable<Object> clear(@Query("action") String url,
                             @Query("deviceId") String usadrl,
                             @Query("last_qid") String lastid);

    @GET("eq_webservices.php?")
    Observable<Object> old_earthquake(@Query("action") String url,
                                      @Query("deviceId") String usadrl,
                                      @Query("filter_date") String selecteddate);

    @GET("eq_webservices.php?")
    Observable<Object> userexplist(@Query("action") String url,
                                   @Query("deviceId") String usadrl,
                                   @Query("post_id") String postid);

    @GET("eq_webservices.php?")
    Observable<Object> userexp(@Query("action") String url,
                               @Query("deviceId") String usadrl);

    @GET("eq_webservices.php?")
    Observable<Object> alerts (@Query("action") String url,
                               @Query("deviceId") String usadr,
                               @Query("deviceToken") String dtoken,
                               @Query("status") String statusSwitch);

    @GET("eq_webservices.php?")
    Observable<Object> prealerts (@Query("action") String url,
                                  @Query("deviceId") String usadrl);

    @GET("eq_webservices.php?")
    Observable<Object> language (@Query("action") String url,
                                 @Query("deviceId") String usadrl,
                                 @Query("language") String lang);


}

