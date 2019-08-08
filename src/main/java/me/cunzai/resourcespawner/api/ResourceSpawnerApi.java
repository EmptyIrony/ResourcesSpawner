package me.cunzai.resourcespawner.api;

import me.cunzai.resourcespawner.RS;
import me.cunzai.resourcespawner.data.PointData;

public class ResourceSpawnerApi {
    public static void changePointEnableStatus(String pointName, boolean isEnable) {
        for (PointData point : RS.getIns().getConfigManager().getPoints()) {
            if (point.getName().equalsIgnoreCase(pointName)) {
                point.setEnable(!point.isEnable());
                return;
            }
        }
        try {
            throw new Exception("can't find point exception");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
