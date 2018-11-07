package com.app.golfapp.utils;


//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.LatLngBounds;
/**
 * Created by Simon on 20180502
 *
 * 20180605
 *  Fixed some operator errors and changed: static double[] QuadToBox(double[] corners)
 *  to: static public double[] quadToBox(double[] corners)
 */
public class QuadToBox {
    /*
        Attempt to convert gx:LatLonQuad to LatLonBox which is supported by the Android KML-parser.
        Only deals with the Maths. Actual tag change in the file is not made here.

        Assumptions:
            - The gx:LatLonQuad coordinates mark the corners of a rectangle.
            - The rectangle is small. Ie can view the lat-lon coordinates as xy on a flat plane
              after adjusting the length of the longitudes.
    */

    protected static double maxError = 1e-6;
    //protected static double maxErrorRatio = 1e-8;

    public QuadToBox() {
        // Do nothing. Class is designed to use static methods.
    }

    /**
     *  The corners must be in the order UL lon, UL lat, UR lon, UR lat, LR lon, LR lat, LL lon,
     *  LL lat. Lon, Lat is the order LatLonQuad use in its coordinate tuples. Note that the corner
     *  order in the opposite order compared to the quad-element.
     *  https://developers.google.com/kml/documentation/kmlreference#gxlatlonquad
     *
     *  Return value is in north, south, east, west, anti clockwise rotation.
     *
     * @param corners an array containing 8 doubles defining the corners of the LatLonQuadBox
     * @return an array containing 5 doubles defining the LatLonBox
     */
    static public double[] quadToBox(double[] corners){
        double avg_lon = (corners[0] + corners[2] + corners[4] + corners[6])/4e0;
        double avg_lat = (corners[1] + corners[3] + corners[5] + corners[7])/4e0;

        // Convenience variables:

        double UL_lon = corners[0];
        double UL_lat = corners[1];
        double UR_lon = corners[2];
        double UR_lat = corners[3];
        double LR_lon = corners[4];
        double LR_lat = corners[5];
        double LL_lon = corners[6];
        double LL_lat = corners[7];

        // Conversion to xy
        double lonDistortion = getLonDistortion(avg_lat);
        double UL_x = (UL_lon - avg_lon) * lonDistortion;
        double UL_y = (UL_lat - avg_lat);
        double UR_x = (UR_lon - avg_lon) * lonDistortion;
        double UR_y = (UR_lat - avg_lat);
        double LR_x = (LR_lon - avg_lon) * lonDistortion;
        double LR_y = (LR_lat - avg_lat);
        double LL_x = (LL_lon - avg_lon) * lonDistortion;
        double LL_y = (LL_lat - avg_lat);

        double rot = getRotationAngle(
                            UL_x,
                            UL_y,
                            UR_x,
                            UR_y    );
        // We could make the rest on a few rows but lets keep it readable.
        // Rotation
        double cos_rot = Math.cos(-rot); // -rot since we are rotating the coordinates back
        double sin_rot = Math.sin(-rot);

        // Based on a 2d rotation matrix:
        double north_xy    = sin_rot * UL_x + cos_rot * UL_y;
        double south_xy    = sin_rot * LL_x + cos_rot * LL_y;
        double east_xy     = cos_rot * UR_x - sin_rot * UR_y;
        double west_xy     = cos_rot * UL_x - sin_rot * UL_y;

        // Conversion back to LatLon
        double north = avg_lat + north_xy;
        double south = avg_lat + south_xy;
        double east  = avg_lon + east_xy / lonDistortion;
        double west  = avg_lon + west_xy / lonDistortion;



        // Verification, rotating the new-box back (ie rot has changed sign):
        // Should not be necessary when we gain confidence in this code.
        // Based on the inverse matrix of the operations we did above.
        double cos_rot_a = cos_rot/lonDistortion;
        double sin_rot_a = sin_rot/lonDistortion;
        double UL_lon_test =  cos_rot_a * (west - avg_lon) + sin_rot_a * (north - avg_lat) + avg_lon;
        double UL_lat_test = -sin_rot   * (west - avg_lon) + cos_rot   * (north - avg_lat) + avg_lat;
        double UR_lon_test =  cos_rot_a * (east - avg_lon) + sin_rot_a * (north - avg_lat) + avg_lon;;
        double UR_lat_test = -sin_rot   * (east - avg_lon) + cos_rot   * (north - avg_lat) + avg_lat;;
        double LR_lon_test =  cos_rot_a * (east - avg_lon) + sin_rot_a * (south - avg_lat) + avg_lon;;
        double LR_lat_test = -sin_rot   * (east - avg_lon) + cos_rot   * (south - avg_lat) + avg_lat;;
        double LL_lon_test =  cos_rot_a * (west - avg_lon) + sin_rot_a * (south - avg_lat) + avg_lon;;
        double LL_lat_test = -sin_rot   * (west - avg_lon) + cos_rot   * (south - avg_lat) + avg_lat;;

        double corners_test[]=   {UL_lon_test, UL_lat_test, UR_lon_test, UR_lat_test,
                                 LR_lon_test, LR_lat_test, LL_lon_test, LL_lat_test };
        for(int i = 0; i<8;i++){
            if(Math.abs(corners[i]-corners_test[i]) > maxError){
                // Error larger than maxError decimal degrees. Do something.
                /*
                    According to Simons tests in python the error was larger than maxError but it
                    wasn't noticeable on google earth or android. Still the error value is
                    interesting for trouble shooting purposes.

                    maxError corresponds to about  ~0.1m. Actual error in meters will depend on
                    place on earth and rotation of the LatLonQuad. Also if we don't take into
                    account that the earth is an spheroid or ellipsoid the error might be up to
                    about 0.3% which on over 500 metres is a ~1.6 metre error. This is within the
                    error of GPS drift and acceptable for now.
                */
            }else{
                // Hope error is not noticeable.
            }
        }
        // End of verification

        double rot_deg = 180/Math.PI * rot;
        double[] ret = {north,south,east,west,rot_deg};
        return ret;
    }

