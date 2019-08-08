package me.cunzai.resourcespawner.runnable;

import me.cunzai.resourcespawner.RS;
import me.cunzai.resourcespawner.data.PointData;
import me.cunzai.resourcespawner.utils.LocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class Runnable extends BukkitRunnable {

    @Override
    public void run() {
        List<PointData> points = RS.getIns().getConfigManager().getPoints();
        if (points.size() == 0) {
            return;
        }

        for (PointData data : points) {
            if (!data.isEnable()) {
                continue;
            }
            int timer = data.getWaitTimer();
            if (timer >= data.getTime()) {
                Location spawn = data.getSpawn();
                if (data.getLimit() != 0) {
                    int num = 0;
                    for (Entity entity : LocationUtil.getNearByEntityOn(spawn)) {
                        if (entity instanceof Item) {

                            ItemStack item = ((Item) entity).getItemStack();
                            if (item.getType() == data.getItem().getType()) {
                                num = num + item.getAmount();
                            }

                        }
                    }
                    if (num >= data.getLimit()) {
                        continue;
                    }
                }
                if (data.isMulti()) {
                    int num = 0;
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (LocationUtil.isIn(data.getPointA(), data.getPointB(), player.getLocation())) {
                            num++;
                        }
                    }
                    if (num <= 1) {
                        spawn.getWorld().dropItemNaturally(spawn, data.getItem());
                    } else {
                        for (int i = 0; i < num; i++) {
                            spawn.getWorld().dropItemNaturally(spawn, data.getItem());
                        }
                    }
                } else {
                    spawn.getWorld().dropItemNaturally(spawn, data.getItem());
                }
                data.setWaitTimer(0);
            } else {
                data.setWaitTimer(data.getWaitTimer() + 1);
            }
        }
    }
}
