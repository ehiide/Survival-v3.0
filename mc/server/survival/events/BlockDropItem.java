package mc.server.survival.events;

import mc.server.survival.managers.FileManager;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;

import java.util.List;

public class BlockDropItem
implements Listener
{
    private final boolean gravityExpander = (boolean) FileManager.getInstance().getConfigValue("function.gravity-expander");

    @EventHandler
    public void onEvent(BlockDropItemEvent event)
    {
        if (!gravityExpander) return;

        if (!event.getBlockState().getData().toItemStack(1).getType().toString().contains("CARPET") & hasCarpet(event.getItems(), "CARPET") ||
            !event.getBlockState().getData().toItemStack(1).getType().toString().contains("PRESSURE_PLATE") & hasCarpet(event.getItems(), "PRESSURE_PLATE") ||
            !event.getBlockState().getData().toItemStack(1).getType().toString().contains("RAIL") & hasCarpet(event.getItems(), "RAIL"))
            event.setCancelled(true);
    }

    private boolean hasCarpet(final List<Item> items, final String type)
    {
        for (final Item item : items)
            if (item.getItemStack().getType().toString().contains(type))
                return true; return false;
    }
}
