package xyz.novaserver.fallback.listener;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;
import xyz.novaserver.fallback.Fallback;
import xyz.novaserver.fallback.player.FallbackPlayer;

public class PlayerListener implements Listener {

    private final Fallback plugin;

    public PlayerListener(Fallback plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (player.getGameMode() != GameMode.CREATIVE) {
            player.getInventory().clear();
        }

        player.setHealth(20D);
        player.setFoodLevel(20);
        player.setLevel(0);
        player.setExp(0.0F);

        plugin.getPlayerMap().put(player, new FallbackPlayer());
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("message.join")));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        plugin.getPlayerMap().remove(event.getPlayer());
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager().getType() == EntityType.PLAYER
                && ((Player) event.getDamager()).getGameMode() != GameMode.CREATIVE) {
            if (event.getEntity().getType() == EntityType.ITEM_FRAME) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (event.getEntityType() == EntityType.PLAYER
                && ((Player)event.getEntity()).getGameMode() != GameMode.CREATIVE) {
            event.setDamage(0.0D);
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (event.getEntityType() == EntityType.PLAYER) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.getPlayer().getLocation().getY() < 0
                && event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            event.getPlayer().teleport(event.getPlayer().getWorld().getSpawnLocation());
        }
    }

    @EventHandler
    public void onPlayerGamemodeChange(PlayerGameModeChangeEvent event) {
        if (event.getNewGameMode() != GameMode.CREATIVE && event.getNewGameMode() != GameMode.ADVENTURE) {
            event.setCancelled(true);
            event.getPlayer().setGameMode(GameMode.ADVENTURE);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
        }
    }
}
