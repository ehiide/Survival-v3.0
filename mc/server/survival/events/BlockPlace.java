package mc.server.survival.events;

import mc.server.survival.worlds.WorldHandler;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class BlockPlace
implements Listener
{
    @EventHandler
    public void onEvent(BlockPlaceEvent event)
    {
        final Player player = event.getPlayer();
        final Material material = event.getBlockPlaced().getType();

        if (material == Material.PLAYER_HEAD || material == Material.PLAYER_WALL_HEAD)
        {
            final ItemStack itemStack = event.getItemInHand();

            event.setCancelled(!(itemStack.getEnchantments().isEmpty()));
        }

        if (WorldHandler.getInstance().getChunkHandler().shouldBeLeafHandled(event.getBlock()))
            if (!WorldHandler.getInstance().isEnd(player.getWorld()) && !WorldHandler.getInstance().isNether(player.getWorld()))
                WorldHandler.getInstance().getChunkHandler().handleLeafChunk(event.getBlock().getChunk());
    }
}