    /**
     * Calculates the magnitude of a vector between two points.
     *
     * @param p1_x   Point 1 x-value
     * @param p1_y   Point 1 y-value
     * @param p2_x   Point 2 x-value
     * @param p2_y   Point 2 y-value
     * @return Magnitude of the vector from point 1 to point 2.
     */
    private double getMagnitude(double p1_x, double p1_y, double p2_x, double p2_y){
        return Math.sqrt( Math.pow(p2_x - p1_x, 2) + Math.pow(p2_y - p1_y, 2) );
    }

    /**
     * Returns the argument of a vector. Aka angle between vector and x-axis.
     * @param v_x
     * @param v_y
     * @return
     */
    private double getArgument(double v_x, double v_y){
        double mag = getMagnitude(v_x, v_y, 0,0);
        double cos = Math.acos(v_x/mag);

        // Adjust angles to get value for the correct solution branch.
        if(v_y < 0) {// cos(x) = cos(-x) -> have to adjust for solution branch.
            cos = -cos;
        }
        return cos;
    }

    /**
     *  Convenience function. Returns the rotation angle needed for vector v1 and v2 to have their
     *  y values being equal and positive.
     *
     *  @param v1_x Vector 1 x-value
     *  @param v1_y Vector 1 y-value
     *  @param v2_x Vector 2 x-value
     *  @param v2_y Vector 2 y-value
     *
     *  @return Angle needed for both vectors to be horizontal in radians
     */
    private static double getRotationAngle(double v1_x, double v1_y, double v2_x, double v2_y){

        double delta_x  = v2_x - v1_x;
        double delta_y  = v2_y - v1_y;
        double mag      = Math.sqrt(delta_x*delta_x + delta_y*delta_y); // should perhaps use getMagnitude since I have written that method.

        double rot = Math.acos( delta_x/mag );

        /*
            Check what solution branch we are at by checking which branch is closest to being
            horizontal after rotating the vector back by -rot.

            For example delta_x = -1, and delta_y = 1 should correspond to a negative value of rot.
        */
        double cos_rot = Math.cos(-rot); // -rot since we will rotate the vector back to a
        double sin_rot = Math.sin(-rot); // horizontal level.
        double branch1 =  sin_rot * delta_x + cos_rot * delta_y; // From 2d rotation matrix . We can make the two rows here more efficient.
        double branch2 = -sin_rot * delta_x + cos_rot * delta_y; // sin(-x) = -sin(x)
        if(Math.abs(branch2) < Math.abs(branch1)){ // We are solving for y_out == 0, smallest branch solution can be seen as the real one.
            rot = -rot;
        }

        return rot;
    }

    /**
     * Returns how shorter longitudes becomes as latitude change.
     *
     * @param latitude Latitude in decimal degrees
     * @return
     */
    private static double getLonDistortion(double latitude){
        /*
            This is where we could adjust for the earth being an ellipsoid and not a sphere.
            Either adding another method for latitude or

            This value is valid for longitudes but the way it is used we use it assumes a lon/lat
            is 1 at the equator.
         */
        return Math.cos(Math.PI/180e0 * latitude);

    }
}

