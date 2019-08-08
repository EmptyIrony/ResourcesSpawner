package me.cunzai.resourcespawner.manager;

import me.cunzai.resourcespawner.RS;
import me.cunzai.resourcespawner.data.EditData;
import me.cunzai.resourcespawner.data.PointData;
import me.cunzai.resourcespawner.utils.LocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static me.cunzai.resourcespawner.utils.StringUtil.cf;

public class ConfigManager {
    private RS plugin;
    private int num;
    private FileConfiguration config;
    private List<PointData> points;
    private File file;

    public ConfigManager(RS rs) {
        this.plugin = rs;
        loadConfig();
    }

    public void loadConfig() {
        this.points = new ArrayList<>();

        file = new File(plugin.getDataFolder(), "saves.config");
        this.config = YamlConfiguration.loadConfiguration(file);
        if (!file.exists()) {
            return;
        }

        for (String name : config.getConfigurationSection("points").getKeys(false)) {
            PointData data = new PointData();
            data.setName(name);
            data.setPointA(LocationUtil.deserialize(config.getString("points." + name + ".pointA")));
            data.setPointB(LocationUtil.deserialize(config.getString("points." + name + ".pointB")));
            data.setItem(config.getItemStack("points." + name + ".item"));
            data.setTime(config.getLong("points." + name + ".time"));
            data.setLimit(config.getInt("points." + name + ".limit"));
            data.setMulti(config.getBoolean("points." + name + ".multi"));
            data.setSpawn(LocationUtil.deserialize(config.getString("points." + name + ".spawn")));
            data.setEnable(true);
            points.add(data);
        }
        this.num = config.getConfigurationSection("points").getKeys(false).size();
    }

    public void savePoint(EditData data) {
        config.set("points." + data.getName() + ".pointA", LocationUtil.serialize(data.getPointA()));
        config.set("points." + data.getName() + ".pointB", LocationUtil.serialize(data.getPointB()));
        config.set("points." + data.getName() + ".item", data.getItem());
        config.set("points." + data.getName() + ".time", data.getTime());
        config.set("points." + data.getName() + ".multi", data.isMulti());
        config.set("points." + data.getName() + ".limit", data.getLimit());
        config.set("points." + data.getName() + ".spawn", LocationUtil.serialize(data.getSpawnLocation()));
        try {
            config.save(file);
        } catch (Exception e) {
            Bukkit.broadcastMessage(cf("&c文件保存异常！"));
        }
    }

    public void deletePoint(PointData data) {
        config.set("points." + data.getName(), null);
        try {
            config.save(file);
        } catch (Exception e) {
            Bukkit.broadcastMessage(cf("&c文件保存异常！"));
        }
    }


    public List<PointData> getPoints() {
        return points;
    }

    public int getNum() {
        return num;
    }
}
