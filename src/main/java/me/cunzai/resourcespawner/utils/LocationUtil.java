package me.cunzai.resourcespawner.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import java.util.Collection;

public class LocationUtil {
    public LocationUtil() {
    }

    public static String serialize(Location location) {
        return location.getWorld().getName() + ":" + location.getX() + ":" + location.getY() + ":" + location.getZ() + ":" + location.getYaw() + ":" + location.getPitch();
    }

    public static Location deserialize(String source) {
        if (source == null) {
            return null;
        } else {
            String[] split = source.split(":");
            World world = Bukkit.getServer().getWorld(split[0]);
            return world == null ? null : new Location(world, Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]), Float.parseFloat(split[4]), Float.parseFloat(split[5]));
        }
    }

    public static boolean isIn(Location locationA, Location locationB, Location location) {
        return (location.getX() - locationA.getX()) * (location.getX() - locationB.getX()) <= 0 && (location.getY() - locationA.getY()) * (location.getY() - locationB.getY()) <= 0 && (location.getZ() - locationA.getZ()) * (location.getZ() - locationB.getZ()) <= 0;
    }

    public static Collection<Entity> getNearByEntityOn(Location lo) {
        return lo.getWorld().getNearbyEntities(lo, 7, 7, 7);
    }

}
