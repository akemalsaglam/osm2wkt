package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Vector;

public class CoordinateTrasformator {

    private static int precisonFloating = 3; // use 3 decimals after comma for rounding

    public static boolean transformCoordinates(HashMap<Long, Landmark> landmarks) {

        // in this function we have to restrict the precision we calculate the x, y coordinates
        // of the point to avoid floating point errors
        System.out.println("transforming geographic landmarks ...");

        // search for top,bottom,left,right marks
        // latitude is horizontal from -90 -- 0 -- 90
        // longitude is vertical from -180 -- 0 -- 180
        double latMin, latMax, lonMin, lonMax;

        // initialize switched to move to value in for loop
        latMin = 30.8491;
        latMax = 31.4093;
        lonMin = 121.0222;
        lonMax = 121.877;
        /*latMin = 31.2221000;
        latMax = 31.2666000;
        lonMin = 121.4247000;
        lonMax = 121.4853000;*/

        // search for geographic bounds
        for (Landmark landmark : landmarks.values()) {
            if (landmark.latitude < latMin) latMin = landmark.latitude;
            if (landmark.latitude > latMax) latMax = landmark.latitude;
            if (landmark.longitude < lonMin) lonMin = landmark.longitude;
            if (landmark.longitude > lonMax) lonMax = landmark.longitude;
        }

        System.out.println("found geographic bounds:"
                + " latitude from " + latMin + " to " + latMax
                + " longitude from " + lonMin + " to " + lonMax);


        double width = geoDistance(latMin, lonMin, latMin, lonMax);
        double height = geoDistance(latMin, lonMin, latMax, lonMin);

        System.out.println("geographic area dimensions are: height "
                + height + "m, width " + width + "m");

        // put coordinate system to upper left corner with (0,0), output in meters
        for (Landmark landmark : landmarks.values()) {
            landmark.x = geoDistance(landmark.latitude, landmark.longitude, landmark.latitude, 121.877);
            landmark.y = geoDistance(landmark.latitude, landmark.longitude, 31.4093, landmark.longitude);
        }

        return true;
    }

    private static double geoDistance(double lat1, double lon1, double lat2, double lon2) {
        // return distance between two gps fixes in meters
        double R = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = (R * c * 1000.0d);
        distance = round(distance, precisonFloating);
        return distance;
    }

    private static double round(double d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Double.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }
}
