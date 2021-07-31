package xyz.novaserver.fallback;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.novaserver.fallback.command.JoinCommand;
import xyz.novaserver.fallback.listener.*;
import xyz.novaserver.fallback.player.FallbackPlayer;
import xyz.novaserver.fallback.task.StatusChecker;

import java.util.HashMap;

public class Fallback extends JavaPlugin {

    private final HashMap<Player, FallbackPlayer> playerMap = new HashMap<>();
    private boolean survivalOnline = false;
    private boolean queueFinished = true;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        getCommand("join").setExecutor(new JoinCommand(this));

        getServer().getPluginManager().registerEvents(new ResourcePackListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        getServer().getPluginManager().registerEvents(new ItemListener(this), this);
        getServer().getPluginManager().registerEvents(new BowTargetListener(this), this);

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getScheduler().runTaskTimerAsynchronously(this, new StatusChecker(this), 1000L, 1000L);
    }

    public HashMap<Player, FallbackPlayer> getPlayerMap() {
        return playerMap;
    }

    public boolean getSurvivalOnline() {
        return survivalOnline;
    }

    public void setSurvivalOnline(boolean survivalOnline) {
        this.survivalOnline = survivalOnline;
    }
}
