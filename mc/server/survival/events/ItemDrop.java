package mc.server.survival.events;

import mc.server.Logger;
import mc.server.survival.libraries.TaskLib;
import mc.server.survival.managers.FileManager;
import mc.server.survival.worlds.WorldHandler;
import mc.server.survival.worlds.twilight.Twilight;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import java.util.Objects;

public class ItemDrop implements Listener
{
    public void onEvent(PlayerDropItemEvent event)
    {
        if (!(boolean) FileManager.getInstance().getConfigValue("visual.entities.item-hologram"))
            return;

        Entity entity = event.getItemDrop();

        if (!Objects.requireNonNull(event.getItemDrop().getItemStack().getItemMeta()).getDisplayName().equalsIgnoreCase(""))
            entity.setCustomName(ChatColor.translateAlternateColorCodes('&', "&a" + Objects.requireNonNull(event.getItemDrop().getItemStack().getItemMeta()).getDisplayName() + " &ex" + event.getItemDrop().getItemStack().getAmount()));
        else
            entity.setCustomName(ChatColor.translateAlternateColorCodes('&', "&a" + event.getItemDrop().getName() + " &ex" + event.getItemDrop().getItemStack().getAmount()));
        entity.setCustomNameVisible(true);
    }

    @EventHandler
    public void onEvent(ItemSpawnEvent event)
    {
        final Item item = event.getEntity();

        if (item.getItemStack().getType() == Material.DIAMOND && item.getThrower() != null)
        {
            TaskLib.getInstance().runSyncLater(() -> {
                final Material inside = item.getLocation().getBlock().getType();

                if (inside == Material.WATER || inside == Material.SEAGRASS || inside == Material.TALL_SEAGRASS || inside == Material.SEA_PICKLE)
                {
                    if ((boolean) FileManager.getInstance().getConfigValue("function.twilight.status"))
                        if (Twilight.getInstance().getTwilightPortal().isPortal(item.getLocation()))
                        {
                            item.remove();

                            final Player player = Bukkit.getPlayer(item.getThrower());
                            final World world = WorldHandler.getWorld(player.getWorld().getName().equalsIgnoreCase("survival") ? "survival_twilight" : "survival");
                            final int x = player.getLocation().getBlockX();
                            final int z = player.getLocation().getBlockZ();
                            final int y = WorldHandler.getHighestBlockAt(world, x, z).getLocation().getBlockY();

                            player.teleport(new Location(world, x, y, z));
                            WorldHandler.queueWorldChange(player);

                            Logger.getInstance().store(Logger.StoreType.PORTAL, player.getName() + " uzyl portalu swiata twilight");
                        }}
            }, 20);
        }

        if (!(boolean) FileManager.getInstance().getConfigValue("visual.entities.item-hologram"))
            return;

        Entity entity = event.getEntity();

        if (!Objects.requireNonNull(event.getEntity().getItemStack().getItemMeta()).getDisplayName().equalsIgnoreCase(""))
            entity.setCustomName(ChatColor.translateAlternateColorCodes('&', "&a" + Objects.requireNonNull(event.getEntity().getItemStack().getItemMeta()).getDisplayName() + " &ex" + event.getEntity().getItemStack().getAmount()));
        else
            entity.setCustomName(ChatColor.translateAlternateColorCodes('&', "&a" + event.getEntity().getName() + " &ex" + event.getEntity().getItemStack().getAmount()));
        entity.setCustomNameVisible(true);
    }
}