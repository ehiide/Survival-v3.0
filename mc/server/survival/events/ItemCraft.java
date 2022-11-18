package mc.server.survival.events;

import mc.server.Logger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

public class ItemCraft implements Listener
{
    @EventHandler(priority = EventPriority.MONITOR)
    public void onEvent(CraftItemEvent event)
    {
        final Player player = (Player) event.getWhoClicked();
        final ItemStack result = event.getRecipe().getResult();

        Logger.getInstance().store(Logger.StoreType.CRAFT, player.getName() + " scraftowal " + result.getType() + "x" + result.getAmount());
    }
}