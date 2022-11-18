package mc.server.survival.events;

import mc.server.survival.libraries.MathLib;
import mc.server.survival.managers.DataManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Objects;

public class PlayerTeleport implements Listener
{
    @EventHandler
    public void onEvent(PlayerTeleportEvent event)
    {
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL)
        {
            event.setCancelled(true);

            if (!MathLib.chanceOf(20 * DataManager.getInstance().getLocal(event.getPlayer()).getUpgradeLevel(event.getPlayer().getName(), "honorable")))
                event.getPlayer().damage(4.0);

            event.getPlayer().teleport(Objects.requireNonNull(event.getTo()));
        }
    }
}