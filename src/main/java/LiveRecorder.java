import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class LiveRecorder extends JavaPlugin {

    private String recorderName = "Recorder";
    private int recordSeconds = 15;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getLogger().info("LiveRecorder 插件已启用！");
    }

    @Override
    public void onDisable() {
        getLogger().info("LiveRecorder 插件已禁用！");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.GREEN + "/live reload - 重载配置文件");
            sender.sendMessage(ChatColor.GREEN + "/live target <name> - 设置目标玩家为直播对象");
            sender.sendMessage(ChatColor.GREEN + "/live time <seconds> - 设置每个玩家被直播的时间");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "reload":
                reloadConfig();
                recorderName = getConfig().getString("recorder-name", "Recorder");
                recordSeconds = getConfig().getInt("record-seconds", 15);
                sender.sendMessage(ChatColor.GREEN + "配置文件已重载！");
                break;
            case "target":
                if (args.length < 2) {
                    sender.sendMessage(ChatColor.RED + "用法: /live target <name>");
                    return true;
                }
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    sender.sendMessage(ChatColor.RED + "目标玩家不存在！");
                    return true;
                }
                sender.sendMessage(ChatColor.GREEN + "已设置目标玩家为: " + target.getName());
                break;
            case "time":
                if (args.length < 2) {
                    sender.sendMessage(ChatColor.RED + "用法: /live time <seconds>");
                    return true;
                }
                try {
                    recordSeconds = Integer.parseInt(args[1]);
                    sender.sendMessage(ChatColor.GREEN + "每个玩家被直播的时间已设置为: " + recordSeconds + " 秒");
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + "请输入有效的数字！");
                }
                break;
            default:
                sender.sendMessage(ChatColor.RED + "未知命令！");
                break;
        }

        return true;
    }
}
