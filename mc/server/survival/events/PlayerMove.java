package mc.server.survival.events;

import mc.server.survival.libraries.TaskLib;
import mc.server.survival.managers.FileManager;
import mc.server.survival.worlds.WorldHandler;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class PlayerMove implements Listener
{
    private final boolean high_jump = (boolean) FileManager.getInstance().getConfigValue("function.aether.function.high-jump");
    private final boolean stonecutter_damage = (boolean) FileManager.getInstance().getConfigValue("function.stonecutter-damage");

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEvent(PlayerMoveEvent event)
    {
        final Player player = event.getPlayer();

        TaskLib.getInstance().runAsync(() ->
        {
            if (stonecutter_damage)
                if (player.getLocation().clone().add(0, -0.05, 0).getBlock().getType() == Material.STONECUTTER)
                    TaskLib.getInstance().runSync(() -> player.damage(0.5));

            if (player.getWorld().getName().equalsIgnoreCase("survival_aether"))
            {
                if (high_jump)
                {
                    if (0.42 - (event.getTo().getY() - event.getFrom().getY()) < 0.001)
                        if (0.42 - (event.getTo().getY() - event.getFrom().getY()) > 0)
                            TaskLib.getInstance().runSync(() -> player.setVelocity(new Vector(player.getVelocity().getX() * 1.75, 0.75, player.getVelocity().getZ() * 1.75)));

                    if (0.40 - (event.getTo().getY() - event.getFrom().getY()) < 0.001)
                        if (0.40 - (event.getTo().getY() - event.getFrom().getY()) > 0)
                            TaskLib.getInstance().runSync(() -> player.setVelocity(new Vector(player.getVelocity().getX() * 1.75, 0.75, player.getVelocity().getZ() * 1.75)));
                }

                if (event.getTo().getY() <= 70)
                {
                    TaskLib.getInstance().runSync(() ->
                    {
                        player.teleport(new Location(WorldHandler.getWorld("survival"), event.getTo().getX(), 320, event.getTo().getZ(), event.getTo().getYaw(), event.getTo().getPitch()));
                        WorldHandler.queueWorldChange(event.getPlayer());
                    });
                }
            }
        });
    }
}