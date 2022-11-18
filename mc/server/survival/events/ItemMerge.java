package mc.server.survival.events;

import mc.server.survival.managers.FileManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemMergeEvent;

import java.util.Objects;

public class ItemMerge implements Listener
{
    @EventHandler
    public void onEvent(ItemMergeEvent event)
    {
        if (!(boolean) FileManager.getInstance().getConfigValue("visual.entities.item-hologram"))
            return;

        Entity entity = event.getTarget();
        final int itemAmount = event.getEntity().getItemStack().getAmount() + event.getTarget().getItemStack().getAmount();

        if (!Objects.requireNonNull(event.getEntity().getItemStack().getItemMeta()).getDisplayName().equalsIgnoreCase(""))
            entity.setCustomName(ChatColor.translateAlternateColorCodes('&', "&a" + Objects.requireNonNull(event.getEntity().getItemStack().getItemMeta()).getDisplayName() + " &ex" + itemAmount));
        else
            if (event.getTarget().getName().length() > event.getTarget().getItemStack().getType().toString().length())
                entity.setCustomName(ChatColor.translateAlternateColorCodes('&', "&a" + event.getTarget().getName().substring(0, event.getTarget().getItemStack().getType().toString().length() + 2) + " &ex" + itemAmount));
            else
                entity.setCustomName(ChatColor.translateAlternateColorCodes('&', "&a" + event.getTarget().getName().substring(0, event.getTarget().getItemStack().getType().toString().length()) + " &ex" + itemAmount));
        entity.setCustomNameVisible(true);
    }
}