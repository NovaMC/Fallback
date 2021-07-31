package xyz.novaserver.fallback.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import xyz.novaserver.fallback.Fallback;
import xyz.novaserver.fallback.util.FallbackUtils;

import java.util.Collections;
import java.util.List;

public class JoinCommand implements TabExecutor {

    private final Fallback plugin;

    public JoinCommand(Fallback plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be used by players!");
            return true;
        }

        if (!plugin.getSurvivalOnline()) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("message.offline")));
        }
        else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("message.send")));

            FallbackUtils.sendToServer(plugin, (Player) sender, plugin.getConfig().getString("server.name"));
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Collections.emptyList();
    }
}
