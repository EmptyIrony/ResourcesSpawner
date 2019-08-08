package me.cunzai.resourcespawner;

import me.cunzai.resourcespawner.command.Commands;
import me.cunzai.resourcespawner.manager.ConfigManager;
import me.cunzai.resourcespawner.runnable.Runnable;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class RS extends JavaPlugin {
    private static RS ins;
    private int pointNum;
    private ConfigManager configManager;

    public static RS getIns() {
        return ins;
    }

    @Override
    public void onEnable() {
        ins = this;

        getLogger().info("正在初始化资源点...");
        this.configManager = new ConfigManager(this);
        getLogger().info("成功初始化" + this.configManager.getNum() + "个资源点！");

        Bukkit.getPluginCommand("rs").setExecutor(new Commands(this));

        new Runnable().runTaskTimer(this, 100, 20);


    }


    public ConfigManager getConfigManager() {
        return configManager;
    }

}
