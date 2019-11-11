package com.leonardo.util;


/**
 * Small Java utility class for calculating the distance between two points using the Haversine formula
 * Based on https://github.com/jasonwinn/haversine
 */
public class HaversineAlgorithmUtil {

    private static final int EARTH_RADIUS = 6371; // Approx Earth radius in KM

    /**
     * Calculating the distance between two points using the Haversine formula
     * @param startLat
     * @param startLong
     * @param endLat
     * @param endLong
     * @return
     */
    public static double distance(double startLat, double startLong, double endLat, double endLong) {

        double dLat  = Math.toRadians((endLat - startLat));
        double dLong = Math.toRadians((endLong - startLong));

        startLat = Math.toRadians(startLat);
        endLat   = Math.toRadians(endLat);

        double a = haversin(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversin(dLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

    private static double haversin(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }
}
