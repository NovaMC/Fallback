package xyz.novaserver.fallback.task;

import xyz.novaserver.fallback.Fallback;
import xyz.novaserver.fallback.lib.MineStat;

public class StatusChecker implements Runnable {

    private final Fallback plugin;
    private final MineStat status;

    public StatusChecker(Fallback plugin) {
        this.plugin = plugin;

        MineStat status = new MineStat(plugin.getConfig().getString("server.ip"), plugin.getConfig().getInt("server.port"));
        status.setTimeout(2);
        this.status = status;
    }

    @Override
    public void run() {
        status.refresh();

        if (status.isServerUp()) {
            // Delay prevents players from joining back during the queue
            plugin.getServer().getScheduler().runTaskLater(plugin,
                    () -> plugin.setSurvivalOnline(true), plugin.getServer().getOnlinePlayers().size() * 20L);
        }
        else {
            plugin.setSurvivalOnline(false);
        }
    }
}
