package xyz.novaserver.fallback.listener;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.novaserver.fallback.Fallback;
import xyz.novaserver.novaenchants.enchantment.Enchantments;
import xyz.novaserver.novaenchants.util.EnchantmentUtils;

public class ItemListener implements Listener {

    private final Fallback plugin;

    public ItemListener(Fallback plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE) {
            return;
        }

        ItemStack novaBoots = new ItemStack(Material.DIAMOND_BOOTS);

        // Add boot enchantments
        EnchantmentUtils.addEnchant(novaBoots, Enchantments.DASHING.getEnchantment(), 1);
        EnchantmentUtils.addEnchant(novaBoots, Enchantments.LEAPING.getEnchantment(), 1);
        novaBoots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);

        // Set item meta
        ItemMeta itemMeta = novaBoots.getItemMeta();
        itemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Jump Boots");
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        novaBoots.setItemMeta(itemMeta);

        event.getPlayer().getInventory().setBoots(novaBoots);

        ItemMeta meta;

        // Give infinite arrow
        ItemStack arrow = new ItemStack(Material.ARROW);
        meta = arrow.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + "Infinite Arrow");
        arrow.setItemMeta(meta);
        event.getPlayer().getInventory().setItem(9, arrow);

        // Bow stuff
        ItemStack bow = new ItemStack(Material.BOW);
        bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
        bow.addEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
        meta = bow.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        bow.setItemMeta(meta);
        event.getPlayer().getInventory().setItem(0, bow);

        // Crossbow stuff
        ItemStack crossbow = new ItemStack(Material.CROSSBOW);
        crossbow.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
        crossbow.addUnsafeEnchantment(Enchantment.QUICK_CHARGE, 4);
        meta = crossbow.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        crossbow.setItemMeta(meta);
        event.getPlayer().getInventory().setItem(1, crossbow);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked().getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemDamage(PlayerItemDamageEvent event) {
        if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
        }
    }
}
