package mc.server.survival.events;

import mc.server.survival.libraries.ChatLib;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class PrepareAnvil implements Listener
{
    @EventHandler
    public void onEvent(PrepareAnvilEvent event)
    {
        final ItemStack itemStack = event.getResult();

        if (itemStack != null && itemStack.hasItemMeta() && !Objects.equals(event.getInventory().getRenameText(), ""))
        {
            final ItemMeta meta = itemStack.getItemMeta();
            assert meta != null;

            meta.setDisplayName(ChatLib.ColorUtil.formatHEX(Objects.requireNonNull(event.getInventory().getRenameText())));
            itemStack.setItemMeta(meta);
        }
    }
}