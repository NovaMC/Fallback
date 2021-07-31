package xyz.novaserver.fallback.listener;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import xyz.novaserver.fallback.Fallback;
import xyz.novaserver.fallback.player.FallbackPlayer;

public class BowTargetListener implements Listener {

    private final Fallback plugin;

    private final float MAX_MULTIPLIER;
    private final int BASE_HIT_SCORE;

    public BowTargetListener(Fallback plugin) {
        this.plugin = plugin;
        this.MAX_MULTIPLIER = (float)plugin.getConfig().getDouble("bow.maxMultiplier");
        this.BASE_HIT_SCORE = plugin.getConfig().getInt("bow.baseScore");
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (!(event.getEntity().getShooter() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getEntity().getShooter();

        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            FallbackPlayer fallbackPlayer = plugin.getPlayerMap().get(player);

            if (event.getHitBlock() != null) {
                if (fallbackPlayer.isLastBlock(event.getHitBlock())) {
                    event.getEntity().remove();

                    float playerExp = player.getExp();

                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 0.5F);
                    player.setExp(0.0F);

                    plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 0.5F);
                        player.setExp(playerExp);
                    }, 2L);
                }
                else if (event.getHitBlock().isBlockFacePowered(event.getHitBlockFace())) {
                    event.getEntity().remove();
                    fallbackPlayer.setLastBlock(event.getHitBlock());

                    fallbackPlayer.setScore(fallbackPlayer.getScore() + BASE_HIT_SCORE * fallbackPlayer.getMultiplier());
                    player.setLevel(fallbackPlayer.getScore());

                    fallbackPlayer.setMultiplier(Math.min((int) MAX_MULTIPLIER, fallbackPlayer.getMultiplier() + 1));

                    if (fallbackPlayer.getMultiplier() == (int) MAX_MULTIPLIER) {
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5F, 1.25F);
                    }
                    else {
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F,
                                (fallbackPlayer.getMultiplier() / MAX_MULTIPLIER) / 2 + 0.75F);
                    }

                    player.setExp((fallbackPlayer.getMultiplier() - 1) / (MAX_MULTIPLIER - 1.0F));
                }
                else {
                    fallbackPlayer.setMultiplier(1);
                    player.setExp(0.0F);
                }
            }
        }, 1L);
    }
}
