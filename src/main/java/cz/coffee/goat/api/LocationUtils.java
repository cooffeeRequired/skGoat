package cz.coffee.goat.api;

import org.bukkit.Location;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class LocationUtils {
    public static boolean isLocationBetween(Location location, Location loc1, Location loc2) {
        Vector min = new Vector(
                Math.min(loc1.getX(), loc2.getX()),
                Math.min(loc1.getY(), loc2.getY()),
                Math.min(loc1.getZ(), loc2.getZ())
        );
        Vector max = new Vector(
                Math.max(loc1.getX(), loc2.getX()),
                Math.max(loc1.getY(), loc2.getY()),
                Math.max(loc1.getZ(), loc2.getZ())
        );
        Vector loc = location.toVector();

        return loc.getX() >= min.getX() && loc.getX() <= max.getX() &&
                loc.getY() >= min.getY() && loc.getY() <= max.getY() &&
                loc.getZ() >= min.getZ() && loc.getZ() <= max.getZ();
    }

    public static boolean isLocationWithin(Location location, Location center, double radius) {
        Vector loc = location.toVector();
        Vector cent = center.toVector();
        return loc.distance(cent) <= radius;
    }

    public static boolean isLocationWithin(Location location, Location loc1, Location loc2) {
        return isLocationBetween(location, loc1, loc2);
    }
}
