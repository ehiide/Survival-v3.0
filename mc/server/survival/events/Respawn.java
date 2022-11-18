package mc.server.survival.events;

import mc.server.survival.libraries.ScoreboardLib;
import mc.server.survival.libraries.TaskLib;
import mc.server.survival.libraries.chemistry.ChemistryCore;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.jetbrains.annotations.NotNull;

public class Respawn
implements Listener
{
    @EventHandler(priority = EventPriority.MONITOR)
    public void onEvent(@NotNull PlayerRespawnEvent event)
    {
        TaskLib.getInstance().runSyncLater(() -> {
            final Player player = event.getPlayer();

            ScoreboardLib.getInstance().reloadContents(player);
            ChemistryCore.apply(player);
        }, 20);
    }
}