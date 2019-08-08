package me.cunzai.resourcespawner.data;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EditData {
    private static Map<UUID, EditData> editDataMap = new HashMap<>();

    private UUID uuid;
    private Location spawnLocation;
    private Location pointA;
    private Location pointB;
    private ItemStack item;
    private long time;
    private boolean multi;
    private int limit;
    private String name;

    public EditData(UUID uuid) {
        this.uuid = uuid;
    }

    public static void resetByUUID(UUID uuid) {
        editDataMap.put(uuid, new EditData(uuid));
    }

    public static boolean isExits(UUID uuid) {
        return editDataMap.get(uuid) != null;
    }

    public static EditData getByUUID(UUID uuid) {
        return editDataMap.get(uuid);
    }

    public static void saveData(UUID uuid, EditData editData) {
        editDataMap.put(uuid, editData);
    }

    public Location getPointA() {
        return pointA;
    }

    public void setPointA(Location pointA) {
        this.pointA = pointA;
    }

    public Location getPointB() {
        return pointB;
    }

    public void setPointB(Location pointB) {
        this.pointB = pointB;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public boolean isMulti() {
        return multi;
    }

    public void setMulti(boolean multi) {
        this.multi = multi;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public void setSpawnLocation(Location spawnLocation) {
        this.spawnLocation = spawnLocation;
    }
}
