package me.cunzai.resourcespawner.command;

import me.cunzai.resourcespawner.RS;
import me.cunzai.resourcespawner.data.EditData;
import me.cunzai.resourcespawner.data.PointData;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static me.cunzai.resourcespawner.utils.StringUtil.cf;
import static me.cunzai.resourcespawner.utils.StringUtil.stringToInt;

public class Commands implements CommandExecutor {
    private RS plugin;

    public Commands(RS rs) {
        this.plugin = rs;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            this.plugin.getLogger().info("你不能在命令台执行本命令");
            return true;
        }
        Player player = (Player) commandSender;

        if (!player.hasPermission("ResourcesSpawner.admin")) {
            player.sendMessage(cf("&c你没有权限使用本命令！"));
            return true;
        }

        if (s.equalsIgnoreCase("rs")) {
            if (!EditData.isExits(player.getUniqueId())) {
                EditData.saveData(player.getUniqueId(), new EditData(player.getUniqueId()));
            }

            EditData data = EditData.getByUUID(player.getUniqueId());
            if (strings.length >= 1) {
                if (strings[0].equalsIgnoreCase("pointA")) {

                    data.setPointA(player.getLocation());
                    player.sendMessage(cf("&a成功设置点A！"));

                    if (data.getPointB() == null) {
                        player.sendMessage(cf("&a我们注意到你还没有设置点B，输入/rs pointB来保存点B"));
                    } else {
                        player.sendMessage(cf("&a你已经成功设置完AB点，如果你需要更改ab点可以直接重新输入指令设置，接下来你可以输入/rs item来设置产生的物品"));
                    }

                    return true;

                } else if (strings[0].equalsIgnoreCase("pointB")) {

                    data.setPointB(player.getLocation());
                    player.sendMessage(cf("&a成功设置点B！"));

                    if (data.getPointA() == null) {
                        player.sendMessage(cf("&a我们注意到你还没有设置点A，输入/rs pointA来保存点A"));
                    } else {
                        player.sendMessage(cf("&a你已经成功设置完AB点！"));
                    }

                    return true;

                } else if (strings[0].equalsIgnoreCase("item") && strings.length == 1) {
                    TextComponent text1 = new TextComponent(cf("&a现在！拿着物品在你手上，并且点击"));
                    TextComponent clickMessage = new TextComponent(cf("&e&n&l这里保存"));
                    TextComponent text2 = new TextComponent(cf("&a,可以支持不同名字，不同lore保存，不同&c数量&a保存！"));

                    clickMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(cf("&e&l点击保存！")).create()));
                    clickMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/rs item save"));

                    text1.addExtra(clickMessage);
                    text1.addExtra(text2);

                    player.spigot().sendMessage(text1);
                    return true;
                } else if (strings[0].equalsIgnoreCase("item") && strings.length == 2 && strings[1].equalsIgnoreCase("save")) {
                    if (player.getItemInHand() == null || player.getItemInHand().getType() == Material.AIR) {
                        player.sendMessage(cf("&c你手上必须得拿着一样东西才可以"));
                        return true;
                    }

                    data.setItem(player.getItemInHand());
                    player.sendMessage(cf("&a成功保存产出物品！"));
                    return true;
                } else if (strings[0].equalsIgnoreCase("time")) {
                    if (strings.length == 1) {
                        player.sendMessage(cf("&c必须输入参数,例子/rs time 5"));
                        return true;
                    }

                    int timer;
                    try {
                        timer = stringToInt(strings[1]);
                    } catch (Exception e) {
                        player.sendMessage(cf("&c参数必须为整数(int型)"));
                        return true;
                    }
                    if (timer <= 0) {
                        player.sendMessage("&c必须输入大于0的数！");
                        return true;
                    }

                    data.setTime(timer);

                    player.sendMessage(cf("&a成功设置时间！"));
                    return true;
                } else if (strings[0].equalsIgnoreCase("multi")) {
                    if (data.isMulti()) {
                        player.sendMessage(cf("已关闭倍率生成"));
                        data.setMulti(false);
                        return true;
                    } else {
                        player.sendMessage(cf("&a已开启倍率生成，如需关闭，再输入一次&e/rs multi"));
                        data.setMulti(true);
                        return true;
                    }
                } else if (strings[0].equalsIgnoreCase("limit")) {
                    if (strings.length == 1) {
                        player.sendMessage(cf("&c必须输入参数,例子/rs limit 10"));
                        return true;
                    } else {
                        int num;
                        try {
                            num = stringToInt(strings[1]);
                        } catch (Exception e) {
                            player.sendMessage(cf("&c参数必须为正整数"));
                            return true;
                        }
                        if (num < 0) {
                            player.sendMessage("&c必须输入一个正整数！如果limit设置为0则为无限制");
                            return true;
                        }

                        data.setLimit(num);
                        player.sendMessage(cf("&ac成功！"));
                        return true;
                    }
                } else if (strings[0].equalsIgnoreCase("save")) {
                    if (strings.length == 1) {
                        player.sendMessage(cf("&c必须输入名称,例子/rs save 1"));
                        return true;
                    } else {
                        if (data.getPointA() == null) {
                            player.sendMessage(cf("&c你好像还没有设置pointA，站在A点输入&e/rs pointA&c来设置A点"));
                            return true;
                        }
                        if (data.getPointB() == null) {
                            player.sendMessage(cf("&c你好像还没有设置pointB，站在B点输入&e/rs pointB&c来设置B点"));
                            return true;
                        }
                        if (data.getItem() == null) {
                            player.sendMessage(cf("&c你好像还没有设置产出物品，输入&e/rs item&c来设置产出物品"));
                            return true;
                        }
                        if (data.getSpawnLocation() == null) {
                            player.sendMessage(cf("&c你好像还没有设置产出位置，站在产出位置输入&e/rs spawn&c来设置产出位置"));
                            return true;
                        }
                        if (data.getTime() == 0) {
                            player.sendMessage(cf("&c你好像还没有设置产出时间间隔，输入&e/rs time 时间 &c即可设置产出时间间隔"));
                            return true;
                        }
                        String name = strings[1];
                        data.setName(name);
                        this.plugin.getConfigManager().savePoint(data);
                        this.plugin.getConfigManager().loadConfig();
                        EditData.resetByUUID(player.getUniqueId());
                        player.sendMessage(cf("&a保存成功！"));
                        return true;
                    }

                } else if (strings[0].equalsIgnoreCase("spawn")) {
                    data.setSpawnLocation(player.getLocation());
                    player.sendMessage(cf("&a成功保存物品产出点！"));
                    return true;
                } else if (strings[0].equalsIgnoreCase("remove") || strings[0].equalsIgnoreCase("delete")) {
                    if (strings.length == 1) {
                        player.sendMessage(cf("&c用法: /rs delete 产出点名称"));
                        return true;
                    }
                    final List<PointData> deleteData = new ArrayList<>();
                    this.plugin.getConfigManager().getPoints().forEach(pointData -> {
                        if (pointData.getName().equalsIgnoreCase(strings[1])) {
                            pointData.setEnable(false);
                            deleteData.add(pointData);
                            this.plugin.getConfigManager().deletePoint(pointData);
                            player.sendMessage(cf("&a删除点" + strings[1] + "成功！"));
                        }
                    });
                    for (PointData datum : deleteData) {
                        this.plugin.getConfigManager().getPoints().remove(datum);
                    }
                    if (deleteData.size() == 0) {
                        player.sendMessage(cf("&c没有找到这个点！"));
                        return true;
                    }
                    return true;
                } else if (strings[0].equalsIgnoreCase("list")) {
                    List<PointData> list = new ArrayList<>(this.plugin.getConfigManager().getPoints());
                    if (list.size() == 0) {
                        player.sendMessage(cf("&c目前还没有创建任何一个点！"));
                        return true;
                    }
                    player.sendMessage(cf("&7&m-----------------------"));
                    for (PointData pointData : list) {
                        Location loc = pointData.getSpawn();
                        player.sendMessage(cf("&a - &e" + pointData.getName() + "&a 位置:&e x:" + loc.getBlockX() + ",y:" + loc.getBlockY() + ",z:" + loc.getBlockZ() + ",世界:" + loc.getWorld().getName()));
                    }
                    player.sendMessage(cf("&7&m-----------------------"));
                    return true;
                } else if (strings[0].equalsIgnoreCase("toggle")) {
                    if (strings.length == 1) {
                        player.sendMessage(cf("&c必须输入点名！例子/rs toggle 1"));
                        return true;
                    }
                    for (PointData point : this.plugin.getConfigManager().getPoints()) {
                        if (point.getName().equalsIgnoreCase(strings[1])) {
                            point.setEnable(!point.isEnable());
                            player.sendMessage(cf(point.isEnable() ? "&a资源点" + point.getName() + "已激活！" : "&c资源点" + point.getName() + "已停用！"));
                            return true;
                        }
                    }
                    player.sendMessage(cf("&c没有找到名为: &e" + strings[1] + " &c的点"));
                    return true;


                }
            }
        }
        player.sendMessage(cf("&7&m---------------------"));
        player.sendMessage(cf("&7 - &e/rs pointA   &a保存生产区域范围点A"));
        player.sendMessage(cf("&7 - &e/rs pointB   &a保存生产区域范围点B"));
        player.sendMessage(cf("&7 - &e/rs item   &a设置生产点产生的资源"));
        player.sendMessage(cf("&7 - &e/rs time <秒>  &a设置产生资源点的间隔"));
        player.sendMessage(cf("&7 - &e/rs spawn   &a设置产生资源的位置"));
        player.sendMessage(cf("&7 - &e/rs multi   &a设置是否根据人数生成对应数量资源"));
        player.sendMessage(cf("&7 - &e/rs delete <资源点名>   &a删除"));
        player.sendMessage(cf("&7 - &e/rs save <资源点名>   &a保存"));
        player.sendMessage(cf("&7 - &e/rs list   &a查看所有资源点列表"));
        player.sendMessage(cf("&7 - &e/rs toggle <点名>  &a切换资源点是否启用"));
        player.sendMessage(cf("&7&m---------------------"));


        return true;
    }
}
