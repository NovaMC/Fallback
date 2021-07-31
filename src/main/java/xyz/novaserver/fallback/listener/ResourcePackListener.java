package xyz.novaserver.fallback.listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent.Status;
import xyz.novaserver.fallback.Fallback;
import xyz.novaserver.fallback.util.FallbackUtils;

public class ResourcePackListener implements Listener {

    private final Fallback plugin;

    public ResourcePackListener(Fallback plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onResourcePackStatus(PlayerResourcePackStatusEvent event) {
        Status status = event.getStatus();
        Player player = event.getPlayer();

        String message = "";
        int delay = -1;

        switch (status) {
            case DECLINED:
                message = plugin.getConfig().getString("message.resourcepack.declined");
                delay = plugin.getConfig().getInt("delay.declined");
                break;
            case ACCEPTED:
                message = plugin.getConfig().getString("message.resourcepack.accepted");
                delay = plugin.getConfig().getInt("delay.accepted");
                break;
            case FAILED_DOWNLOAD:
                message = plugin.getConfig().getString("message.resourcepack.failed");
                delay = plugin.getConfig().getInt("delay.failed");
                break;
            case SUCCESSFULLY_LOADED:
                message = plugin.getConfig().getString("message.resourcepack.loaded");
                delay = plugin.getConfig().getInt("delay.loaded");
                break;
        }

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));

        if (delay > -1 && plugin.getSurvivalOnline()) {
            plugin.getServer().getScheduler().runTaskLater(plugin,
                    () -> FallbackUtils.sendToServer(plugin, player, plugin.getConfig().getString("server.name")), delay * 20L);
        }
    }
}
