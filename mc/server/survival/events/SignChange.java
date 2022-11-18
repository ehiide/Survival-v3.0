package mc.server.survival.events;

import mc.server.Logger;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignChange implements Listener
{
    @EventHandler(priority = EventPriority.MONITOR)
    public void onEvent(SignChangeEvent event)
    {
        final Player player = event.getPlayer();
        final Location location = event.getBlock().getLocation();
        final String[] lines = event.getLines();

        StringBuilder text = new StringBuilder();

        for (final String line : lines)
            text.append(" ").append(line);

        final String outputText = text.toString();
        final String outputLocation = "<" + location.getWorld().getName() + "/" + location.getBlockX() + "/" + location.getBlockY() + "/" + location.getBlockZ() + ">";

        Logger.getInstance().store(Logger.StoreType.SIGN, player.getName() + " dokonal zmiany tekstu na tabliczce" + outputLocation + " na '" + outputText + "'");
    }
}