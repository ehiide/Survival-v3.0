package mc.server.survival.events;

import mc.server.Broadcaster;
import mc.server.Logger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class Advancement implements Listener
{
    @EventHandler(priority = EventPriority.MONITOR)
    public void onEvent(PlayerAdvancementDoneEvent event)
    {
        if (Broadcaster.getInstance().getAnnouncer().announceAdvancements()) return;

        final Player player = event.getPlayer();

        if (!player.getAdvancementProgress(event.getAdvancement()).isDone()) return;

        final String advancement = event.getAdvancement().getKey().getKey();

        if (advancement.contains("recipes/")) return;

        final String advancementName = advancement.toUpperCase().replaceAll("_", " ");

        Broadcaster.broadcastMessage("#bfff00[⛏] #8c8c8cGracz " + player.getName() + " zdobyl osiagniecie: #bfff00" + advancementName);

        Logger.getInstance().store(Logger.StoreType.ADVANCEMENT, player.getName() + " zdobyl osiagniecie " + advancementName);
    }
}