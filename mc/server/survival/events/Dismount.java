package mc.server.survival.events;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.spigotmc.event.entity.EntityDismountEvent;

public class Dismount implements Listener
{
    @EventHandler(priority = EventPriority.MONITOR)
    public void onEvent(EntityDismountEvent event)
    {
        if (event.getDismounted() instanceof ArmorStand && event.getEntity() instanceof final Player player)
            dismount(player, event.getDismounted());
    }

    public static void dismount(final Player player, final Entity entity)
    {
        if (entity instanceof ArmorStand)
        {
            PlayerInteract.setSitting(player, null);

            player.teleport(player.getLocation().add(0, 1, 0));
            entity.remove();
        }
    }
}