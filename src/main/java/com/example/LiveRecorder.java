package com.example;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class LiveRecorder extends JavaPlugin {

    private String targetPlayer;
    private int liveTime = 15;
    private String recorderName = "Recorder";

    @Override
    public void onEnable() {
        getCommand("live").setExecutor(this::onCommand);
    }

    @Override
    public void onDisable() {
    }

    public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("使用 /live <target|time|recorder|reset|reload> [args]");
            return false;
        }

        switch (args[0].toLowerCase()) {
            case "target":
                if (args.length == 2) {
                    targetPlayer = args[1];
                    sender.sendMessage("已设置目标玩家为直播对象: " + targetPlayer);
                }
                break;
            case "time":
                if (args.length == 2) {
                    try {
                        liveTime = Integer.parseInt(args[1]);
                        sender.sendMessage("每个玩家被直播的时间设置成功: " + liveTime + "秒");
                    } catch (NumberFormatException e) {
                        sender.sendMessage("无效的时间参数");
                    }
                }
                break;
            case "recorder":
                if (args.length == 2) {
                    recorderName = args[1];
                    sender.sendMessage("已设置目标玩家为直播录制者: " + recorderName);
                }
                break;
            case "reset":
                targetPlayer = null;
                sender.sendMessage("重设直播时间，自动切换直播目标");
                break;
            case "reload":
                reloadConfig();
                sender.sendMessage("配置文件重载成功");
                break;
            default:
                return false;
        }

        return true;
    }

    private void reloadConfig() {
        // 重新加载配置文件的逻辑
    }

    // 用于处理玩家跟随和直播的逻辑
    public void startLive(Player player) {
        if (targetPlayer == null || !Bukkit.getPlayer(targetPlayer).isOnline()) {
            player.sendMessage("目标玩家不存在或不在线");
            return;
        }

        Player target = Bukkit.getPlayer(targetPlayer);
        // 实现自动跟随、切换镜头、以及其他直播功能
    }
}
