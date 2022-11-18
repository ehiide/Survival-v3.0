package mc.server.survival.events;

import mc.server.survival.libraries.MathLib;
import mc.server.survival.libraries.QuestLib;
import mc.server.survival.libraries.TimeLib;
import mc.server.survival.libraries.chemistry.ChemistryCore;
import mc.server.survival.managers.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class EntityInteract implements Listener
{
    @EventHandler(priority = EventPriority.MONITOR)
    public void onEvent(PlayerInteractEntityEvent event)
    {
        final Entity entity = event.getRightClicked();
        final Player player = event.getPlayer();
        final Location location = entity.getLocation();

        if (entity instanceof Player && DataManager.getInstance().getLocal(player).getMarry() != null && entity.equals(Bukkit.getPlayer(DataManager.getInstance().getLocal(player).getMarry())))
            if (entity.getName().equalsIgnoreCase(DataManager.getInstance().getLocal(player).getMarry()))
                if (location.distance(player.getLocation()) < 1.5)
                {
                    final int timexp = TimeLib.getDifferenceInMinutes(DataManager.getInstance().getLocal(player).getMarryDate()) / 6;
                    final int xp = DataManager.getInstance().getLocal(player).getMarryLevel() + DataManager.getInstance().getLocal(null).getMarryLevel(DataManager.getInstance().getLocal(player).getMarry()) + timexp;
                    final int level = xp / 100;

                    if (level >= 100)
                        QuestLib.manageQuest(player, 12);

                    player.getWorld().spawnParticle(Particle.HEART, location.add(0, 2, 0), 1, 1, 1, 1, 1);
                    if (MathLib.chanceOf(12))
                        DataManager.getInstance().getLocal(player).setMarryLevel(DataManager.getInstance().getLocal(player).getMarryLevel() + 1);
                }

        if (entity instanceof final Tameable tameable)
            if (!tameable.isTamed())
                if (MathLib.chanceOf(ChemistryCore.ABILITY_PLAYER_LUCK.get(player)))
                    new EntityTameEvent(tameable, player);
    }
}