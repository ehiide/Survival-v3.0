package mc.server.survival.events;

import mc.server.survival.libraries.chemistry.ChemistryCore;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class RegainHealth implements Listener
{
    @EventHandler
    public void onEvent(EntityRegainHealthEvent event)
    {
        if (event.getEntity() instanceof Player)
        {
            final Player player = (Player) event.getEntity();

            final double regeneration = ChemistryCore.ABILITY_PLAYER_REGENERATION_BOOST.get(player);
            final double health = (event.getAmount() * regeneration);

            event.setAmount(health);
        }
    }
}