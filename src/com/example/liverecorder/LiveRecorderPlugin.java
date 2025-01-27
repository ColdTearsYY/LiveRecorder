package com.example.liverecorder;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class LiveRecorderPlugin extends JavaPlugin {

    private String recorderName = "Recorder";
    private int recordSeconds = 15;
    private int inactivityTimeout = 5;
    private int cameraPitch = 45;
    private int cameraDistance = 3;
    private String permissionCommand = "lp user {recorder} permission set {permission} true";
    private Player targetPlayer;
    private Player recorderPlayer;
    private long lastActivityTime;

    @Override
    public void onEnable() {
        // 插件启用时的初始化逻辑
        getCommand("live").setExecutor(new LiveCommandExecutor(this));
        startInactivityTimer();
    }

    @Override
    public void onDisable() {
        // 插件禁用时的清理逻辑
    }

    public void setTargetPlayer(Player player) {
        this.targetPlayer = player;
    }

    public void setRecorderPlayer(Player player) {
        this.recorderPlayer = player;
    }

    public void startLive() {
        if (recorderPlayer == null || targetPlayer == null) {
            getLogger().warning("直播员或目标玩家未设置！");
            return;
        }

        // 设置直播目标为指定玩家
        new BukkitRunnable() {
            @Override
            public void run() {
                // 每隔一段时间检查目标玩家
                if (targetPlayer.isOnline()) {
                    // 执行直播逻辑
                    // 例如切换镜头角度，给直播员权限等
                    Bukkit.broadcastMessage("直播开始啦， 全体做好上镜准备~");
                    Bukkit.broadcastMessage("在线人数：" + Bukkit.getOnlinePlayers().size());
                    // 设置时间
                    // 其他直播相关设置...
                }
            }
        }.runTaskTimer(this, 0, recordSeconds * 20L); // 每个玩家展示 recordSeconds 秒
    }

    public void resetLive() {
        // 重置直播目标
        Bukkit.broadcastMessage("重设成功， 将自动切换直播目标");
    }

    public void reloadConfig() {
        // 重新加载配置文件
        reloadConfig();
        saveConfig();
        Bukkit.broadcastMessage("配置信息重载成功");
    }

    private void startInactivityTimer() {
        new BukkitRunnable() {
            @Override
            public void run() {
                // 检查玩家是否活跃
                if (targetPlayer != null && System.currentTimeMillis() - lastActivityTime > inactivityTimeout * 1000) {
                    // 如果不活跃，切换目标
                    resetLive();
                }
            }
        }.runTaskTimer(this, 0, 20L); // 每秒钟检查一次
    }

    // 示例命令执行器
    public class LiveCommandExecutor implements CommandExecutor {

        private LiveRecorderPlugin plugin;

        public LiveCommandExecutor(LiveRecorderPlugin plugin) {
            this.plugin = plugin;
        }

        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (args.length == 0) {
                return false;
            }

            switch (args[0].toLowerCase()) {
                case "target":
                    if (args.length > 1) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (target != null) {
                            plugin.setTargetPlayer(target);
                            sender.sendMessage("已设置目标玩家为直播对象");
                        } else {
                            sender.sendMessage("目标玩家不存在");
                        }
                    }
                    break;
                case "time":
                    if (args.length > 1) {
                        try {
                            int time = Integer.parseInt(args[1]);
                            plugin.recordSeconds = time;
                            sender.sendMessage("每个玩家被直播的时间设置成功");
                        } catch (NumberFormatException e) {
                            sender.sendMessage("时间设置无效");
                        }
                    }
                    break;
                case "recorder":
                    if (args.length > 1) {
                        Player recorder = Bukkit.getPlayer(args[1]);
                        if (recorder != null) {
                            plugin.setRecorderPlayer(recorder);
                            sender.sendMessage("已设置目标玩家为直播录制者");
                        } else {
                            sender.sendMessage("目标玩家不存在");
                        }
                    }
                    break;
                case "reset":
                    plugin.resetLive();
                    break;
                case "reload":
                    plugin.reloadConfig();
                    break;
                default:
                    sender.sendMessage("无效命令");
                    break;
            }
            return true;
        }
    }
}
